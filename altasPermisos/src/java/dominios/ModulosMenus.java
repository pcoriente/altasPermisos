/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dominios;

import java.io.Serializable;

/**
 *
 * @author Comodoro
 */
public class ModulosMenus implements Serializable {

    public ModulosMenus() {
    }
    private int idMenu;
    private String menu;

    public int getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(int idMenu) {
        this.idMenu = idMenu;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.idMenu;
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
        final ModulosMenus other = (ModulosMenus) obj;
        if (this.idMenu != other.idMenu) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return menu;
    }
}
