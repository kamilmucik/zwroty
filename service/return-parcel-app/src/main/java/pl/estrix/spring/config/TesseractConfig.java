package pl.estrix.spring.config;

import net.sourceforge.tess4j.Tesseract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TesseractConfig {


    @Bean
    Tesseract tesseract() {
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("/usr/share/tesseract-ocr/4.00/tessdata"); //files of the example : https://github.com/tesseract-ocr/tessdata
        tesseract.setLanguage("pol");
        return tesseract;
    }
}
