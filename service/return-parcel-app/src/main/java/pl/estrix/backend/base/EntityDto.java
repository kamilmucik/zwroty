package pl.estrix.backend.base;

public interface EntityDto<T> {

    /**
     * Returns id of the entity.
     *
     * @return id of the entity
     */
    T getId();

    /**
     * Returns human readable label for the entity.
     *
     * @return label of the entity
     */
    String getLabel();

}
