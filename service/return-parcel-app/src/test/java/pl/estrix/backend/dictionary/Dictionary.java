package pl.estrix.backend.dictionary;

import jdk.nashorn.internal.ir.annotations.Immutable;
import lombok.Data;

@Immutable
@Data
public class Dictionary {

    private int id;
    private String description;

}
