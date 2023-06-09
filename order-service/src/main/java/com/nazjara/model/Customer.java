package com.nazjara.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @Size(max = 50)
    private String contactInfo;

    @Email
    @NotNull
    private String email;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.PERSIST)
    private Set<OrderHeader> orders = new HashSet<>();

    @Version
    private int version; // optimistic locking

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

        if (!Objects.equals(contactInfo, customer.contactInfo))
            return false;
        return Objects.equals(email, customer.email);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (contactInfo != null ? contactInfo.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }
}
