package pl.estrix.backend.release.dao;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QReleaseArticle is a Querydsl query type for ReleaseArticle
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QReleaseArticle extends EntityPathBase<ReleaseArticle> {

    private static final long serialVersionUID = -405227187L;

    public static final QReleaseArticle releaseArticle = new QReleaseArticle("releaseArticle");

    public final pl.estrix.backend.base.QAuditableEntity _super = new pl.estrix.backend.base.QAuditableEntity(this);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final ListPath<ReleaseArticlePallet, QReleaseArticlePallet> pallets = this.<ReleaseArticlePallet, QReleaseArticlePallet>createList("pallets", ReleaseArticlePallet.class, QReleaseArticlePallet.class, PathInits.DIRECT2);

    public final DatePath<java.time.LocalDate> releaseDate = createDate("releaseDate", java.time.LocalDate.class);

    public QReleaseArticle(String variable) {
        super(ReleaseArticle.class, forVariable(variable));
    }

    public QReleaseArticle(Path<? extends ReleaseArticle> path) {
        super(path.getType(), path.getMetadata());
    }

    public QReleaseArticle(PathMetadata<?> metadata) {
        super(ReleaseArticle.class, metadata);
    }

}

