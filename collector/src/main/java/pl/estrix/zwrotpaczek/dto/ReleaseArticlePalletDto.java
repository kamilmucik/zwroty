package pl.estrix.zwrotpaczek.dto;

public class ReleaseArticlePalletDto extends BaseEntityDto<Long>{

    private String releaseCode;

    private String artNumber;

    private Long counter;

    public ReleaseArticlePalletDto() {
    }

    public ReleaseArticlePalletDto(String releaseCode, String artNumber, Long counter) {
        this.releaseCode = releaseCode;
        this.artNumber = artNumber;
        this.counter = counter;
    }

    public String getReleaseCode() {
        return releaseCode;
    }

    public void setReleaseCode(String releaseCode) {
        this.releaseCode = releaseCode;
    }

    public String getArtNumber() {
        return artNumber;
    }

    public void setArtNumber(String artNumber) {
        this.artNumber = artNumber;
    }

    public Long getCounter() {
        return counter;
    }

    public void setCounter(Long counter) {
        this.counter = counter;
    }
}
