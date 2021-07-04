package pl.estrix.backend.base;

import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Component;
import pl.estrix.common.base.ListResponseDto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import java.util.stream.Collectors;


@Component
public abstract class BaseCommandExecutor<E extends Serializable, D extends BaseDto> {

    private static final QPageRequest DEFAULT_PAGEABLE = new QPageRequest(0, QueryDslRepositorySupportBase.DEFAULT_LIMIT);


//    @Autowired
//    protected MapperFacade mapper;

    protected <K> K convert(E entity, Class<K> returnClass) {

//        return mapper.map(entity, returnClass);
        return null;
    }

    protected D convert(E entity) {
        return convert(entity, getDtoClass());
    }

    protected <K> List<K> convert(Collection<E> entities, Class<K> returnClass) {
        return entities.stream().map(entity -> convert(entity, returnClass)).collect(Collectors.toCollection(ArrayList::new));
    }

    protected <K> ListResponseDto<K> createListResponseDto(PagingCriteria pagingCriteria, Supplier<List<K>> findSupplier,
                                                           IntSupplier countSupplier) {

//        ListResponseDtoBuilder<K> listResponseDtoBuilder = ListResponseDto.builder();
        ListResponseDto<K> listResponseDtoBuilder = new ListResponseDto();

        List<K> results = findSupplier.get();

        listResponseDtoBuilder.setData(results);
//        listResponseDtoBuilder.data(results);

//        ListResponseDto<K> listResponseDto = listResponseDtoBuilder.build();
        ListResponseDto<K> listResponseDto = listResponseDtoBuilder;
        setTotalCount(pagingCriteria, listResponseDto, countSupplier);

        return listResponseDto;
    }

    protected void setTotalCount(PagingCriteria pagingCriteria, ListResponseDto<?> listResponseDto, IntSupplier countSupplier) {
        if (pagingCriteria == null || pagingCriteria.getLimit() == null || pagingCriteria.getLimit() <= 0) {
            return;
        }

        if (listResponseDto.getData().size() < pagingCriteria.getLimit() && pagingCriteria.getStart() <= 0) {
            listResponseDto.setTotalCount(listResponseDto.getData().size());
            return;
        }
        listResponseDto.setTotalCount(countSupplier.getAsInt());
    }

    protected List<D> convert(Collection<E> entities) {
        return convert(entities, getDtoClass());
    }

    protected abstract Class<D> getDtoClass();
}
