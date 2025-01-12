package pl.estrix.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SettingDto extends BaseEntityDto<Long> {

    private String name;

    private String value;
}
