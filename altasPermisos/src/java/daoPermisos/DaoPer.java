/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daoPermisos;

import dominios.Acciones;
import dominios.BaseDatos;
import dominios.DominioUsuarios;
import dominios.Modulo;
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
        ArrayList<DominioUsuarios> usuarios = new ArrayList<>();
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

    public void insertarUsuario(DominioUsuarios u) throws SQLException, Exception {
        String sql = "INSERT INTO usuarios (usuario, login, password, status, idPerfil, email) VALUES(?,?,?,?,?,?)";
        Utilerias utilerias = new Utilerias();
        String password = utilerias.md5(u.getPassword());
        Connection cn = ds.getConnection();
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1, u.getUsuario());
        ps.setString(2, u.getLogin());
        ps.setString(3, password);
        ps.setInt(4, u.getStatus2());
        ps.setInt(5, u.getIdPerfil());
        ps.setString(6, u.getEmail());
        try {
            ps.executeUpdate();

        } catch (Exception e) {
            System.err.println(e);
        } finally {
            cn.close();
        }
    }

    public void guardarModulo(Modulo m) throws SQLException {
        String sqlGuardarModulo = "INSERT INTO modulos (modulo) VALUES(?)";
        Connection cn = ds.getConnection();
        PreparedStatement ps = cn.prepareStatement(sqlGuardarModulo);
        ps.setString(1, m.getModulo());
        try {
            ps.executeUpdate();
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            cn.close();
        }
    }

    public ArrayList<Modulo> dameModulos() throws SQLException {
        ArrayList<Modulo> modulos = new ArrayList<>();
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
        } catch (Exception e) {
            System.err.println(e);
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
        } catch (Exception e) {
            System.err.println(e);
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
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            cn.close();
        }

        return listBd;
    }

    public void insertarAcciones(Acciones acciones) throws SQLException {
        Connection cn = ds.getConnection();
        String sql = "INSERT INTO acciones (accion, status, idBoton, idModulo) VALUES(?,?,?,?)";
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1, acciones.getAccion());
        ps.setInt(2, acciones.getStatus());
        ps.setString(3, acciones.getIdBoton());
        ps.setInt(4, acciones.getIdMOdulo());
        try {
            ps.executeUpdate();
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            cn.close();
        }
    }

    public void insertarPerfil(Perfiles perfil) throws SQLException {
        Connection cn = ds.getConnection();
        String sql = "INSERT INTO perfiles VALUES (?)";
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1, perfil.getPerfil());
        try {
            ps.executeUpdate();
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            cn.close();
        }
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
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            cn.close();
        }
        return b;
    }

    public ArrayList<Perfiles> damePefiles() throws SQLException {
        ArrayList<Perfiles> perfil = new ArrayList<>();
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
        } catch (Exception e) {
            System.err.println(e);
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
        } catch (Exception e) {
            System.err.println(e);
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
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            cn.close();
        }
    }
//    public ArrayList<UsuarioPerfil> dameValoresUsuarioPerfil() throws SQLException {
//        ArrayList<UsuarioPerfil> uP = new ArrayList<>();
//        String sql = "SELECT * \n"
//                + "FROM usuarioPerfil up\n"
//                + "INNER JOIN basesDeDatos bd \n"
//                + "on  bd.idBaseDeDatos = up.idBaseDeDatos\n"
//                + "INNER JOIN modulos md \n"
//                + "on md.idModulo = up.idModulo\n"
//                + "INNER JOIN perfiles p \n"
//                + "on p.idPerfil = up.idPerfil";
//        ArrayList<UsuarioPerfil> usuarioPerfil = new ArrayList<>();
//        Connection cn = ds.getConnection();
//        PreparedStatement ps = cn.prepareStatement(sql);
//        ResultSet rs = ps.executeQuery();
//        while (rs.next()) {
//        }
//        return uP;
//    }

    public ArrayList<Acciones> dameAcciones() throws SQLException {
        Connection cn = ds.getConnection();
        ArrayList<Acciones> listAcciones = new ArrayList<>();
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
        } catch (Exception e) {
            System.err.println(e);
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
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            cn.close();
        }

        return acciones;
    }

    public ArrayList<Acciones> dameListaAcciones(int idAccion) throws SQLException {
        ArrayList<Acciones> acciones = new ArrayList<>();
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
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            cn.close();
        }
        return acciones;
    }

    public ArrayList<Acciones> dameValores(String bd, int modulo, int perfil) throws SQLException {
        ArrayList<Acciones> tabla = new ArrayList<>();
        Connection cn = ds.getConnection();
        String sql = "SELECT * FROM modulos m \n"
                + "                    INNER JOIN acciones a on \n"
                + "                    a.idModulo = m.idModulo\n"
                + "                    LEFT JOIN(SELECT * FROM  " + bd + ".dbo.usuarioPerfil WHERE idPerfil=" + perfil + ") up\n"
                + "                    on up.idModulo=m.idModulo\n"
                + "                    and up.idAccion=a.idAccion\n"
                + "                    where m.idModulo=" + modulo;

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
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            cn.close();
        }

        return tabla;
    }
}
