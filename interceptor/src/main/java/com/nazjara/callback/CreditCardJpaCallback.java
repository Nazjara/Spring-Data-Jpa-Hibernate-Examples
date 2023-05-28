package com.nazjara.callback;

import com.nazjara.model.CreditCard;
import com.nazjara.service.EncryptionService;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreditCardJpaCallback {

    @Autowired
    private EncryptionService encryptionService;

    @PrePersist
    @PreUpdate
    public void beforeInsertOrUpdate(CreditCard creditCard) {
        creditCard.setCreditCardNumber(encryptionService.encrypt(creditCard.getCreditCardNumber()));
    }

    @PostLoad
    @PostPersist
    @PostUpdate
    public void afterLoad(CreditCard creditCard) {
        creditCard.setCreditCardNumber(encryptionService.decrypt(creditCard.getCreditCardNumber()));
    }
}
