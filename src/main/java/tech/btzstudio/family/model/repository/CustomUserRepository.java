package tech.btzstudio.family.model.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tech.btzstudio.family.model.entity.User;

import javax.persistence.EntityManager;

@Repository
public class CustomUserRepository extends AbstractEntityRepository<User> {

    @Autowired
    public CustomUserRepository(final EntityManager entityManager) {
        super(User.class, entityManager);
    }

}
