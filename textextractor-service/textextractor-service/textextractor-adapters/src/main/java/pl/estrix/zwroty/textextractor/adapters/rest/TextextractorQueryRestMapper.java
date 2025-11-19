package pl.estrix.zwroty.textextractor.adapters.rest;

import org.springframework.stereotype.Component;
import pl.estrix.zwroty.textextractor.adapters.rest.dto.GetExtractedTextResponse;
import pl.estrix.zwroty.textextractor.application.query.ExtractedTextProjection;

@Component
public class TextextractorQueryRestMapper {

    public GetExtractedTextResponse extractedToGetExtractedResponse(ExtractedTextProjection projection) {
        return GetExtractedTextResponse.builder()
                .responseId(projection.responseId())
                .words(projection.words())
                .build();
    }
}
