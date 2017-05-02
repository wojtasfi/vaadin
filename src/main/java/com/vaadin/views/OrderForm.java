package com.vaadin.views;

import com.vaadin.data.Binder;
import com.vaadin.entities.Customer;
import com.vaadin.entities.Order;
import com.vaadin.service.CustomerService;
import com.vaadin.service.OrderService;
import com.vaadin.ui.*;

/**
 * Created by wojci on 5/2/2017.
 */
public class OrderForm extends FormLayout {

    private final OrderService orderService;
    private Order order;
    private OrderView orderView;
    private Binder<Order> binder = new Binder<>(Order.class);
    private NativeSelect<Customer> customerList = new NativeSelect<>("Customer");
    private TextField product = new TextField("Product");
    private TextField quantity = new TextField("Quantity");
    private Button save = new Button("Save");
    private Button delete = new Button("Delete");


    public OrderForm(OrderView orderView, OrderService orderService) {
        this.orderService=orderService;
        this.orderView = orderView;

    }

    private void delete() {
        orderService.delete(order);
        orderView.refreshTable();
        setVisible(false);
    }

    private void save() {

        if(customerList.isEmpty() || quantity.isEmpty() || product.isEmpty()){
            showValidationErrorPopUp();

        }else{
            orderService.save(order);
            orderView.refreshTable();
            setVisible(false);
        }

    }

    public void setOrder(Order order) {
        this.order = order;
        binder.setBean(order);
        delete.setVisible(order.isPersisted());

    }

    private void showValidationErrorPopUp(){
        Window subWindow = new Window("Sub-window");
        VerticalLayout subContent = new VerticalLayout();
        subWindow.setContent(subContent);

        subContent.addComponent(new Label("Fill out all fields!"));
        Button button = new Button("OK");
        button.addClickListener(event -> {subWindow.close();});
        subContent.addComponent(button);

        subWindow.setSizeUndefined();
        subWindow.center();

        this.getUI().addWindow(subWindow);
    }

}
