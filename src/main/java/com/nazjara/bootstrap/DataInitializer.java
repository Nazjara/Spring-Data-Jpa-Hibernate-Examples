package com.nazjara.bootstrap;

import com.nazjara.model.Book;
import com.nazjara.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({"mysql", "default"})
public class DataInitializer implements CommandLineRunner {

    private final BookRepository bookRepository;

    public DataInitializer(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) {
        if (bookRepository.count() == 0) {
            bookRepository.save(new Book("Domain Driver Design", "123", "RandomHouse1"));
            bookRepository.save(new Book("Spring in Action", "456", "RandomHouse2"));
        }
    }
}
