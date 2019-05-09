/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gluonapplicationsimple.ui.controller;

import clientside.controller.CustomerManager;
import com.gluonapplicationsimple.GluonApplicationSimple;
import com.gluonhq.charm.glisten.control.DropdownButton;
import java.util.concurrent.TimeoutException;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.stage.Stage;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
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
    private static final String CUSTOMER_WITH_SEVERAL_ACCOUNTS="102263301";
    private static final String CUSTOMER_WITH_NO_ACCOUNTS="345678401";
    private static final String CUSTOMER_NON_EXISTENT="999999";
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
     */
    @Before
    public void setUp(){
        //Close initial gluon layer if it is shown.
        try{
            Node close=lookup("CLOSE").query();
            clickOn(close);
        }catch(EmptyNodeQueryException e){
            //Does nothing
        }
    }
    //@After
    public void cleanUp() throws Exception{
        FxToolkit.cleanupStages();
    }
    /**
     * Open Advanced View from Home View entering customerID
     * @param customerID 
     */
    private void enterCustomerId(String customerID){
        try{
            Node close=lookup("#btEnter").query();
            clickOn("#tfCustomerID");
            write(customerID);
            verifyThat("#btEnter",isEnabled());
            clickOn("#btEnter");
        }catch(EmptyNodeQueryException e){
            //Change customer in session
            app.getSession().replace("customer",
                    ((CustomerManager)app.getSession().get("manager"))
                            .getCustomerAccountsFullInfo(new Long(customerID)));
            //initialize view
            //TO DO
        }
    }
    /**
     * Test welcome for customer is visible.
     */
    @Test
    public void testCustomerWelcomeIsVisibleForCustomerWithAccounts() {
        enterCustomerId(CUSTOMER_WITH_SEVERAL_ACCOUNTS);
        verifyThat("Welcome John S. Smith",isVisible());
    }
    @Test
    public void testCustomerWelcomeIsVisibleForCustomerWithNoAccounts() {
        enterCustomerId(CUSTOMER_WITH_NO_ACCOUNTS);
        verifyThat("Welcome Raymond J. Williams",isVisible());
    }
    /**
     * Test first account is selected and shown.
     */
    @Test
    public void testFirstAccountIsSelected(){
        enterCustomerId(CUSTOMER_WITH_SEVERAL_ACCOUNTS);
        DropdownButton btAccount=lookup("#btAccount").query();
        assertEquals("First account of the customer is not selected!!!",
                     "STANDARD # 1569874954",
                     btAccount.getSelectedItem().getText());
    }
    /*@Test
    public void testErrorOn*/
}
