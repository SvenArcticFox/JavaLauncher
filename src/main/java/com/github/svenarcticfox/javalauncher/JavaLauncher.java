package com.github.svenarcticfox.javalauncher;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.stage.StageStyle;

import javax.swing.JOptionPane;

public class JavaLauncher extends Application
{
    private Menu applicationsMenu;

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
        settingsMenu.getItems().add(addApplication);
        addApplication.setOnAction(x -> addNewApplicationMenuItem());

        menuBar.getMenus().addAll(fileMenu , applicationsMenu , settingsMenu);
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(menuBar);
        Scene scene = new Scene(borderPane, 600 , 400 , Color.WHITE);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Java Launcher");
        primaryStage.show();
    }

    /**
     * Adds a new application to the ApplicationMenu
     */
    public void addNewApplicationMenuItem()
    {
        Stage stage = new Stage();

        Label nameLabel = new Label("Name of Application:");
        TextField nameTextField = new TextField();
        HBox nameHBox = new HBox(10 , nameLabel , nameTextField);

        Label applicationPathLabel = new Label("Application Location:");
        TextField applicationPathTextField = new TextField();
        Button browse = new Button("Browse");
        browse.setOnAction(event ->
        {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter exeFilter = new FileChooser.ExtensionFilter("Executable Files" ,
                    "*.exe");
            fileChooser.getExtensionFilters().add(exeFilter);
            try
            {
                String location = fileChooser.showOpenDialog(stage).getAbsolutePath();
                applicationPathTextField.setText(location);
            }
            catch (Exception e)
            {
                System.out.println("No file selected in file chooser dialog");
                e.printStackTrace();
            }
        });
        HBox applicationHBox = new HBox(10 , applicationPathLabel , applicationPathTextField , browse);

        Button add = new Button("Add Application");
        add.setOnAction(event ->
        {
            ApplicationMenuItem applicationMenuItem = new ApplicationMenuItem();
            if (nameTextField.getText().isEmpty())
            {
                JOptionPane.showMessageDialog(
                        null ,
                        "Enter a name for the application." ,
                        "Invalid Name" ,
                        JOptionPane.ERROR_MESSAGE);
                System.out.println("The name text field is null.");
                throw new NullPointerException();
            }
            if (applicationPathTextField.getText().isEmpty())
            {
                JOptionPane.showMessageDialog(
                        null ,
                        "Enter a valid location for the application." ,
                        "Invalid Location" ,
                        JOptionPane.ERROR_MESSAGE);
                System.out.println("The location text field is null.");
                throw new NullPointerException();
            }
            else
            {
                applicationMenuItem.setName(nameTextField.getText());
                applicationMenuItem.setLocation(applicationPathTextField.getText());

                MenuItem menuItem = MenuItemCreator.create(applicationMenuItem);
                applicationsMenu.getItems().add(menuItem);
                stage.close();
            }
        });

        Button cancel = new Button("Cancel");
        cancel.setOnAction(event -> stage.close());

        HBox buttonHBox = new HBox(10 ,add , cancel);
        buttonHBox.setAlignment(Pos.CENTER);

        VBox mainVBox = new VBox(15 , nameHBox , applicationHBox , buttonHBox);
        mainVBox.setPadding(new Insets(10));
        mainVBox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(mainVBox);
        stage.setScene(scene);
        stage.setTitle("Add Application");
        stage.setAlwaysOnTop(true);
        stage.initStyle(StageStyle.UTILITY);
        stage.resizableProperty().setValue(false);
        stage.show();
    }
}
