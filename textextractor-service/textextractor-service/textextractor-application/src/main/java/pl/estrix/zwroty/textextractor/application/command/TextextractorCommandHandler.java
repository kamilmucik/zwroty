package pl.estrix.zwroty.textextractor.application.command;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.estrix.zwroty.textextractor.application.command.dto.ExtractTextCommand;
import pl.estrix.zwroty.textextractor.common.domain.vo.ResponseId;
import pl.estrix.zwroty.textextractor.domain.TextExtractorDomainService;
import pl.estrix.zwroty.textextractor.domain.port.TextExtractorRepository;

@Slf4j
@Component
public class TextextractorCommandHandler {


    private final TextExtractorDomainService textExtractorDomainService;
    private final TextExtractorRepository textExtractorRepository;

    public TextextractorCommandHandler(
            final TextExtractorDomainService textExtractorDomainService,
            final TextExtractorRepository textExtractorRepository
    ) {
        this.textExtractorDomainService = textExtractorDomainService;
        this.textExtractorRepository = textExtractorRepository;
    }

    @Transactional
    public ResponseId extractText(ExtractTextCommand textextractorCommand) {
        log.info("extractText: {}", textextractorCommand.requestId());

        var words = textExtractorDomainService.shouldExtractTextFromImageTest(textextractorCommand.base64Image());
        textExtractorRepository.save(textextractorCommand.requestId(), words);

        return new ResponseId(textextractorCommand.requestId());
    }

}
