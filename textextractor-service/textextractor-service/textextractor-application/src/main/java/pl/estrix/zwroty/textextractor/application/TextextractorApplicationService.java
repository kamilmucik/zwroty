package pl.estrix.zwroty.textextractor.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.estrix.zwroty.textextractor.application.command.TextextractorCommandHandler;
import pl.estrix.zwroty.textextractor.application.command.dto.ExtractTextCommand;
import pl.estrix.zwroty.textextractor.application.query.ExtractedTextProjection;
import pl.estrix.zwroty.textextractor.application.query.GetExtractedTextQueryHandler;
import pl.estrix.zwroty.textextractor.common.domain.vo.ResponseId;

import java.util.UUID;

@Service
@Slf4j
public class TextextractorApplicationService {

    private final TextextractorCommandHandler textextractorCommandHandler;
    private final GetExtractedTextQueryHandler getExtractedTextQueryHandler;

    public TextextractorApplicationService(
            final TextextractorCommandHandler textextractorCommandHandler,
            final GetExtractedTextQueryHandler getExtractedTextQueryHandler) {
        this.textextractorCommandHandler = textextractorCommandHandler;
        this.getExtractedTextQueryHandler = getExtractedTextQueryHandler;
    }

    @Transactional
    public ResponseId createTextExtractRequest(ExtractTextCommand textextractorCommand) {
        return textextractorCommandHandler.extractText(textextractorCommand);
    }


    public ExtractedTextProjection getExtractedDetails(UUID requestId) {
        return getExtractedTextQueryHandler.getExtractedTextById(requestId);
    }

}
