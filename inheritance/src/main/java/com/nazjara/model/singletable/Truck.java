package com.nazjara.model.singletable;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("truck")
@Getter
@Setter
public class Truck extends Vehicle {

    private String payload;
}
