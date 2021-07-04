package pl.estrix.zwrotpaczek.dao.model;

import java.util.Date;

public class SettingDao extends BaseDao {

    private Integer id;

    private String name;

    private String value;

    private Date update;

    public SettingDao() {
    }

    public SettingDao(Integer id, String name, String value, Date update) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.update = update;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getUpdate() {
        return update;
    }

    public void setUpdate(Date update) {
        this.update = update;
    }

    @Override
    public String toString() {
        return "SettingDao{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", update=" + update +
                '}';
    }
}
