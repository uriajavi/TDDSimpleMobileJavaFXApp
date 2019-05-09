/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gluonapplicationsimple.ui.controller;

import com.gluonapplicationsimple.GluonApplicationSimple;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.Snackbar;
import java.util.concurrent.TimeoutException;
import javafx.scene.Node;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isInvisible;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import org.testfx.service.query.EmptyNodeQueryException;
import org.testfx.util.WaitForAsyncUtils;

/**
 * Simple test class for Mobile JavaFX UI Controller.
 * Note the extension from FxRobot to use its interacting methods.
 * @author javi
 */
public class BasicFXMLControllerTest extends FxRobot {
    /**
     * JavaFX Application instance to be tested.  
     */
    private static GluonApplicationSimple app;
    /**
     * Test data.
     */
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
     * Close initial Gluon Layer. 
     */
    @Before
    public void setUp(){
        try{
            Node close=lookup("CLOSE").query();
            clickOn(close);
        }catch(EmptyNodeQueryException e){
            //Does nothing
        }
    }
    /**
     * Test of initial state for the ui view.
     */
    @Test
    public void testInitialState() {
        verifyThat("Customer ID",isVisible());
        verifyThat("#tfCustomerID",isVisible());
        verifyThat("#btEnter",isVisible());
        verifyThat("#btEnter",isDisabled());
    }
    /**
     * Test of initial state for the ui view.
     */
    @Test
    public void testEnterIsEnabledDisabledOnCustomerIDChange(){
        verifyThat("Customer ID",isVisible());
        verifyThat("#tfCustomerID",isVisible());
        clickOn("#tfCustomerID");
        write(CUSTOMER_NON_EXISTENT);
        verifyThat("#btEnter",isEnabled());
        //clear written id
        eraseText(10);
        verifyThat("#btEnter",isDisabled());
    }
    /**
     * Test AppBar state and appearance.
     */
    @Test
    public void testAppBarAppearance(){
        assertEquals("AppBar title is not as expected!!!",
                      "Simple Mobile App",
                      MobileApplication.getInstance().getAppBar().getTitleText());
    }
    /**
     * Test that a message appears when Customer ID is not numeric.
     */
    @Test
    public void testCustomerIdIsNumeric(){
        clickOn("#tfCustomerID");
        write("thisIsNotANumber");
        verifyThat("#btEnter",isEnabled());
        clickOn("#btEnter");
        verifyThat("Customer ID must be numeric!!!",isVisible());
        //wait for error in snackbar to hide
        WaitForAsyncUtils.waitForFxEvents();
        //clear written id
        clickOn("#tfCustomerID");
        eraseText(16);
    }
    /**
     * Test error is shown for non existent customer.
     */
    @Test
    public void testErrorIsVisibleForNonExistentCustomer() {
        clickOn("#tfCustomerID");
        write(CUSTOMER_NON_EXISTENT);
        verifyThat("#btEnter",isEnabled());
        clickOn("#btEnter");
        verifyThat("Cannot find customer with id # "+CUSTOMER_NON_EXISTENT,isVisible());
        //wait for error in snackbar to hide
        WaitForAsyncUtils.waitForFxEvents();
        //clear written id
        clickOn("#tfCustomerID");
        eraseText(10);
    }
    /**
     * Test welcome for customer is visible.
     */
    @Test
    public void testCustomerWelcomeIsVisibleForCustomerWithAccounts() {
        clickOn("#tfCustomerID");
        write(CUSTOMER_WITH_SEVERAL_ACCOUNTS);
        verifyThat("#btEnter",isEnabled());
        clickOn("#btEnter");
        verifyThat("Welcome John S. Smith",isVisible());
    }
}
