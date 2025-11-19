package pl.estrix.zwroty.textextractor.adapters.rest.dto;

import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record GetExtractedTextResponse(
        UUID responseId,
        String status,
        List<String> words) {
}
