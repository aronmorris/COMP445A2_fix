import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class KillExecutor implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        final String response = "Shutting down server.";

        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());

        HttpServerOperator.getServerInstance().shutdownServer();

        System.exit(0);

    }
}
