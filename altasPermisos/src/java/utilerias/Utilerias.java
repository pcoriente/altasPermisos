/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilerias;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Comodoro
 */
public class Utilerias {

    public String dameFecha() {
        String fecha = null;
        Date d = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
         fecha = formateador.format(d);
        return fecha;
    }
}
