package com.jpql;

import javax.persistence.*;

@Entity
@Table(name = "JPQL_ORDERS")
public class JpqlOrder {

    @Id
    @GeneratedValue
    private Long id;
    private int orderAmount;

    @Embedded
    private JpqlAddress address;

    // N : 1
    @ManyToOne
    @JoinColumn(name = "product")
    private JpqlProduct product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(int orderAmount) {
        this.orderAmount = orderAmount;
    }

    public JpqlAddress getAddress() {
        return address;
    }

    public void setAddress(JpqlAddress address) {
        this.address = address;
    }

    public JpqlProduct getProduct() {
        return product;
    }

    public void setProduct(JpqlProduct product) {
        this.product = product;
    }
}
