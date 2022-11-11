package Case_Study.Model;

import Case_Study.Data.Data;
import Case_Study.Function.UserManager;

import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private String password;
        public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {return name;}
    public void menu(Data data) {
        UserManager userManager = new UserManager(this, data);
        userManager.menu();
    }
}
