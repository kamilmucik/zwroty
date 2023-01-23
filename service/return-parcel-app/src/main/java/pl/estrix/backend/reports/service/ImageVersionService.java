package pl.estrix.backend.reports.service;

import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.ZipFile;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import pl.estrix.common.dto.model.ProductImageVersionDto;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;

@Slf4j
@Service
public class ImageVersionService {

    private static final String TEMP_DIR = "java.io.tmpdir";

    public static final String SEPARATOR = System.getProperty("file.separator");

    public final static String ZIP_DIR = System.getProperty(TEMP_DIR) + SEPARATOR + "wersje" + SEPARATOR;
    public static final String OUTPUT_ZIP = System.getProperty(TEMP_DIR) + SEPARATOR + "wersje.zip";

    public File prepareImageVersions(String fileName, List<ProductImageVersionDto> data)throws IOException {
        Files.deleteIfExists(Paths.get(OUTPUT_ZIP));

        try {
            FileUtils.deleteDirectory(Paths.get(ZIP_DIR).toFile());
        } catch (IOException e) {
//                throw new RuntimeException(e);
        }
        data.forEach( obj ->{
            String tmpPath = ZIP_DIR + obj.getArtNumber() + SEPARATOR + obj.getEan()+SEPARATOR+obj.getLastUpdate();

            try {
                Files.createDirectories(Paths.get(tmpPath));
                if (StringUtils.isNotEmpty(obj.getImgFrontBase64())) {
                    addFile(obj.getImgFrontBase64().getBytes(StandardCharsets.UTF_8),tmpPath,"przod.jpg");
                }

                if (StringUtils.isNotEmpty(obj.getImgBackBase64())) {
                    addFile(obj.getImgBackBase64().getBytes(StandardCharsets.UTF_8),tmpPath,"tyl.jpg");
                }
                if (StringUtils.isNotEmpty(obj.getImgTopBase64())) {
                    addFile(obj.getImgTopBase64().getBytes(StandardCharsets.UTF_8),tmpPath,"gora.jpg");
                }
                if (StringUtils.isNotEmpty(obj.getImgBottomBase64())) {
                    addFile(obj.getImgBottomBase64().getBytes(StandardCharsets.UTF_8),tmpPath,"dol.jpg");
                }
                if (StringUtils.isNotEmpty(obj.getImgLeftBase64())) {
                    addFile(obj.getImgLeftBase64().getBytes(StandardCharsets.UTF_8),tmpPath,"lewy.jpg");
                }
                if (StringUtils.isNotEmpty(obj.getImgRightBase64())) {
                    addFile(obj.getImgRightBase64().getBytes(StandardCharsets.UTF_8),tmpPath,"prawy.jpg");
                }
                if (obj.getReason() != null) {
                    Path destinationFile = Paths.get(tmpPath, "opis.txt");
                    Files.write(destinationFile, obj.getReason().getBytes(StandardCharsets.UTF_8));
                }
            } catch (IOException e) {
//                throw new RuntimeException(e);
            }
        });
        new ZipFile(OUTPUT_ZIP).addFolder(new File(ZIP_DIR));

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
