package com.nazjara.model.singletable;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("car")
@Getter
@Setter
public class Car extends Vehicle {

    private String trimLevel;
}
