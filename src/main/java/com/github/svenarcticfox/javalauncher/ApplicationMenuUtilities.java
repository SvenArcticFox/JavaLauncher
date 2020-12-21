package com.github.svenarcticfox.javalauncher;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;

/**
 * Class that contains all of the utilities for the Application Menu on the main JavaLauncher class
 */
public abstract class ApplicationMenuUtilities
{
    /**
     * Adds an item to the application menu
     */
    public static void addMenuItem()
    {
        ArrayList<ApplicationMenuItem> applicationMenuItems = JavaLauncher.getApplicationMenuItems();

        Menu applicationsMenu = JavaLauncher.getApplicationsMenu();
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
            fileChooser.setTitle("Select Application");
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
            //checks to make sure the name and location text boxes aren't empty
            if (nameTextField.getText().isEmpty())
            {
                stage.toBack();
                Alert isEmptyAlert = new Alert(Alert.AlertType.ERROR);
                isEmptyAlert.setTitle("Invalid Name");
                isEmptyAlert.setContentText("Enter a name for the application.");
                isEmptyAlert.showAndWait();
                System.out.println("The name text field is null.");
                throw new NullPointerException();
            }
            if (applicationPathTextField.getText().isEmpty())
            {
                stage.toBack();
                Alert isEmptyAlert = new Alert(Alert.AlertType.ERROR);
                isEmptyAlert.setTitle("Invalid Location");
                isEmptyAlert.setContentText("Enter a valid location for the application.");
                isEmptyAlert.showAndWait();
                System.out.println("The location text field is null.");
                throw new NullPointerException();
            }
            else
            {
                applicationMenuItem.setName(nameTextField.getText());
                applicationMenuItem.setLocation(applicationPathTextField.getText());

                applicationMenuItems.add(applicationMenuItem);
                JavaLauncher.setApplicationMenuItems(applicationMenuItems);

                MenuItem menuItem = MenuItemCreator.create(applicationMenuItem);
                applicationsMenu.getItems().add(menuItem);
                JavaLauncher.setApplicationsMenu(applicationsMenu);
                stage.close();
            }
        });
        add.setDefaultButton(true);

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

    /**
     * Removes an item from the application menu by displaying a list to the user and allowing them to select
     * an item to be removed from the menu
     */
    public static void removeMenuItem()
    {
        ArrayList<ApplicationMenuItem> applicationMenuItems = JavaLauncher.getApplicationMenuItems();
        Menu applicationsMenu = JavaLauncher.getApplicationsMenu();
        Stage stage = new Stage();

        //gets an array of menu items from the applications menu
        MenuItem[] menuItems = applicationsMenu.getItems().toArray(new MenuItem[0]);
        ListView<String> applicationMenuItemList = new ListView<>();

        //gets the names from each of the menu items and adds them to the list view
        for (MenuItem menuItem : menuItems)
            applicationMenuItemList.getItems().add(menuItem.getText());

        applicationMenuItemList.setMaxSize(175 , 250);

        Button remove = new Button("Remove Application");
        remove.setOnAction(event ->
        {
            //checks to make sure an item was selected from the list
            try
            {
                int removedMenuItemIndex = applicationMenuItemList.getSelectionModel().getSelectedIndex();
                applicationsMenu.getItems().remove(removedMenuItemIndex);
                applicationMenuItems.remove(removedMenuItemIndex);
                JavaLauncher.setApplicationMenuItems(applicationMenuItems);
                JavaLauncher.setApplicationsMenu(applicationsMenu);
                stage.close();
            }
            catch (Exception e)
            {
                System.out.println("No item was selected from the list.");
                e.printStackTrace();
                Alert nothingSelectedAlert = new Alert(Alert.AlertType.ERROR);
                nothingSelectedAlert.setTitle("No Selected Item");
                nothingSelectedAlert.setContentText("Select an item on the list.");
                stage.toBack();
                nothingSelectedAlert.showAndWait();
            }
        });

        Button cancel = new Button("Cancel");
        cancel.setOnAction(event -> stage.close());

        HBox buttonHBox = new HBox(10 , remove , cancel);
        buttonHBox.setAlignment(Pos.CENTER);

        VBox mainVBox = new VBox(15 , applicationMenuItemList , buttonHBox);
        mainVBox.setAlignment(Pos.CENTER);
        mainVBox.setPadding(new Insets(10));

        Scene scene = new Scene(mainVBox);
        stage.setScene(scene);
        stage.setTitle("Remove Application");
        stage.setAlwaysOnTop(true);
        stage.initStyle(StageStyle.UTILITY);
        stage.resizableProperty().setValue(false);
        stage.show();
    }
}
