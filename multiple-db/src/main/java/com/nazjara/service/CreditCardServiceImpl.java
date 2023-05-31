package com.nazjara.service;

import com.nazjara.model.CreditCard;
import com.nazjara.model.CreditCardHolder;
import com.nazjara.model.CreditCardPan;
import com.nazjara.repository.card.CreditCardRepository;
import com.nazjara.repository.cardholder.CreditCardHolderRepository;
import com.nazjara.repository.pan.CreditCardPanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreditCardServiceImpl implements CreditCardService {

    private final CreditCardRepository creditCardRepository;
    private final CreditCardHolderRepository creditCardHolderRepository;
    private final CreditCardPanRepository creditCardPanRepository;

    @Override
    @Transactional
    public CreditCard save(CreditCard creditCard) {
        var savedCreditCard = creditCardRepository.save(creditCard);

        creditCardHolderRepository.save(CreditCardHolder.builder()
                        .creditCardId(savedCreditCard.getId())
                        .firstName(creditCard.getFirstName())
                        .lastName(creditCard.getLastName())
                        .zipCode(creditCard.getZipCode())
                .build());

        creditCardPanRepository.save(CreditCardPan.builder()
                        .creditCardId(savedCreditCard.getId())
                        .creditCardNumber(creditCard.getCreditCardNumber())
                .build());


        return savedCreditCard;
    }

    @Override
    @Transactional(readOnly = true)
    public CreditCard getById(Long id) {
        var creditCard = creditCardRepository.findById(id).orElseThrow();
        var creditCardHolder = creditCardHolderRepository.findByCreditCardId(id).orElseThrow();
        var creditCardPan = creditCardPanRepository.findByCreditCardId(id).orElseThrow();

        creditCard.setFirstName(creditCardHolder.getFirstName());
        creditCard.setLastName(creditCardHolder.getLastName());
        creditCard.setZipCode(creditCardHolder.getZipCode());
        creditCard.setCreditCardNumber(creditCardPan.getCreditCardNumber());

        return creditCard;
    }
}
