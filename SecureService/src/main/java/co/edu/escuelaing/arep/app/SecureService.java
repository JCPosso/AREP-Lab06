package co.edu.escuelaing.arep.app;

import static spark.Spark.*;

import spark.Request;
import spark.Response;

public class SecureService {
    private static SecureLogin secureLoginController;

    public static void main(String[] args) {
        port(getPort());
        secure("SecureService/keystores/ecikeystore.p12", "123456", "SecureService/keystores/myTrustStore", "123456");
        staticFiles.location("/public");
        secureLoginController = new SecureLogin();
        get("/", (req, res) -> {
            res.redirect("public/index.html");
            return "";
        });
        post("login", (req, res) -> secureLoginController.login(req, res));
        post("logout", (req, res) -> secureLoginController.logOut(req, res));
        get("hello", (req, res) -> "Hello Secure Services");
        get("info", (req, res) -> getInfo(req,res));
    }

    private static String getInfo(Request req, Response res) {
        return secureLoginController.getFromOther(req,res,"https://localhost:5001/info");
    }

    /**
     * Metodo encargado de ejecutar el programa de manera local con un puerto predeterminado.
     * @return Puerto asignado por defecto para ejecutar el programa de forma local, que es 5000.
     */
    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 5000;
    }
}
