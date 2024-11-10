package store.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileReaderUtil {

    private static final String FILE_NOT_FOUND = "[ERROR] 파일을 찾을 수 없습니다";
    private static final String FILE_READ_ERROR = "[ERROR] 파일을 읽는 중 에러가 발생했습니다.";

    public static List<String> readFile(final String fileName) {
        InputStream inputStream = getInputStreamByFileName(fileName);
        return readLines(inputStream);
    }

    private static InputStream getInputStreamByFileName(final String fileName) {
        InputStream resourceAsStream = FileReaderUtil.class.getClassLoader().getResourceAsStream(fileName);
        if (resourceAsStream == null) {
            throw new IllegalArgumentException(FILE_NOT_FOUND);
        }
        return resourceAsStream;
    }

    private static List<String> readLines(final InputStream inputStream) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            br.readLine();
            readAllLines(lines, br);
        } catch (IOException e) {
            System.out.println(FILE_READ_ERROR);
        }
        return lines;
    }

    private static void readAllLines(final List<String> lines, final BufferedReader br) throws IOException {
        String line;
        while ((line = br.readLine()) != null) {
            lines.add(line);
        }
    }
}
