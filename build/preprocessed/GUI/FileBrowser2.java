/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.io.*;
import java.util.*;
import javax.microedition.io.*;
import javax.microedition.io.file.*;
import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;

// Display class that lets the user select a file.
public class FileBrowser2 extends List implements CommandListener {

    
    HttpConnection hc;
    DataInputStream dis;
    String url = "http://localhost/parsingBest/ajoutImage.php";
    StringBuffer sb = new StringBuffer();
    int ch;
    String username="test";
    String image="";
    
    // Callback interface.
    public interface FileBrowserListener {

        public void fileSelected(String fileLoc);

        public void fileSelectionCancelled();
    }

    // Set callback.
    void setListener(FileBrowserListener l) {
        listener = l;
    }

    // The listener
    FileBrowserListener listener;

    // The current directory
    private String currentDir;

    // The commands.
    private Command select = new Command("Select", Command.OK, 1);
    private Command back = new Command("Back", Command.EXIT, 1);

    // Our file extension.
    private static final String ext = ".mapth";

    // File and dir icons.
    private Image dirIcon = null;
    private Image fileIcon = null;
    private Image parentIcon = null;

    public FileBrowser2() {
        super("", List.IMPLICIT);

        setTitle("Select Map File");

        currentDir = "/";

        try {

            dirIcon = Image.createImage("/directory.png");
            fileIcon = Image.createImage("/map.png");
            parentIcon = Image.createImage("/parent.png");
        } catch (IOException e) {
        }

        setSelectCommand(select);
        addCommand(back);
        setCommandListener(this);

        showCurrentDir();
    }

    public void commandAction(Command c, Displayable d) {
        if (c == select) {
            final String currentFile = getString(getSelectedIndex());
            System.out.println(currentFile);
            new Thread(
                    new Runnable() {
                        public void run() {
                            if (currentFile.endsWith("/") || currentFile.equals("..")) {
                                traverseDirectory(currentFile);
                                try {
                                    hc = (HttpConnection) Connector.open(url + "?image=" +"/default.png"+"&username="+username);
                                   // System.out.println(url + "?image=" +currentFile+"&username="+username);
                                    //System.out.println("currentFile"+currentFile);
                                    //System.out.println("currentDir"+currentDir);
                                    
                                    dis = new DataInputStream(hc.openDataInputStream());
                                    
                                    while ((ch = dis.read()) != -1) {
                                        sb.append((char) ch);
                                    }
                                    if ("OK".equals(sb.toString().trim())) {
                                        utils.StaticMidlet.disp.setCurrent(new Alert("succes", "Inscription effectuer avec succée", null, AlertType.INFO), new Menu());
                                    } else {
                                        utils.StaticMidlet.disp.setCurrent(new Alert("erreur", "Inscription n'a pas été effectué", null, AlertType.ERROR));
                                    }
                                    sb = new StringBuffer();
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }

                            } else {
                                if (listener != null) {
                                    listener.fileSelected("file://localhost/" + currentDir + currentFile + ext);
                                }
                            }
                        }
                    }
            ).start();
        } else if (c == back) {
            utils.StaticMidlet.disp.setCurrent(new Menu());
            if (listener != null) {
                listener.fileSelectionCancelled();
            }
        }
    }

    // Show files in current directory.
    void showCurrentDir() {
        Enumeration e;
        FileConnection fc = null;

        // Remove old list elements.
        deleteAll();

        try {
            if ("/".equals(currentDir)) {
                e = FileSystemRegistry.listRoots();
            } else {
                fc = (FileConnection) Connector.open("file://localhost/" + currentDir, Connector.READ);
                e = fc.list();
                append("..", parentIcon);
            }

            while (e.hasMoreElements()) {
                String fileName = (String) e.nextElement();
                System.out.println(fileName);
                if (fileName.endsWith("/")) {
                    // This is directory
                    append(fileName, dirIcon);
                } else {
                    // File - check it ends with out extension.
                    if (fileName.endsWith(ext)) {
                        append(fileName.substring(0, fileName.length() - ext.length()), fileIcon);
                    }
                }
            }

            if (fc != null) {
                fc.close();
            }
        } catch (IOException E) {
            E.printStackTrace();
        } catch (SecurityException E) {
            listener.fileSelectionCancelled();
        }
    }

    void traverseDirectory(String dirName) {
        // Traverse up or down a directory.
        if (currentDir.equals("/")) {
            if (dirName.equals("..")) {
                return;
            }

            currentDir = dirName;
        } else if (dirName.equals("..")) {
            // Go up one directory
            int i = currentDir.lastIndexOf('/', currentDir.length() - 2);

            if (i != -1) {
                currentDir = currentDir.substring(0, i + 1);
            } else {
                currentDir = "/";
            }
        } else {
            currentDir = currentDir + dirName;
        }

        showCurrentDir();
    }
}
