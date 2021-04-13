package sample;

import javafx.application.Application;
import javafx.concurrent.Task;;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Main extends Application {

    Task<Void> task;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("AutoPotion");
        primaryStage.setScene(new Scene(root, 275, 250));
        Button btn1 = (Button) root.lookup("#btn1");
        Button btn2 = (Button) root.lookup("#btn2");
        TextField txt1 = (TextField) root.lookup("#txt1");

        btn1.setOnAction(event -> {
            if (task != null) {
                System.out.println("Task already running");
                return;
            }
            task = new Task<Void>() {
                @Override
                protected Void call() {
                    activeAutoPotion(Integer.parseInt(txt1.getText()));
                    return null;
                }
            };
            Thread thread = new Thread(task);
            thread.setDaemon(true);
            thread.start();
        });
        btn2.setOnAction(event -> {

            if (task == null) {
                return;
            }
            task.cancel();
            task = null;
        });
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void activeAutoPotion(int delay) {
        while (!task.isCancelled()) {
            Robot robot = null;
            try {
                robot = new Robot();
                robot.setAutoDelay(delay);
                robot.keyPress(KeyEvent.VK_Q);
                robot.keyRelease(KeyEvent.VK_Q);
                robot.keyPress(KeyEvent.VK_W);
                robot.keyRelease(KeyEvent.VK_W);
                robot.keyPress(KeyEvent.VK_E);
                robot.keyRelease(KeyEvent.VK_E);
            } catch (AWTException e) {
                e.printStackTrace();
            }
        }
        return;
    }
}

