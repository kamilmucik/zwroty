package pl.estrix.zwroty.textextractor.application.query;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.estrix.zwroty.textextractor.domain.port.TextExtractorRepository;

import java.util.UUID;

@Slf4j
@Component
public class GetExtractedTextQueryHandler {


    private final TextExtractorRepository textExtractorRepository;

    public GetExtractedTextQueryHandler(TextExtractorRepository textExtractorRepository) {
        this.textExtractorRepository = textExtractorRepository;
    }

    @Transactional(readOnly = true)
    public ExtractedTextProjection getExtractedTextById(UUID orderId) {
        return ExtractedTextProjection
                .builder()
                .responseId(orderId)
                .words(textExtractorRepository.getExtractedWords(orderId))
                .build();
    }
}
