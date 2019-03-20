import java.nio.file.Path;

public class httpfs {

    public static void main(String[] args) {

        HttpServerOperator hso = new HttpServerOperator(); //init to default

        setFlags(args, hso); //establish flags and change params as needed

        hso.initializeServer(); //activate the server

    }

    static void setFlags(String[] args, HttpServerOperator hso) {

        for (int i = 0; i < args.length; i++) {

            if (args[i].equalsIgnoreCase("-v")) {
                hso.setVerbose(true);
            }
            if (args[i].contains("-p")) {
                hso.setPort(Integer.parseInt(args[i + 1]));
            }
            if (args[i].contains("-d")) {
                hso.setPath(args[i + 1]);
            }

        }

    }

}
