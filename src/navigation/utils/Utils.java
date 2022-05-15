/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package navigation.utils;

import java.util.Map;
import navigation.PoiUPVApp;
import navigation.controllers.FXMLDocumentController;

/**
 *
 * @author User
 */
public class Utils {
    
    private static final String VIEWS_FOLDER = "views/";
    private static final String FXML_EXTENSION = ".fxml";
    
    private static final Map<Class<?>, String> fxmlControllersMap = Map.of(
            FXMLDocumentController.class, "FXMLDocument" 
    );
    
    public static String getFXMLName (Class<?> controllerClass) {
        return VIEWS_FOLDER + fxmlControllersMap.get(controllerClass) + FXML_EXTENSION;
    }
}
