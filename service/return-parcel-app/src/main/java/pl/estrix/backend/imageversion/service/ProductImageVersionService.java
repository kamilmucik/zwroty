package pl.estrix.backend.imageversion.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.estrix.backend.base.PagingCriteria;
import pl.estrix.backend.imageversion.dao.ProductImageVersionRevision;
import pl.estrix.backend.imageversion.executor.*;
import pl.estrix.backend.settings.executor.ReadSettingCommandExecutor;
import pl.estrix.common.base.ListResponseDto;
import pl.estrix.common.dto.*;
import pl.estrix.common.dto.model.*;
import pl.estrix.common.exception.CustomException;
import pl.estrix.common.util.CustomStringUtils;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductImageVersionService {


    private static final String PATH_IDENTIFIER = "%s/%s";
    private static final String PATH_DB_IDENTIFIER = "%s_%s/%s.jpg";
    private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd_HHmmss");

    @Autowired
    private ReadProductImageVersionCommandExecutor readExecutor;
    @Autowired
    private CreateProductImageVersionCommandExecutor createExecutor;
    @Autowired
    private UpdateProductImageVersionCommandExecutor updateExecutor;
    @Autowired
    private DeleteProductImageVersionCommandExecutor deleteExecutor;

    @Autowired
    private ReadProductImageVersionRevisionCommandExecutor readRevisionExecutor;
    @Autowired
    private CreateProductImageVersionRevisionCommandExecutor createRevisionExecutor;
    @Autowired
    private UpdateProductImageVersionRevisionCommandExecutor updateRevisionExecutor;
    @Autowired
    private DeleteProductImageVersionRevisionCommandExecutor deleteRevisionExecutor;

    @Autowired
    private ReadSettingCommandExecutor readSettingCommandExecutor;
    private String filePath;


    public ListResponseDto<ProductImageVersionDto> getItems(ProductImageVersionSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria){
        ListResponseDto<ProductImageVersionDto> listResponseDto =  readExecutor.find(searchCriteria,pagingCriteria);

        return listResponseDto;
    }

    public ListResponseDto<ProductImageVersionRevisionDto> getItems(ProductImageVersionRevisionSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria){
        return readRevisionExecutor.find(searchCriteria,pagingCriteria);
    }

    @Transactional
    public void delete (ProductImageVersionDto dto){
        deleteRevisionExecutor.deleteByVersionId(dto.getId());
        deleteExecutor.delete(dto);
    }
    @Transactional
    public void deleteRevision (ProductImageVersionRevisionDto dto){
        deleteRevisionExecutor.delete(dto);
    }

    @Transactional
    public ProductImageVersionDto getItem(Long id){
        return readExecutor.findById(id);
    }

    public ProductImageVersionDto getItem(String ean){
        ProductImageVersionDto selected = null;
        PagingCriteria pCriteria = PagingCriteria
                .builder()
                .start(0)
                .page(0)
                .limit(1)
                .build();
        ProductImageVersionSearchCriteriaDto searchCriteriaDto = ProductImageVersionSearchCriteriaDto
                .builder()
                .tableSearch(ean)
                .sortField("id")
                .sortOrder(SortOrder.DESCENDING)
                .build();

        ListResponseDto<ProductImageVersionDto> revisionDto = readExecutor.find(searchCriteriaDto, pCriteria);
        if (!revisionDto.isEmpty()){
            selected = revisionDto.getData().get(0);
        }
        return selected;
    }



//    @Transactional
    public ProductImageVersionRevisionDto getRevisionItem(Long id){
        return readRevisionExecutor.findById(id);
    }

    @Transactional
    public ProductImageVersionDto saveOrUpdate(ProductImageVersionDto dto){
        ProductImageVersionDto temp = null;
        if (dto.getId() != null){
            temp = updateExecutor.update(dto);
        } else {
            temp = createExecutor.create(dto);
        }
        return temp;
    }
    @Transactional
    public ProductImageVersionRevisionDto saveOrUpdate(ProductImageVersionRevisionDto dto){
        ProductImageVersionRevisionDto temp = null;
        if (dto.getId() != null){
            temp = updateRevisionExecutor.update(dto);
        } else {
            temp = createRevisionExecutor.create(dto);
        }
        return temp;
    }

//    @Transactional
    public void setMainImage(Long id){
        updateRevisionExecutor.setMainImage(id);
    }

    public CompletableFuture<RestProductImageVersionDto> findByEAN(String ean) {
        CompletableFuture<RestProductImageVersionDto> completableFuture = new CompletableFuture<>();
        Executors.newCachedThreadPool().submit(() -> {
            ListResponseDto<ProductImageVersionDto> revisionDto = getProductImageVersionDtoListResponseDto(ean);

            if (revisionDto.getData() == null) {
                completableFuture.cancel(false);
            } else {
                RestProductImageVersionDto results = new RestProductImageVersionDto(revisionDto.getData());
                completableFuture.complete(results);
            }
        });

        return completableFuture;
    }

    private ListResponseDto<ProductImageVersionDto> getProductImageVersionDtoListResponseDto(String ean) {
        PagingCriteria pCriteria = PagingCriteria
                .builder()
                .start(0)
                .page(0)
                .limit(5)
                .build();
        ProductImageVersionSearchCriteriaDto searchCriteriaDto = ProductImageVersionSearchCriteriaDto
                .builder()
                .tableSearch(ean)
                .shouldAddAllImages(true)
                .sortField("id")
                .sortOrder(SortOrder.ASCENDING)
                .build();

        ListResponseDto<ProductImageVersionDto> revisionDto = this.getItems(searchCriteriaDto, pCriteria);

        revisionDto.getData().stream().forEach(  image -> {
            ProductImageVersionRevisionSearchCriteriaDto searchCriteria = new ProductImageVersionRevisionSearchCriteriaDto();
            searchCriteria.setMainOnly(true);
            searchCriteria.setVersionId(image.getId());
//            searchCriteria
            PagingCriteria pagingCriteria = PagingCriteria
                    .builder()
                    .start(0)
                    .page(0)
                    .limit(15)
                    .build();
            ListResponseDto<ProductImageVersionRevisionDto> responseDto = this.getItems(searchCriteria, pagingCriteria);
            List<ProductImageVersionRevisionDto> baseList = responseDto
                    .getData()
                    .stream()
                    .map(this::mapWithBas64Img)
                    .collect(Collectors.toList());
            image.getRevisions().addAll(baseList);
        });

        return revisionDto;
    }

    private ProductImageVersionRevisionDto mapWithBas64Img(ProductImageVersionRevisionDto dto){
        return ProductImageVersionRevisionDto
                .builder()
                .id(dto.getId())
                .imgPath(Base64.getEncoder().encodeToString(dto.getImgPath().getBytes()))
                .main(dto.isMain())
                .description(dto.getDescription())
                .hashGroup(dto.getHashGroup())
                .build();
    }

    public RestProductImageVersionRevisionDto addVersion(ProductImageVersionRevisionDto dto) {
        SettingDto versionDirectorySettingDto = readSettingCommandExecutor.findByName("versionDirectory");
        if (versionDirectorySettingDto != null){
            filePath = versionDirectorySettingDto.getValue();
        }
        String path = String.format(PATH_DB_IDENTIFIER, dto.getArtNumber().trim(), dto.getEan().trim(),dateFormatter.format(new Date()));
        File file = new File(String.format(PATH_IDENTIFIER,filePath,path));
        try {
            FileUtils.writeByteArrayToFile(file, Base64.getDecoder().decode(dto.getImgBas64()));
        } catch (IOException e) {
            return RestProductImageVersionRevisionDto
                    .builder()
                    .message("Błąd dodawania obrazka")
                    .build();
        }

        dto.setLastUpdate(LocalDateTime.now());
        dto.setImgPath(path);
        dto.setMain(true);

        boolean changesDetected = false;
        if (!StringUtils.isEmpty(dto.getHashGroup())) {
            ProductImageVersionRevisionDto lastVersion = readRevisionExecutor.findByHash(dto.getHashGroup());

            String noHTML = CustomStringUtils.prepareStringToCompare(lastVersion.getDescription());

            changesDetected = CustomStringUtils.isTextIsSame(noHTML, dto.getDescription());

            dto.setDescription(
                    CustomStringUtils.markDifferencesInText(lastVersion.getDescription(), dto.getDescription())
                            .stream()
                            .collect(Collectors.joining(" "))
            );
        }

        dto.setHashGroup(dto.getHashGroup() != null? dto.getHashGroup() : UUID.randomUUID().toString());
        ProductImageVersionRevisionDto selected = mapWithBas64Img(createRevisionExecutor.create(dto));
        selected.setChangesDetected(changesDetected);
        return RestProductImageVersionRevisionDto
                    .builder()
                    .message("Dodano wersję produktu")
                    .dto(selected)
                    .build();
    }

    public RestProductImageVersionRevisionDto updateVersion(ProductImageVersionRevisionDto dto) {
        ProductImageVersionRevisionDto selected = readRevisionExecutor.findById(dto.getId());
        selected.setLastUpdate(LocalDateTime.now());
        selected.setVersionId(dto.getVersionId());
        selected.setDescription(dto.getDescription());
        saveOrUpdate(selected);

        return RestProductImageVersionRevisionDto
                .builder()
                .message("Dodano wersję produktu")
                .dto(selected)
                .build();
    }

    public void updateProductImageVersionRevisionDescription(Long id, String description){

        updateRevisionExecutor.updateDescription(id, description);
    }

    public InputStream getLastImage(String imageHash) throws IOException {
        SettingDto versionDirectorySettingDto = readSettingCommandExecutor.findByName("versionDirectory");
        if (versionDirectorySettingDto != null){
            filePath = versionDirectorySettingDto.getValue();
        }
        String path = new String(Base64.getDecoder().decode(imageHash));

        return Files.newInputStream(Paths.get(filePath,path));
    }

//    private boolean isTextIsSame(String oldText, String newText){
//        boolean isDifferent = false;
//        boolean isDifferent2 = newText.equals(oldText);
//
//        String[] words1Old = oldText.split(" ");
//        String[] words2New = newText.split(" ");
//
//        int maxLength = Math.max(words1Old.length, words2New.length);
//
//        for (int i = 0; i < maxLength; i++) {
//            String word1 = (i < words1Old.length) ? words1Old[i] : "";
//            String word2 = (i < words2New.length) ? words2New[i] : "";
//
//            if (!word1.equals(word2)) {
//                isDifferent = true;
//            }
//        }
//        return isDifferent;
//    }
//
//    public static List<String> markDifferencesInText(String oldText, String newText) {
//        List<String> differences = new ArrayList<>();
//
//        String[] words1Old = oldText.split(" ");
//        String[] words2New = newText.split(" ");
//
//        int maxLength = Math.max(words1Old.length, words2New.length);
//
//        for (int i = 0; i < maxLength; i++) {
//            String word1 = (i < words1Old.length) ? words1Old[i] : "";
//            String word2 = (i < words2New.length) ? words2New[i] : "";
//
//            if (!word1.equals(word2)) {
//                differences.add("<b style='color: red;'>" + word2 + "</b>");
//            } else {
//                differences.add(word2);
//            }
//        }
//
//        return differences;
//    }
}
