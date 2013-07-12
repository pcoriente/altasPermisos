/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mdPermisos;

import daoPermisos.DaoPer;
import dominios.DominioUsuarios;
import dominios.Modulo;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import utilerias.Utilerias;

/**
 *
 * @author Comodoro
 */
@ManagedBean
@RequestScoped
public class MbUsuarios {

    /**
     * Creates a new instance of MbUsuarios
     */
    public MbUsuarios() {
    }
    private List<SelectItem> listaDbs;
    DominioUsuarios u = new DominioUsuarios();
    DominioUsuarios u2 = new DominioUsuarios();
    Modulo m = new Modulo();
    ArrayList<DominioUsuarios> tablaUsuarios = new ArrayList<>();

    public DominioUsuarios getU2() {
        return u2;
    }

    public void setU2(DominioUsuarios u2) {
        this.u2 = u2;
    }

    public ArrayList<DominioUsuarios> getTablaUsuarios() {
        return tablaUsuarios;
    }

    public void setTablaUsuarios(ArrayList<DominioUsuarios> tablaUsuarios) {
        this.tablaUsuarios = tablaUsuarios;
    }

    public Modulo getM() {
        return m;
    }

    public void setM(Modulo m) {
        this.m = m;
    }

    public DominioUsuarios getU() {
        return u;
    }

    public void setU(DominioUsuarios u) {
        this.u = u;
    }

    public List<SelectItem> getListaDbs() throws SQLException {
        listaDbs = dameValores();
        return listaDbs;
    }

    public void setListaDbs(List<SelectItem> listaDbs) {
        this.listaDbs = listaDbs;
    }

    private List<SelectItem> dameValores() throws SQLException {
        List<SelectItem> usuarios = new ArrayList<>();
        ArrayList<DominioUsuarios> du = new ArrayList<DominioUsuarios>();
        DaoPer dp = new DaoPer();
        DominioUsuarios dominioUsuario = new DominioUsuarios();
        dominioUsuario.setUsuario("Seleccione un Usuario");
        dominioUsuario.setPassword("0");
        SelectItem Si = new SelectItem(dominioUsuario, dominioUsuario.getUsuario());
        usuarios.add(Si);
        du = dp.dameUsuarios();
        for (DominioUsuarios d : du) {
            usuarios.add(new SelectItem(d, d.getUsuario()));
        }
        return usuarios;
    }

    public void guardarDatos() {
        if (u.getIdUsuario() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario no encontrado", "Seleccione un Usuario"));
        } else {
            u.getUsuario();
            System.err.println(u.getPassword());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Permisos Concedidos", "Datos Guardados"));
        }
    }

    public void insertarDatos() throws SQLException {
        DaoPer daoUsuario = new DaoPer();
        Utilerias utilerias = new Utilerias();
        String fecha = utilerias.dameFecha();
        u.setFechaCreacion(fecha);
        daoUsuario.insertarUsuario(u);
        u.setUsuario("");
        u.setLogin("");
        u.setActualizacion("");
        u.setEmail("");
        u.setIdUsuario(0);
        u.setPassword("");
        u.setStatus(0);
    }

    public void eliminarObjetoUsuarios() {
        u.setUsuario("");
        u.setLogin("");
        u.setActualizacion("");
        u.setEmail("");
        u.setIdUsuario(0);
        u.setPassword("");
        u.setStatus(0);
    }

    public void guardarModulo() throws SQLException {
        DaoPer daoPer = new DaoPer();
        daoPer.guardarModulo(m);
    }
}
