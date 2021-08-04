package pl.estrix.zwrotpaczek.dto;

public class PrintLabelDto extends BaseEntityDto<Long> {

    private Long artNumber;
    private String returnNumber;
    private String palletOption;
    private Long counter;
    private Integer palletCounter;
    private String author;
    private Long collectorId;

    public PrintLabelDto() {
        this.author = "";
    }

    public PrintLabelDto(int returnCode) {
        super.setReturnCode(returnCode);
    }

    public PrintLabelDto(Long artNumber,
                              String returnNumber,
                              Long counter,
                              String author
    ) {
        this.artNumber = artNumber;
        this.returnNumber = returnNumber;
        this.counter = counter;
        this.author = author;
    }

    public PrintLabelDto(Long artNumber, String returnNumber, Long counter, String author, Long collectorId) {
        this.artNumber = artNumber;
        this.returnNumber = returnNumber;
        this.counter = counter;
        this.author = author;
        this.collectorId = collectorId;
    }

    public PrintLabelDto(Long artNumber, String returnNumber, String palletOption, Long counter, String author, Long collectorId) {
        this.artNumber = artNumber;
        this.returnNumber = returnNumber;
        this.palletOption = palletOption;
        this.counter = counter;
        this.author = author;
        this.collectorId = collectorId;
    }

    public PrintLabelDto(Long artNumber, String returnNumber, String palletOption, Long counter, Integer palletCounter, String author, Long collectorId) {
        this.artNumber = artNumber;
        this.returnNumber = returnNumber;
        this.palletOption = palletOption;
        this.counter = counter;
        this.palletCounter = palletCounter;
        this.author = author;
        this.collectorId = collectorId;
    }

    public Long getArtNumber() {
        return artNumber;
    }

    public void setArtNumber(Long artNumber) {
        this.artNumber = artNumber;
    }

    public String getReturnNumber() {
        return returnNumber;
    }

    public void setReturnNumber(String returnNumber) {
        this.returnNumber = returnNumber;
    }

    public Long getCounter() {
        return counter;
    }

    public void setCounter(Long counter) {
        this.counter = counter;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getCollectorId() {
        return collectorId;
    }

    public void setCollectorId(Long collectorId) {
        this.collectorId = collectorId;
    }

    public String getPalletOption() {
        return palletOption;
    }

    public void setPalletOption(String palletOption) {
        this.palletOption = palletOption;
    }

    public Integer getPalletCounter() {
        return palletCounter;
    }

    public void setPalletCounter(Integer palletCounter) {
        this.palletCounter = palletCounter;
    }
}
