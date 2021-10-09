package co.edu.escuelaing.arep.app;

import static spark.Spark.*;
import spark.Request;
import spark.Response;

public class SecureService {
    private static SecureLogin secureLoginController;

    public static void main(String[] args) {
        port(getPort());
        secure("SecureService/keystores/ecikeystore.p12", "123456", null, null);
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

    private static String getInfo(Request req, Response res) {
        System.out.println("https://" + ((req.url().split("/")[2]).split(":")[0]) + ":5001/consumir");
        return secureLoginController.getFromOther(req, res, "https://" + ((req.url().split("/")[2]).split(":")[0]) + ":5001/consumir");
    }
    /**
     * Metodo encargado de ejecutar el programa de manera local con un puerto predeterminado.
     * @return Puerto asignado por defecto para ejecutar el programa de forma local, que es 5000.
     */
    private static void doBefore(Request req, Response res) {
        boolean authenticated = req.session().attribute("username") != null;
        boolean isLogin = req.pathInfo().equals("/index.html") ;
        String url = req.url();
        url = url.replace(req.pathInfo(), "");
        if (!authenticated && !isLogin){
            // Spark.halt(401, "You are not welcome here");
            res.redirect(url + "/index.html");
        }
    }
    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 5000;
    }
}
