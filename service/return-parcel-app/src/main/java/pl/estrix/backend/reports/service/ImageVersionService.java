package pl.estrix.backend.reports.service;

import lombok.extern.slf4j.Slf4j;
//import net.lingala.zip4j.ZipFile;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import pl.estrix.backend.imageversion.service.ProductImageVersionService;
import pl.estrix.backend.settings.service.SettingService;
import pl.estrix.common.dto.model.ProductImageVersionDto;
import pl.estrix.common.dto.model.ProductImageVersionRevisionDto;
import pl.estrix.common.dto.model.SettingsDto;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.castor.mapping.AbstractMappingLoaderFactory.LOG;

@Service
public class ImageVersionService {

    private static final Logger LOG = LoggerFactory.getLogger(ImageVersionService.class);

    private static final SimpleDateFormat dt2 = new SimpleDateFormat("yyyy-MM-dd");

    private static final String TEMP_DIR = "java.io.tmpdir";

    public static final String SEPARATOR = System.getProperty("file.separator");

    public final static String ZIP_DIR = System.getProperty(TEMP_DIR) + SEPARATOR + "wersje" + SEPARATOR;
    public static final String OUTPUT_ZIP = System.getProperty(TEMP_DIR) + SEPARATOR + "wersje.zip";

    public static Predicate<String> hasLengthOf10 = new Predicate<String>() {
        @Override
        public boolean test(String t)
        {
            return t.length() > 10;
        }
    };

    @Autowired
    private SettingService settingService;

    @Autowired
    private ProductImageVersionService service;

    public void updateDBFromFolder() throws IOException {
        List<ProductImageVersionDto> products = new ArrayList<>();
        SettingsDto selected = settingService.getSetting();
        Path rootPath = Paths.get(selected.getVersionDirectory());
        listFilesUsingDirectoryStream( rootPath )
                .forEach( file ->{
                    String filePath = file.toString().replace(rootPath.toString(),"")
                            .replace("/", "")
                            .replace("\\", "");
                    if (StringUtils.isNotEmpty(filePath)) {
                        ProductImageVersionDto productImageVersion = prepareRecord(filePath);
                        products.add(service.saveOrUpdate(productImageVersion));
                        ;
                    }
        });

//        products.forEach( product -> {
//            LOG.debug("Product: {}", product);
//            try {
//                List<Path> images = listFilesUsingDirectoryStream( Paths.get(rootPath.toString(), product.getPath()) )
//                        .stream()
//                        .filter(file -> !Files.isDirectory(file))
//                        .collect(Collectors.toList());
//
//                ProductImageVersionRevisionDto productImage = new ProductImageVersionRevisionDto();
//                productImage.setReason("Product");
//                productImage.setVersionImageId(product.getId());
//                productImage.setImgFrontPath(getImageIfExistInList(images,0));
//                productImage.setImgBackPath(getImageIfExistInList(images,1));
//                productImage.setImgLeftPath(getImageIfExistInList(images,2));
//                productImage.setImgRightPath(getImageIfExistInList(images,3));
//                productImage.setImgTopPath(getImageIfExistInList(images,4));
//                productImage.setImgBottomPath(getImageIfExistInList(images,5));
//                service.saveOrUpdate(productImage);
//
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        });
    }

    private String getImageIfExistInList(List<Path> images, Integer index){
        if(index!=null && index<images.size()){
            return images.get(index).toString();
        }
        return null;
    }

    private ProductImageVersionDto prepareRecord(String path){
        ProductImageVersionDto productImageVersion = new ProductImageVersionDto();
        String[] parts = path.split(" ");

//        productImageVersion.setPath(path);
//        productImageVersion.setLastVersionDate(dt2.format(new Date()));
        productImageVersion.setArtNumber(Long.parseLong(parts[0]));
        productImageVersion.setTitle("");
        productImageVersion.setEan(parts[1]);
        return productImageVersion;
    }

    public List<Path> listFilesUsingDirectoryStream(Path dir) throws IOException {
        try (Stream<Path> stream = Files.walk(dir, 1)) {
             return stream
                    .collect(Collectors.toList());
        }
    }

    public File prepareImageVersions(String fileName, List<ProductImageVersionDto> data)throws IOException {
        Files.deleteIfExists(Paths.get(OUTPUT_ZIP));

        try {
            FileUtils.deleteDirectory(Paths.get(ZIP_DIR).toFile());
        } catch (IOException e) {
//                throw new RuntimeException(e);
        }
//        new ZipFile(OUTPUT_ZIP).addFolder(new File(ZIP_DIR));

        return Paths.get(OUTPUT_ZIP).toFile();
    }

    private void addFile(byte[] content, String tmpPath, String fileName){
        try {
            byte[] decodedImg = Base64.getDecoder().decode(content);
            Path destinationFile = Paths.get(tmpPath, fileName);
            Files.write(destinationFile, decodedImg);
        } catch (IOException | IllegalArgumentException e) {
//            throw new RuntimeException(e);
        }
    }

}
