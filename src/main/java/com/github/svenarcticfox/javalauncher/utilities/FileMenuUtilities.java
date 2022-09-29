package com.github.svenarcticfox.javalauncher.utilities;

import com.github.svenarcticfox.javalauncher.components.ApplicationMenuItem;
import com.github.svenarcticfox.javalauncher.JavaLauncher;
import com.github.svenarcticfox.javalauncher.components.MenuItemCreator;
import javafx.scene.control.Menu;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.MenuItem;


import java.io.*;
import java.util.ArrayList;

public abstract class FileMenuUtilities {
    /**
     * Writes the array list that contains all of the ApplicationMenuItem objects on to the disk
     */
    public static void writeApplicationMenu() {
        Stage stage = new Stage();
        ArrayList<ApplicationMenuItem> applicationMenuItemList = JavaLauncher.getApplicationMenuItemList();
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter binFilter = new FileChooser.ExtensionFilter("Binary Files",
                "*.bin");
        fileChooser.getExtensionFilters().add(binFilter);
        fileChooser.setTitle("Save Application Menu");
        try {
            //writes the ArrayList to a file, so it can be retrieved later
            FileOutputStream fos = new FileOutputStream(fileChooser.showSaveDialog(stage).getAbsolutePath());

            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(applicationMenuItemList);
            oos.close();
            fos.close();
        } catch (IOException | NullPointerException e) {
            System.out.println("The location was not selected or it is invalid/not available");
            e.printStackTrace();
        }
    }

    /**
     * Opens the array list that contains all of the ApplicationMenuItem objects and converts each object to a MenuItem
     * for the Application Menu
     */
    public static void openApplicationMenu() {
        Stage stage = new Stage();
        Menu applicationsMenu = JavaLauncher.getApplicationsMenu();
        ArrayList<ApplicationMenuItem> applicationMenuItemList;


        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter binFilter = new FileChooser.ExtensionFilter("Binary Files",
                "*.bin");
        fileChooser.getExtensionFilters().add(binFilter);
        fileChooser.setTitle("Open Application Menu");
        try {
            FileInputStream fis = new FileInputStream(fileChooser.showOpenDialog(stage).getAbsolutePath());
            ObjectInputStream ois = new ObjectInputStream(fis);

            Object obj = ois.readObject();
            ois.close();
            fis.close();
            {
                //checks to make sure that the file loaded is an arraylist object of ApplicationMenuItem
                if (obj instanceof ArrayList) {
                    //clears the application menu and adds each item from the ArrayList individually
                    applicationsMenu.getItems().clear();
                    applicationMenuItemList = (ArrayList<ApplicationMenuItem>) obj;
                    for (ApplicationMenuItem element : applicationMenuItemList) {
                        MenuItem menuItem = MenuItemCreator.create(element);
                        applicationsMenu.getItems().add(menuItem);
                    }

                    JavaLauncher.setApplicationsMenu(applicationsMenu);
                    JavaLauncher.setApplicationMenuItemList(applicationMenuItemList);
                } else {
                    //throws a ClassCastException if the object is not an instance of ArrayList
                    throw new ClassCastException();
                }
            }
        } catch (IOException | NullPointerException e) {
            System.out.println("The file was not selected or could not be found.");
            e.printStackTrace();
        } catch (ClassNotFoundException | ClassCastException e) {
            System.out.println("The class could not be determined.");
            e.printStackTrace();
        }
    }
}
