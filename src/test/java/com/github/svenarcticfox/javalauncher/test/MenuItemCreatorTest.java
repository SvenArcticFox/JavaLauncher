package com.github.svenarcticfox.javalauncher.test;

import javafx.scene.control.MenuItem;
import com.github.svenarcticfox.javalauncher.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class MenuItemCreatorTest
{
    /**
     * This tests to make sure the new MenuItem is not null and the action is not null.
     */
    @Test
    public void Creator()
    {
        ApplicationMenuItem applicationMenuItem = new ApplicationMenuItem("Notepad" , "notepad");
        assertNotNull(applicationMenuItem.getName(), "The name is supposed to be not null.");
        assertNotNull(applicationMenuItem.getLocation(), "The location is supposed to be not null.");
        MenuItem menuItem = MenuItemCreator.create(applicationMenuItem);
        assertNotNull(menuItem, "The menuItem object should not be null");
        assertNotNull(menuItem.getOnAction(), "The action should not be null");
    }
}
