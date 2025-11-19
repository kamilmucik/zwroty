package pl.estrix.backend.ocr.service.dto;

import lombok.Builder;

import java.io.Serializable;

@Builder
public class TextExtractorPostRequest implements Serializable {

    private String requestId;
    private String base64Img;

}
