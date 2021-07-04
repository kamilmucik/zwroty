package pl.estrix.backend.release.dao;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QReleaseArticlePallet is a Querydsl query type for ReleaseArticlePallet
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QReleaseArticlePallet extends EntityPathBase<ReleaseArticlePallet> {

    private static final long serialVersionUID = 2113347405L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReleaseArticlePallet releaseArticlePallet = new QReleaseArticlePallet("releaseArticlePallet");

    public final pl.estrix.backend.base.QAuditableEntity _super = new pl.estrix.backend.base.QAuditableEntity(this);

    public final StringPath artNumber = createString("artNumber");

    public final NumberPath<Long> counter = createNumber("counter", Long.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final NumberPath<Long> palletCounter = createNumber("palletCounter", Long.class);

    public final StringPath palletFlag = createString("palletFlag");

    public final StringPath productEan = createString("productEan");

    public final QReleaseArticle releaseArticle;

    public final StringPath releaseCode = createString("releaseCode");

    public final StringPath returnNumber = createString("returnNumber");

    public QReleaseArticlePallet(String variable) {
        this(ReleaseArticlePallet.class, forVariable(variable), INITS);
    }

    public QReleaseArticlePallet(Path<? extends ReleaseArticlePallet> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QReleaseArticlePallet(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QReleaseArticlePallet(PathMetadata<?> metadata, PathInits inits) {
        this(ReleaseArticlePallet.class, metadata, inits);
    }

    public QReleaseArticlePallet(Class<? extends ReleaseArticlePallet> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.releaseArticle = inits.isInitialized("releaseArticle") ? new QReleaseArticle(forProperty("releaseArticle")) : null;
    }

}

