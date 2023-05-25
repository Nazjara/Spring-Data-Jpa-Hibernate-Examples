package com.nazjara.model.tabperclass;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Dog extends Mammal {

    private String breed;
}
