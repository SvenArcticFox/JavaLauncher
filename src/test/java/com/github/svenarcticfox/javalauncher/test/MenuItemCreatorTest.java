package com.github.svenarcticfox.javalauncher.test;

import javafx.scene.control.MenuItem;
import com.github.svenarcticfox.javalauncher.*;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class MenuItemCreatorTest
{
    @Test
    public void Creator()
    {
        ApplicationMenuItem applicationMenuItem = new ApplicationMenuItem("Notepad" , "notepad");
        assertNotNull("The name is supposed to be not null.", applicationMenuItem.getName());
        assertNotNull("The location is supposed to be not null.", applicationMenuItem.getLocation());
        MenuItem menuItem = MenuItemCreator.create(applicationMenuItem);
        assertNotNull("The menuItem object should not be null" , menuItem);
        assertNotNull("The action should not be null", menuItem.getOnAction());
    }
}
