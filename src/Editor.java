import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;

public class Editor extends Application {

    private TextArea textArea;
    private boolean hackerMode = false;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Simple Text Editor");

        textArea = new TextArea();
        BorderPane root = new BorderPane(textArea);

        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");

        menuBar.getMenus().add(fileMenu);

        MenuItem openMenuItem = new MenuItem("Open");
        openMenuItem.setOnAction(e -> openFile());
        MenuItem saveMenuItem = new MenuItem("Save");
        saveMenuItem.setOnAction(e -> saveFile());
        MenuItem exitMenuItem = new MenuItem("Exit");
        exitMenuItem.setOnAction(e -> System.exit(0));

        ToggleButton hackerMode = new ToggleButton("Hacker Mode");
        hackerMode.setOnAction(e -> hackerModeToggle());

        fileMenu.getItems().addAll(openMenuItem, saveMenuItem, new SeparatorMenuItem(), exitMenuItem);

        root.setTop(menuBar);
        root.setBottom(hackerMode);

        Scene scene = new Scene(root, 1600, 900);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void openFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Text File");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                textArea.clear();
                while ((line = reader.readLine()) != null) {
                    textArea.appendText(line + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Text File");
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(textArea.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void hackerModeToggle() {
        hackerMode = !hackerMode;

        if (hackerMode) {
            textArea.setStyle("-fx-control-inner-background: black; -fx-text-fill: green;");
        } else {
            textArea.setStyle("-fx-control-inner-background: white; -fx-text-fill: black;");
        }
    }
}
