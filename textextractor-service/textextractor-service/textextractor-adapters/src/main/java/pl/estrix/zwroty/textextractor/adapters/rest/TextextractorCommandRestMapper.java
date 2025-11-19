package pl.estrix.zwroty.textextractor.adapters.rest;

import org.springframework.stereotype.Component;
import pl.estrix.zwroty.textextractor.adapters.rest.dto.ExtractTextRequest;
import pl.estrix.zwroty.textextractor.application.command.dto.ExtractTextCommand;

import java.time.Instant;

@Component
public class TextextractorCommandRestMapper {

    public ExtractTextCommand creteTextextractorRequestToCreateTextextractorCommand(ExtractTextRequest extractTextRequest) {
        return ExtractTextCommand.builder()
                .requestId(extractTextRequest.requestId())
                .base64Image(extractTextRequest.base64Img())
                .creationDate(Instant.now())
                .build();
    }
}
