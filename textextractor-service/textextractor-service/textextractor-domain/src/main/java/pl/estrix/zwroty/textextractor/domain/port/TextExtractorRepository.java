package pl.estrix.zwroty.textextractor.domain.port;

import java.util.List;
import java.util.UUID;

public interface TextExtractorRepository {

    void save(UUID requestId, List<String> words);

    List<String> getExtractedWords(UUID requestId);
}
