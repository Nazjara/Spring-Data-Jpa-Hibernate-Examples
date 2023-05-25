package com.nazjara.model.joined;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ElectricGuitar extends Guitar {

    private int numberOfPickups;
}
