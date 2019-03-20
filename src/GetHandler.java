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
        parseQuery(query, parameters);

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

    public static void parseQuery(String query, Map<String,
            Object> parameters) throws UnsupportedEncodingException {

        if (query != null) {
            String pairs[] = query.split("[&]");
            for (String pair : pairs) {
                String param[] = pair.split("[=]");
                String key = null;
                String value = null;
                if (param.length > 0) {
                    key = URLDecoder.decode(param[0],
                            System.getProperty("file.encoding"));
                }

                if (param.length > 1) {
                    value = URLDecoder.decode(param[1],
                            System.getProperty("file.encoding"));
                }

                if (parameters.containsKey(key)) {
                    Object obj = parameters.get(key);
                    if (obj instanceof List<?>) {
                        List<String> values = (List<String>) obj;
                        values.add(value);

                    } else if (obj instanceof String) {
                        List<String> values = new ArrayList<String>();
                        values.add((String) obj);
                        values.add(value);
                        parameters.put(key, values);
                    }
                } else {
                    parameters.put(key, value);
                }
            }
        }
    }
}
