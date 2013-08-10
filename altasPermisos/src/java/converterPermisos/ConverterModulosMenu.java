/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converterPermisos;

import daoPermisos.DaoPer;
import dominios.ModulosMenus;
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
public class ConverterModulosMenu implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        ModulosMenus m = new ModulosMenus();
        int id = Integer.parseInt(value);
        DaoPer daoPermisos = new DaoPer();
        try {
            m = daoPermisos.dameModulosMenu(id);
        } catch (SQLException ex) {
            Logger.getLogger(ConverterModulosMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        return m;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        ModulosMenus m = (ModulosMenus) value;
        String id = Integer.toString(m.getIdMenu());
        return id;
    }
}
