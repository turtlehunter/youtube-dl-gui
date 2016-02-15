package github.turtlehunter.youtube_dl_gui;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Paths;

/**
 * github.turtlehunter.youtube_dl_gui - uriel youtubedlgui 30/1/2016
 */
public class Main {
    public static File currentDir;
    public static File youtubedl;
    public static GuiMain guiMain = new GuiMain();
    public static String command;
    public static boolean finished = true;

    public static boolean showYesNo(String title, String message) {
        int result = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
        return result == JOptionPane.OK_OPTION;
    }

    public static void main(String[] args) {
        currentDir = new File(Paths.get(".").toAbsolutePath().normalize().toString());
        youtubedl = new File(currentDir, "youtube-dl.exe");

        if(!youtubedl.exists()) {
            boolean download = showYesNo("Youtube-dl not found", "Do you want to download youtube-dl?");
            if(!download) System.exit(-1);
            JFrame frame = new JFrame("Downloading");
            frame.setContentPane(new DownloadGUI().panel1);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
            Thread downloadThread = new Thread(){
                @Override
                public void run() {
                    super.run();
                    try {
                        URL website = new URL("https://rg3.github.io/youtube-dl/update/versions.json");
                        ReadableByteChannel rbc = Channels.newChannel(website.openStream());
                        File tempFile = File.createTempFile("youtubedlgui", "versions");
                        FileOutputStream fos = new FileOutputStream(tempFile);
                        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                        rbc.close();
                        fos.close();

                        JSONParser parser = new JSONParser();
                        Object obj = parser.parse(new FileReader(tempFile));
                        JSONObject jsonObject =  (JSONObject) obj;
                        String latest = (String) jsonObject.get("latest");
                        JSONObject versions = (JSONObject) jsonObject.get("versions");
                        JSONObject downloads = (JSONObject) versions.get(latest);
                        String downloadURL = (String) ((JSONArray) downloads.get("exe")).get(0);

                        URL website2 = new URL(downloadURL);
                        ReadableByteChannel rbc2 = Channels.newChannel(website2.openStream());
                        FileOutputStream fos2 = new FileOutputStream(youtubedl);
                        fos2.getChannel().transferFrom(rbc2, 0, Long.MAX_VALUE);
                        rbc2.close();
                        fos2.close();
                        Main.finished = true;
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }


            };
            finished = false;
            downloadThread.start();
            while(!finished) try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();

            }
            frame.dispose();
        }

        JFrame frame = new JFrame("GuiMain");
        frame.setContentPane(guiMain.panel1);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
