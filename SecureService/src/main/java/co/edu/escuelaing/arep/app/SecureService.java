package co.edu.escuelaing.arep.app;

import static spark.Spark.*;
import spark.Request;
import spark.Response;

/**
 * @author  Juan Camilo Posso (JCPosso)
 * @version del 9/10/21 (1.0)
 */
public class SecureService {
    private static SecureLogin secureLoginController;
    /**
     * Constructor
     */
    public static void main(String[] args) {
        port(getPort());
        secure("keystores/ecikeystore.p12", "123456", null, null);
        staticFiles.location("/public");
        secureLoginController = new SecureLogin();
        get("/", (req, res) -> {
            res.redirect("public/index.html");
            return "";
        });
        post("/login", (req, res) -> secureLoginController.login(req, res));
        post("logout", (req, res) -> secureLoginController.logOut(req, res));
        get("hello", (req, res) -> "Hello Secure Services");
       get("/info", (req, res) -> getInfo(req,res));
    }
    /**
     * Obtiene un texto desde el otro servicio web.
     * @param req Spark Request.
     * @param res Spark Response.
     * @return Un texto desde otro servicio web.
     */
    private static String getInfo(Request req, Response res) {
        System.out.println("https://" + ((req.url().split("/")[2]).split(":")[0]) + ":5001/getInfo");
        return secureLoginController.getFromOther(req, res, "https://" + ((req.url().split("/")[2]).split(":")[0]) + ":5001/getInfo");
    }

    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 5000;
    }
}
