package com.vaadin.views;

import com.vaadin.data.Binder;
import com.vaadin.entities.Customer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.service.CustomerService;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.MultiSelectionModel;
import com.vaadin.ui.themes.ValoTheme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by wojci on 5/2/2017.
 */
@UIScope
@SpringView(name = CustomersView.VIEW_NAME)
public class CustomersView extends VerticalLayout implements View {
    public static final String VIEW_NAME = "";

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomersView.class);

    @Autowired
    private CustomerService customerService;

    private CustomerForm customerForm;
    private Grid<Customer> grid = new Grid<>(Customer.class);
    private TextField filterText = new TextField();
    private Button deleteSelected = new Button("Delete selected");
    private HorizontalLayout layout;
    @PostConstruct
    void init() {
        customerForm = new CustomerForm(this, customerService);
         layout = new HorizontalLayout();

        filterText.setPlaceholder("filter by name...");
        filterText.addValueChangeListener(e -> refreshTable());
        filterText.setValueChangeMode(ValueChangeMode.LAZY);

        Button clearFilterTextBtn = new Button();
        clearFilterTextBtn.setDescription("Clear the current filter");
        clearFilterTextBtn.addClickListener(e -> filterText.clear());

        CssLayout filtering = new CssLayout();
        filtering.addComponents(filterText, clearFilterTextBtn);
        filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        Button addCustomerBtn = new Button("Add new customer");
        addCustomerBtn.addClickListener(e -> {

            if (customerForm.isVisible()) {
                customerForm.setVisible(false);
            } else {
                customerForm.setVisible(true);
                customerForm.setCustomer(new Customer());
            }
        });

        setGrid();

        HorizontalLayout toolbar = new HorizontalLayout(filtering, addCustomerBtn);
        layout.addComponents(grid, customerForm);
        layout.setSizeFull();
        grid.setSizeFull();
        customerForm.setVisible(false);

        addComponent(toolbar);
        addComponent(layout);
        refreshTable();


    }

    private void setGrid() {
        grid.setSizeFull();
        grid.setColumns("firstName", "lastName", "email", "birthday");

        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() == null) {
                customerForm.setVisible(false);
            } else {
                customerForm.setVisible(true);
                customerForm.setCustomer(event.getValue());
            }
        });
    }

    public void refreshTable() {
        LOGGER.info("refreshing");

        List<Customer> customers;
        if (filterText.isEmpty()) {
            customers = customerService.findAll();
        } else {
            customers = customerService.findAll(filterText.getValue());
        }

        customers.forEach(customer -> LOGGER.info(customer.toString()));
        grid.setItems(customers);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }


}
