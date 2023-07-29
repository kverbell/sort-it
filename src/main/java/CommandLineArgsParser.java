import sorter.DataType;
import sorter.SortOrder;

import java.util.ArrayList;
import java.util.List;

public class CommandLineArgsParser {
    private List<String> inputFileNames;
    private String outputFileName = "";
    private DataType dataType;
    private SortOrder sortOrder;
    String invalidChar = ".*[^a-zA-Z0-9_\\.].*";

    public CommandLineArgsParser(String[] args) {
        inputFileNames = new ArrayList<>();
        parseArgs(args);
    }

    private void parseArgs(String[] args) {

        if (args.length < 3) {
            throw new IllegalArgumentException("Ошибка: недостаточно аргументов командной строки.");
        }

        for (int i = 0; i < args.length; i++) {

            String arg = args[i];

            if (arg.equalsIgnoreCase("-i") || arg.equalsIgnoreCase("-s")) {
                if (arg.equalsIgnoreCase("-i")) {
                    dataType = DataType.INTEGER;
                } else {
                    dataType = DataType.STRING;
                }
            } else if (arg.equalsIgnoreCase("-a") || arg.equalsIgnoreCase("-d")) {
                if (arg.equalsIgnoreCase("-a")) {
                    sortOrder = SortOrder.ASCENDING;
                } else {
                    sortOrder = SortOrder.DESCENDING;
                }
            } else {
                if (outputFileName.isEmpty()) {
                    outputFileName = arg;
                } else {
                    if (inputFileNames.contains(arg) || arg.equals(outputFileName)) {
                        throw new IllegalArgumentException("Ошибка: имена файлов не должны повторяться.");
                    }
                    if (arg.matches(invalidChar)) {
                        throw new IllegalArgumentException("Ошибка: имена файлов не должны содержать недопустимые символы.");
                    }
                    inputFileNames.add(arg);
                }
            }
        }


        if (dataType == null) {
            throw new IllegalArgumentException("Ошибка: не указан тип данных.");
        }

        if (sortOrder == null) {
            sortOrder = SortOrder.ASCENDING;
        }

    }

    public List<String> getInputFileNames() {
        return inputFileNames;
    }

    public String getOutputFileName() {
        return outputFileName;
    }

    public DataType getDataType() {
        return dataType;
    }

    public SortOrder getSortOrder() {
        return sortOrder;
    }
}

