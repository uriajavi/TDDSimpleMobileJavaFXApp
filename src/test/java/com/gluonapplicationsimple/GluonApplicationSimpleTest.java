/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gluonapplicationsimple;

import java.util.concurrent.TimeoutException;
import com.gluonhq.charm.glisten.application.MobileApplication;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

/**
 * Test class for TDD Mobile JavaFX application class.
 * @author javi
 */
public class GluonApplicationSimpleTest {
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
     * Test that the application is a JavaFX application. Note the use of 
     * Class.isAssignableFrom method.
     * @throws java.util.concurrent.TimeoutException
     */
    @Test
    public void testIsJavaFXMobileApplication() throws TimeoutException {
        assertTrue("The app is not a JavaFX Mobile application!!!",
            MobileApplication.class.isAssignableFrom(app.getClass()));
    }
    /**
     * Test that the home view is visible.
     */
    @Test
    public void testHomeViewIsVisible(){
        assertTrue("Home view is not present!!",app.isViewPresent(app.HOME_VIEW));
        app.switchView(app.HOME_VIEW);
        verifyThat(app.getView(),isVisible());
    }
    /**
     * Test that app bar is visible.
     */
    @Test
    public void testAppBarIsVisible(){
        verifyThat(app.getAppBar(),isVisible());
    }
}
