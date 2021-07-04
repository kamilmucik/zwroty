package pl.estrix.backend.event.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.estrix.backend.base.PagingCriteria;
import pl.estrix.backend.event.executor.CreateShipmentEventCommandExecutor;
import pl.estrix.backend.event.executor.ReadShipmentEventCommandExecutor;
import pl.estrix.common.base.ListResponseDto;
import pl.estrix.common.dto.ShipmentEventDto;
import pl.estrix.common.dto.ShipmentEventSearchCriteriaDto;

import javax.transaction.Transactional;

@Service
public class ShipmentEventService {


    @Autowired
    private ReadShipmentEventCommandExecutor readShipmentExecutor;
    @Autowired
    private CreateShipmentEventCommandExecutor createShipmentExecutor;

    @Transactional
    public ListResponseDto<ShipmentEventDto> getItems(ShipmentEventSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria){
        ListResponseDto<ShipmentEventDto> listResponseDto = readShipmentExecutor.find(searchCriteria,pagingCriteria);

//        listResponseDto.getData().stream().forEach( o -> {
//            o.setProductCounter(readShipmentProductExecutor.countByShipmentId(o.getId()));
//        });

        return listResponseDto;
    }

    @Transactional
    public ShipmentEventDto saveOrUpdate(ShipmentEventDto dto){
        ShipmentEventDto temp = null;
        if (dto.getId() != null){
//            System.out.println(": " + storeDto.getId());
//            System.out.println(": " + storeDto.getGroup());
//            temp = createShipmentExecutor.update(storeDto);
        } else {
            temp = createShipmentExecutor.create(dto);
        }
        return temp;
    }
}
