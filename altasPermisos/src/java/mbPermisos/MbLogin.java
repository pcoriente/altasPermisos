package mbPermisos;

import daoPermisos.DaoPer;
import dominios.Login;
import java.sql.SQLException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Comodoro
 */
@ManagedBean
@RequestScoped
public class MbLogin {

    private Login login = new Login();

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public MbLogin() {
    }

    public String validarLogin() throws SQLException {
        String pagina = "";
        boolean valido;
        DaoPer daop = new DaoPer();
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = null;
        boolean loggedIn = false;
        valido = daop.loguearme(login);
        if (valido == false) {
            msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Ingrese los datos correspondientes");
        }
        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Removido", "Las bases de Datos fueron removidas");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.addCallbackParam("Bd´s", loggedIn);
        return pagina;
    }
}