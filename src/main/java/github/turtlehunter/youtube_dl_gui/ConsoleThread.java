package github.turtlehunter.youtube_dl_gui;

import javax.swing.*;
import java.io.*;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * github.turtlehunter.youtube_dl_gui - uriel youtubedlgui 30/1/2016
 */
public class ConsoleThread extends Thread {
    @Override
    public void run() {
        super.run();
        try {
            Process process = Runtime.getRuntime().exec(Main.command);
            Scanner sc = new Scanner(process.getInputStream());
            while (sc.hasNext()) {
                String str = sc.nextLine();
                Main.guiMain.textArea1.append(str + "\n");
                Main.guiMain.consoleOutput2.append(str + "\n");
                System.out.println(str);
            }
            try {
                process.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
