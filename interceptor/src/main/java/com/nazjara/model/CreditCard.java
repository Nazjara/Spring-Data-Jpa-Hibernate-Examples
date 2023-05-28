package com.nazjara.model;

import com.nazjara.converter.CreditCardConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
//@EntityListeners(CreditCardJpaCallback.class)
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @EncryptedString
    @Convert(converter = CreditCardConverter.class)
    private String creditCardNumber;

    private String cvv;
    private String expirationDate;
}
