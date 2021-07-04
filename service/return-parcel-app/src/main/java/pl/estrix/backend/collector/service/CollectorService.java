package pl.estrix.backend.collector.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.estrix.backend.collector.executor.CreateCollectorCommandExecutor;
import pl.estrix.backend.collector.executor.DeleteCollectorCommandExecutor;
import pl.estrix.backend.collector.executor.ReadCollectorCommandExecutor;
import pl.estrix.backend.collector.executor.UpdateCollectorCommandExecutor;
import pl.estrix.common.base.ListResponseDto;
import pl.estrix.common.dto.CollectorSearchCriteriaDto;
import pl.estrix.common.dto.GetCollectorDetailsDto;
import pl.estrix.common.dto.model.CollectorDto;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

@Service
public class CollectorService {

    @Autowired
    private ReadCollectorCommandExecutor readExecutor;
    @Autowired
    private CreateCollectorCommandExecutor createExecutor;
    @Autowired
    private UpdateCollectorCommandExecutor updateExecutor;
    @Autowired
    private DeleteCollectorCommandExecutor deleteExecutor;


    public CompletableFuture<GetCollectorDetailsDto> saveOrUpdate(String collectorNumber) {
        CompletableFuture<GetCollectorDetailsDto> completableFuture = new CompletableFuture<>();

        Executors.newCachedThreadPool().submit(() -> {
            GetCollectorDetailsDto collectorDetailsDto = new GetCollectorDetailsDto();
            CollectorSearchCriteriaDto searchCriteriaDto = CollectorSearchCriteriaDto
                    .builder()
                    .tableSearch(collectorNumber)
                    .build();
            ListResponseDto<CollectorDto> shipmentDetailsDto = readExecutor.find(searchCriteriaDto,null);

            String sessionId = UUID.randomUUID().toString();
            Optional<CollectorDto> collector = shipmentDetailsDto.getData().stream().findFirst();
            if (collector.isPresent()){
                CollectorDto collectorDto = collector.get();
                collectorDto.setSessionId(sessionId);
                updateExecutor.update(collectorDto);

                collectorDetailsDto.setCollector(collectorDto);
            } else {
                CollectorDto collectorDto = CollectorDto
                        .builder()
                        .number(collectorNumber)
                        .sessionId(sessionId)
                        .build();
                collectorDto = createExecutor.create(collectorDto);
                if( collectorDto.getNumber().isEmpty()) {
                    collectorDto.setNumber(collectorDto.getId().toString());
                }
                collectorDetailsDto.setCollector(collectorDto);
            }

            completableFuture.complete(collectorDetailsDto);
            return null;
        });
        return completableFuture;
    }


}
