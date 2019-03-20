import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileServerOperator {

    private Path workingDir;

    private static FileServerOperator instance = null;

    protected FileServerOperator(Path directory) {

        workingDir = directory;

        instance = this;

    }

    public static FileServerOperator getFileServerInstance() {

        return instance;

    }

    protected String displayFilesInDirectory() {

        StringBuilder files = new StringBuilder();

        try (Stream<Path> paths = Files.walk(workingDir)) {

            List<String> list = paths
                    .map(path -> Files.isDirectory(path) ? path.toString() + '/' : path.toString())
                    .collect(Collectors.toList());


            for (String s : list) {
                files.append(s + "\n");

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return files.toString();
    }

    protected String retrieveFileContent(String fileName) {

        File tmp = new File(fileName);

        String returnVal = "";

        if (tmp.exists()) {

            if (tmp.isFile()) {

                try {
                    returnVal = readFile(tmp.getPath(), Charset.defaultCharset());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {

                returnVal = "Cannot read directory: please request file inside with a path specified.";

            }


        } else {

            returnVal = "File does not exist.";

        }

        return returnVal;

    }

    private String readFile(String path, Charset encoding) throws IOException {

        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);

    }
}
