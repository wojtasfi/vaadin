package com.vaadin.views;

import com.vaadin.data.Binder;
import com.vaadin.entities.Customer;
import com.vaadin.entities.CustomerOrder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.service.CustomerService;
import com.vaadin.service.OrderService;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wojci on 5/2/2017.
 */
public class OrderForm extends FormLayout {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderForm.class);
    private final OrderService orderService;
    private final CustomerService customerService;
    private CustomerOrder customerOrder;
    private OrderView orderView;
    private Binder<CustomerOrder> binder = new Binder<>(CustomerOrder.class);
    private NativeSelect<Customer> customer = new NativeSelect<>("Customer");
    private TextField product = new TextField("Product");
    private TextField quantity = new TextField("Quantity");
    private Button save = new Button("Save");
    private Button delete = new Button("Delete");


    public OrderForm(OrderView orderView, OrderService orderService, CustomerService customerService) {
        this.orderService = orderService;
        this.orderView = orderView;
        this.customerService = customerService;

        setSizeUndefined();
        HorizontalLayout buttons = new HorizontalLayout(save, delete);

        customer.setItems(customerService.findAll());

        addComponents(product, quantity, customer, buttons);

        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        binder.bindInstanceFields(this);
        save.addClickListener(e -> this.save());

        delete.addClickListener(e -> this.delete());

    }

    private void delete() {
        orderService.delete(customerOrder);
        orderView.refreshTable();
        setVisible(false);
    }

    private void save() {

        if (customer.isEmpty() || quantity.isEmpty() || product.isEmpty()) {
            showValidationErrorPopUp();

        } else {
            LOGGER.info(customerOrder.toString());
            orderService.save(customerOrder);
            orderView.refreshTable();
            setVisible(false);
        }

    }

    public void setCustomerOrder(CustomerOrder customerOrder) {
        this.customerOrder = customerOrder;
        binder.setBean(customerOrder);
        delete.setVisible(customerOrder.isPersisted());

    }

    private void showValidationErrorPopUp() {
        Window subWindow = new Window("Sub-window");
        VerticalLayout subContent = new VerticalLayout();
        subWindow.setContent(subContent);

        subContent.addComponent(new Label("Fill out all fields!"));
        Button button = new Button("OK");
        button.addClickListener(event -> subWindow.close());
        subContent.addComponent(button);

        subWindow.setSizeUndefined();
        subWindow.center();

        this.getUI().addWindow(subWindow);
    }

}
