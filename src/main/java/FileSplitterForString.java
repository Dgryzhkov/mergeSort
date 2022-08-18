import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Dgryzhkov
 */
public class FileSplitterForString {
    private static int numberOfFiles = 0;
    private static int totalCountOfLines = 0;
    private static List<File> listOfTempFiles = new ArrayList<>();
    private int countOfLinesInTheFile;
    private boolean isSortDescending;
    private File file;
    protected int maxLinesInMemoryBuffer = 100;

    public static int getTotalCountOfLines() {
        return totalCountOfLines;
    }

    public static List<File> getListOfTempFiles() {
        return listOfTempFiles;
    }

    public FileSplitterForString(int lineNumber, File file, boolean isSortDescending) {
        this.countOfLinesInTheFile = lineNumber;
        this.file = file;
        this.isSortDescending = isSortDescending;
        totalCountOfLines += lineNumber;
        numberOfFiles++;
    }

    public void run() {
        String tempFileName = "temp-file-" + numberOfFiles + "-";

        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            int slices = (int) Math.ceil((double) countOfLinesInTheFile / maxLinesInMemoryBuffer);
            int i, j;
            j = 0;
            for (i = 0; i < slices; i++) {
                String[] buffer = new String[Math.min(countOfLinesInTheFile - i * j, maxLinesInMemoryBuffer)];
                j = readingItemsFromFile(bufferedReader, buffer);
                bubbleSorting(buffer);
                writingItemsToTempFile(tempFileName, i, j, buffer);
            }

            bufferedReader.close();
            fileReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writingItemsToTempFile(String tempFileName, int i, int j, String[] buffer) throws IOException {
        File file = new File(tempFileName + i + ".txt");
        listOfTempFiles.add(file);
        FileWriter fileWriter = new FileWriter(file);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        for (int k = 0; k < j; k++) {
            printWriter.println(buffer[k]);
        }

        printWriter.close();
        fileWriter.close();
    }

    private void bubbleSorting(String[] buffer) {
        if (isSortDescending) {
            BubbleSort.sortDescendingString(buffer);
        } else {
            BubbleSort.sortAscendingString(buffer);
        }
    }

    private int readingItemsFromFile(BufferedReader bufferedReader, String[] buffer) throws IOException {
        int j;
        for (j = 0; j < (Math.min(countOfLinesInTheFile, maxLinesInMemoryBuffer)); j++) {
            String t = bufferedReader.readLine();
            if (t != null) {
                buffer[j] = t;
            } else
                break;
        }
        return j;
    }
}