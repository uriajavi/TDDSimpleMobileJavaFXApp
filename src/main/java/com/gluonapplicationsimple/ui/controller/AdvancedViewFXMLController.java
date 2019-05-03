/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gluonapplicationsimple.ui.controller;

import clientside.controller.CustomerManager;
import clientside.model.Account;
import clientside.model.Customer;
import com.gluonapplicationsimple.GluonApplicationSimple;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.DropdownButton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;

/**
 * FXML Controller class
 *
 * @author javi
 */
public class AdvancedViewFXMLController{
    private GluonApplicationSimple app;
    private Customer customer;
    @FXML
    private Label lblWelcome;
    @FXML
    private DropdownButton btAccount;
    /**
     * Initializes the controller class.
     */
    public void initialize() {
        app=(GluonApplicationSimple)MobileApplication.getInstance();
        //get customer data
        CustomerManager manager=(CustomerManager)app.getSession().get("manager");
        Long customerID=(Long)app.getSession().get("customerID");
        customer=manager.getCustomerAccountsFullInfo(customerID);
        //show welcome to customer
        lblWelcome.setText("Welcome "+ customer.getFirstName()+" "+
                           customer.getMiddleInitial()+" "+
                           customer.getLastName());
        //load accounts on account drop down button
        customer.getAccounts().forEach((account) -> {
            btAccount.getItems().add(new MenuItem(account.toString()));
        });

    }    
    
}
