package com.nazjara.service;

import com.nazjara.model.CreditCard;

public interface CreditCardService {
    CreditCard save(CreditCard creditCard);
    CreditCard getById(Long id);
}
