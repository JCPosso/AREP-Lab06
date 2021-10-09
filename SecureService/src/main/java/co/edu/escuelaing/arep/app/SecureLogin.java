package co.edu.escuelaing.arep.app;

import static javax.swing.JOptionPane.showMessageDialog;
import co.edu.escuelaing.arep.app.Model.User;
import co.edu.escuelaing.arep.app.Persistence.UserPersistenceException;
import co.edu.escuelaing.arep.app.Persistence.userImpl;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

import java.util.concurrent.ConcurrentHashMap;
/**
 * Controlador que genera devuelve las acciones solicitadas desde Secure Service
 * @author  Juan Camilo Posso (JCPosso)
 * @version del 9/10/21 (1.0)
 */
public class SecureLogin {
    ConcurrentHashMap<String, User> listUsers;
    URLReader urlReader;

    /**
     * Constructor
     */
    public SecureLogin() {
        userImpl userimp = new userImpl();
        listUsers = userimp.getUsers();
        urlReader.main();
    }

    /**
     * Servicio de login.
     * @param req Spark Request.
     * @param res Spark Response.
     * @return mensaje si hizo login correcto.
     */

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

    /**
     * Servicio de logout.
     * @param req Spark Request.
     * @param res Spark Response.
     * @return mensaje si hizo logout.
     */

    public String logOut(Request req, Response res){
        req.session().invalidate();;
        return "Logout";
    }
    /**
     * Obtiene el cuerpo del nuevo servicio.
     * @param req Spark request
     * @param res Spark Response
     * @param url url del nuevo servicio entrante
     * @return
     */
    public String getFromOther(Request req, Response res, String url){
        String resp = URLReader.readURL(url);
        return resp;
    }
}