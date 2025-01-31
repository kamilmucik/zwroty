package pl.estrix.backend.imageversion.service;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.nio.file.Paths;

import static org.junit.Assert.assertNotNull;

public class TesseractOCRServiceTest {

    static Tesseract tesseract;
    @BeforeClass
    public static void setUp() throws Exception {
        tesseract = new Tesseract();
        tesseract.setDatapath("/opt/homebrew/Cellar/tesseract/5.5.0/lib");
    }

    @Test
    public void testOCRService() throws Exception {

        tesseract = new Tesseract();
        tesseract.setDatapath("/opt/homebrew/Cellar/tesseract/5.5.0/lib");
        BufferedImage image = ImageIO.read(Paths.get("/Users/kamilmuc/ws/test/20230425/zwroty/service/tests/ocr/IMG_0205.jpg").toFile());

       String imageText = null;
        try {
            imageText = tesseract.doOCR(image);
        } catch (TesseractException e) {
            e.printStackTrace();
            // handle error
        }

        assertNotNull(imageText);
    }
}
