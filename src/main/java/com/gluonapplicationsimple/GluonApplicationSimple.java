package com.gluonapplicationsimple;

import com.gluonapplicationsimple.ui.AdvancedViewFactory;
import com.gluonapplicationsimple.ui.BasicViewFactory;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.license.License;
import com.gluonhq.charm.glisten.visual.Swatch;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import clientside.controller.CustomerManager;
import clientside.controller.CustomerManagerFactory;
import java.util.HashMap;

@License(key="7e71ebf2-bb31-44b7-806e-2cb0e3a7f4ba")
public class GluonApplicationSimple extends MobileApplication {

    public static final String ADVANCED_VIEW = "Advanced View";
    private HashMap session;

    @Override
    public void init() {
        //get views
        addViewFactory(HOME_VIEW, BasicViewFactory::getView);
        addViewFactory(ADVANCED_VIEW, AdvancedViewFactory::getView);
        //create session
        session=new HashMap();
        //get business logic controller and stores in the session object
        CustomerManager manager= CustomerManagerFactory.getCustomerManager();
        manager.setServerName("localhost");
        session.put("manager",manager);
    }

    @Override
    public void postInit(Scene scene) {
        Swatch.BLUE.assignTo(scene);

        ((Stage) scene.getWindow()).getIcons()
                .add(new Image(GluonApplicationSimple.class.getResourceAsStream("/icon.png")));
    }
    
    public HashMap getSession(){
        return this.session;
    }
}
