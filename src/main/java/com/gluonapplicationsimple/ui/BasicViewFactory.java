package com.gluonapplicationsimple.ui;

import com.gluonhq.charm.glisten.mvc.View;
import java.io.IOException;
import javafx.fxml.FXMLLoader;

public class BasicViewFactory {

    public static View getView() {
        View view=null;
        try{
            view=FXMLLoader.load(BasicViewFactory.class.getResource("fxml/BasicViewFXML.fxml"));
        }catch (IOException e) {
            System.out.println("IOException: " + e);
            view=new View();
        }
        return view;
    }

}
