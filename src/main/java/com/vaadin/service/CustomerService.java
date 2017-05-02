package com.vaadin.service;

import com.vaadin.dao.CustomerRepository;
import com.vaadin.entities.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * Created by wojci on 5/2/2017.
 */
@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> findAll() {
        return (List<Customer>) customerRepository.findAll();
    }

    public List<Customer> findAll(String filter) {
        return customerRepository.findByFirstNameLikeIgnoreCase(filter);
    }

    public void delete(Set<Customer> selectedItems) {
        customerRepository.delete(selectedItems);
    }

    public void delete(Customer customer) {
        customerRepository.delete(customer);
    }

    public void save(Customer customer) {
        customerRepository.save(customer);
    }
}
