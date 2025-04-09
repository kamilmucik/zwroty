package pl.estrix.common.dto.model;

import lombok.*;
import pl.estrix.backend.base.BaseEntityDto;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class ProductImageVersionRevisionDto extends BaseEntityDto<Long> {

    private String  artNumber;
    private String  ean;
    private Long  versionId;
    private Long  parentId;
    private String description;
    private String imgPath;
    private boolean main;
    private String imgBas64;
    private String hashGroup;
    private LocalDateTime lastUpdate;
    @Setter
    private boolean changesDetected;

    @Builder
    public ProductImageVersionRevisionDto(Long id, String artNumber, String ean, Long versionId, Long parentId, String description, String imgPath, boolean main, String imgBas64, String hashGroup, LocalDateTime lastUpdate, boolean changesDetected) {
        super(id);
        this.artNumber = artNumber;
        this.ean = ean;
        this.versionId = versionId;
        this.parentId = parentId;
        this.description = description;
        this.imgPath = imgPath;
        this.main = main;
        this.imgBas64 = imgBas64;
        this.hashGroup = hashGroup;
        this.lastUpdate = lastUpdate;
        this.changesDetected = changesDetected;
    }
}
