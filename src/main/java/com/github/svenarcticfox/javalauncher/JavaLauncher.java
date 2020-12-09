package com.github.svenarcticfox.javalauncher;

import javafx.application.Application;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Scene;

import lombok.Getter;
import lombok.Setter;

public class JavaLauncher extends Application
{
    @Getter
    @Setter
    private static Menu applicationsMenu;

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
        exitItem.setOnAction(x -> primaryStage.close());
        fileMenu.getItems().add(exitItem);

        applicationsMenu = new Menu("Applications");

        Menu settingsMenu = new Menu("Settings");

        MenuItem addApplication = new MenuItem("Add Application");
        addApplication.setOnAction(event -> ApplicationMenuUtilities.addMenuItem());

        MenuItem removeApplication = new MenuItem("Remove Application");
        removeApplication.setOnAction(event -> ApplicationMenuUtilities.removeMenuItem());

        settingsMenu.getItems().addAll(addApplication , removeApplication);

        menuBar.getMenus().addAll(fileMenu , applicationsMenu , settingsMenu);
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(menuBar);
        Scene scene = new Scene(borderPane, 600 , 400 , Color.WHITE);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Java Launcher");
        primaryStage.show();
    }
}
