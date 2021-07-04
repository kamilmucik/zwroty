package pl.estrix.backend.print.dao;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QPrintFile is a Querydsl query type for PrintFile
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QPrintFile extends EntityPathBase<PrintFile> {

    private static final long serialVersionUID = -759929327L;

    public static final QPrintFile printFile = new QPrintFile("printFile");

    public final pl.estrix.backend.base.QAuditableEntity _super = new pl.estrix.backend.base.QAuditableEntity(this);

    public final BooleanPath active = createBoolean("active");

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath name = createString("name");

    public final StringPath path = createString("path");

    public QPrintFile(String variable) {
        super(PrintFile.class, forVariable(variable));
    }

    public QPrintFile(Path<? extends PrintFile> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPrintFile(PathMetadata<?> metadata) {
        super(PrintFile.class, metadata);
    }

}

