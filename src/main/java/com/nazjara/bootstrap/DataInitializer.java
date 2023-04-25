package com.nazjara.bootstrap;

import com.nazjara.model.Book;
import com.nazjara.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final BookRepository bookRepository;

    public DataInitializer(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) {
        bookRepository.save(new Book("Domain Driver Design", "123", "RandomHouse1"));
        bookRepository.save(new Book("Spring in Action", "456", "RandomHouse2"));
        bookRepository.findAll().forEach(System.out::println);
    }
}
