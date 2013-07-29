/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mdPermisos;

import daoPermisos.DaoPer;
import dominios.Acciones;
import dominios.BaseDatos;
import dominios.DominioUsuarios;
import dominios.Modulo;
import dominios.Perfiles;
import dominios.TablaAcciones;
import dominios.UsuarioPerfil;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import org.primefaces.model.DualListModel;
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
    private List<SelectItem> listaUsuarios;
    private List<SelectItem> listaPerfiles;
    private List<SelectItem> listaModulos;
    private List<SelectItem> listaBaseDatos;
    private List<SelectItem> listaAcciones;
    DominioUsuarios u = new DominioUsuarios();
    DominioUsuarios u2 = new DominioUsuarios();
    Modulo m = new Modulo();
    Modulo m2 = new Modulo();
    Modulo m3 = new Modulo();
    BaseDatos bd = new BaseDatos();
    ArrayList<DominioUsuarios> tablaUsuarios = new ArrayList<DominioUsuarios>();
    private boolean s;
    Modulo modulo = new Modulo();
    Acciones acciones = new Acciones();
    Acciones accionesCmb = new Acciones();
    Perfiles perfil = new Perfiles();
    Perfiles perfil2 = new Perfiles();
    DualListModel<Modulo> pickModulos = new DualListModel<Modulo>();
    ArrayList<Modulo> moduloOrigen = new ArrayList<Modulo>();
    ArrayList<Modulo> moduloFinal = new ArrayList<Modulo>();
    ArrayList<Modulo> mPrueba = new ArrayList<Modulo>();
    DualListModel<Acciones> pickAcciones = new DualListModel<Acciones>();
    ArrayList<Acciones> accionesOrigen = new ArrayList<Acciones>();
    ArrayList<Acciones> accionesDestino = new ArrayList<Acciones>();

    public ArrayList<Acciones> getAccionesOrigen() {
        return accionesOrigen;
    }

    public void setAccionesOrigen(ArrayList<Acciones> accionesOrigen) {
        this.accionesOrigen = accionesOrigen;
    }

    public ArrayList<Acciones> getAccionesDestino() {
        return accionesDestino;
    }

    public void setAccionesDestino(ArrayList<Acciones> accionesDestino) {
        this.accionesDestino = accionesDestino;
    }

    public DualListModel<Acciones> getPickAcciones() {
        pickAcciones = new DualListModel<Acciones>(accionesOrigen, accionesDestino);
        return pickAcciones;
    }

    public void setPickAcciones(DualListModel<Acciones> pickAcciones) {
        this.pickAcciones = pickAcciones;
    }

    public MbUsuarios() throws SQLException {
    }

    public Modulo getModulo() {
        return modulo;
    }

    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }

    public Acciones getAccionesCmb() {
        return accionesCmb;
    }

    public void setAccionesCmb(Acciones accionesCmb) {
        this.accionesCmb = accionesCmb;
    }

    public List<SelectItem> getListaAcciones() throws SQLException {
        listaAcciones = dameListaAcciones();
        return listaAcciones;
    }

    public void setListaAcciones(List<SelectItem> listaAcciones) {
        this.listaAcciones = listaAcciones;
    }

    public ArrayList<Modulo> getModuloOrigen() {
        return moduloOrigen;
    }

    public void setModuloOrigen(ArrayList<Modulo> moduloOrigen) {
        this.moduloOrigen = moduloOrigen;
    }

    public ArrayList<Modulo> getModuloFinal() {
        return moduloFinal;
    }

    public void setModuloFinal(ArrayList<Modulo> moduloFinal) {
        this.moduloFinal = moduloFinal;
    }

    public ArrayList<Modulo> getmPrueba() {
        return mPrueba;
    }

    public void setmPrueba(ArrayList<Modulo> mPrueba) {
        this.mPrueba = mPrueba;
    }

    public DualListModel<Modulo> getPickModulos() throws SQLException {
        DaoPer p = new DaoPer();
        moduloOrigen = p.dameModulos();
        pickModulos = new DualListModel<Modulo>(moduloOrigen, moduloFinal);
        return pickModulos;
    }

    public void setPickModulos(DualListModel<Modulo> pickModelos) {
        this.pickModulos = pickModelos;
    }

    public Modulo getM3() {
        return m3;
    }

    public void setM3(Modulo m3) {
        this.m3 = m3;
    }

    public Perfiles getPerfil2() {
        return perfil2;
    }

    public void setPerfil2(Perfiles perfil2) {
        this.perfil2 = perfil2;
    }

    public BaseDatos getBd() {
        return bd;
    }

    public void setBd(BaseDatos bd) {
        this.bd = bd;
    }

    public Perfiles getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfiles perfil) {
        this.perfil = perfil;
    }

    public Acciones getAcciones() {
        return acciones;
    }

    public void setAcciones(Acciones acciones) {
        this.acciones = acciones;
    }

    public List<SelectItem> getListaUsuarios() throws SQLException {
        listaUsuarios = dameValores();
        return listaUsuarios;
    }

    public void setListaUsuarios(List<SelectItem> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public List<SelectItem> getListaBaseDatos() {
        try {
            listaBaseDatos = dameBd();
        } catch (SQLException ex) {
            Logger.getLogger(MbUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaBaseDatos;
    }

    public void setListaBaseDatos(List<SelectItem> listaBaseDatos) {
        this.listaBaseDatos = listaBaseDatos;
    }

    public List<SelectItem> getListaPerfiles() throws SQLException {
        listaPerfiles = damePerfiles();
        return listaPerfiles;
    }

    public void setListaPerfiles(List<SelectItem> listaPerfiles) {
        this.listaPerfiles = listaPerfiles;
    }

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

    private List<SelectItem> dameValores() throws SQLException {
        List<SelectItem> usuarios = new ArrayList<SelectItem>();
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

    public void insertarDatos() throws SQLException, Exception {
        if (s == true) {
            u.setStatus2(1);
        } else {
            u.setStatus2(0);
        }
        u.setIdPerfil(perfil.getIdPerfiles());
        DaoPer daoUsuario = new DaoPer();
        Utilerias utilerias = new Utilerias();
        String fecha = utilerias.dameFecha();
        u.setFechaCreacion(fecha);
        daoUsuario.insertarUsuario(u);
//      u = new DominioUsuarios();
        u.setUsuario("");
    }

    public void guardarModulo() throws SQLException {
        DaoPer daoPer = new DaoPer();
        daoPer.guardarModulo(m);
    }

    private List<SelectItem> dameModulos() throws SQLException {
        List<SelectItem> Modulos = new ArrayList<SelectItem>();
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

    public void dameValoresCmb() throws SQLException {

        if (bd.getIdBaseDatos() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Seleccione una Base de Datos"));
        } else {

            perfil2.getIdUsuario();
            ArrayList<Acciones> acciones = new ArrayList<Acciones>();
            acciones = (ArrayList<Acciones>) pickAcciones.getTarget();
            UsuarioPerfil usuaPerfil = new UsuarioPerfil();
            String jndi = bd.getJndi();
            DaoPer daoPermisos = new DaoPer(jndi);
            usuaPerfil.setIdPerfil(perfil2.getIdPerfiles());
            usuaPerfil.setIdModulo(modulo.getIdModulo());
            mPrueba = (ArrayList<Modulo>) pickModulos.getSource();
            daoPermisos.insertarUsuarioPerfil(usuaPerfil, acciones);
            m2.setIdModulo(0);
            perfil2.setIdPerfiles(0);
            bd.setIdBaseDatos(0);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Se insertaron los datos Correctamente"));
        }
    }

    public void guardarAcciones() throws SQLException {
        if (acciones.isSta() == true) {
            acciones.setStatus(1);
        } else {
            acciones.setStatus(0);
        }
        acciones.getAccion();
        acciones.getIdBoton();
        acciones.setIdMOdulo(m3.getIdModulo());
        DaoPer daoPermisos = new DaoPer();
        daoPermisos.insertarAcciones(acciones);
        eliminarAcciones();
    }

    public void eliminarAcciones() {
        acciones.setAccion(null);
        acciones.setIdAccion(0);
        acciones.setIdMOdulo(0);
        acciones.setIdBoton(null);
        acciones.setSta(false);
        acciones.setStatus(0);
        acciones.setStatus(0);
        m3.setIdModulo(0);
    }

    private List<SelectItem> dameBd() throws SQLException {
        List<SelectItem> Bds = new ArrayList<SelectItem>();
        ArrayList<BaseDatos> bds = new ArrayList<BaseDatos>();
        DaoPer dp = new DaoPer();
        BaseDatos baseDatos = new BaseDatos();
        baseDatos.setBaseDatos("Seleccione BD");
        baseDatos.setIdBaseDatos(0);
        SelectItem itemModulo = new SelectItem(baseDatos, baseDatos.getBaseDatos());
        Bds.add(itemModulo);
        bds = dp.dameBaseDatos();
        for (BaseDatos bd : bds) {
            Bds.add(new SelectItem(bd, bd.getBaseDatos()));
        }
        return Bds;
    }

    public void guardarPerfil() throws SQLException {
        DaoPer daoPer = new DaoPer();
        u2.getIdUsuario();
        perfil.setIdUsuario(u2.getIdUsuario());
        daoPer.insertarPerfil(perfil);
    }

    private List<SelectItem> damePerfiles() throws SQLException {
        List<SelectItem> perfiles = new ArrayList<SelectItem>();
        ArrayList<Perfiles> perfil = new ArrayList<Perfiles>();
        DaoPer dp = new DaoPer();
        Perfiles pF = new Perfiles();
        pF.setPerfil("Seleccione Perfil");
        pF.setIdPerfiles(0);
        SelectItem itemModulo = new SelectItem(pF, pF.getPerfil());
        perfiles.add(itemModulo);
        perfil = dp.damePefiles();
        for (Perfiles pf : perfil) {
            perfiles.add(new SelectItem(pf, pf.getPerfil()));
        }
        return perfiles;
    }

    public void dameValoresPickList() {
        ArrayList<Modulo> jjj = new ArrayList<Modulo>();
        ArrayList<Modulo> jjji = new ArrayList<>();
        jjj = (ArrayList<Modulo>) pickModulos.getTarget();
        jjj = (ArrayList<Modulo>) pickModulos.getSource();
    }

    private List<SelectItem> dameListaAcciones() throws SQLException {
        List<SelectItem> ListAcciones = new ArrayList<SelectItem>();
        ArrayList<Acciones> du = new ArrayList<Acciones>();
        DaoPer dp = new DaoPer();
        Acciones accions = new Acciones();
        accions.setAccion("Seleccione una Accion");
        accions.setIdAccion(0);
        SelectItem Si = new SelectItem(accions, accions.getAccion());
        ListAcciones.add(Si);
        du = dp.dameAcciones();
        for (Acciones d : du) {
            ListAcciones.add(new SelectItem(d, d.getAccion()));
        }
        return ListAcciones;
    }

    public void dameModulosAcciones() throws SQLException {

        String nomBd = bd.getBaseDatos();
        int idPerfil = perfil2.getIdPerfiles();
        int idModulo = modulo.getIdModulo();

        DaoPer daoPermisos = new DaoPer();
        ArrayList<Acciones> acciones = new ArrayList<Acciones>();
        acciones = daoPermisos.dameValores(nomBd, idModulo, idPerfil);
        for (Acciones ac : acciones) {
            if (ac.getIdPerfil() == 0) {
                accionesOrigen.add(ac);
            } else {
                accionesDestino.add(ac);
            }
        }
    }

    public String entrar() {
        String valor = "altasPermisos";
        return valor;
    }
}