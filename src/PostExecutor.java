import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import javax.sound.midi.SysexMessage;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class PostExecutor implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        System.out.println("POST request received.");

        Map<String, Object> parameters = new LinkedHashMap<String, Object>();
        InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);

        List<String> contents = new ArrayList<String>();
        String line;

        while ((line = br.readLine()) != null) {

            contents.add(line);

        }

        String query = httpExchange.getRequestURI().getRawQuery();
        QueryParser.parseQuery(query, parameters);

        //TODO for debugging re-add this
        //parameters.forEach((k, v) -> System.out.println(k + ": " + v));

        Map.Entry<String, Object> entry = parameters.entrySet().iterator().next();

        System.out.println(entry.getValue().toString());

        Path file = Paths.get(entry.getValue().toString());

        Files.write(file, contents, Charset.forName("UTF-8"));

        String response = "File received and written as " + entry.getValue().toString();

        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
        br.close();

    }
}
