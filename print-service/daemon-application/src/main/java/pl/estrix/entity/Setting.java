package pl.estrix.entity;

import javax.persistence.*;

import lombok.Data;

@Entity
@Table(name="printer_setting")
@Data
public class Setting {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "setting_name", length = 256, nullable = false)
    private String name;

    @Column(name = "setting_value", length = 256, nullable = false)
    private String value;

    @Column(name = "setting_description", length = 256, nullable = false)
    private String description;


}
