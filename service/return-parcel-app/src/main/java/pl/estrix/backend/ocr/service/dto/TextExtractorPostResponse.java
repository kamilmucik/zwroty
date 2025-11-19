package pl.estrix.backend.ocr.service.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
public class TextExtractorPostResponse implements Serializable {

    private String requestId;
}
