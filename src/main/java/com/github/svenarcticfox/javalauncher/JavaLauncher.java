package com.github.svenarcticfox.javalauncher;

import com.github.svenarcticfox.javalauncher.components.ApplicationMenuItem;
import com.github.svenarcticfox.javalauncher.utilities.ApplicationMenuUtilities;
import com.github.svenarcticfox.javalauncher.utilities.FileMenuUtilities;

import javafx.application.Application;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

/**To oversimplify, this is a program that launches other programs. This is the main class.
 * Written by Trevor Radez (SvenArcticFox) in 2020
 * Language: Java
 * JDK: BellSoft Liberica 1.8 (Provided by IntelliJ)
 */
public class JavaLauncher extends Application
{
    @Getter
    @Setter
    private static Menu applicationsMenu;
    @Getter
    @Setter
    private static ArrayList<ApplicationMenuItem> applicationMenuItemList;

    public static void main(String[] args)
    {
        launch(args);
    }

    /**
     * Creates the layout for the main window and adds shortcut keys for certain functions.
     * @param primaryStage the stage, provided by JavaFX.
     */
    @Override
    public void start(Stage primaryStage)
    {
        applicationMenuItemList = new ArrayList<>();
        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new Menu("File");

        MenuItem saveItem = new MenuItem("Save");
        saveItem.setOnAction(event -> FileMenuUtilities.writeApplicationMenu());
        saveItem.setAccelerator(new KeyCodeCombination(KeyCode.S , KeyCombination.CONTROL_DOWN));

        MenuItem openItem = new MenuItem("Open");
        openItem.setOnAction(event -> FileMenuUtilities.openApplicationMenu());
        openItem.setAccelerator(new KeyCodeCombination(KeyCode.O , KeyCombination.CONTROL_DOWN));

        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(x -> primaryStage.close());
        exitItem.setAccelerator(new KeyCodeCombination(KeyCode.F4 , KeyCombination.ALT_DOWN));

        fileMenu.getItems().addAll(openItem , saveItem , exitItem);

        applicationsMenu = new Menu("Applications");

        Menu settingsMenu = new Menu("Settings");

        MenuItem addApplication = new MenuItem("Add Application");
        addApplication.setOnAction(event -> ApplicationMenuUtilities.addMenuItem());

        MenuItem editApplication = new MenuItem("Edit Application");
        editApplication.setOnAction(event -> ApplicationMenuUtilities.editMenuItem());

        MenuItem removeApplication = new MenuItem("Remove Application");
        removeApplication.setOnAction(event -> ApplicationMenuUtilities.removeMenuItem());

        settingsMenu.getItems().addAll(addApplication , editApplication , removeApplication);

        menuBar.getMenus().addAll(fileMenu , applicationsMenu , settingsMenu);
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(menuBar);

        Image windowIcon = new Image("/icons/CoffeeBean.png");
        primaryStage.getIcons().add(windowIcon);

        primaryStage.setScene(new Scene(borderPane, 600 , 400 , Color.WHITE));
        primaryStage.setTitle("Java Launcher");
        primaryStage.show();
    }
}
