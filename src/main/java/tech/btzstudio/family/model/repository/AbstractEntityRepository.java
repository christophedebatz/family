package tech.btzstudio.family.model.repository;

import io.smallrye.mutiny.Uni;
import org.hibernate.reactive.mutiny.Mutiny;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.NoRepositoryBean;
import tech.btzstudio.family.model.entity.EntityInterface;

import javax.persistence.criteria.*;
import java.util.*;

@NoRepositoryBean
abstract class AbstractEntityRepository<E extends EntityInterface> {

    @Autowired
    private Mutiny.SessionFactory factory;

    private final Class<E> classType;

    public AbstractEntityRepository(Class<E> classType) {
        this.classType = classType;
    }

    protected Mutiny.SessionFactory getFactory () {
        return factory;
    }

    protected Class<E> getClassType() {
        return classType;
    }

    public <S extends E> Uni<S> save(S entity) {
        return factory.withSession(session -> session.persist(entity).chain(session::flush).replaceWith(entity));
    }

    public <S extends E> Uni<List<S>> saveAll(Iterable<S> entities) {
        List<S> list = new ArrayList<>();
        entities.forEach(list::add);

        return factory.withTransaction(session ->
                session.persistAll(list.toArray())
                        .chain(session::flush)
                        .chain(() -> Uni.createFrom().item(list)
                )
        );
    }

    public <T extends UUID> Uni<E> findById(T uuid) {
        return factory.withSession(session -> session.find(classType, uuid));
    }

    public <T extends UUID> Uni<Boolean> existsById(T uuid) {
        return findById(uuid).chain(entity -> Uni.createFrom().item(null != entity));
    }

    public Uni<List<E>> findAll() {
        CriteriaBuilder cb = factory.getCriteriaBuilder();
        CriteriaQuery<E> query = cb.createQuery(classType);
        Root<E> root = query.from(classType);
        return factory.withSession(session -> session.createQuery(query).getResultList());
    }

    public Uni<List<E>> findAllById(Iterable<? extends UUID> uuids, int offset, int limit) {
        CriteriaBuilder cb = factory.getCriteriaBuilder();
        CriteriaQuery<E> query = cb.createQuery(classType);
        query.where(query.from(classType).get("id").in(uuids));

        return factory.withSession(session -> session.createQuery(query)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList());
    }

    public Uni<Long> count() {
        CriteriaBuilder cb = factory.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        query.select(cb.count(query.from(classType)));
        return factory.withSession(session -> session.createQuery(query).getSingleResult());
    }

    public Uni<Integer> deleteById(UUID uuid) {
        return deleteAllById(List.of(uuid));
    }

    public Uni<Void> delete(E entity) {
        return factory.withSession(session -> session.remove(entity));
    }

    public Uni<Integer> deleteAll(Iterable<? extends E> entities) {
        List<UUID> ids = new ArrayList<>();
        entities.forEach(entity -> ids.add(entity.getId()));
        return deleteAllById(ids);
    }

    public Uni<Integer> deleteAllById(Iterable<? extends UUID> uuids) {
        CriteriaBuilder cb = factory.getCriteriaBuilder();
        CriteriaDelete<E> cd = cb.createCriteriaDelete(classType);
        Root<E> rootEntry = cd.from(classType);

        Expression<UUID> parentExpression = rootEntry.get("id");
        Predicate parentPredicate = parentExpression.in(uuids);
        cd.where(parentPredicate);

        return factory.withSession(session -> session.createQuery(cd).executeUpdate());
    }
}
