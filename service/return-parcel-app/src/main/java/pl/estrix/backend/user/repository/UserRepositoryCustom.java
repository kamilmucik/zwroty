package pl.estrix.backend.user.repository;


import pl.estrix.backend.base.PagingCriteria;
import pl.estrix.backend.user.dao.User;
import pl.estrix.common.dto.UserSearchCriteriaDto;

import java.util.List;

public interface UserRepositoryCustom {

    List<User> find(UserSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria);

    long findCount(UserSearchCriteriaDto searchCriteria);}
