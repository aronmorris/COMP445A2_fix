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

        boolean verbose = HttpServerOperator.isVerbose();

        if (verbose) {
            System.out.println("POST Request received. Servicing.");
        }

        Map<String, Object> parameters = new LinkedHashMap<String, Object>();
        InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);

        List<String> contents = new ArrayList<String>();
        String line;

        if (verbose) {
            System.out.println("Acquiring message body for file transcription.");
        }

        while ((line = br.readLine()) != null) {

            contents.add(line);

        }

        if (verbose) {
            System.out.println("Message body acquired as: \n" + contents);
        }

        String query = httpExchange.getRequestURI().getRawQuery();
        QueryParser.parseQuery(query, parameters);

        //TODO for debugging re-add this
        //parameters.forEach((k, v) -> System.out.println(k + ": " + v));

        Map.Entry<String, Object> entry = parameters.entrySet().iterator().next();

        System.out.println(entry.getValue().toString());

        Path file = Paths.get(entry.getValue().toString());

        if (verbose) {
            System.out.println("Writing file: " + file.toString());
        }

        Files.write(file, contents, Charset.forName("UTF-8"));

        if (verbose) {
            System.out.println("File written to system. Replying 200.");
        }

        String response = "File received and written as " + entry.getValue().toString();

        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
        br.close();

    }
}
