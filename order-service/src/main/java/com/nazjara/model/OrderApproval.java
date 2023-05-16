package com.nazjara.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrderApproval extends BaseEntity {

    private String approvedBy;

    @OneToOne
    @JoinColumn(name = "order_header_id")
    private OrderHeader orderHeader;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        OrderApproval that = (OrderApproval) o;

        return Objects.equals(approvedBy, that.approvedBy);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (approvedBy != null ? approvedBy.hashCode() : 0);
        return result;
    }
}
