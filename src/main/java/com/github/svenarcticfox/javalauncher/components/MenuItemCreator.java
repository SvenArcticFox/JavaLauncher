package com.github.svenarcticfox.javalauncher.components;

import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;

public abstract class MenuItemCreator {
    /**
     * This creates a MenuItem Javafx object from the ApplicationMenuItem object
     *
     * @param applicationMenuItem the ApplicationMenuItem object
     * @return the MenuItem object
     */
    public static MenuItem create(ApplicationMenuItem applicationMenuItem) {
        MenuItem menuItem = new MenuItem(applicationMenuItem.getName());
        //Sets the action of the menu item to check of the location is empty or invalid and alerts the user.
        menuItem.setOnAction(x -> {
            try {
                applicationMenuItem.run();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                if (applicationMenuItem.getLocation().isEmpty()) {
                    alert.setTitle("Empty Location");
                    alert.setContentText("The location for application " + applicationMenuItem.getName() + " is empty.");
                    alert.show();
                    System.out.println("The location is null.");
                } else {
                    alert.setTitle("Location does not Exist");
                    alert.setContentText("The location for " + applicationMenuItem.getName() + " does not exist.");
                    alert.show();
                    System.out.println("The location does not exist.");
                }
                e.printStackTrace();
            }
        });
        return menuItem;
    }
}
