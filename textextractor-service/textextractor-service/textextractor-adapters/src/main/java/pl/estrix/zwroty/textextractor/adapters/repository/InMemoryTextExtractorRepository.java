package pl.estrix.zwroty.textextractor.adapters.repository;

import org.springframework.stereotype.Component;
import pl.estrix.zwroty.textextractor.domain.port.TextExtractorRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class InMemoryTextExtractorRepository implements TextExtractorRepository {

    private final Map<UUID, List<String>> store = new HashMap<>();

    @Override
    public void save(UUID requestId, List<String> words) {
        store.put(requestId, words);
    }

    @Override
    public List<String> getExtractedWords(UUID requestId) {
        return store.get(requestId);
    }
}
