package com.github.svenarcticfox.javalauncher;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApplicationMenuItem
{
    String name;
    String location;

    /**
     * This method runs the program specified at the location string
     */
    public void run()
    {
        Runtime runtime = Runtime.getRuntime();
        try
        {
            runtime.exec(location);
        }
        catch (Exception e)
        {
            if (location.isEmpty())
            {
                System.out.println("The location is null.");
            }
            else
            {
                System.out.println("The application could not be located.");
            }
            e.printStackTrace();
        }
    }
}