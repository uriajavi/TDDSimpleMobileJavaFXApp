/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gluonapplicationsimple.ui.controller;

import com.gluonapplicationsimple.GluonApplicationSimple;
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
import org.testfx.service.query.EmptyNodeQueryException;

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
        //Open Advanced View with customer ID=102263301
        try{
            Node close=lookup("#btEnter").query();
            clickOn("#tfCustomerID");
            write("102263301");
            verifyThat("#btEnter",isEnabled());
            clickOn("#btEnter");
        }catch(EmptyNodeQueryException e){
            //Does nothing
        }
    }
    /**
     * Test welcome for customer is visible.
     */
    @Test
    public void testCustomerWelcomeIsVisible() {
        verifyThat("Welcome John S. Smith",isVisible());
    }
    /**
     * Test first account is selected and shown.
     */
    @Test
    public void testFirstAccountIsSelected(){
        DropdownButton btAccount=lookup("#btAccount").query();
        assertEquals("First account of the customer is not selected!!!",
                     "STANDARD # 1569874954",
                     btAccount.getSelectedItem().getText());
    }
}
