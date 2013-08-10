/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dominios;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Comodoro
 */
public class ModulosSubMenus implements Serializable {

    public ModulosSubMenus() {
    }
    private int idSubMenu;
    private String subMenu;

    public ModulosSubMenus(int idSubMenu, String subMenu) {
        this.idSubMenu = idSubMenu;
        this.subMenu = subMenu;
    }

    public int getIdSubMenu() {
        return idSubMenu;
    }

    public void setIdSubMenu(int idSubMenu) {
        this.idSubMenu = idSubMenu;
    }

    public String getSubMenu() {
        return subMenu;
    }

    public void setSubMenu(String subMenu) {
        this.subMenu = subMenu;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + this.idSubMenu;
        hash = 43 * hash + Objects.hashCode(this.subMenu);
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
        final ModulosSubMenus other = (ModulosSubMenus) obj;
        if (this.idSubMenu != other.idSubMenu) {
            return false;
        }
        if (!Objects.equals(this.subMenu, other.subMenu)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return subMenu;
    }
}
