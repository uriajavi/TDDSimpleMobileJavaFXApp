package com.gluonapplicationsimple;

import com.gluonapplicationsimple.ui.AdvancedViewFactory;
import com.gluonapplicationsimple.ui.BasicViewFactory;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.license.License;
import com.gluonhq.charm.glisten.visual.Swatch;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

@License(key="7e71ebf2-bb31-44b7-806e-2cb0e3a7f4ba")
public class GluonApplicationSimple extends MobileApplication {

    public static final String ADVANCED_VIEW = "Advanced View";

    @Override
    public void init() {
        addViewFactory(HOME_VIEW, BasicViewFactory::getView);
        addViewFactory(ADVANCED_VIEW, AdvancedViewFactory::getView);
    }

    @Override
    public void postInit(Scene scene) {
        Swatch.BLUE.assignTo(scene);

        ((Stage) scene.getWindow()).getIcons()
                .add(new Image(GluonApplicationSimple.class.getResourceAsStream("/icon.png")));
    }
}
