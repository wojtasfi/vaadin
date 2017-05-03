package com.vaadin.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by wojci on 5/2/2017.
 */

@Entity
public class CustomerOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String product;

    @NotNull
    private String quantity;

    @NotNull
    @ManyToOne
    private Customer customer;

    public CustomerOrder() {
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public boolean isPersisted() {
        return id != null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "CustomerOrder{" +
                "id=" + id +
                ", product='" + product + '\'' +
                ", quantity='" + quantity + '\'' +
                ", customer=" + customer +
                '}';
    }
}
