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
    private List<SelectItem> listaModulos;
    DominioUsuarios u = new DominioUsuarios();
    DominioUsuarios u2 = new DominioUsuarios();
    Modulo m = new Modulo();
    Modulo m2 = new Modulo();
    ArrayList<DominioUsuarios> tablaUsuarios = new ArrayList<>();
    private boolean s;

    public boolean isS() {
        return s;
    }

    public void setS(boolean s) {
        this.s = s;
    }

    public Modulo getM2() {
        return m2;
    }

    public void setM2(Modulo m2) {
        this.m2 = m2;
    }

    public List<SelectItem> getListaModulos() throws SQLException {
        listaModulos = dameModulos();
        return listaModulos;
    }

    public void setListaModulos(List<SelectItem> listaModulos) {
        this.listaModulos = listaModulos;
    }

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
        dominioUsuario.setIdUsuario(0);
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
        if (s == true) {
            u.setStatus2(1);
        } else {
            u.setStatus2(0);
        }
        DaoPer daoUsuario = new DaoPer();
        Utilerias utilerias = new Utilerias();
        String fecha = utilerias.dameFecha();
        u.setFechaCreacion(fecha);
        daoUsuario.insertarUsuario(u);
        u = new DominioUsuarios();
        u.setUsuario(null);
    }

    public void guardarModulo() throws SQLException {
        DaoPer daoPer = new DaoPer();
        daoPer.guardarModulo(m);
    }

    private List<SelectItem> dameModulos() throws SQLException {
        List<SelectItem> Modulos = new ArrayList<>();
        ArrayList<Modulo> modul = new ArrayList<Modulo>();
        DaoPer dp = new DaoPer();
        Modulo modulo = new Modulo();
        modulo.setModulo("Seleccione un Modulo");
        modulo.setIdModulo(0);
        SelectItem itemModulo = new SelectItem(modulo, modulo.getModulo());
        Modulos.add(itemModulo);
        modul = dp.dameModulos();
        for (Modulo d : modul) {
            Modulos.add(new SelectItem(d, d.getModulo()));
        }
        return Modulos;
    }

    public void dameValoresCmb() {

        m2.getModulo();
        System.err.println("El modulo es= " + m2.getModulo() + u2.getUsuario());
    }
}
