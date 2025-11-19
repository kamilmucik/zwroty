package pl.estrix.backend.ocr.service.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Builder
@Data
public class GetTextExtractorResultRequest implements Serializable {

    private String responseId;
    private List<String> words;
}
