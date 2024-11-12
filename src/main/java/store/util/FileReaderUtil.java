package store.util;

import store.exception.CustomFileException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileReaderUtil {
    public static List<String> readFile(final String filePath) {
        List<String> fileData;
        try {
            fileData = Files.readAllLines(Paths.get(filePath));
            fileData.removeFirst();
        } catch (IOException e) {
            throw new CustomFileException(filePath);
        }
        return fileData;
    }

}
