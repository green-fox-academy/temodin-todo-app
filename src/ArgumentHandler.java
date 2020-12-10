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
            printManualFileContent(getFileContent(Paths.get("../Data/manual.txt")));
        }
        else if (arguments[0].equals("-l")) {
            printToDoFileContent(getFileContent(Paths.get("../Data/todo.txt")));
        }
        else if (arguments[0].equals("-a")) {
            addNewTask(Paths.get("../Data/todo.txt"),arguments[1]);
        }

    }

    public List<String> getFileContent(Path filePath) {
        Path pathManual = filePath;
        List<String> fileContent = new ArrayList<>();
        try {
            fileContent = Files.readAllLines(filePath);
        } catch (IOException e) {
            System.out.println("File not found.");
        }
        return fileContent;

    }

    public void printManualFileContent(List<String> fileContent) {
        for (String line : fileContent
        ) {
            System.out.println(line);
        }
    }
    public void printToDoFileContent(List<String> fileContent) {
        if (fileContent.size() == 0) {
            System.out.println("No todos for today! :)");
        }
        for (int i = 0; i < fileContent.size(); i++) {
            System.out.println(i+1 + " - " + fileContent.get(i));
        }
    }

    private void addNewTask (Path filePath, String task) {
        try {
            Files.write(filePath, Collections.singleton("/n" + task), StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("Cannot write file");
        }
    }
}
