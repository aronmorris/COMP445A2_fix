import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.FileSystems;
import java.nio.file.Path;


public class HttpServerOperator {

    private final int DEFAULT_PORT = 8080;
    private final Path DEFAULT_PATH = FileSystems.getDefault().getPath(".");

    private int port;
    private Path path;

    private static boolean verbose = false;

    protected static FileServerOperator fileServer;

    public HttpServerOperator(int port) {

        this.port = port;
        this.path = DEFAULT_PATH;

    }

    public HttpServerOperator(String directoryPath) {

        this.port = DEFAULT_PORT;
        this.path = FileSystems.getDefault().getPath(directoryPath).toAbsolutePath();

    }

    public HttpServerOperator(int port, String directoryPath) {

        this.port = port;
        this.path = FileSystems.getDefault().getPath(directoryPath).toAbsolutePath();

    }

    public HttpServerOperator() {

        this.port = DEFAULT_PORT;
        this.path = DEFAULT_PATH;

    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setPath(String path) {
        this.path = FileSystems.getDefault().getPath(path).toAbsolutePath();
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    public static boolean isVerbose() {
        return verbose;
    }

    public void initializeServer() {

        try {

            this.fileServer = new FileServerOperator(path);

            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

            System.out.println("Server started at port " + port);

            server.createContext("/", new GetHandler());

            server.createContext("/post", new PostExecutor());

            server.setExecutor(null); //Can be changed later with an Executor for multithreading

            server.start();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
