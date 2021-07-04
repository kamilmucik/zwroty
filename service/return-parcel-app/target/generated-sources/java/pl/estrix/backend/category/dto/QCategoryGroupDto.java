package pl.estrix.backend.category.dto;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QCategoryGroupDto is a Querydsl query type for CategoryGroupDto
 */
@Generated("com.mysema.query.codegen.SupertypeSerializer")
public class QCategoryGroupDto extends EntityPathBase<CategoryGroupDto> {

    private static final long serialVersionUID = -349335486L;

    public static final QCategoryGroupDto categoryGroupDto = new QCategoryGroupDto("categoryGroupDto");

    public final ListPath<CategoryDto, SimplePath<CategoryDto>> categories = this.<CategoryDto, SimplePath<CategoryDto>>createList("categories", CategoryDto.class, SimplePath.class, PathInits.DIRECT2);

    public final StringPath name = createString("name");

    public QCategoryGroupDto(String variable) {
        super(CategoryGroupDto.class, forVariable(variable));
    }

    public QCategoryGroupDto(Path<? extends CategoryGroupDto> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCategoryGroupDto(PathMetadata<?> metadata) {
        super(CategoryGroupDto.class, metadata);
    }

}

