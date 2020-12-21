package com.github.svenarcticfox.javalauncher;

import javafx.scene.control.Menu;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.MenuItem;


import java.io.*;
import java.util.ArrayList;

public abstract class FileMenuUtilities implements Serializable
{
    /**
     * Writes the array list that contains all of the ApplicationMenuItem objects on to the disk
     */
    public static void writeApplicationMenu()
    {
        Stage stage = new Stage();
        ArrayList<ApplicationMenuItem> applicationMenuItems = JavaLauncher.getApplicationMenuItems();
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter binFilter = new FileChooser.ExtensionFilter("Binary Files" ,
                "*.bin");
        fileChooser.getExtensionFilters().add(binFilter);
        fileChooser.setTitle("Save Application Menu");
        try
        {
            FileOutputStream fos = new FileOutputStream(fileChooser.showSaveDialog(stage).getAbsolutePath());

            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(applicationMenuItems);
            oos.close();
            fos.close();
        }
        catch (IOException | NullPointerException e)
        {
            System.out.println("The location was not selected or it is invalid/not available");
            e.printStackTrace();
        }
    }

    public static void openApplicationMenu()
    {
        Stage stage = new Stage();
        Menu applicationsMenu = JavaLauncher.getApplicationsMenu();
        ArrayList<ApplicationMenuItem> applicationMenuItems;


        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter binFilter = new FileChooser.ExtensionFilter("Binary Files" ,
                "*.bin");
        fileChooser.getExtensionFilters().add(binFilter);
        fileChooser.setTitle("Open Application Menu");
        try
        {
            FileInputStream fis = new FileInputStream(fileChooser.showOpenDialog(stage).getAbsolutePath());
            ObjectInputStream ois = new ObjectInputStream(fis);

            Object obj = ois.readObject();
            ois.close();
            fis.close();
            {
                if (obj instanceof ArrayList)
                {
                    applicationsMenu.getItems().clear();
                    applicationMenuItems = (ArrayList<ApplicationMenuItem>) obj;
                    for (ApplicationMenuItem element : applicationMenuItems)
                    {
                        MenuItem menuItem = MenuItemCreator.create(element);
                        applicationsMenu.getItems().add(menuItem);
                    }

                    JavaLauncher.setApplicationsMenu(applicationsMenu);
                    JavaLauncher.setApplicationMenuItems(applicationMenuItems);
                }
                else
                {
                    throw new ClassNotFoundException();
                }
            }
        }
        catch (IOException | NullPointerException e)
        {
            System.out.println("The file was not selected or could not be found.");
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("The class could not be determined.");
            e.printStackTrace();
        }
    }
}
