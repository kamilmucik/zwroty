package pl.estrix.zwrotpaczek.dao.model;

public class OptionalDao<T> extends BaseDao implements EntityDao<T> {

    private T obj;

    private int status;

    private String message;

    @Override
    public T get() {
        return obj;
    }

    @Override
    public boolean isPresent() {
        return obj!=null;
    }

    public void setResult(T obj) {
        this.obj = obj;
    }

    @Override
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "OptionalDao{" +
                "obj=" + obj +
                ", status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
