/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mbPermisos;

import daoPermisos.DaoPer;
import dominios.Acciones;
import dominios.BaseDatos;
import dominios.DominioUsuarios;
import dominios.Modulo;
import dominios.ModuloMenu;
import dominios.ModuloSubMenu;
import dominios.Perfiles;
import dominios.UsuarioPerfil;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.primefaces.model.DualListModel;
import utilerias.Utilerias;

/**
 *
 * @author Comodoro
 */
@ManagedBean
@SessionScoped
public class MbUsuarios implements Serializable {

    /**
     * Creates a new instance of MbUsuarios
     */
    private List<SelectItem> listaUsuarios;
    private List<SelectItem> listaPerfiles;
    private List<SelectItem> listaModulos;
    private List<SelectItem> listaBaseDatos;
    private List<SelectItem> listaAcciones;
    private ArrayList<SelectItem> listaModulosMenu;
    private ArrayList<SelectItem> listaModulosSubMenu;
    DominioUsuarios u = new DominioUsuarios();
    DominioUsuarios u2 = new DominioUsuarios();
    Modulo m = new Modulo();
    Modulo m2 = new Modulo();
    Modulo m3 = new Modulo();
    BaseDatos bd = new BaseDatos();
    BaseDatos bdAltas = new BaseDatos();
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
    DualListModel<BaseDatos> pickBd = new DualListModel<BaseDatos>();
    ArrayList<BaseDatos> DestinoBd = new ArrayList<BaseDatos>();
    ArrayList<BaseDatos> OrigenBd = new ArrayList<BaseDatos>();
    ModuloMenu moduloMenu = new ModuloMenu();
    ModuloSubMenu moduloSubMenu = new ModuloSubMenu();

    public ArrayList<SelectItem> getListaModulosSubMenu() throws SQLException {
        return listaModulosSubMenu;
    }

    public void setListaModulosSubMenu(ArrayList<SelectItem> listaModulosSubMenu) {
        this.listaModulosSubMenu = listaModulosSubMenu;
    }

    public ArrayList<SelectItem> getListaModulosMenu() throws SQLException {
        if(this.listaModulosMenu==null) {
            listaModulosMenu = this.dameModulosMenu();
        }
        return listaModulosMenu;
    }

    public void setListaModulosMenu(ArrayList<SelectItem> listaModulosMenu) {
        this.listaModulosMenu = listaModulosMenu;
    }

    public DualListModel<BaseDatos> getPickBd() throws SQLException {
        ArrayList<BaseDatos> a1 = new ArrayList<BaseDatos>();
        ArrayList<BaseDatos> a2 = new ArrayList<BaseDatos>();
        DaoPer daoPermisos = new DaoPer();
        a1 = daoPermisos.dameListaBds();
        a2 = daoPermisos.dameBaseDatos();
//      a1.remove(1);
        for (int x = 0; x < a1.size(); x++) {
            for (int y = 0; y < a2.size(); y++) {
                BaseDatos b = new BaseDatos();
                BaseDatos b2 = new BaseDatos();
                b.setBaseDatos(a1.get(x).getBaseDatos());
                b2.setBaseDatos(a2.get(y).getBaseDatos());
                if (a1.get(x).getBaseDatos().equals(a2.get(y).getBaseDatos())) {
                    a1.remove(x);
                    x--;
                    break;
                }
            }
        }
        pickBd = new DualListModel<BaseDatos>(a1, a2);
        return pickBd;
    }

    public void setPickBd(DualListModel<BaseDatos> pickBd) {
        this.pickBd = pickBd;
    }

    public ArrayList<BaseDatos> getDestinoBd() {
        return DestinoBd;
    }

    public void setDestinoBd(ArrayList<BaseDatos> DestinoBd) {
        this.DestinoBd = DestinoBd;
    }

    public ArrayList<BaseDatos> getOrigenBd() throws SQLException {

        return OrigenBd;
    }

    public void setOrigenBd(ArrayList<BaseDatos> OrigenBd) {
        this.OrigenBd = OrigenBd;
    }

    public BaseDatos getBdAltas() {
        return bdAltas;
    }

    public void setBdAltas(BaseDatos bdAltas) {
        this.bdAltas = bdAltas;
    }

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

    public DualListModel<Acciones> getPickAcciones() throws SQLException {
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
        DaoPer daoUsuario = new DaoPer();
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = null;
        boolean loggedIn = false;
        boolean validarEmail = false;
        Utilerias utilerias = new Utilerias();
        if (u.getUsuario().equals("") && u.getLogin().equals("") && u.getPassword().equals("") && u.getEmail().equals("")) {
            loggedIn = false;
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Llene todos los Campos");
        }
        if (u.getUsuario().equals("")) {
            loggedIn = false;
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Escriba un Usuario");
        } else if (u.getLogin().equals("")) {
            loggedIn = false;
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Escriba un Login");
        } else if (u.getPassword().equals("")) {
            loggedIn = false;
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Escriba un Password");
        } else if (u.getEmail().equals("")) {
            loggedIn = false;
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Escriba un Email");
        } else {
            validarEmail = utilerias.validarEmail(u.getEmail());
            if (validarEmail == true) {
                String fecha = utilerias.dameFecha();
                daoUsuario.insertarUsuario(u, bd.getIdBaseDatos(), perfil2.getIdPerfiles());
                bd.getIdBaseDatos();
                perfil2.getIdPerfiles();
                u = new DominioUsuarios();
                loggedIn = true;
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Nuevo Usuario Disponible");
            } else {
                loggedIn = false;
                msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Ingrese un Email Valido");
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.addCallbackParam("loggedIn", loggedIn);
    }

    public void guardarModulo() throws SQLException {
        String strSubMenu = this.moduloSubMenu.getSubMenu();
        DaoPer daoPer = new DaoPer();
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = null;
        boolean loggedIn = false;
        if (modulo.getIdModulo() != 0) {
            m.getModulo();
            m.setIdModulo(modulo.getIdModulo());
            daoPer.ActualizarModulos(m);
            loggedIn = true;
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "El Modulo fue Actualizado Correctamente");
        } else {
            if (m.getModulo().equals("")) {
                loggedIn = false;
                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Ingrese un Nombre de Modulo");
            } else {
                int identity = daoPer.guardarModulo(m);
                loggedIn = true;
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Nuevo Modulo Disponible");
                modulo.setIdModulo(identity);
                modulo.setModulo(m.getModulo());
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.addCallbackParam("loggedIn", loggedIn);
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

    public void guardarValores() throws SQLException {
        if (bd.getIdBaseDatos() == 0 && perfil2.getIdUsuario() == 0 && modulo.getIdModulo() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Seleccione todas las Opciones"));
        } else if (bd.getIdBaseDatos() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Seleccione una Base de Datos"));
        } else if (perfil2.getIdPerfiles() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Seleccione un perfil"));
        } else if (modulo.getIdModulo() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Seleccione un Modulo"));
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
            if (usuaPerfil.getIdModulo() != 0 && usuaPerfil.getIdPerfil() != 0) {
                daoPermisos.insertarUsuarioPerfil(usuaPerfil, acciones);
                m2.setIdModulo(0);
                perfil2.setIdPerfiles(0);
                bd.setIdBaseDatos(0);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Se insertaron los datos Correctamente"));
            }
        }
    }

    public void guardarAcciones() throws SQLException {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = null;
        boolean loggedIn = false;
        if (acciones.getAccion().equals("") && acciones.getIdBoton().equals("")) {
            loggedIn = false;
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Ingrese una Accion y un IdButom");
            dameModulosAcciones(0);
        } else if (acciones.getIdBoton().equals("")) {
            loggedIn = false;
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Ingrese el idBotom");
            dameModulosAcciones(0);
        } else if (acciones.getAccion().equals("")) {
            loggedIn = false;
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Ingrese una Acción");
            dameModulosAcciones(0);
        } else {
            loggedIn = true;
            DaoPer daoPermisos = new DaoPer();
            acciones.setIdMOdulo(modulo.getIdModulo());
            daoPermisos.insertarAcciones(acciones);
            dameModulosAcciones(0);
            acciones = new Acciones();
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Nuevas Acciones Disponibles");
//            RequestContext.getCurrentInstance().execute("dlg.hide();");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.addCallbackParam("loggedIn", loggedIn);
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
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = null;
        boolean loggedIn = false;
        if (perfil2.getIdPerfiles() != 0) {
            perfil.getPerfil();
            perfil.setIdPerfiles(perfil2.getIdPerfiles());
            daoPer.ActualizarPerfiles(perfil);
            loggedIn = true;
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Perfil Actualizado");
        } else {
            if (perfil.getPerfil().equals("")) {
                loggedIn = false;
                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Ingrese un Nombre de Perfil");
            } else {
                u2.getIdUsuario();
                perfil.setIdUsuario(u2.getIdUsuario());
                int identity = daoPer.insertarPerfil(perfil);
                dameModulosAcciones(identity);
                perfil2.setIdPerfiles(identity);
                perfil2.setPerfil(perfil.getPerfil());
                loggedIn = true;
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Nuevo Perfil Disponible");
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.addCallbackParam("loggedIn", loggedIn);
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
        ArrayList<Modulo> jjji = new ArrayList<Modulo>();
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

    public void dameModulosAcciones(int id) throws SQLException {
        if (bd.getIdBaseDatos() != 0) {
            bdAltas.setBaseDatos(bd.getBaseDatos());
            bdAltas.setIdBaseDatos(bd.getIdBaseDatos());
            bdAltas.setJndi(bd.getJndi());
        }
        if (perfil2.getIdPerfiles() != 0) {
            perfil.setIdPerfiles(perfil2.getIdPerfiles());
            perfil.setPerfil(perfil2.getPerfil());
        }
        if (modulo.getIdModulo() != 0) {
            m.setModulo(modulo.getModulo());
        }
        if (bd.getIdBaseDatos() == 0) {
            bd = new BaseDatos();
        }
        if (perfil2.getIdPerfiles() == 0) {
            perfil2 = new Perfiles();
        }
        if (modulo.getIdModulo() == 0) {
            modulo = new Modulo();
        }
        String nomBd = bd.getBaseDatos();
        int idPerfil = 0;
        if (id > 0) {
            idPerfil = id;
        } else {
            idPerfil = perfil2.getIdPerfiles();
        }
        int idModulo = modulo.getIdModulo();
        if (idPerfil != 0 && idModulo != 0 && nomBd != null) {
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
    }

    public String entrar() {
        String valor = "altasPermisos";
        return valor;
    }

    public void guardarBds() throws SQLException {
        DaoPer daoPermisos = new DaoPer();
        if (bd.getIdBaseDatos() != 0) {
            bdAltas.getBaseDatos();
            bdAltas.setIdBaseDatos(bd.getIdBaseDatos());
            bdAltas.getJndi();
            daoPermisos.actualizarBaseDatos(bdAltas);
        } else {
            int identyti = daoPermisos.guardarBaseDatos(bdAltas);
            bd.setBaseDatos(bdAltas.getBaseDatos());
            bd.setIdBaseDatos(identyti);
            bd.setJndi(bdAltas.getJndi());
        }

    }

    public void cancelarUsuarioPerfil() {
        bd = new BaseDatos();
        perfil2 = new Perfiles();
        modulo = new Modulo();
    }

    public void guardarBd() {
        ArrayList<BaseDatos> bd = (ArrayList<BaseDatos>) pickBd.getTarget();
        bd.size();
    }

    public void dameBdsPickList() throws SQLException {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = null;
        boolean loggedIn;
        ArrayList<BaseDatos> bd = new ArrayList<BaseDatos>();
        if (bd.size() > 0) {
        } else {
            bd = (ArrayList<BaseDatos>) pickBd.getTarget();
            DaoPer p = new DaoPer();
            p.insertarBd(bd);
            if (pickBd.getTarget().size() == 0) {
                loggedIn = true;
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Removido", "Las bases de Datos fueron removidas");
            } else {
                loggedIn = true;
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Agregadas", "Nuevas Bases de Datos disponibles");
            }
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.addCallbackParam("Bd´s", loggedIn);
        }
    }

    public void elimanarModulosClose(CloseEvent event) {
        m = new Modulo();
    }

    public void elimanarUsuariosClose(CloseEvent event) {
        u = new DominioUsuarios();
    }

    public void elimanarAltasUsuariosClose(CloseEvent event) {
        perfil = new Perfiles();
    }

    public void elimianarAccionesModulos(CloseEvent event) {
        acciones = new Acciones();
    }

    private ArrayList<SelectItem> dameModulosMenu() {
        ArrayList<SelectItem> modulosMenu = new ArrayList<>();
        try {
            ModuloMenu dModulosMenu = new ModuloMenu();
            dModulosMenu.setIdMenu(0);
            dModulosMenu.setMenu("SELECCIONE UN MODULO");
            SelectItem se = new SelectItem(dModulosMenu, dModulosMenu.getMenu());
            modulosMenu.add(se);
            
            DaoPer daoPermisos = new DaoPer();
            ArrayList<ModuloMenu> m = daoPermisos.dameMOdulosMenu();
            for (ModuloMenu moduloMenu : m) {
                modulosMenu.add(new SelectItem(moduloMenu, moduloMenu.getMenu()));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MbUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        return modulosMenu;
    }

    public void dameValorCmb() {
        System.err.println("hola mundo");
    }

    public void dameValorId() {
        try {
            int id = moduloMenu.getIdMenu();
            DaoPer daopermisos = new DaoPer();
            ArrayList<ModuloSubMenu> subMenus = new ArrayList<ModuloSubMenu>();
            subMenus = daopermisos.dameSubMenus(id);
            
            
            ModuloSubMenu modulosSub = new ModuloSubMenu();
            modulosSub.setIdSubMenu(0);
            modulosSub.setSubMenu("Selecciona un SubModulo");
            SelectItem selectItem = new SelectItem(modulosSub, modulosSub.getSubMenu());
            
            this.listaModulosSubMenu=new ArrayList<SelectItem>();
            listaModulosSubMenu.add(selectItem);
            for (ModuloSubMenu m4 : subMenus) {
                SelectItem s = new SelectItem(m4, m4.getSubMenu());
                listaModulosSubMenu.add(s);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MbUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ModuloMenu getModuloMenu() {
        return moduloMenu;
    }

    public void setModuloMenu(ModuloMenu moduloMenu) {
        this.moduloMenu = moduloMenu;
    }

    public ModuloSubMenu getModuloSubMenu() {
        return moduloSubMenu;
    }

    public void setModuloSubMenu(ModuloSubMenu moduloSubMenu) {
        this.moduloSubMenu = moduloSubMenu;
    }
}
