import sorter.*;

import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class FileMergerManager {
    private String directoryPath;
    private List<String> inputFileNames;
    private String outputFileName;
    private DataType dataType;
    private SortOrder sortOrder;
    private List<List<String>> fileContents = new ArrayList<>();
    private List<?> mergeSortedList;

    public FileMergerManager(String directoryPath, List<String> inputFileNames, String outputFileName,
                             DataType dataType, SortOrder sortOrder) {
        this.directoryPath = directoryPath;
        this.inputFileNames = inputFileNames;
        this.dataType = dataType;
        this.sortOrder = sortOrder;
        this.outputFileName = outputFileName;

    }

    public List<List<String>> readFilesFromDirectory() {

        fileContents.clear();

        for (String fileName : inputFileNames) {

            File file = new File(directoryPath, fileName);

            if (!file.exists()) {
                throw new IllegalArgumentException("Ошибка: файл с именем \"" + fileName +
                                                    "\" не найден в указанной директории.");
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                List<String> lines = new ArrayList<>();
                String line;
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }
                fileContents.add(lines);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileContents;
    }


    public List<?> mergeAndSortFiles() {

        FileSorter fileSorter;

        if (dataType == DataType.INTEGER) {
            fileSorter = new IntegerSorter(fileContents, sortOrder);
        } else {
            fileSorter = new StringSorter(fileContents, sortOrder);
        }

        mergeSortedList = fileSorter.sort();

        return mergeSortedList;
    }


    public File writeToFile() {

        File outputFile = new File(directoryPath, outputFileName);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            for (Object item : mergeSortedList) {
                writer.write(item.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return outputFile;
    }
}
