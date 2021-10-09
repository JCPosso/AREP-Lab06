package co.edu.escuelaing.arep.app;
import spark.Request;
import spark.Response;

import static spark.Spark.*;

public class InfoService {

    public static void main(String[] args) {
        port(getPort());
        secure("InfoService/keystores/ecikeystore.p12","123456","InfoService/keystores/myTrustStore","123456");

        get("/consumir", (req, res) -> getInfo(req,res));

    }
    private static String getInfo(Request req, Response res) {
        System.out.println("hereeee");
        return "mensaje de prueba";
    }
    /**
     * Metodo encargado de ejecutar el programa de manera local con un puerto predeterminado.
     * @return Puerto asignado por defecto para ejecutar el programa de forma local, que es 5000.
     */
    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 5001;
    }
}
