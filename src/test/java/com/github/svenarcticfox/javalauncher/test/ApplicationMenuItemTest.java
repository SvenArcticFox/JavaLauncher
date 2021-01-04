package com.github.svenarcticfox.javalauncher.test;

import com.github.svenarcticfox.javalauncher.ApplicationMenuItem;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ApplicationMenuItemTest
{
    /**
     * Checks to make sure that all of the fields are null
     */
    @Test
    public void NoArgsConstructor()
    {
        ApplicationMenuItem applicationMenuItem = new ApplicationMenuItem();
        assertNull(applicationMenuItem.getName() , "The name is supposed to be null.");
        assertNull(applicationMenuItem.getLocation() , "The location is supposed to be null");
    }

    /**
     * Checks to make sure that all of the fields are not null and executes the path of the program if the location
     * is not null.
     */
    @Test
    public void AllArgsConstructor()
    {
        ApplicationMenuItem applicationMenuItem = new ApplicationMenuItem("Notepad" , "notepad");
        assertNotNull(applicationMenuItem.getName() , "The name is supposed to be not null.");
        assertNotNull(applicationMenuItem.getLocation() , "The location is supposed to be not null.");
        if (!applicationMenuItem.getLocation().isEmpty())
            applicationMenuItem.run();
    }

    /**
     * Checks to make sure that the run method in the object throws an exception when the location is null or not found.
     */

    @Test
    public void Exception()
    {
        ApplicationMenuItem applicationMenuItem = new ApplicationMenuItem();
        applicationMenuItem.setLocation("test");
        applicationMenuItem.run();

        applicationMenuItem.setLocation("");
        applicationMenuItem.run();
    }
}