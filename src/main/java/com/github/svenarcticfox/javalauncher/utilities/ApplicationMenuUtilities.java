package com.github.svenarcticfox.javalauncher.utilities;

import com.github.svenarcticfox.javalauncher.components.ApplicationMenuItem;
import com.github.svenarcticfox.javalauncher.JavaLauncher;
import com.github.svenarcticfox.javalauncher.components.MenuItemCreator;

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
public abstract class ApplicationMenuUtilities {
    /**
     * Adds an item to the application menu
     */
    public static void addMenuItem() {
        ArrayList<ApplicationMenuItem> applicationMenuItems = JavaLauncher.getApplicationMenuItemList();

        Menu applicationsMenu = JavaLauncher.getApplicationsMenu();
        Stage stage = new Stage();

        Label nameLabel = new Label("Name of Application:");
        TextField nameTextField = new TextField();
        HBox nameHBox = new HBox(10, nameLabel, nameTextField);

        Label applicationPathLabel = new Label("Application Location:");
        TextField applicationPathTextField = new TextField();
        Button browse = new Button("Browse");
        browse.setOnAction(event ->
        {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter exeFilter = new FileChooser.ExtensionFilter("Executable Files",
                    "*.exe");
            fileChooser.getExtensionFilters().add(exeFilter);
            fileChooser.setTitle("Select Application");
            try {
                String location = fileChooser.showOpenDialog(stage).getAbsolutePath();
                applicationPathTextField.setText(location);
            } catch (Exception e) {
                System.out.println("No file selected in file chooser dialog");
                e.printStackTrace();
            }
        });
        HBox applicationHBox = new HBox(10, applicationPathLabel, applicationPathTextField, browse);

        Button add = new Button("Add Application");
        add.setOnAction(event ->
        {
            ApplicationMenuItem applicationMenuItem = new ApplicationMenuItem();
            //checks to make sure the name and location text boxes aren't empty
            if (nameTextField.getText().isEmpty()) {
                stage.toBack();
                Alert isEmptyAlert = new Alert(Alert.AlertType.ERROR);
                isEmptyAlert.setTitle("Invalid Name");
                isEmptyAlert.setContentText("Enter a name for the application.");
                isEmptyAlert.showAndWait();
                System.out.println("The name text field is null.");
                throw new NullPointerException();
            }
            if (applicationPathTextField.getText().isEmpty()) {
                stage.toBack();
                Alert isEmptyAlert = new Alert(Alert.AlertType.ERROR);
                isEmptyAlert.setTitle("Invalid Location");
                isEmptyAlert.setContentText("Enter a valid location for the application.");
                isEmptyAlert.showAndWait();
                System.out.println("The location text field is null.");
                throw new NullPointerException();
            } else {
                applicationMenuItem.setName(nameTextField.getText());
                applicationMenuItem.setLocation(applicationPathTextField.getText());

                applicationMenuItems.add(applicationMenuItem);
                JavaLauncher.setApplicationMenuItemList(applicationMenuItems);

                MenuItem menuItem = MenuItemCreator.create(applicationMenuItem);
                applicationsMenu.getItems().add(menuItem);
                JavaLauncher.setApplicationsMenu(applicationsMenu);
                stage.close();
            }
        });
        add.setDefaultButton(true);

        Button cancel = new Button("Cancel");
        cancel.setOnAction(event -> stage.close());

        HBox buttonHBox = new HBox(10, add, cancel);
        buttonHBox.setAlignment(Pos.CENTER);

        VBox mainVBox = new VBox(15, nameHBox, applicationHBox, buttonHBox);
        mainVBox.setPadding(new Insets(10));
        mainVBox.setAlignment(Pos.CENTER);

        stage.setScene(new Scene(mainVBox));
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
    public static void removeMenuItem() {
        ArrayList<ApplicationMenuItem> applicationMenuItemList = JavaLauncher.getApplicationMenuItemList();
        Menu applicationsMenu = JavaLauncher.getApplicationsMenu();
        Stage stage = new Stage();

        ListView<String> applicationMenuItemListView = new ListView<>();

        //gets the names from each of the menu items and adds them to the list view
        for (ApplicationMenuItem menuItem : applicationMenuItemList)
            applicationMenuItemListView.getItems().add(menuItem.getName());

        applicationMenuItemListView.setMaxSize(175, 250);

        Button remove = new Button("Remove Application");
        remove.setOnAction(event ->
        {
            //checks to make sure an item was selected from the list
            try {
                int removedMenuItemIndex = applicationMenuItemListView.getSelectionModel().getSelectedIndex();
                applicationsMenu.getItems().remove(removedMenuItemIndex);
                applicationMenuItemList.remove(removedMenuItemIndex);
                JavaLauncher.setApplicationMenuItemList(applicationMenuItemList);
                JavaLauncher.setApplicationsMenu(applicationsMenu);
                stage.close();
            } catch (Exception e) {
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

        HBox buttonHBox = new HBox(10, remove, cancel);
        buttonHBox.setAlignment(Pos.CENTER);

        VBox mainVBox = new VBox(15, applicationMenuItemListView, buttonHBox);
        mainVBox.setAlignment(Pos.CENTER);
        mainVBox.setPadding(new Insets(10));

        stage.setScene(new Scene(mainVBox));
        stage.setTitle("Remove Application");
        stage.setAlwaysOnTop(true);
        stage.initStyle(StageStyle.UTILITY);
        stage.resizableProperty().setValue(false);
        stage.show();
    }

    /**
     * Allows users to edit items in their Application Menu by providing a list of the applications in the menu and
     * displaying a dialog that prompts the user
     */
    public static void editMenuItem() {
        ArrayList<ApplicationMenuItem> applicationMenuItemList = JavaLauncher.getApplicationMenuItemList();
        Menu applicationsMenu = JavaLauncher.getApplicationsMenu();
        Stage stage = new Stage();

        ListView<String> applicationMenuItemListView = new ListView<>();

        //adds each of the names of the ApplicationMenuItems objects to the ListView
        for (ApplicationMenuItem appMenuItem : applicationMenuItemList)
            applicationMenuItemListView.getItems().add(appMenuItem.getName());

        applicationMenuItemListView.setMaxSize(175, 250);

        Button edit = new Button("Edit Application");
        edit.setOnAction(event ->
        {
            try {
                menuItemEditor(applicationMenuItemListView.getSelectionModel().getSelectedIndex(),
                        applicationMenuItemList, applicationsMenu);
                stage.close();
            } catch (Exception e) {
                System.out.println("No item was selected from the list.");
                e.printStackTrace();
                Alert nothingSelectedAlert = new Alert(Alert.AlertType.ERROR);
                nothingSelectedAlert.setTitle("No Selected Item");
                nothingSelectedAlert.setContentText("Select an item on the list.");
                stage.toBack();
                nothingSelectedAlert.showAndWait();
            }
        });

        Button close = new Button("Close");
        close.setOnAction(event -> stage.close());

        HBox buttonHBox = new HBox(10, edit, close);
        buttonHBox.setAlignment(Pos.CENTER);

        VBox mainVBox = new VBox(15, applicationMenuItemListView, buttonHBox);
        mainVBox.setAlignment(Pos.CENTER);
        mainVBox.setPadding(new Insets(10));

        stage.setScene(new Scene(mainVBox));
        stage.setTitle("Select Application to Edit");
        stage.setAlwaysOnTop(true);
        stage.initStyle(StageStyle.UTILITY);
        stage.resizableProperty().setValue(false);
        stage.show();
    }

    /**
     * Method that edits menu items
     *
     * @param listIndex               Where the item is in the menu list
     * @param applicationMenuItemList The menu list
     * @param applicationMenu         The menu itself
     */
    private static void menuItemEditor(int listIndex, ArrayList<ApplicationMenuItem> applicationMenuItemList,
                                       Menu applicationMenu) {
        ApplicationMenuItem applicationMenuItem = applicationMenuItemList.get(listIndex);

        Stage stage = new Stage();

        Label nameLabel = new Label("Name of Application:");
        TextField nameTextField = new TextField();
        nameTextField.setText(applicationMenuItem.getName());
        HBox nameHBox = new HBox(10, nameLabel, nameTextField);

        Label applicationPathLabel = new Label("Application Location:");
        TextField applicationPathTextField = new TextField();
        applicationPathTextField.setText(applicationMenuItem.getLocation());

        Button browse = new Button("Browse");
        browse.setOnAction(event ->
        {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter exeFilter = new FileChooser.ExtensionFilter("Executable Files",
                    "*.exe");
            fileChooser.getExtensionFilters().add(exeFilter);
            fileChooser.setTitle("Select Application");
            try {
                String location = fileChooser.showOpenDialog(stage).getAbsolutePath();
                applicationPathTextField.setText(location);
            } catch (Exception e) {
                System.out.println("No file selected in file chooser dialog");
                e.printStackTrace();
            }
        });
        HBox applicationHBox = new HBox(10, applicationPathLabel, applicationPathTextField, browse);

        Button saveChanges = new Button("Save Changes");
        saveChanges.setOnAction(event -> {
            //checks if there are any changes in the name before writing changes
            if (!nameTextField.getText().equalsIgnoreCase(applicationMenuItem.getName())) {
                applicationMenuItem.setName(nameTextField.getText());
            }
            //checks if there are any changes in the location before writing changes
            if (!applicationPathTextField.getText().equalsIgnoreCase((applicationMenuItem.getLocation()))) {
                applicationMenuItem.setLocation(applicationPathTextField.getText());
            }
            //removes old item and adds new item.
            applicationMenu.getItems().remove(listIndex);
            applicationMenu.getItems().add(listIndex, MenuItemCreator.create(applicationMenuItem));

            //Confirms to the user that changes have been added
            Alert changesSavedAlert = new Alert(Alert.AlertType.INFORMATION);
            changesSavedAlert.setTitle("Changes Added");
            changesSavedAlert.setContentText("All changes have been added to the menu.");
            changesSavedAlert.show();
            stage.close();
        });

        Button cancel = new Button("Cancel");
        cancel.setOnAction(event -> stage.close());

        HBox buttonHBox = new HBox(10, saveChanges, cancel);
        buttonHBox.setAlignment(Pos.CENTER);

        VBox mainVBox = new VBox(15, nameHBox, applicationHBox, buttonHBox);
        mainVBox.setPadding(new Insets(10));
        mainVBox.setAlignment(Pos.CENTER);

        stage.setScene(new Scene(mainVBox));
        stage.setTitle("Edit Application");
        stage.setAlwaysOnTop(true);
        stage.initStyle(StageStyle.UTILITY);
        stage.resizableProperty().setValue(false);
        stage.show();
    }
}