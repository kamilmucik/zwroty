package pl.estrix.zwroty.textextractor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(scanBasePackages = "pl.estrix.zwroty.textextractor")
public class TextextractorServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TextextractorServiceApplication.class, args);
    }
}
