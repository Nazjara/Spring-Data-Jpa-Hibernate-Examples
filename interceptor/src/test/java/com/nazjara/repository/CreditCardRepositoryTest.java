package com.nazjara.repository;

import com.nazjara.model.CreditCard;
import com.nazjara.service.EncryptionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("mysql")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CreditCardRepositoryTest {

    @Autowired
    CreditCardRepository creditCardRepository;

    @Autowired
    EncryptionService encryptionService;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    void saveCreditCard() {
        var creditCard = new CreditCard();
        creditCard.setCreditCardNumber("12345678900000");
        creditCard.setCvv("123");
        creditCard.setExpirationDate("12/2028");

        var savedCreditCard = creditCardRepository.save(creditCard);

        var dbCardValue = (String) jdbcTemplate
                .queryForMap("SELECT * FROM credit_card WHERE id = " + savedCreditCard.getId())
                .get("credit_card_number");

        assertEquals(dbCardValue, encryptionService.encrypt(creditCard.getCreditCardNumber()));

        var fetchedCreditCard = creditCardRepository.findById(savedCreditCard.getId()).get();

        assertEquals(fetchedCreditCard.getCreditCardNumber(), creditCard.getCreditCardNumber());
    }
}