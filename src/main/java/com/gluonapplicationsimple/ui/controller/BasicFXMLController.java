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
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.Snackbar;
import com.gluonhq.charm.glisten.control.TextField;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import java.net.URL;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 * @author javi
 */
public class BasicFXMLController {
    @FXML
    private View vwBasic;
    @FXML
    private Button btEnter;
    @FXML
    private TextField tfCustomerID;
    private Snackbar snackbar;
    /**
     * Initializes the controller class.
     */
    public void initialize() {
        //Add event handlers
        vwBasic.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setTitleText("Simple Mobile App");
            }
        });
        tfCustomerID.textProperty().addListener(this::handleCustomerIDChange);
        btEnter.setOnAction(this::handleOnActionEnter);
        //Initial state
        btEnter.setDisable(true);
        snackbar= new Snackbar("","OK", e -> snackbar.cancel());
        
    }

    public void handleCustomerIDChange(ObservableValue observable,
             String oldValue,
             String newValue){
        snackbar.cancel();
        if(newValue.trim().isEmpty())
            btEnter.setDisable(true);
        else 
            btEnter.setDisable(false);
    }
    
    public void handleOnActionEnter(ActionEvent event){
        try{
            //validate CustomerID
            if(!tfCustomerID.getText().matches("\\d+")) 
                throw new Exception("Customer ID must be numeric!!!");
            //get customer data and store in the session hashmap
            GluonApplicationSimple app=
                        (GluonApplicationSimple)MobileApplication.getInstance();
            CustomerManager manager=(CustomerManager)app.getSession().get("manager");
            Customer customer=manager.getCustomerAccountsFullInfo(new Long(tfCustomerID.getText().trim()));
            //Store customer id in session and show advanced view
            app.getSession().put("customer",customer);
            app.switchView(GluonApplicationSimple.ADVANCED_VIEW);
        }catch(Exception e){
             snackbar.setMessage(e.getMessage());
             snackbar.show();
        }
    }
}
