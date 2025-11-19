package pl.estrix.zwroty.textextractor.application.command.dto;

import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record ExtractTextCommand(
        String base64Image,
        UUID requestId,
        Instant creationDate
) {
}
