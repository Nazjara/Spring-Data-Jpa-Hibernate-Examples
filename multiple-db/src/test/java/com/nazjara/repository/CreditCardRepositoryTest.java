package com.nazjara.repository;

import com.nazjara.model.CreditCard;
import com.nazjara.service.CreditCardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class CreditCardRepositoryTest {

    @Autowired
    CreditCardService creditCardService;

    @Test
    void saveCreditCard() {
        var creditCard = new CreditCard();
        creditCard.setCreditCardNumber("12345678900000");
        creditCard.setCvv("123");
        creditCard.setExpirationDate("12/2028");
        creditCard.setFirstName("firstName");
        creditCard.setLastName("lastName");
        creditCard.setZipCode("zipCode");

        var savedCreditCard = creditCardService.save(creditCard);

        assertNotNull(savedCreditCard);
        assertNotNull(savedCreditCard.getId());
        assertNotNull(savedCreditCard.getCreditCardNumber());

        var creditCardToVerify = creditCardService.getById(savedCreditCard.getId());

        assertEquals(savedCreditCard, creditCardToVerify);
    }
}