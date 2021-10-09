package co.edu.escuelaing.arep.app;
import spark.Request;
import spark.Response;

import static spark.Spark.*;

public class InfoService {

    public static void main(String[] args) {
        port(getPort());
        secure("keystores/ecikeystore.p12","123456","keystores/myTrustStore","123456");

        get("/getInfo", (req, res) -> getInfo(req,res));

    }
    private static String getInfo(Request req, Response res) {

        return "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";
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
