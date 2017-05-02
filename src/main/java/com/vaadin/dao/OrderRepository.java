package com.vaadin.dao;

import com.vaadin.entities.Customer;
import com.vaadin.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wojci on 5/2/2017.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {


}
