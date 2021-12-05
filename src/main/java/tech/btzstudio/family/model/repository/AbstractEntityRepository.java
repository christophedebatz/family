package tech.btzstudio.family.model.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import tech.btzstudio.family.model.entity.BaseEntity;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@NoRepositoryBean
abstract class AbstractEntityRepository<E extends BaseEntity> implements CrudRepository<E, UUID> {

    @Autowired
    protected EntityManager entityManager;

    private final Class<E> classType;

    public AbstractEntityRepository(Class<E> classType) {
        this.classType = classType;
    }

    @Override
    @NotNull
    public <S extends E> S save(S entity) {
        this.entityManager.persist(entity);
        return entity;
    }

    @Override
    @NotNull
    public <S extends E> Iterable<S> saveAll(Iterable<S> entities) {
        entities.forEach(this::save);
        return entities;
    }

    @Override
    @NotNull
    public Optional<E> findById(@NotNull UUID uuid) {
        return Optional.ofNullable(this.entityManager.find(this.classType, uuid));
    }

    @Override
    public boolean existsById(@NotNull UUID uuid) {
        return this.findById(uuid).isPresent();
    }

    @Override
    @NotNull
    public Iterable<E> findAll() {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<E> cq = cb.createQuery(this.classType);
        Root<E> rootEntry = cq.from(this.classType);
        CriteriaQuery<E> all = cq.select(rootEntry);
        TypedQuery<E> allQuery = this.entityManager.createQuery(all);
        return allQuery.getResultList();
    }

    @Override
    @NotNull
    public Iterable<E> findAllById(@NotNull Iterable<UUID> uuids) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<E> cq = cb.createQuery(this.classType);
        Root<E> rootEntry = cq.from(this.classType);

        Expression<UUID> parentExpression = rootEntry.get("id");
        Predicate parentPredicate = parentExpression.in(uuids);
        cq.where(parentPredicate);

        CriteriaQuery<E> all = cq.select(rootEntry).where();
        TypedQuery<E> allQuery = this.entityManager.createQuery(all);
        return allQuery.getResultList();
    }

    @Override
    public long count() {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        cq.select(cb.count(cq.from(this.classType)));
        return entityManager.createQuery(cq).getSingleResult();
    }

    @Override
    public void deleteById(@NotNull UUID uuid) {
        this.deleteAllById(List.of(uuid));
    }

    @Override
    public void delete(@NotNull E entity) {
        this.entityManager.remove(entity);
    }

    @Override
    public void deleteAllById(@NotNull Iterable<? extends UUID> uuids) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaDelete<E> cd = cb.createCriteriaDelete(this.classType);
        Root<E> rootEntry = cd.from(this.classType);

        Expression<UUID> parentExpression = rootEntry.get("id");
        Predicate parentPredicate = parentExpression.in(uuids);
        cd.where(parentPredicate);

        this.entityManager.createQuery(cd).executeUpdate();
    }

    @Override
    public void deleteAll(@NotNull Iterable<? extends E> entities) {
        List<UUID> ids = new ArrayList<>();
        entities.forEach(entity -> ids.add(entity.getId()));
        this.deleteAllById(ids);
    }

    @Override
    public void deleteAll() {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaDelete<E> cd = cb.createCriteriaDelete(this.classType);
        this.entityManager.createQuery(cd).executeUpdate();
    }
}
