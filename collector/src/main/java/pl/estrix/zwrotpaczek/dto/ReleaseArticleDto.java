package pl.estrix.zwrotpaczek.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReleaseArticleDto  extends BaseEntityDto<Long> {

    private LocalDate releaseDate;

    private List<ReleaseArticlePalletDto> palletDtoList = new ArrayList<>();

    public ReleaseArticleDto() {
    }

    public ReleaseArticleDto(int returnCode) {
        super.setReturnCode(returnCode);
    }

    public ReleaseArticleDto(LocalDate releaseDate, List<ReleaseArticlePalletDto> palletDtoList) {
        this.releaseDate = releaseDate;
        this.palletDtoList = palletDtoList;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<ReleaseArticlePalletDto> getPalletDtoList() {
        return palletDtoList;
    }

    public void setPalletDtoList(List<ReleaseArticlePalletDto> palletDtoList) {
        this.palletDtoList = palletDtoList;
    }
}
