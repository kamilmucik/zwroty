package pl.estrix.frontend.web.version;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateVersionDialog {

    private Long id;
    private Long revisionId;
    private String selectedImage;
    private String selectedDescription;
}
