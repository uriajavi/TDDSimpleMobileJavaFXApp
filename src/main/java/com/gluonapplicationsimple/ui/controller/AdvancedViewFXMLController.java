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
import com.gluonhq.charm.glisten.control.CharmListCell;
import com.gluonhq.charm.glisten.control.CharmListView;
import com.gluonhq.charm.glisten.control.DropdownButton;
import com.gluonhq.charm.glisten.control.LifecycleEvent;
import com.gluonhq.charm.glisten.control.Snackbar;
import com.gluonhq.charm.glisten.mvc.View;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

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
    private CharmListView<Movement,Date> lstMovements;
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
        //set cell factory for movements
        lstMovements.setCellFactory( it -> new CharmListCell<Movement>(){
            @Override 
            public void updateItem(Movement item, boolean empty) {
                super.updateItem(item, empty);
                if(item!=null && !empty){
                    Label lblDescription=new Label(item.getDescription());
                    lblDescription.setStyle("-fx-font-weight:bold;");
                    Label lblAmount=new Label(item.getAmount().toString()+" $");
                    setAmountColor(lblAmount,item.getAmount());
                    lblAmount.setStyle(lblAmount.getStyle()+"-fx-font-weight:bold;");
                    Label lblBalance=new Label(item.getBalance().toString()+" $");
                    //setAmountColor(lblBalance,item.getBalance());
                    VBox mvQuantities=new VBox();
                    mvQuantities.getChildren().addAll(lblAmount,lblBalance);
                    mvQuantities.setStyle("-fx-alignment:center-right;");
                    //lblAmount.setStyle("-fx-text-alignment:right;");
                    VBox mvDescription=new VBox();
                    mvDescription.getChildren().add(lblDescription);
                    mvDescription.setStyle("-fx-alignment:top-left");
                    HBox mvDetail=new HBox();
                    mvDetail.setHgrow(mvDescription,Priority.ALWAYS);
                    mvDetail.setHgrow(mvQuantities,Priority.ALWAYS);
                    mvDetail.getChildren().addAll(mvDescription,mvQuantities);
                    setGraphic(mvDetail);
                } else {
                    setText("");
                }
            }
        });
        //set movements list headers
        lstMovements.setHeadersFunction(Movement::getTimestamp);
        /*lstMovements.setHeaderCellFactory( it -> new CharmListCell<Movement>(){
            @Override 
            public void updateItem(Movement item, boolean empty) {
                super.updateItem(item, empty);
                if(item!=null && !empty){
                    setText(DateFormat.getDateInstance().format(item.getTimestamp()));
                } else {
                    setText("");
                }
            }
        }); */
    }  
    /**
     * Get customer info and initialize view content.
     * @param event 
     */
    public void handleViewShowing(LifecycleEvent event){
        try{
            //set appbar text
            app.getAppBar().setTitleText("Bank Statement");
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
                                        .findFirst().get().toString()+" $");
            lblCurrentBalance.setStyle(lblCurrentBalance.getStyle()+
                                       "-fx-font-weight:bold;" );
            //Set account movements as items for lstMovements
            List <Movement> movements=customer.getAccounts().stream()
                                        .filter(acc->acc.getId().equals(new Long(AccID)))
                                        .findFirst()
                                        .get().getMovements();
            if(movements!=null){
                lstMovements.setItems(FXCollections.observableList(movements));
            }
            else{
                lstMovements.setItems(FXCollections.emptyObservableList());
                throw new Exception("No movements for this account!!");
            }
        }catch(Exception e){
            snackbar.setMessage(e.getMessage());
            snackbar.show();
        }
    }
    /**
     * Set text color for a label showing an amount. 
     * @param label The label showing the amount.
     * @param amount The amount to be shown.
     */
    private void setAmountColor(Label label, Double amount){
        if(amount<0)
            label.setStyle("-fx-text-fill:red;");
        else 
            label.setStyle("-fx-text-fill:green;");
    }
}
