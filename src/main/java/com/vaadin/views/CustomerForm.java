package com.vaadin.views;


import com.vaadin.data.Binder;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.entities.Customer;
import com.vaadin.entities.Gender;
import com.vaadin.event.ShortcutAction;
import com.vaadin.service.CustomerService;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolationException;


public class CustomerForm extends FormLayout {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerForm.class);


    private final CustomerService customerService;
    private Customer customer;


    private CustomersView customersView;
    private Binder<Customer> binder = new Binder<>(Customer.class);

    private TextField firstName = new TextField("First name");
    private TextField lastName = new TextField("Last name");
    private TextField email = new TextField("Email");
    private NativeSelect<Gender> gender = new NativeSelect<>("Gender");
    private DateField birthday = new DateField("Birthday");
    private Button save = new Button("Save");
    private Button delete = new Button("Delete");

    public CustomerForm(CustomersView customersView, CustomerService customerService) {
        this.customerService = customerService;
        this.customersView = customersView;

        setSizeUndefined();
        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        gender.setItems(Gender.values());
        addComponents(firstName, lastName, gender, email, birthday, buttons);

        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        binder.bindInstanceFields(this);
        save.addClickListener(e -> this.save());

        delete.addClickListener(e -> this.delete());
    }

    private void delete() {
        customerService.delete(customer);
        customersView.refreshTable();
        setVisible(false);
    }

    private void save() {

        if(firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || birthday.isEmpty()){
            showValidationErrorPopUp();

        }else{
            customerService.save(customer);
            customersView.refreshTable();
            setVisible(false);
        }

    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        binder.setBean(customer);
        delete.setVisible(customer.isPersisted());

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
