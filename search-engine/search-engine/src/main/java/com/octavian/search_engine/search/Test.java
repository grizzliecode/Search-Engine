package com.octavian.search_engine.search;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Test {
    public static void main(String[] args) {
        File file = new File("d:\\6th semester\\CV_Vlad_Roatiș___2025 (1).pdf");
        System.out.println(file.exists());
        try {
            Desktop desktop = Desktop.getDesktop();
            desktop.open(file);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
