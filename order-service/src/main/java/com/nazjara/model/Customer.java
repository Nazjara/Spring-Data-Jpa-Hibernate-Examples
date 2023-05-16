package com.nazjara.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Customer extends BaseEntity {

    private String contactInfo;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.PERSIST)
    private Set<OrderHeader> orders = new HashSet<>();

    public void addOrder(OrderHeader order) {
        orders.add(order);
        order.setCustomer(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Customer customer = (Customer) o;

        return Objects.equals(contactInfo, customer.contactInfo);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (contactInfo != null ? contactInfo.hashCode() : 0);
        return result;
    }
}
