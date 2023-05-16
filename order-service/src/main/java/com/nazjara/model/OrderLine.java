package com.nazjara.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Getter
@Setter
public class OrderLine extends BaseEntity {
    private int quantityOrdered;

    @ManyToOne
    private OrderHeader orderHeader;

    @ManyToOne
    private Product product;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        OrderLine orderLine = (OrderLine) o;

        if (quantityOrdered != orderLine.quantityOrdered) return false;
        return Objects.equals(product, orderLine.product);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + quantityOrdered;
        result = 31 * result + (product != null ? product.hashCode() : 0);
        return result;
    }
}
