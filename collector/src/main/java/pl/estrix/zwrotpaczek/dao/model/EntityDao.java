package pl.estrix.zwrotpaczek.dao.model;

public interface EntityDao<T> {

    T get();

    boolean isPresent();

    int getStatus();

    String getMessage();
}
