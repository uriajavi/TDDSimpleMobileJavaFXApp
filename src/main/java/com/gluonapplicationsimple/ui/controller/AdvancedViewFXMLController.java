/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gluonapplicationsimple.ui.controller;

import clientside.controller.CustomerManager;
import clientside.model.Customer;
import com.gluonapplicationsimple.GluonApplicationSimple;
import com.gluonhq.charm.glisten.application.MobileApplication;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

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
    /**
     * Initializes the controller class.
     */
    public void initialize() {
        app=(GluonApplicationSimple)MobileApplication.getInstance();
        //get customer data
        CustomerManager manager=(CustomerManager)app.getSession().get("manager");
        Long customerID=(Long)app.getSession().get("customerID");
        customer=manager.getCustomerAccountsFullInfo(customerID);
        lblWelcome.setText(customer.getFirstName()+" "+
                           customer.getMiddleInitial()+" "+
                           customer.getLastName());

    }    
    
}
