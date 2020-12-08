package com.github.svenarcticfox.javalauncher;

import javafx.application.Application;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.control.MenuBar;

public class JavaLauncher extends Application
{

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)
    {
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        MenuItem exitItem = new MenuItem("Exit");
        fileMenu.getItems().add(exitItem);
        Menu applicationsMenu = new Menu("Applications");

        menuBar.getMenus().add(fileMenu);
        BorderPane borderPane = new BorderPane();
    }
}
