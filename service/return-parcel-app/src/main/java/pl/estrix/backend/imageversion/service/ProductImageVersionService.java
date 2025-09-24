package pl.estrix.backend.imageversion.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jfree.util.Log;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import pl.estrix.restapi.PrintLabelRestController;

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


    private static Logger LOG = LoggerFactory.getLogger(ProductImageVersionService.class);


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

    @Getter
    private Map<String, List<ProductImageVersionRevisionDto>> concatenateMap = new HashMap<>();

    public List getConcatenateList(String ean){
        return concatenateMap.get(ean);
    }

    public ProductImageVersionRevisionDto addToConcatenateList(ProductImageVersionRevisionDto imageVersion){
        List tmpList = concatenateMap.getOrDefault(imageVersion.getEan(), new ArrayList<>());

        imageVersion.setLastUpdate(LocalDateTime.now());
        imageVersion.setId(tmpList.size() + 1L);
        tmpList.add(imageVersion);
        concatenateMap.put(imageVersion.getEan(), tmpList);

        //Todo proba łączenia
        return imageVersion;
    }

    public void clearList(String ean){
        concatenateMap.getOrDefault(ean, new ArrayList<>()).clear();
    }


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


    public void setMainImage(Long id){
        updateRevisionExecutor.setMainImage(id);
    }

    public RestProductImageVersionDto findByEAN(String ean) {
        ListResponseDto<ProductImageVersionDto> revisionDto = getProductImageVersionDtoListResponseDto(ean);
        return new RestProductImageVersionDto(revisionDto.getData());
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
                    .sorted(
                            Comparator.comparing(ProductImageVersionRevisionDto::getOrderTimestamp).reversed()
                                    .thenComparing(Comparator.comparing(ProductImageVersionRevisionDto::getId).reversed())
                    )
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
                .orderTimestamp(dto.getOrderTimestamp())
                .build();
    }

    public RestProductImageVersionRevisionDto deleteVersion(ProductImageVersionRevisionDto dto) {
        SettingDto versionDirectorySettingDto = readSettingCommandExecutor.findByName("versionDirectory");
        if (versionDirectorySettingDto != null){
            filePath = versionDirectorySettingDto.getValue();
        }

        try {
            ProductImageVersionRevisionDto dtoToDelete = readRevisionExecutor.findByHash(dto.getHashGroup());
            LOG.debug("deleteVersion[{}]: {}", dto.getHashGroup(), dtoToDelete.getId());
            File file = new File(String.format(PATH_IDENTIFIER,filePath,readRevisionExecutor.findById(dtoToDelete.getId()).getImgPath()));
            Files.deleteIfExists(file.toPath());
            deleteRevisionExecutor.delete(dtoToDelete);
            ProductImageVersionRevisionDto mainCandidate = readRevisionExecutor.findByHash(dto.getHashGroup());

            if (mainCandidate != null){
                LOG.debug("mainCandidate[{}]: {}", dto.getHashGroup(), mainCandidate.getId());
                updateRevisionExecutor.setMainImage(mainCandidate.getId());
            }
        } catch (IOException e) {
            return RestProductImageVersionRevisionDto
                    .builder()
                    .message("Błąd usuwania obrazka")
                    .dto(ProductImageVersionRevisionDto.builder().orderTimestamp(new Date().getTime()).build())
                    .build();
        }
        return RestProductImageVersionRevisionDto
                .builder()
                .message("Usuwam wersję produktu")
                .dto(ProductImageVersionRevisionDto.builder().orderTimestamp(new Date().getTime()).build())
                .build();
    }

    public RestProductImageVersionRevisionDto changeOrder(ProductImageVersionRevisionDto dto) {
        updateRevisionExecutor.setTopOrder(dto.getHashGroup());
        return RestProductImageVersionRevisionDto
                .builder()
                .message("Zmiana kolejności")
                .dto(ProductImageVersionRevisionDto.builder().orderTimestamp(new Date().getTime()).build())
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
        dto.setOrderTimestamp(new Date().getTime());
        dto.setImgPath(path);
        dto.setMain(true);

        boolean changesDetected = false;
        dto.setHashGroup(dto.getHashGroup() != null? dto.getHashGroup() : UUID.randomUUID().toString());
        ProductImageVersionRevisionDto selected = mapWithBas64Img(createRevisionExecutor.create(dto));
        selected.setChangesDetected(changesDetected);


        if (!StringUtils.isEmpty(dto.getHashGroup())) {
            ProductImageVersionRevisionDto lastVersion = readRevisionExecutor.findByHash(dto.getHashGroup());

//            String prevNoHTML = CustomStringUtils.prepareStringToCompare(lastVersion.getDescription());
            String currNoHTML = CustomStringUtils.prepareStringToCompare(dto.getDescription());
//            String currDicValidatio = CustomStringUtils.checkWordsInDictionary(dto.getDescription(), true);

//            changesDetected = CustomStringUtils.isTextIsSame(prevNoHTML, currNoHTML);

            dto.setDescription(currNoHTML);
            selected.setDescription(currNoHTML);
//            dto.setDescription(
//                    CustomStringUtils.markDifferencesInText(lastVersion.getDescription(), dto.getDescription())
//                            .stream()
//                            .collect(Collectors.joining(" "))
//            );
        }



        //mergowanie obrazkow
//        if (dto.isMerge()){
//            List tmpList = concatenateMap.getOrDefault(dto.getEan(), new ArrayList<>());
//
//            dto.setLastUpdate(LocalDateTime.now());
//            dto.setId(tmpList.size() + 1L);
//            tmpList.add(dto);
//            concatenateMap.put(dto.getEan(), tmpList);
//        }


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

}
