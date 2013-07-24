/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converterPickList;

import daoPermisos.DaoPer;
import dominios.Modulo;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

/**
 *
 * @author Comodoro
 */
public class ConverterPikList implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent arg1, String arg2) {
        Object ret = null;
        Modulo m = new Modulo();
        DaoPer p = new DaoPer();
        int idModulo = Integer.parseInt(arg2);
        try {
            m = p.dameModulo(idModulo);
        } catch (SQLException ex) {
            Logger.getLogger(ConverterPikList.class.getName()).log(Level.SEVERE, null, ex);
        }
        return m;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        String str = "";
        Modulo m = (Modulo) value;
        str = Integer.toString(m.getIdModulo());
        return str;
    }
}
