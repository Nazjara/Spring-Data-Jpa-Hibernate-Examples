package com.nazjara.dao;

import com.nazjara.model.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDaoImpl implements BookDao {

    private final EntityManagerFactory factory;

    public BookDaoImpl(EntityManagerFactory factory) {
        this.factory = factory;
    }

    @Override
    public Book getById(Long id) {
        try (var entityManager = getEntityManager()) {
            return entityManager.find(Book.class, id);
        }
    }

    @Override
    public Book getByTitle(String title) {
        try (var entityManager = getEntityManager()) {
            var query = entityManager.createNamedQuery("bookFindByTitle", Book.class);
            query.setParameter("title", title);
            return query.getSingleResult();
        }
    }

    @Override
    public Book getByTitleCriteria(String title) {
        try (var entityManager = getEntityManager()) {
            var criteriaBuilder = entityManager.getCriteriaBuilder();
            var criteriaQuery = criteriaBuilder.createQuery(Book.class);
            var root = criteriaQuery.from(Book.class);

            var titleParam = criteriaBuilder.parameter(String.class);
            var titlePredicate = criteriaBuilder.equal(root.get("title"), titleParam);

            criteriaQuery
                    .select(root)
                    .where(criteriaBuilder.isTrue(titlePredicate));

            var query = entityManager.createQuery(criteriaQuery);
            query.setParameter(titleParam, title);

            return query.getSingleResult();
        }
    }

    @Override
    public Book getByTitleNative(String title) {
        try (var entityManager = getEntityManager()) {
            var query = entityManager.createNativeQuery("SELECT * FROM book WHERE title = :title",
                    Book.class);
            query.setParameter("title", title);
            return (Book) query.getSingleResult();
        }
    }

    @Override
    public Book getByIsbn(String isbn) {
        try (var entityManager = getEntityManager()) {
            var query = entityManager.createQuery("SELECT b FROM Book b WHERE b.isbn = :isbn", Book.class);
            query.setParameter("isbn", isbn);

            return query.getSingleResult();
        }
    }

    @Override
    public List<Book> getAll() {
        try (var entityManager = getEntityManager()) {
            var query = entityManager.createNamedQuery("bookFindAll", Book.class);
            return query.getResultList();
        }
    }

    @Override
    public Book save(Book book) {
        try (var entityManager = getEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager.persist(book);
            entityManager.flush();
            entityManager.getTransaction().commit();
            return book;
        }
    }

    @Override
    public Book update(Book book) {
        try (var entityManager = getEntityManager()) {
            entityManager.getTransaction().begin();
            book = entityManager.merge(book);
            entityManager.flush();
            entityManager.getTransaction().commit();
            return book;
        }
    }

    @Override
    public void delete(Long id) {
        try (var entityManager = getEntityManager()) {
            entityManager.getTransaction().begin();
            var book = entityManager.find(Book.class, id);
            entityManager.remove(book);
            entityManager.flush();
            entityManager.getTransaction().commit();
        }
    }

    private EntityManager getEntityManager() {
        return factory.createEntityManager();
    }
}
