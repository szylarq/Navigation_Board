package navigation.dao;

import DBAccess.NavegacionDAOException;
import model.Navegacion;

public class NavigationDAO{   
    public static Navegacion initializeInstanceDAO() {
        try {
            return Navegacion.getSingletonNavegacion();
        } catch (NavegacionDAOException e) {
            e.printStackTrace();
            
            return null;
        }
    }
}