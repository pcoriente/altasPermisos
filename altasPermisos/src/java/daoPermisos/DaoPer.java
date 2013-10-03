/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daoPermisos;

import dominios.Acciones;
import dominios.BaseDatos;
import dominios.DominioUsuarios;
import dominios.Login;
import dominios.Modulo;
import dominios.ModuloMenu;
import dominios.ModuloSubMenu;
import dominios.Perfiles;
import dominios.TablaAcciones;
import dominios.UsuarioPerfil;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.model.SelectItem;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import utilerias.Utilerias;

/**
 *
 * @author Comodoro
 */
public class DaoPer {

    DataSource ds;

    public DaoPer() {
        try {
            Context cI = new InitialContext();
            ds = (DataSource) cI.lookup("java:comp/env/jdbc/__systemWeb");
        } catch (NamingException ex) {
            Logger.getLogger(DaoPer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public DaoPer(String jndi) {
        try {
            Context cI = new InitialContext();
            ds = (DataSource) cI.lookup("java:comp/env/" + jndi);
        } catch (NamingException ex) {
            Logger.getLogger(DaoPer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<DominioUsuarios> dameUsuarios() throws SQLException {
        ArrayList<DominioUsuarios> usuarios = new ArrayList<DominioUsuarios>();
        String sql = "SELECT * FROM usuarios";
        Connection cn = ds.getConnection();
        PreparedStatement ps = cn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            DominioUsuarios d = new DominioUsuarios();
            d.setIdUsuario(rs.getInt("idUsuario"));
            d.setUsuario(rs.getString("usuario"));
            d.setPassword(rs.getString("password"));
            usuarios.add(d);
        }
        cn.close();
        return usuarios;
    }

    public DominioUsuarios dameUsuarios(int id) throws SQLException {
        DominioUsuarios dU = new DominioUsuarios();
        String sql = "SELECT * FROM usuarios WHERE idUsuario =" + id;
        Connection cn = ds.getConnection();
        PreparedStatement ps = cn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            dU.setIdUsuario(rs.getInt("idUsuario"));
            dU.setUsuario(rs.getString("usuario"));
            dU.setPassword(rs.getString("password"));
        }
        cn.close();
        return dU;
    }

    public void insertarUsuario(DominioUsuarios u, int bd, int perfil) throws SQLException, Exception {
        String sql = "INSERT INTO usuarios (usuario,login, password,email) VALUES(?,?,?,?)";
        String sqlIdentity = "SELECT @@IDENTITY as indentidad";
        Utilerias utilerias = new Utilerias();
        String password = utilerias.md5(u.getPassword());
        Connection cn = ds.getConnection();
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1, u.getUsuario());
        ps.setString(2, u.getLogin());
        ps.setString(3, password);
        ps.setString(4, u.getEmail());
        try {
            ps.executeUpdate();
            ps = cn.prepareStatement(sqlIdentity);
            ResultSet rs = ps.executeQuery();
            int identidad = 0;
            while (rs.next()) {
                identidad = rs.getInt("indentidad");
            }
            insertarAcceso(identidad, bd, perfil);



        } finally {
            cn.close();
        }
    }

    public int guardarModulo(Modulo m) throws SQLException {
        int identity = 0;
        String sqlGuardarModulo = "INSERT INTO modulos (modulo) VALUES(?)";
        String sqlIdentity = "SELECT @@IDENTITY as indentidad";
        Connection cn = ds.getConnection();
        PreparedStatement ps = cn.prepareStatement(sqlGuardarModulo);
        ps.setString(1, m.getModulo());
        try {
            ps.executeUpdate();
            ps = cn.prepareStatement(sqlIdentity);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                identity = rs.getInt("indentidad");
            }
        } finally {
            cn.close();
        }
        return identity;
    }

    public ArrayList<Modulo> dameModulos() throws SQLException {
        ArrayList<Modulo> modulos = new ArrayList<Modulo>();
        String sqlModulos = "SELECT * FROM modulos";
        Connection cn = ds.getConnection();
        PreparedStatement ps = cn.prepareStatement(sqlModulos);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Modulo modulo = new Modulo();
                modulo.setIdModulo(rs.getInt("idModulo"));
                modulo.setModulo(rs.getString("modulo"));
                modulos.add(modulo);
            }
        } finally {
            cn.close();
        }

        return modulos;
    }

    public Modulo dameModulo(int idModulo) throws SQLException {
        Connection cn = ds.getConnection();
        Modulo m = new Modulo();
        String sql = "SELECT * FROM modulos WHERE idModulo = " + idModulo;
        PreparedStatement ps = cn.prepareStatement(sql);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                m.setIdModulo(rs.getInt("idModulo"));
                m.setModulo(rs.getString("modulo"));
            }
        } finally {
            cn.close();
        }
        return m;
    }

    public ArrayList<BaseDatos> dameBaseDatos() throws SQLException {
        String sql = "SELECT * FROM basesDeDatos";
        ArrayList<BaseDatos> listBd = new ArrayList<BaseDatos>();
        Connection cn = ds.getConnection();
        PreparedStatement ps = cn.prepareStatement(sql);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                BaseDatos bd = new BaseDatos();
                bd.setIdBaseDatos(rs.getInt("idBaseDeDatos"));
                bd.setBaseDatos(rs.getString("baseDeDatos"));
                bd.setJndi(rs.getString("jndi"));
                listBd.add(bd);
            }
        } finally {
            cn.close();
        }

        return listBd;
    }

    public void insertarAcciones(Acciones acciones) throws SQLException {
        Connection cn = ds.getConnection();
        String sql = "INSERT INTO acciones (accion,  idBoton, idModulo) VALUES(?,?,?)";
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1, acciones.getAccion());
        ps.setString(2, acciones.getIdBoton());
        ps.setInt(3, acciones.getIdMOdulo());
        try {
            ps.executeUpdate();
        } finally {
            cn.close();
        }
    }

    public int insertarPerfil(Perfiles perfil) throws SQLException {
        int identity = 0;
        Connection cn = ds.getConnection();
        String sql = "INSERT INTO perfiles VALUES (?)";
        String sqlIdentity = "SELECT @@IDENTITY as indentidad";
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1, perfil.getPerfil());
        try {
            ps.executeUpdate();
            ps = cn.prepareStatement(sqlIdentity);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                identity = rs.getInt("indentidad");
            }
        } finally {
            cn.close();
        }
        return identity;
    }

    public BaseDatos dameBaseDatos(int idBaseDatos) throws SQLException {
        String sql = "SELECT * FROM basesDeDatos WHERE idBaseDeDatos=" + idBaseDatos;
        BaseDatos b = new BaseDatos();
        Connection cn = ds.getConnection();
        PreparedStatement ps = cn.prepareStatement(sql);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                b.setIdBaseDatos(rs.getInt("idBaseDeDatos"));
                b.setBaseDatos(rs.getString("baseDeDatos"));
                b.setJndi(rs.getString("jndi"));
            }
        } finally {
            cn.close();
        }
        return b;
    }

    public ArrayList<Perfiles> damePefiles() throws SQLException {
        ArrayList<Perfiles> perfil = new ArrayList<Perfiles>();
        Connection cn = ds.getConnection();
        String sql = "SELECT * FROM perfiles";
        PreparedStatement ps = cn.prepareStatement(sql);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Perfiles p = new Perfiles();
                p.setIdPerfiles(rs.getInt("idPerfil"));
                p.setPerfil(rs.getString("perfil"));
                perfil.add(p);
            }
        } finally {
            cn.close();
        }

        return perfil;
    }

    public Perfiles damePerfil(int id) throws SQLException {
        Connection cn = ds.getConnection();
        Perfiles p = new Perfiles();
        String sql = "SELECT * FROM perfiles WHERE idPerfil =" + id;
        PreparedStatement ps = cn.prepareStatement(sql);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                p.setIdPerfiles(rs.getInt("idPerfil"));
                p.setPerfil(rs.getString("perfil"));
            }
        } finally {
            cn.close();
        }
        return p;
    }

    public void insertarUsuarioPerfil(UsuarioPerfil usuaPerfil, ArrayList<Acciones> acciones) throws SQLException {
        Connection cn = ds.getConnection();
        try {
            String sqlElimiar = "DELETE FROM usuarioPerfil WHERE idPerfil=" + usuaPerfil.getIdPerfil() + " and idModulo = " + usuaPerfil.getIdModulo();
            Statement st = cn.createStatement();
            st.executeUpdate(sqlElimiar);
            String sql = "INSERT INTO usuarioPerfil (idPerfil, idModulo, idAccion) VALUES (?,?,?)";
            PreparedStatement ps = cn.prepareStatement(sql);
            for (int i = 0; i < acciones.size(); i++) {
                int idAccion = acciones.get(i).getIdAccion();
                ps.setInt(1, usuaPerfil.getIdPerfil());
                ps.setInt(2, usuaPerfil.getIdModulo());
                ps.setInt(3, idAccion);
                ps.executeUpdate();
            }
        } finally {
            cn.close();
        }
    }

    public ArrayList<Acciones> dameAcciones() throws SQLException {
        Connection cn = ds.getConnection();
        ArrayList<Acciones> listAcciones = new ArrayList<Acciones>();
        String sql = "SELECT * FROM acciones";
        PreparedStatement ps = cn.prepareStatement(sql);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Acciones acciones = new Acciones();
                acciones.setIdAccion(rs.getInt("idAccion"));
                acciones.setAccion(rs.getString("accion"));
                acciones.setIdBoton(rs.getString("idBoton"));
                acciones.setIdMOdulo(rs.getInt("idModulo"));
                listAcciones.add(acciones);
            }
        } finally {
            cn.close();
        }

        return listAcciones;
    }

    public Acciones dameAcciones(int idModulo) throws SQLException {
        Connection cn = ds.getConnection();
        Acciones acciones = new Acciones();
        String sql = "SELECT * FROM acciones WHERE idAccion=" + idModulo;
        PreparedStatement ps = cn.prepareStatement(sql);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                acciones.setIdAccion(rs.getInt("idAccion"));
                acciones.setAccion(rs.getString("accion"));
                acciones.setIdBoton(rs.getString("idBoton"));
                acciones.setIdMOdulo(rs.getInt("idModulo"));
            }
        } finally {
            cn.close();
        }

        return acciones;
    }

    public ArrayList<Acciones> dameListaAcciones(int idAccion) throws SQLException {
        ArrayList<Acciones> acciones = new ArrayList<Acciones>();
        Connection cn = ds.getConnection();
        String sql = "SELECT * FROM acciones WHERE idModulo=" + idAccion;
        PreparedStatement ps = cn.prepareStatement(sql);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Acciones accion = new Acciones();
                accion.setAccion(rs.getString("accion"));
                accion.setIdAccion(rs.getInt("idAccion"));
                acciones.add(accion);
            }
        } finally {
            cn.close();
        }
        return acciones;
    }

    public ArrayList<Acciones> dameValores(String bd, int modulo, int perfil) throws SQLException {
        ArrayList<Acciones> tabla = new ArrayList<Acciones>();
        Connection cn = ds.getConnection();
        String sql = "SELECT * FROM modulos m \n"
                + "   INNER JOIN acciones a on \n"
                + "   a.idModulo = m.idModulo\n"
                + "   LEFT JOIN(SELECT * FROM  " + bd + ".dbo.usuarioPerfil WHERE idPerfil=" + perfil + ") up\n"
                + "   on up.idModulo=m.idModulo\n"
                + "   and up.idAccion=a.idAccion\n"
                + "   where m.idModulo=" + modulo;

        PreparedStatement ps = cn.prepareStatement(sql);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Acciones tA = new Acciones();
                tA.setIdAccion(rs.getInt("idAccion"));
                tA.setIdMOdulo(rs.getInt("idModulo"));
                tA.setAccion(rs.getString("accion"));
                tA.setIdPerfil(rs.getInt("idPerfil"));
                tabla.add(tA);
            }
        } finally {
            cn.close();
        }

        return tabla;
    }

    public int guardarBaseDatos(BaseDatos bdAltas) throws SQLException {
        int identity = 0;
        Connection cn = ds.getConnection();
        String sql = "INSERT INTO basesDeDatos (baseDeDatos, jndi) VALUES(?,?)";
        String sqlIdentity = "SELECT @@IDENTITY as indentidad";
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1, bdAltas.getBaseDatos());
        ps.setString(2, bdAltas.getJndi());
        try {
            ps.executeUpdate();
            ps = cn.prepareStatement(sqlIdentity);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                identity = rs.getInt("indentidad");
            }
        } finally {
            cn.close();
        }
        return identity;
    }

    public void insertarAcceso(int idUsuario, int idBd, int idPerfil) throws SQLException {
        Connection cn = ds.getConnection();
        String sql = "INSERT INTO accesos (idUsuario, idDbs,idPerfil) VALUES(?,?,?)";
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setInt(1, idUsuario);
        ps.setInt(2, idBd);
        ps.setInt(3, idPerfil);
        try {
            ps.executeUpdate();
        } finally {
            cn.close();
        }
    }

    public void actualizarBaseDatos(BaseDatos bdAltas) throws SQLException {
        String sql = "UPDATE basesDeDatos set baseDeDatos='"
                + bdAltas.getBaseDatos() + "', jndi ='" + bdAltas.getJndi()
                + "' WHERE idBaseDeDatos =" + bdAltas.getIdBaseDatos();
        Connection cn = ds.getConnection();
        PreparedStatement ps = cn.prepareStatement(sql);
        try {
            ps.executeUpdate();
        } finally {
            cn.close();
        }
    }

    public void ActualizarPerfiles(Perfiles perfil) throws SQLException {
        String sql = "UPDATE perfiles set perfil ='" + perfil.getPerfil() + "' WHERE idPerfil=" + perfil.getIdPerfiles();
        Connection cn = ds.getConnection();
        PreparedStatement ps = cn.prepareStatement(sql);
        try {
            ps.executeUpdate();
        } finally {
            cn.close();
        }

    }

    public void ActualizarModulos(Modulo m) throws SQLException {
        String sql = "UPDATE modulos set modulo='" + m.getModulo() + "' WHERE idModulo=" + m.getIdModulo();
        Connection cn = ds.getConnection();
        PreparedStatement ps = cn.prepareStatement(sql);
        try {
            ps.executeUpdate();
        } finally {
            cn.close();
        }
    }

    public ArrayList<BaseDatos> dameListaBds() throws SQLException {
        ArrayList<BaseDatos> lista = new ArrayList<BaseDatos>();
        Connection cn = null;
        cn = ds.getConnection();
        try {
            Statement preparedStatement = cn.createStatement();
            ResultSet cursorBases = preparedStatement.executeQuery("exec sp_databases");
            int id = 1;
            while (cursorBases.next()) {
                BaseDatos bds = new BaseDatos();
                bds.setIdBaseDatos(id);
                bds.setBaseDatos(cursorBases.getString("DATABASE_NAME"));
                lista.add(bds);
                id++;
            }
        } finally {
            cn.close();
        }
        return lista;
    }

    public void insertarBd(ArrayList<BaseDatos> bd) throws SQLException {
        Connection cn = ds.getConnection();
        String sql = "INSERT INTO basesDeDatos (baseDeDatos, jndi) VALUES (?,?)";
        String sqlTruncar = "truncate table basesDeDatos";
        PreparedStatement ps;
        ps = cn.prepareStatement(sqlTruncar);
        ps.executeUpdate();
        ps = cn.prepareStatement(sql);
        try {
            for (int i = 0; i < bd.size(); i++) {
                String jndi = "jdbc/__" + bd.get(i).getBaseDatos();
                ps.setString(1, bd.get(i).getBaseDatos());
                ps.setString(2, jndi);
                ps.executeUpdate();
            }
        } finally {
            cn.close();
        }
    }
    /*
    public boolean loguearme(Login log) throws SQLException {
        boolean validado = false;
        String sql = "SELECT * FROM tabla";
        Connection cn = ds.getConnection();
        PreparedStatement ps = cn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        if (rs.first() == true) {
            validado = true;
        }
        return validado;
    }
    * */
    public String damePassword() throws SQLException {
        String pass = null;
        String sql = "SELECT * FROM accesoAdministrativo";
        
        Connection cn = ds.getConnection();
        PreparedStatement ps = cn.prepareStatement(sql);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                pass = rs.getString("password");
            }
        } finally {
            cn.close();
        }
        return pass;
    }

    public ArrayList<ModuloMenu> dameMOdulosMenu() throws SQLException {
        String sql = "SELECT * FROM modulosMenus";
        ArrayList<ModuloMenu> modulosMenus = new ArrayList<ModuloMenu>();
        
        Connection cn = ds.getConnection();
        PreparedStatement ps = cn.prepareStatement(sql);
        ResultSet rs;
        try {
            rs = ps.executeQuery();
             while (rs.next()) {
                ModuloMenu dModulosMenus = new ModuloMenu();
                dModulosMenus.setIdMenu(rs.getInt("idMenu"));
                dModulosMenus.setMenu(rs.getString("menu"));
                modulosMenus.add(dModulosMenus);
            }
        } finally {
            cn.close();
        }
        return modulosMenus;
    }

    public ModuloMenu dameModulosMenu(int id) throws SQLException {
        String sql = "SELECT * FROM modulosMenus where idMenu=" + id;
        ModuloMenu m = new ModuloMenu();
        Connection cn = ds.getConnection();
        PreparedStatement ps = cn.prepareStatement(sql);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                m.setIdMenu(rs.getInt("idMenu"));
                m.setMenu(rs.getString("menu"));
            }
        } finally {
            cn.close();
        }
        return m;
    }

    public ArrayList<ModuloSubMenu> dameSubMenus(int id) throws SQLException {
        ArrayList<ModuloSubMenu> modulosMenus = new ArrayList<ModuloSubMenu>();
        String sql = "SELECT * FROM modulosSubMenus WHERE idMenu =" + id;
        Connection cn = ds.getConnection();
        PreparedStatement ps = cn.prepareStatement(sql);
        try {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ModuloSubMenu m = new ModuloSubMenu();
                m.setIdSubMenu(rs.getInt("idSubMenu"));
                m.setSubMenu(rs.getString("subMenu"));
                modulosMenus.add(m);
            }
        } finally {
            cn.close();
        }
        return modulosMenus;
    }

    public ModuloSubMenu dameSubModulosMenu(int id) throws SQLException {
        ModuloSubMenu moduloSubMenus = new ModuloSubMenu();
        String sql = "SELECT * FROM modulosSubMenus WHERE idSubMenu =" + id;
        Connection cn = ds.getConnection();
        PreparedStatement ps = cn.prepareStatement(sql);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                moduloSubMenus.setIdSubMenu(rs.getInt("idSubMenu"));
                moduloSubMenus.setSubMenu(rs.getString("subMenu"));
            }
        } finally {
            cn.close();
        }
        return moduloSubMenus;
    }
}