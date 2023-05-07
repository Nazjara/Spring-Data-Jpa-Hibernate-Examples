package com.nazjara.dao;

import com.nazjara.model.Author;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthorDaoImpl implements AuthorDao {

    private final EntityManagerFactory factory;

    public AuthorDaoImpl(EntityManagerFactory factory) {
        this.factory = factory;
    }

    @Override
    public Author getById(Long id) {
        try (var entityManager = getEntityManager()) {
            return entityManager.find(Author.class, id);
        }
    }

    @Override
    public Author getByName(String firstName, String lastName) {
        try (var entityManager = getEntityManager()) {
            var query = entityManager.createNamedQuery("authorFindByName", Author.class);
            query.setParameter("first_name", firstName);
            query.setParameter("last_name", lastName);
            return query.getSingleResult();
        }
    }

    @Override
    public Author getByNameCriteria(String firstName, String lastName) {
        try (var entityManager = getEntityManager()) {
            var criteriaBuilder = entityManager.getCriteriaBuilder();
            var criteriaQuery = criteriaBuilder.createQuery(Author.class);
            var root = criteriaQuery.from(Author.class);

            var firstNameParam = criteriaBuilder.parameter(String.class);
            var lastNameParam = criteriaBuilder.parameter(String.class);

            var firstNamePredicate = criteriaBuilder.equal(root.get("firstName"), firstNameParam);
            var lastNamePredicate = criteriaBuilder.equal(root.get("lastName"), lastNameParam);

            criteriaQuery
                    .select(root)
                    .where(criteriaBuilder.and(firstNamePredicate, lastNamePredicate));

            var query = entityManager.createQuery(criteriaQuery);
            query.setParameter(firstNameParam, firstName);
            query.setParameter(lastNameParam, lastName);

            return query.getSingleResult();
        }
    }

    @Override
    public Author getByNameNative(String firstName, String lastName) {
        try (var entityManager = getEntityManager()) {
            var query = entityManager.createNativeQuery("SELECT * FROM author WHERE first_name = ? and last_name = ?",
                    Author.class);

            query.setParameter(1, firstName);
            query.setParameter(2, lastName);
            return (Author) query.getSingleResult();
        }
    }

    @Override
    public List<Author> getAll() {
        try (var entityManager = getEntityManager()) {
            var query = entityManager.createNamedQuery("authorFindAll", Author.class);
            return query.getResultList();
        }
    }

    @Override
    public List<Author> listByLastNameLike(String lastName) {
        try (var entityManager = getEntityManager()) {
            var query = entityManager.createQuery("SELECT a FROM Author a WHERE a.lastName like :last_name");
            query.setParameter("last_name", String.format("%s%%", lastName));
            return query.getResultList();
        }
    }

    @Override
    public Author save(Author author) {
        try (var entityManager = getEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager.persist(author);
            entityManager.flush();
            entityManager.getTransaction().commit();
            return author;
        }
    }

    @Override
    public Author update(Author author) {
        try (var entityManager = getEntityManager()) {
            entityManager.getTransaction().begin();
            author = entityManager.merge(author);
            entityManager.flush();
            entityManager.getTransaction().commit();
            return author;
        }
    }

    @Override
    public void delete(Long id) {
        try (var entityManager = getEntityManager()) {
            entityManager.getTransaction().begin();
            var author = entityManager.find(Author.class, id);
            entityManager.remove(author);
            entityManager.flush();
            entityManager.getTransaction().commit();
        }
    }

    private EntityManager getEntityManager() {
        return factory.createEntityManager();
    }
}
