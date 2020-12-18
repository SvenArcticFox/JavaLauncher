package com.github.svenarcticfox.javalauncher;

import javafx.scene.control.MenuItem;

public abstract class MenuItemCreator
{
    /**
     * This creates a MenuItem Javafx object from the ApplicationMenuItem object
     * @param applicationMenuItem the ApplicationMenuItem object
     * @return the MenuItem object
     */
    public static MenuItem create(ApplicationMenuItem applicationMenuItem)
    {
        MenuItem menuItem = new MenuItem(applicationMenuItem.getName());
        menuItem.setOnAction(x -> applicationMenuItem.run());
        return menuItem;
    }
}
