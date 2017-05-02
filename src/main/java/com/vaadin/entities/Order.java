package com.vaadin.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by wojci on 5/2/2017.
 */

@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String product;

    @NotNull
    private int quantity;

    @NotNull
    @ManyToOne()
    private Customer customer;

    public boolean isPersisted() {
        return id != null;
    }

}
