package pl.estrix.backend.settings.dao;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QSetting is a Querydsl query type for Setting
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QSetting extends EntityPathBase<Setting> {

    private static final long serialVersionUID = -1548123676L;

    public static final QSetting setting = new QSetting("setting");

    public final pl.estrix.backend.base.QAuditableEntity _super = new pl.estrix.backend.base.QAuditableEntity(this);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath name = createString("name");

    public final StringPath value = createString("value");

    public QSetting(String variable) {
        super(Setting.class, forVariable(variable));
    }

    public QSetting(Path<? extends Setting> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSetting(PathMetadata<?> metadata) {
        super(Setting.class, metadata);
    }

}

