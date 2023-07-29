public class Main {

    public static void main(String[] args) {

        CommandLineArgsParser parser = new CommandLineArgsParser(args);

        String directoryPath = "app/SortAndMerge";

        FileMergerManager fileMergerManager = new FileMergerManager(directoryPath, parser.getInputFileNames(),
                parser.getOutputFileName(), parser.getDataType(), parser.getSortOrder());

        fileMergerManager.readFilesFromDirectory();

        fileMergerManager.mergeAndSortFiles();

        fileMergerManager.writeToFile();

    }
}
