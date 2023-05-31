package com.nazjara.repository.pan;

import com.nazjara.model.CreditCardPan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CreditCardPanRepository extends JpaRepository<CreditCardPan, Long> {
    Optional<CreditCardPan> findByCreditCardId(Long creditCardId);
}