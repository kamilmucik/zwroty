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
        //windows after installation
//        tesseract.setDatapath("c:\\Program Files\\Tesseract-OCR\\tessdata");
        tesseract.setDatapath("c:\\Program Files\\Tesseract-OCR\\tessdata");
    }

    @Test
    public void testOCRService() throws Exception {
        BufferedImage image = ImageIO.read(Paths.get("C:\\workspace\\zwroty\\service\\tests\\ocr\\IMG_0205.jpg").toFile());

       String imageText = null;
        try {
            imageText = tesseract.doOCR(image);
        } catch (TesseractException e) {
            e.printStackTrace();
            // handle error
        }

        System.out.println(imageText);

        assertNotNull(imageText);
    }
}
