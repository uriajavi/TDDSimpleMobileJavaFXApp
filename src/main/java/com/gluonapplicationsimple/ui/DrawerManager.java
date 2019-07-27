/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gluonapplicationsimple.ui;

import com.gluonhq.charm.down.Platform;
import com.gluonhq.charm.down.Services;
import com.gluonhq.charm.down.plugins.LifecycleService;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.application.ViewStackPolicy;
import com.gluonhq.charm.glisten.control.Avatar;
import com.gluonhq.charm.glisten.control.NavigationDrawer;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.scene.image.Image;
import static com.gluonhq.charm.glisten.application.MobileApplication.HOME_VIEW;

/**
 * Navigation menu for simple mobile app.
 * @author javi
 */
public class DrawerManager {

    public static void buildDrawer(MobileApplication app) {
        NavigationDrawer drawer = app.getDrawer();
        
        NavigationDrawer.Header header = new NavigationDrawer.Header("Gluon Mobile",
                "Simple Mobile App",
                new Avatar(21, new Image(DrawerManager.class.getResourceAsStream("/icon.png"))));
        drawer.setHeader(header);
        
        final NavigationDrawer.Item primaryItem = new NavigationDrawer.ViewItem("Home", 
                MaterialDesignIcon.HOME.graphic(), HOME_VIEW, ViewStackPolicy.CLEAR);
        drawer.getItems().addAll(primaryItem);
        
        final NavigationDrawer.Item quitItem = new NavigationDrawer.Item("Quit", 
                    MaterialDesignIcon.EXIT_TO_APP.graphic());
        quitItem.selectedProperty().addListener((obs, ov, nv) -> {
                if (nv) {
                    Services.get(LifecycleService.class).ifPresent(LifecycleService::shutdown);
                }
        });
        drawer.getItems().add(quitItem);
    }
}
