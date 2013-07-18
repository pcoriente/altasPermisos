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
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

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

    public ArrayList<DominioUsuarios> dameUsuarios() throws SQLException {
        ArrayList<DominioUsuarios> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuario";
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

    public void insertarUsuario(DominioUsuarios u) throws SQLException {
        String sql = "INSERT INTO usuarios (usuario, login, password, fechaCreacion,status, email, rol) VALUES(?,?,?,?,?,?,?)";
        Connection cn = ds.getConnection();
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1, u.getUsuario());
        ps.setString(2, u.getLogin());
        ps.setString(3, u.getPassword());
        ps.setString(4, u.getFechaCreacion());
        ps.setInt(5, u.getStatus2());
        ps.setString(6, u.getEmail());
        ps.setInt(7, u.getRol());
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
        try{
             ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            b.setBaseDatos(rs.getString("baseDeDatos"));
            b.setJndi(rs.getString("jndi"));
        }
        }
        catch (Exception e ){
            System.err.println(e);
        }
        finally{
            cn.close();
        }
        return b;
    }
}
