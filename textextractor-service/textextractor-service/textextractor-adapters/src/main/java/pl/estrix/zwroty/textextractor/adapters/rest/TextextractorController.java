package pl.estrix.zwroty.textextractor.adapters.rest;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.estrix.zwroty.textextractor.adapters.rest.dto.ExtractTextRequest;
import pl.estrix.zwroty.textextractor.adapters.rest.dto.ExtractTextResponse;
import pl.estrix.zwroty.textextractor.adapters.rest.dto.GetExtractedTextResponse;
import pl.estrix.zwroty.textextractor.application.TextextractorApplicationService;


import java.util.UUID;

@RestController
@RequestMapping("/extract")
public class TextextractorController {

    private final TextextractorApplicationService textextractorApplicationService;
    private final TextextractorCommandRestMapper textextractorCommandRestMapper;
    private final TextextractorQueryRestMapper textextractorQueryRestMapper;

    public TextextractorController(final TextextractorApplicationService textextractorApplicationService,
                                   final TextextractorCommandRestMapper textextractorCommandRestMapper,
                                   final TextextractorQueryRestMapper textextractorQueryRestMapper) {
        this.textextractorApplicationService = textextractorApplicationService;
        this.textextractorCommandRestMapper = textextractorCommandRestMapper;
        this.textextractorQueryRestMapper = textextractorQueryRestMapper;
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<GetExtractedTextResponse> getExtractedTextRequest(@PathVariable UUID requestId) {
        return ResponseEntity.ok(textextractorQueryRestMapper.extractedToGetExtractedResponse(textextractorApplicationService.getExtractedDetails(requestId)));
    }

    @PostMapping("/")
    public ResponseEntity<ExtractTextResponse> createRequest(@RequestBody @Valid ExtractTextRequest extractTextRequest) {
        return ResponseEntity.ok(new ExtractTextResponse(
                    textextractorApplicationService.createTextExtractRequest(
                                textextractorCommandRestMapper.creteTextextractorRequestToCreateTextextractorCommand(extractTextRequest)
                            )
                        .id()
                    )
                );
    }
}
