import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Dgryzhkov
 */
public class FileSplitterForInt {
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

    public FileSplitterForInt(int lineNumber, File file, boolean isSortDescending) {
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
                int[] buffer = new int[Math.min(countOfLinesInTheFile - i * j, maxLinesInMemoryBuffer)];
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

    private int readingItemsFromFile(BufferedReader bufferedReader, int[] buffer) throws IOException {
        int j;
        for (j = 0; j < (Math.min(countOfLinesInTheFile, maxLinesInMemoryBuffer)); j++) {
            String t = bufferedReader.readLine();
            if (t != null) {
                try {
                    buffer[j] = Integer.parseInt(t);
                } catch (NumberFormatException n) {
                    System.out.println("In file " + file.getName() + " Invalid format of numeric value found: \"" +
                            t + "\" An attempt is being made to convert.");
                    String[] s = t.split("\\D");
                    StringBuilder stringBuilder = new StringBuilder();
                    for (String value : s) {
                        stringBuilder.append(value);
                    }
                    String result = stringBuilder.toString();
                    try {
                        buffer[j] = Integer.parseInt(result);
                        System.out.println("The conversion attempt was successful. New value: \"" + result + "\"");
                    } catch (NumberFormatException e) {
                        System.out.println("Conversion failed.");

                    }
                }
            } else
                break;
        }
        return j;
    }

    private void bubbleSorting(int[] buffer) {
        if (isSortDescending) {
            BubbleSort.sortDescendingInt(buffer);
        } else {
            BubbleSort.sortAscendingInt(buffer);
        }
    }

    private static void writingItemsToTempFile(String tempFileName, int i, int j, int[] buffer) throws IOException {
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
}