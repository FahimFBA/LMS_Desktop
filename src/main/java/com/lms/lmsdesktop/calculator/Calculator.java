package com.lms.lmsdesktop.calculator;

import com.lms.lmsdesktop.MainApplication;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Calculator extends Application {
    private Stage primaryStage;
    private AnchorPane anchorPane;
    MenuBar menuBar = new MenuBar();

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Calculator");
        primaryStage.setResizable(false);
        //addMenuBar();
        mainView();
        //newView();
    }

    public void addMenuBar() {

        Menu menu = new Menu("Menu");
        MenuItem mi = new MenuItem("Close");
        mi.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                System.exit(0);
            }
        });
        menu.getItems().add(mi);
        menuBar.getMenus().add(menu);

        final String os = System.getProperty("os.name");
        if (os != null && os.startsWith("Mac"))
            menuBar.useSystemMenuBarProperty().set(true);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(menuBar);

        primaryStage.setScene(new Scene(borderPane));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void mainView() {
        try {
            FXMLLoader loader = new FXMLLoader(Calculator.class.getResource("calculator-view.fxml"));
            anchorPane = loader.load();

            CalculatorController controller = loader.getController();
            controller.setMain(this);

            Scene scene = new Scene(anchorPane);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void newView() {
        try {
            FXMLLoader loader = new FXMLLoader(Calculator.class.getResource("calculator-view.fxml"));
            anchorPane = (AnchorPane) loader.load();

            CalculatorController controller = loader.getController();
            controller.setMain(this);

            Scene scene = new Scene(anchorPane);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
