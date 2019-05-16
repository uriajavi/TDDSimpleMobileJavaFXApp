/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gluonapplicationsimple.ui.controller;

import clientside.controller.CustomerManager;
import clientside.model.Account;
import clientside.model.Customer;
import clientside.model.Movement;
import com.gluonapplicationsimple.GluonApplicationSimple;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.CharmListView;
import com.gluonhq.charm.glisten.control.DropdownButton;
import com.gluonhq.charm.glisten.control.LifecycleEvent;
import com.gluonhq.charm.glisten.control.Snackbar;
import com.gluonhq.charm.glisten.mvc.View;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
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
    private View vwAdvanced;
    @FXML
    private Label lblWelcome;
    @FXML
    private DropdownButton btAccount;
    @FXML
    private Label lblCurrentBalance;
    @FXML
    private CharmListView lstMovements;
    private Snackbar snackbar;
    
    /**
     * Initializes the controller class.
     */
    public void initialize() {
        //get app reference.
        app=(GluonApplicationSimple)MobileApplication.getInstance();
        //set event handlers
        vwAdvanced.setOnShowing(this::handleViewShowing);
        snackbar= new Snackbar("","OK", e -> snackbar.cancel());
    }  
    /**
     * Get customer info and initialize view content.
     * @param event 
     */
    public void handleViewShowing(LifecycleEvent event){
        try{
            //get customer data
            customer=(Customer)app.getSession().get("customer");
            //show welcome to customer
            lblWelcome.setText("Welcome "+ customer.getFirstName()+" "+
                               customer.getMiddleInitial()+" "+
                               customer.getLastName());
            //clear accounts list
            btAccount.getItems().clear();
            //load accounts on account drop down button
            List<Account> accounts=customer.getAccounts();
            if(accounts!=null){
                customer.getAccounts().forEach((account) -> {
                    MenuItem menuItem=new MenuItem(account.toString());
                    menuItem.setId(account.getId().toString());
                    menuItem.setOnAction(this::handleAccountSelection);
                    btAccount.getItems().add(menuItem);
                });
                //select first account
                btAccount.setSelectedItem(btAccount.getItems().get(0));
                handleAccountSelection(new ActionEvent(btAccount.getItems().get(0),null));
            }
        }catch(Exception e){
            snackbar.setMessage(e.getMessage());
            snackbar.show();
        }
    }
    /**
     * Handler for ActionEvents on Account selection on DropdownButton.
     * @param event the ActionEvent
     */
    public void handleAccountSelection(ActionEvent event){
        try{
            //Get selected account: account id is the same as the menuitem id 
            String AccID=((MenuItem)event.getSource()).getId();
            //Set current balance as selected account balance
            lblCurrentBalance.setText("Current Balance : "+ 
                                       customer.getAccounts().stream()
                                        .filter(acc->acc.getId().equals(new Long(AccID)))
                                        .map(Account::getBalance)
                                        .findFirst().get().toString());
            //Set account movements as items for lstMovements
            List <Movement> movements=customer.getAccounts().stream()
                                        .filter(acc->acc.getId().equals(new Long(AccID)))
                                        .findFirst()
                                        .get().getMovements();
            if(movements!=null)
                lstMovements.setItems(FXCollections.observableList(movements));
            else{
                lstMovements.setItems(FXCollections.emptyObservableList());
                throw new Exception("No movements for this account!!");
            }
        }catch(Exception e){
            snackbar.setMessage(e.getMessage());
            snackbar.show();
        }
    }
}
