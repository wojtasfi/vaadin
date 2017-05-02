package com.vaadin.views;

import com.vaadin.entities.Customer;
import com.vaadin.entities.Order;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.service.CustomerService;
import com.vaadin.service.OrderService;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by wojci on 5/2/2017.
 */
@UIScope
@SpringView(name = OrderView.VIEW_NAME)
public class OrderView extends VerticalLayout implements View {


    @Autowired
    private OrderService orderService;

    private OrderForm orderForm;

    public static final String VIEW_NAME = "differentView";
    private Grid<Order> grid = new Grid<>(Order.class);
    private HorizontalLayout layout;

    @PostConstruct
    void init() {
        orderForm = new OrderForm(this, orderService);
        Button addCustomerBtn = new Button("Add new customer");
        addCustomerBtn.addClickListener(e -> {

            if (orderForm.isVisible()) {
                orderForm.setVisible(false);
            } else {
                orderForm.setVisible(true);
                orderForm.setOrder(new Order());
            }
        });

        setGrid();

        HorizontalLayout toolbar = new HorizontalLayout(addCustomerBtn);
        layout.addComponents(grid, orderForm);
        layout.setSizeFull();
        grid.setSizeFull();
        orderForm.setVisible(false);

        addComponent(toolbar);
        addComponent(layout);
        refreshTable();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        // This view is constructed in the init() method()
    }

    private void setGrid() {
        grid.setSizeFull();

        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() == null) {
                orderForm.setVisible(false);
            } else {
                orderForm.setVisible(true);
                orderForm.setOrder(event.getValue());
            }
        });

    }

    public void refreshTable() {

        List<Order> orders = orderService.findAll();

        grid.setItems(orders);
    }
}

