import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

/**
 * @author Dgryzhkov
 */
public class LineCounter {
    public static int run(File file) {
        int lineCount = 0;
        try {
            FileReader fileReader = new FileReader(file);
            LineNumberReader lineNumberReader = new LineNumberReader(fileReader);

            while (lineNumberReader.readLine() != null) {
                lineCount++;
            }

            lineNumberReader.close();

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return lineCount;
    }
}
