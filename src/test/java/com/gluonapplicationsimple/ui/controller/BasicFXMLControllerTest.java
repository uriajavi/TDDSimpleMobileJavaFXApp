/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gluonapplicationsimple.ui.controller;

import com.gluonapplicationsimple.GluonApplicationSimple;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.TextField;
import java.util.concurrent.TimeoutException;
import javafx.stage.Stage;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
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
    private static GluonApplicationSimple app;    /**
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
        write("9999999999");
        verifyThat("#btEnter",isEnabled());
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
}
