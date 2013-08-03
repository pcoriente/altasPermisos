/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converterPermisos;

import dominios.BaseDatos;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 *
 * @author Comodoro
 */
public class ConverterPickBds implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        BaseDatos bd = new BaseDatos();
        bd.setBaseDatos(value);
        return bd;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        BaseDatos bd = (BaseDatos) value;
        return bd.getBaseDatos();
    }
}
