package com.github.svenarcticfox.javalauncher.components;
import lombok.*;

import java.io.IOException;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApplicationMenuItem implements Serializable
{
    private String name;
    private String location;

    /**
     * This method runs the program specified at the location string
     */
    public void run() throws IOException {
        Runtime runtime = Runtime.getRuntime();
        runtime.exec(location);
    }
}