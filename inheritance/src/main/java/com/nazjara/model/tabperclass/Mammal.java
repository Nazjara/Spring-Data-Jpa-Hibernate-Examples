package com.nazjara.model.tabperclass;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Setter
public abstract class Mammal {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private long id;

    private int bodyTemp;
    private String species;
}
