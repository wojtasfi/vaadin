package com.vaadin.views;

import com.vaadin.entities.CustomerOrder;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.service.CustomerService;
import com.vaadin.service.OrderService;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by wojci on 5/2/2017.
 */
@UIScope
@SpringView(name = OrderView.VIEW_NAME)
public class OrderView extends VerticalLayout implements View {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderView.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerService customerService;

    private OrderForm orderForm;

    public static final String VIEW_NAME = "Orders";
    private Grid<CustomerOrder> grid = new Grid<>(CustomerOrder.class);
    private HorizontalLayout layout;

    @PostConstruct
    void init() {
        layout = new HorizontalLayout();
        orderForm = new OrderForm(this, orderService, customerService);
        Button addOrderBtn = new Button("Add new order");
        addOrderBtn.addClickListener(e -> {

            if (orderForm.isVisible()) {
                orderForm.setVisible(false);
            } else {
                orderForm.setVisible(true);
                orderForm.setCustomerOrder(new CustomerOrder());
            }
        });

        setGrid();

        HorizontalLayout toolbar = new HorizontalLayout(addOrderBtn);
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
        grid.setColumns("product", "quantity", "customer");
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() == null) {
                orderForm.setVisible(false);
            } else {
                orderForm.setVisible(true);
                orderForm.setCustomerOrder(event.getValue());
            }
        });

    }

    public void refreshTable() {

        List<CustomerOrder> customerOrders = orderService.findAll();

        grid.setItems(customerOrders);
    }
}

