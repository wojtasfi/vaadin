package com.vaadin.dao;

import com.vaadin.entities.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by wojci on 5/2/2017.
 */
@Repository
public interface OrderRepository extends JpaRepository<CustomerOrder, Long> {


}
