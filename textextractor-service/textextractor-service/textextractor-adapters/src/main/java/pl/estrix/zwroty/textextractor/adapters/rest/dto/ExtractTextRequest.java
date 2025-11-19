package pl.estrix.zwroty.textextractor.adapters.rest.dto;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record ExtractTextRequest(
        @NotNull UUID requestId,
        @NotNull String base64Img) {
}
