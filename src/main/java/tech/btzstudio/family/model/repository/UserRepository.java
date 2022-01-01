package tech.btzstudio.family.model.repository;

import io.smallrye.mutiny.Uni;
import org.springframework.stereotype.Repository;
import tech.btzstudio.family.model.entity.QUser;
import tech.btzstudio.family.model.entity.User;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class UserRepository extends AbstractEntityRepository<User> {

    public UserRepository () {
        super(User.class);
    }

    public Uni<User> findByEmail(final String email) {
        CriteriaBuilder cb = getFactory().getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(getClassType());
        Root<User> root = query.from(getClassType());
        query.where(query.from(getClassType());
        return getFactory().withSession(session -> session.createQuery(query).getSingleResult());
    }

}
