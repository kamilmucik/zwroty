package pl.estrix.backend.store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.estrix.backend.base.PagingCriteria;
import pl.estrix.backend.store.executor.CreateStoreCommandExecutor;
import pl.estrix.backend.store.executor.DeleteStoreCommandExecutor;
import pl.estrix.backend.store.executor.ReadStoreCommandExecutor;
import pl.estrix.backend.store.executor.UpdateStoreCommandExecutor;
import pl.estrix.common.base.ListResponseDto;
import pl.estrix.common.dto.model.StoreDto;
import pl.estrix.common.dto.StoreSearchCriteriaDto;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StoreService {

    @Autowired
    private ReadStoreCommandExecutor readExecutor;
    @Autowired
    private CreateStoreCommandExecutor createExecutor;
    @Autowired
    private UpdateStoreCommandExecutor updateExecutor;
    @Autowired
    private DeleteStoreCommandExecutor deleteExecutor;

    @Transactional
    public StoreDto saveOrUpdate(StoreDto storeDto){
        StoreDto temp = null;
        if (storeDto.getId() != null){
//            System.out.println(": " + storeDto.getId());
//            System.out.println(": " + storeDto.getGroup());
            temp = updateExecutor.update(storeDto);
        } else {
            temp = createExecutor.create(storeDto);
        }
        return temp;
    }

    @Transactional
    public ListResponseDto<StoreDto> getItems(StoreSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria){
        return readExecutor.find(searchCriteria,pagingCriteria);
    }

    @Transactional
    public StoreDto getItem(Long id){
        return readExecutor.findById(id);
    }

    public List<StoreDto> getItems(){
        return readExecutor
                .getRepository()
                .findAll()
                .stream()
                .map(readExecutor::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete (StoreDto storeDto){
        deleteExecutor.delete(storeDto);
    }

    @Transactional
    public String findByWeight(Integer group, Double weight) {
        return readExecutor.findByWeight(group, weight).getPlace();
    }


}
