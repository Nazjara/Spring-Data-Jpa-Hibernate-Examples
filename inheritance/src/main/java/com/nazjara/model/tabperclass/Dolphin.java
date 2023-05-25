package com.nazjara.model.tabperclass;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Dolphin extends Mammal {

    private boolean hasSpots;
}
