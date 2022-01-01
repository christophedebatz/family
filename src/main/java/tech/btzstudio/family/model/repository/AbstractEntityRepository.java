package tech.btzstudio.family.model.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import tech.btzstudio.family.model.entity.EntityInterface;

import javax.persistence.EntityManager;
import java.util.UUID;

@NoRepositoryBean
abstract class AbstractEntityRepository<E extends EntityInterface> extends SimpleJpaRepository<E, UUID> {

    public AbstractEntityRepository (final Class<E> classType, final EntityManager entityManager) {
        super(classType, entityManager);
    }
}
