package com.gluonapplicationsimple.ui;

import com.gluonhq.charm.glisten.mvc.View;
import java.io.IOException;
import javafx.fxml.FXMLLoader;

public class AdvancedViewFactory {

    public static View getView() {
        View view=null;
        try{
            view=FXMLLoader.load(AdvancedViewFactory.class.getResource("fxml/AdvancedViewFXML.fxml"));
        }catch (IOException e) {
            System.out.println("IOException: " + e);
            view=new View();
        }
        return view;
    }

}
