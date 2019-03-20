public class httpfs {

    public static void main(String[] args) {

        HttpServerOperator hso = new HttpServerOperator(8080);

        hso.initializeServer();

    }

}
