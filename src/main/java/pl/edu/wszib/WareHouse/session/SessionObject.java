package pl.edu.wszib.warehouse.session;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import pl.edu.wszib.warehouse.model.User;

@Component
@SessionScope
public class SessionObject {
    private User loggedUser = null;
    private String info = null;
    public User getLoggedUser() {
        return loggedUser;
    }
    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }
    public boolean isLogged() {
        return this.loggedUser != null;
    }
    public String getInfo() {
        String tmp = this.info;
        this.info = null;
        return tmp;
    }
    public void setInfo(String info) {
        this.info = info;
    }
}
