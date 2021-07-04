package pl.estrix.backend.print.dao;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QPrinter is a Querydsl query type for Printer
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QPrinter extends EntityPathBase<Printer> {

    private static final long serialVersionUID = 1956749922L;

    public static final QPrinter printer = new QPrinter("printer");

    public final pl.estrix.backend.base.QAuditableEntity _super = new pl.estrix.backend.base.QAuditableEntity(this);

    public final BooleanPath active = createBoolean("active");

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final BooleanPath isDefault = createBoolean("isDefault");

    public final StringPath name = createString("name");

    public final StringPath path = createString("path");

    public QPrinter(String variable) {
        super(Printer.class, forVariable(variable));
    }

    public QPrinter(Path<? extends Printer> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPrinter(PathMetadata<?> metadata) {
        super(Printer.class, metadata);
    }

}

