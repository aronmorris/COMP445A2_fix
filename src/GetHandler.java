import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        Map<String, Object> parameters = new HashMap<String, Object>();
        URI requestedUri = httpExchange.getRequestURI();
        String query = requestedUri.getRawQuery();
        QueryParser.parseQuery(query, parameters);

        String response = "";
        String reqUrl;

        System.out.println("GET request: URL = " + httpExchange.getRequestURI().toString());

        reqUrl = httpExchange.getRequestURI().toString();
        System.out.println(reqUrl);

        if (reqUrl.equalsIgnoreCase("/")) {

            response = FileServerOperator.getFileServerInstance().displayFilesInDirectory();

        }

        else {

            response = FileServerOperator.getFileServerInstance().retrieveFileContent(reqUrl.substring(1));

        }


        System.out.println("sending response");
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.toString().getBytes());

        os.close();

    }

}
