package co.edu.escuelaing.arep.app;

/**
 * Hello world!
 *
 */
import static javax.swing.JOptionPane.showMessageDialog;
import co.edu.escuelaing.arep.app.Model.User;
import co.edu.escuelaing.arep.app.Persistence.UserPersistenceException;
import co.edu.escuelaing.arep.app.Persistence.userImpl;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

import java.util.concurrent.ConcurrentHashMap;

public class SecureLogin {
    ConcurrentHashMap<String, User> listUsers;
    URLReader urlReader;
    public SecureLogin() {
        userImpl userimp = new userImpl();
        listUsers = userimp.getUsers();
        urlReader.main();
    }
    
    public String login(Request req, Response res) throws UserPersistenceException {
        Gson gson = new Gson();
        req.session(true);
        req.session(true);
        User nUser = gson.fromJson(req.body(), User.class);
        nUser.createHash();
        System.out.println(nUser);
        if(listUsers.containsKey(nUser.getHash())){
            req.session().attribute("username", nUser);
            req.session().attribute("Logged",true);
            return "Success";
        }else{
            showMessageDialog(null, "Please check your usename and/or password");
            throw new UserPersistenceException("Please check your usename and/or password");
        }
    }

    public String logOut(Request req, Response res){
        req.session().invalidate();;
        return "Logout";
    }

    public String getFromOther(Request req, Response res, String url){
        String resp = URLReader.readURL(url);
        System.out.println(resp);
        return resp;
    }
}