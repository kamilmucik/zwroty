package pl.estrix.zwrotpaczek.dto;

public class GetCollectorDetailsDto extends BaseEntityDto<Long>  {


    private CollectorDto collector;

    public GetCollectorDetailsDto(CollectorDto collector) {
        this.collector = collector;
    }

    public GetCollectorDetailsDto(CollectorDto collector, int returnCode) {
        this.collector = collector;
        super.setReturnCode(returnCode);
    }

    public CollectorDto getCollector() {
        return collector;
    }

    public void setCollector(CollectorDto collector) {
        this.collector = collector;
    }
}
