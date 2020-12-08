package com.github.svenarcticfox.javalauncher.test;

import com.github.svenarcticfox.javalauncher.ApplicationMenuItem;
import org.junit.Test;
import static org.junit.Assert.*;

public class ApplicationMenuItemTest
{
    /**
     * Checks to make sure that all of the fields are null
     */
    @Test
    public void NoArgsConstructor()
    {
        ApplicationMenuItem applicationMenuItem = new ApplicationMenuItem();
        assertNull("The name is supposed to be null." , applicationMenuItem.getName());
        assertNull("The location is supposed to be null" , applicationMenuItem.getLocation());
    }

    /**
     * Checks to make sure that all of the fields are not null and executes the path of the program if the location
     * is not null.
     */
    @Test
    public void AllArgsConstructor()
    {
        ApplicationMenuItem applicationMenuItem = new ApplicationMenuItem("Notepad" , "notepad");
        assertNotNull("The name is supposed to be not null.", applicationMenuItem.getName());
        assertNotNull("The location is supposed to be not null.", applicationMenuItem.getLocation());
        if (applicationMenuItem.getLocation() != null)
            applicationMenuItem.run();
    }

    /**
     * Checks to make sure that the run method in the object throws an exception when the location is null or not found.
     */
    @Test(expected = Exception.class)
    public void Exception()
    {
        ApplicationMenuItem applicationMenuItem = new ApplicationMenuItem();
        applicationMenuItem.setLocation("test");
        applicationMenuItem.run();
    }
}