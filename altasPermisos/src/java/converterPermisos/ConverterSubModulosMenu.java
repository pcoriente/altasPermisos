/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converterPermisos;

import daoPermisos.DaoPer;
import dominios.ModulosSubMenus;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 *
 * @author Comodoro
 */
public class ConverterSubModulosMenu implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        int id;
        id = Integer.parseInt(value);
        DaoPer daoPermi = new DaoPer();
        ModulosSubMenus m = null;
        try {
            m = daoPermi.dameSubModulosMenu(id);
        } catch (SQLException ex) {
            Logger.getLogger(ConverterSubModulosMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        return m;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        ModulosSubMenus m;
        String id = null;
        try {
            m =  (ModulosSubMenus) value;
            id = Integer.toString(m.getIdSubMenu());
        } catch (Exception e) {
            System.err.println(e);
        }

        return id;
    }
}
