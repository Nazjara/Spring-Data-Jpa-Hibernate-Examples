package com.nazjara.dao;

import com.nazjara.model.Author;
import com.nazjara.repository.AuthorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AuthorDaoImpl implements AuthorDao {

    private final AuthorRepository repository;

    public AuthorDaoImpl(AuthorRepository repository) {
        this.repository = repository;
    }

    @Override
    public Author getById(Long id) {
        return repository.findById(id)
                .orElse(null);
    }

    @Override
    public Author getByName(String firstName, String lastName) {
        return repository.findByFirstNameAndLastName(firstName, lastName)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Author save(Author author) {
        return repository.save(author);
    }

    @Override
    @Transactional
    public Author update(Author author) {
        var foundAuthor = getById(author.getId());
        foundAuthor.setFirstName(author.getFirstName());
        foundAuthor.setLastName(author.getLastName());
        return save(author);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
