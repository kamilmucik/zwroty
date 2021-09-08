package pl.estrix.backend.user.repository;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.types.Projections;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.stereotype.Repository;
import pl.estrix.backend.base.PagingCriteria;
import pl.estrix.backend.base.QueryDslRepositorySupportBase;
import pl.estrix.backend.user.dao.QUser;
import pl.estrix.backend.user.dao.User;
import pl.estrix.common.dto.UserSearchCriteriaDto;

import java.util.List;

@Repository
public class UserRepositoryCustomImpl extends QueryDslRepositorySupportBase implements UserRepositoryCustom {

    private static final QUser user = new QUser("user");

    public UserRepositoryCustomImpl() {
        super(User.class);
    }

    @Override
    public List<User> find(UserSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria) {
        JPQLQuery query = getQueryForFind(searchCriteria);
        query.orderBy(user.id.asc());
        addPagingCriteriaToQuery(query, pagingCriteria);
        return query.list(Projections.bean(
                User.class,
                user.id,
                user.firstName,
                user.lastName,
                user.email,
                user.password,
                user.enabled,
                user.locked,
                user.subscribed,
                user.verificationKey,
                user.role
        ));
    }

    @Override
    public long findCount(UserSearchCriteriaDto searchCriteria) {
        JPQLQuery query = getQueryForFind(searchCriteria);
        return query.count();
    }

    private JPQLQuery getQueryForFind(UserSearchCriteriaDto searchParams) {
        BooleanBuilder builder = new BooleanBuilder();
        JPQLQuery query = from(user);

        if (StringUtils.isNotEmpty(searchParams.getTableSearch())){
            query.where(
                    user.email.contains(searchParams.getTableSearch())
                            .or(user.firstName.contains(searchParams.getTableSearch()))
                            .or(user.lastName.contains(searchParams.getTableSearch()))
            );
        }

        query.where(builder);
        return query;
    }
}
