/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gluonapplicationsimple.ui.controller;

import clientside.model.Customer;
import clientside.model.Movement;
import com.gluonapplicationsimple.GluonApplicationSimple;
import static com.gluonhq.charm.glisten.application.MobileApplication.HOME_VIEW;
import com.gluonhq.charm.glisten.control.CharmListView;
import com.gluonhq.charm.glisten.control.DropdownButton;
import java.util.List;
import java.util.concurrent.TimeoutException;
import javafx.scene.Node;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.LabeledMatchers.hasText;
import org.testfx.service.query.EmptyNodeQueryException;
import org.testfx.util.WaitForAsyncUtils;

/**
 * Test class for Mobile JavaFX UI advanced view controller.
 * Note the extension from FxRobot to use its interacting methods.
 * @author javi
 */
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AdvancedViewFXMLControllerTest extends FxRobot{
    /**
     * JavaFX Application instance to be tested.  
     */
    private static GluonApplicationSimple app;
    private Customer customer;
    private static final String CUSTOMER_WITH_SEVERAL_ACCOUNTS="299985563";
    private static final String CUSTOMER_WITH_NO_ACCOUNTS="345678401";
    private static final String CUSTOMER_NON_EXISTENT="9999999999";
    /**
     * Set up Java FX fixture for tests. This is a general approach for using a 
     * unique instance of the application in the test.
     * @throws java.util.concurrent.TimeoutException
     */
    @BeforeClass
    public static void setUpClass() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        app=(GluonApplicationSimple)FxToolkit.setupApplication(GluonApplicationSimple.class);
   }
    /**
     * Set up view state for testing. 
     * @throws java.util.concurrent.TimeoutException
     */
    @Before
    public void setUp() throws TimeoutException{
        //Close welcome gluon layer if it is shown.
        try{
            Node close=lookup("CLOSE").query();
            clickOn(close);
        }catch(EmptyNodeQueryException e){
            //Do nothing if welcome layer is not shown.
        }
        //Switch to basic view and wait for it to load
        interact(()->app.switchView(HOME_VIEW));
    }
    /**
     * Open Advanced View from Home View entering customerID
     * @param customerID 
     */
    private void enterCustomerId(String customerID){
        try{
            Node close=lookup("#btEnter").query();
            clickOn("#tfCustomerID");
            eraseText(10);
            write(customerID);
            verifyThat("#btEnter",isEnabled());
            clickOn("#btEnter");
            customer=(Customer)app.getSession().get("customer");
        }catch(EmptyNodeQueryException e){
            //do nothing
        }
    }
    /**
     * Test welcome for customers is visible.
     */
    @Test
    //@Ignore
    public void testCustomerWelcomeIsVisibleForCustomerWithAccounts(){
        enterCustomerId(CUSTOMER_WITH_SEVERAL_ACCOUNTS);
        verifyThat("Welcome "+customer.getFirstName()+" "+
                              customer.getMiddleInitial()+" "+
                              customer.getLastName()
                    ,isVisible());
    }
    @Test
    public void testCustomerWelcomeIsVisibleForCustomerWithNoAccounts(){
        enterCustomerId(CUSTOMER_WITH_NO_ACCOUNTS);
        verifyThat("Welcome "+customer.getFirstName()+" "+
                              customer.getMiddleInitial()+" "+
                              customer.getLastName()
                    ,isVisible());
    }
    /**
     * Test first account is selected and shown.
     */
    @Test
    public void testFirstAccountIsSelected(){
        enterCustomerId(CUSTOMER_WITH_SEVERAL_ACCOUNTS);
        DropdownButton btAccount=lookup("#btAccount").query();
        assertEquals("First account of the customer is not selected!!!",
                        customer.getAccounts().get(0).toString(),
                            btAccount.getSelectedItem().getText());
    }
    /**
     * Test current balance and movements are loaded when account is selected.
     */
    @Test
    public void testAccountSelection(){
        enterCustomerId(CUSTOMER_WITH_SEVERAL_ACCOUNTS);
        DropdownButton btAccount=lookup("#btAccount").query();
        CharmListView lstMovements=lookup("#lstMovements").query();
        //first account should be selected by default
        verifyThat("#lblCurrentBalance",
                    hasText("Current Balance : "+
                                customer.getAccounts().get(0).getBalance()+" $"));
        checkMovementsOnAccountSelect(0);
        //click on second account
        clickOn("#btAccount");
        clickOn(customer.getAccounts().get(1).toString());
        verifyThat("#lblCurrentBalance",
                    hasText("Current Balance : "+
                                customer.getAccounts().get(1).getBalance()+" $"));
        checkMovementsOnAccountSelect(1);
        //click on label to hide accounts list and wait for message on snackbar
        //to hide
        clickOn("#lblWelcome");
        sleep(1500);
    }
    /**
     * Test movements are loaded when account is selected.
     * @param accountEntry selected index for account list
     */
    public void checkMovementsOnAccountSelect(int accountEntry){
        CharmListView lstMovements=lookup("#lstMovements").query();
        List<Movement> movements=customer.getAccounts().get(accountEntry).getMovements();
        //first account should be selected by default
        if(movements !=null){
            //if there are movements, check movements list size
            assertEquals("Movements list size is not as expected!!!",
                        movements.size(),
                        lstMovements.itemsProperty().size());
            //check movements detailed info
            lstMovements.itemsProperty().stream()
                    .forEach(m->{
                        verifyThat(((Movement)m).getDescription(),isVisible());
                        verifyThat(((Movement)m).getAmount().toString()+" $",isVisible());
                        verifyThat(((Movement)m).getBalance().toString()+" $",isVisible());
                    });
        }    
        else
            //if there are not movements, verify info message
            verifyThat("No movements for this account!!",isVisible());
    }
}
