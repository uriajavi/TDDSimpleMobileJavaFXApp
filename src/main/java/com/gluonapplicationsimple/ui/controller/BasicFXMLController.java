/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gluonapplicationsimple.ui.controller;

import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.TextField;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 * @author javi
 */
public class BasicFXMLController {
    @FXML
    View primary;
    @FXML
    Button btEnter;
    @FXML
    TextField tfCustomerID;
    /**
     * Initializes the controller class.
     */
    public void initialize() {
        //Add event handlers
        primary.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setTitleText("Simple Mobile App");
            }
        });
        tfCustomerID.textProperty().addListener(this::handleCustomerIDChange);
        //Initial state
        btEnter.setDisable(true);
        
    }

    public void handleCustomerIDChange(ObservableValue observable,
             String oldValue,
             String newValue){
        if(newValue.trim().isEmpty())
            btEnter.setDisable(true);
        else 
            btEnter.setDisable(false);
    }
    
}
