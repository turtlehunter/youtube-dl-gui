package github.turtlehunter.youtube_dl_gui;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultCaret;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ContainerAdapter;
import java.beans.PropertyChangeListener;
import java.io.File;

/**
 * github.turtlehunter.youtube_dl_gui - uriel youtubedlgui 30/1/2016
 */
public class GuiMain {
    private JTabbedPane tabbedPane1;
    public JPanel panel1;
    public JTextArea consoleOutput;
    private JTextField linkTextField;
    private JTextField outputTextField;
    private JTextField folderTextField;
    private JButton downloadButton;
    private JProgressBar downloadProgressBar;
    private JButton button2;
    private JButton checkForUpdatesButton;
    private JProgressBar updatingProgressBar;
    public JTextArea consoleOutput2;
    public JTextArea textArea1;
    private JProgressBar progressBar1;
    private JScrollPane scrollpanel;

    public GuiMain() {
        DefaultCaret caret = (DefaultCaret)textArea1.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setDialogTitle("Select output directory");
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int returnVal = chooser.showOpenDialog(panel1.getParent());
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();
                    folderTextField.setText(file.getAbsolutePath());
                }
            }
        });
        checkForUpdatesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updatingProgressBar.setVisible(true);
                Main.command = Main.youtubedl.getAbsolutePath() + " --update";
                ConsoleThread thread = new ConsoleThread();
                thread.start();
                updatingProgressBar.setVisible(false);
            }
        });

        downloadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                downloadProgressBar.setVisible(true);
                Main.command = Main.youtubedl.getAbsolutePath() + " --no-color " + outputTextField.getText() + " " + linkTextField.getText();
                ConsoleThread thread = new ConsoleThread();
                System.out.println(Main.command);
                thread.start();
                downloadProgressBar.setVisible(false);
            }
        });
    }
}
