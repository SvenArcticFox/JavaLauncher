package com.github.svenarcticfox.javalauncher;


import javafx.scene.control.MenuItem;

public class MenuItemCreator
{
    public static MenuItem create(ApplicationMenuItem applicationMenuItem)
    {
        MenuItem menuItem = new MenuItem(applicationMenuItem.getName());
        menuItem.setOnAction(x -> applicationMenuItem.run());
        return menuItem;
    }
}
