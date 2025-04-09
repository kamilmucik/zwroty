package pl.estrix.frontend.web.version;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompareModal {


    private Long firstId;
    private Long firstRevisionId;
    private String firstDate;
    private String firstImage;
    private String firstDescription;

    private Long secondId;
    private Long secondRevisionId;
    private String secondDate;
    private String secondImage;
    private String secondDescription;
}
