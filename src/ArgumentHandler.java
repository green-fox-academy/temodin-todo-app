import javax.swing.text.DefaultEditorKit;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArgumentHandler {
    public void argumentHandler(String[] arguments) {
        if (arguments.length == 0) {
            printManualFileContent();
        } else if (arguments[0].equals("-l")) {
            printToDoFileContent(getToDoFileContent(Paths.get("../Data/todo.txt")));
        } else if (arguments[0].equals("-a")) {
            try {
                addNewTask(arguments[1]);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Unable to add: no task provided");
            }

        } else if (arguments[0].equals("-r")) {
            if (arguments.length < 2) {
                System.out.println("Unable to remove: no index provided");
            } else removeTask(arguments[1]);
        } else {
            System.out.println("Unsupported argument(s)");
            printManualFileContent();
        }

    }

    public List<String[]> getToDoFileContent(Path filePath) {
        List<String> fileContentLines = new ArrayList<>();
        try {
            fileContentLines = Files.readAllLines(filePath);
        } catch (IOException e) {
            System.out.println("File not found.");
        }
        List<String[]> fileContent = new ArrayList<>();
        for (String line : fileContentLines
        ) {
            fileContent.add(line.split(","));
        }
        return fileContent;
    }

    public void printManualFileContent() {
        List<String> manualLines = new ArrayList<>();
        Path manualPath = Paths.get("../Data/manual.txt");
        try {
            manualLines = Files.readAllLines(manualPath);
        } catch (IOException e) {
            System.out.println("Manual not found.");
            return;
        }

        for (String line : manualLines
        ) {
            System.out.println(line);
        }
    }

    public void printToDoFileContent(List<String[]> fileContent) {
        if (fileContent.size() == 0) {
            System.out.println("No todos for today! :)");
        } else {
            for (int i = 0; i < fileContent.size(); i++) {
                System.out.print(i + 1 + " - " + statusPrint(fileContent.get(i)[0]) + " " + fileContent.get(i)[1]);
                System.out.println();
            }
        }
    }

    private String statusPrint(String statusFlag) {
        if (statusFlag.equals("1")) {
            return "[x]";
        } else return "[ ]";
    }

    private void addToFile(Path filePath, String task) {
        try {
            Files.write(filePath, Collections.singleton("0," +task), StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("Cannot write file");
        }
    }

    private void addNewTask(String task) {
        addToFile(Paths.get("../Data/todo.txt"), task);
    }

    private void removeTask(String input) {
        Path toDoPath = Paths.get("../Data/todo.txt");
        Integer index;
        try {
            index = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Second argument must be a number");
            return;
        }
        List<String[]> fileContent = getToDoFileContent(toDoPath);
        try {
            fileContent.remove(index - 1);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Index is out of bound");
        }
        try {
            Files.write(toDoPath, concatenator(fileContent));
        } catch (IOException e) {
            System.out.println("Cannot write file");
        }

    }

    private List<String> concatenator (List<String[]> listToConvert) {
        List<String> listToReturn = new ArrayList<>();
        for (int i = 0; i < listToConvert.size(); i++) {
            listToReturn.add(listToConvert.get(i)[0] + "," + listToConvert.get(i)[1]);
        }
        return listToReturn;
        }
    }

