package com.gluonapplicationsimple.ui;

import com.gluonhq.charm.glisten.mvc.View;
import java.io.IOException;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;

public class BasicViewFactory {

    public static View getView() {
        View view=null;
        try{
            view=FXMLLoader.load(BasicViewFactory.class.getResource("fxml/BasicViewFXML.fxml"));
        }catch (IOException e) {
            Logger.getLogger("com.gluonapplicationsimple.ui").severe("Error loading FXML "+e.getMessage());
            view=new View();
        }
        return view;
    }

}
