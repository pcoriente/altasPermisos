/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dominios;

import java.util.Objects;

/**
 *
 * @author Comodoro
 */
public class DominioUsuarios {

    private int idUsuario;
    private String usuario;
    private String login;
    private String password;
    private String fechaCreacion;
    private String Actualizacion;
    private int status;
    private String email;
    private int rol;

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getActualizacion() {
        return Actualizacion;
    }

    public void setActualizacion(String Actualizacion) {
        this.Actualizacion = Actualizacion;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return  usuario ;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + this.idUsuario;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DominioUsuarios other = (DominioUsuarios) obj;
        if (this.idUsuario != other.idUsuario) {
            return false;
        }
        return true;
    }

   
    

   
}
