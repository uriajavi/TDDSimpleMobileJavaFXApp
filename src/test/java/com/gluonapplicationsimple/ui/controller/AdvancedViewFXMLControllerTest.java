/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gluonapplicationsimple.ui.controller;

import clientside.model.Customer;
import com.gluonapplicationsimple.GluonApplicationSimple;
import static com.gluonhq.charm.glisten.application.MobileApplication.HOME_VIEW;
import com.gluonhq.charm.glisten.control.DropdownButton;
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
public class AdvancedViewFXMLControllerTest extends FxRobot{
    /**
     * JavaFX Application instance to be tested.  
     */
    private static GluonApplicationSimple app;
    private Customer customer;
    private static final String CUSTOMER_WITH_SEVERAL_ACCOUNTS="102263301";
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
            //Does nothing if welcome layer is not shown.
        }
        //Switch to basic view and wait for it to load
        WaitForAsyncUtils.waitForAsyncFx(10,()->app.switchView(HOME_VIEW));
    }
    /**
     * Open Advanced View from Home View entering customerID
     * @param customerID 
     */
    private void enterCustomerId(String customerID){
        try{
            WaitForAsyncUtils.waitForFxEvents();
            Node close=lookup("#btEnter").query();
            clickOn("#tfCustomerID");
            eraseText(10);
            write(customerID);
            verifyThat("#btEnter",isEnabled());
            clickOn("#btEnter");
            WaitForAsyncUtils.waitForFxEvents();
            customer=(Customer)app.getSession().get("customer");
        }catch(EmptyNodeQueryException e){
            //does nothing
        }
    }
    /**
     * Test welcome for customers is visible.
     */
    @Test
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
     * Test current balance is loaded when account is selected.
     */
    @Test
    public void testCurrentBalanceOnAccountSelect(){
        enterCustomerId(CUSTOMER_WITH_SEVERAL_ACCOUNTS);
        //first account should be selected by default
        verifyThat("#lblCurrentBalance",
                    hasText("Current Balance : "+
                                customer.getAccounts().get(0).getBalance()));
        //click on second account
        clickOn("#btAccount");
        clickOn(customer.getAccounts().get(1).toString());
        WaitForAsyncUtils.waitForFxEvents();
        //this sleep is to allow dropdown menuitmes to hide
        sleep(1000);
        verifyThat("#lblCurrentBalance",
                    hasText("Current Balance : "+
                                customer.getAccounts().get(1).getBalance()));
    }
}
