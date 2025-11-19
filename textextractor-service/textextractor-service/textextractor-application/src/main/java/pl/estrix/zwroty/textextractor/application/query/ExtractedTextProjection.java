package pl.estrix.zwroty.textextractor.application.query;

import lombok.Builder;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Builder
public record ExtractedTextProjection(
        UUID responseId,
        Instant creationDate,
        List<String> words,
        List<String> failureMessages
) {
}
