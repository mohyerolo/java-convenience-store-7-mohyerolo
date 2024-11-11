package store.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileReaderUtil {

    public static List<String> readFile(final String filePath) throws IOException {
        Path path = Paths.get(filePath);
        List<String> fileData = Files.readAllLines(path);
        fileData.removeFirst();
        return fileData;
    }

}
