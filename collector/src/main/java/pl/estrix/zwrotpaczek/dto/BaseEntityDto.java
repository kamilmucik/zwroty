package pl.estrix.zwrotpaczek.dto;

public abstract class BaseEntityDto<T> extends BaseDto implements EntityDto<T> {

    private T id;

    private String label;

    private int returnCode = 200;

    @Override
    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

    @Override
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }
}