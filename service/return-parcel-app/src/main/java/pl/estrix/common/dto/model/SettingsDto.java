package pl.estrix.common.dto.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SettingsDto {

    private String test;

    private String path;

    private String hour;

    private Boolean needBackup;

    private String tempDirectory;
}
