package pl.estrix.frontend.web.print;

import lombok.NoArgsConstructor;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.estrix.backend.base.PagingCriteria;
import pl.estrix.backend.event.service.ShipmentEventService;
import pl.estrix.backend.print.service.PrinterService;
import pl.estrix.common.base.ListResponseDto;
import pl.estrix.common.dto.PrintDto;
import pl.estrix.common.dto.PrinterSearchCriteriaDto;
import pl.estrix.common.dto.ShipmentEventDto;
import pl.estrix.common.dto.ShipmentEventSearchCriteriaDto;
import pl.estrix.common.dto.model.PrintFileDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@NoArgsConstructor
public class PrintLazyDataModel extends LazyDataModel<PrintFileDto> {
    private List<PrintFileDto> datasource;

    private String tableSearch;

    @Autowired
    private PrinterService printService;

    public PrintLazyDataModel(PrinterService printService, String tableSearch) {
        this.printService = printService;
        this.tableSearch = tableSearch;
    }

    @Override
    public PrintFileDto getRowData(String rowKey) {
        if (datasource != null) {
            for (PrintFileDto dto : datasource) {
                if (dto.getId().equals(rowKey))
                    return dto;
            }
        }else {
//            try {
//                FacesContext.getCurrentInstance().getExternalContext().redirect("products.html?id=" + rowKey + "&table_page=0");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
        return null;
    }

    @Override
    public List<PrintFileDto> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
        List<PrintFileDto> data = new ArrayList<>();

        PrinterSearchCriteriaDto searchCriteria = new PrinterSearchCriteriaDto();
        searchCriteria.setTableSearch(tableSearch);

        if (sortField != null) {
            searchCriteria.setSortField(sortField);
            searchCriteria.setSortOrder(sortOrder);
        }

        PagingCriteria pagingCriteria = PagingCriteria
                .builder()
                .start(first)
                .page(0)
                .limit(pageSize)
                .build();
        ListResponseDto<PrintFileDto> responseDto = printService.getItems(searchCriteria,pagingCriteria);


        data.addAll(responseDto.getData());
        this.setRowCount(responseDto.getTotalCount());

        return data;
    }
}
