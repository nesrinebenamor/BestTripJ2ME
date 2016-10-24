/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.io.file.FileConnection;
import javax.microedition.io.file.FileSystemRegistry;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Choice;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.List;
import javax.microedition.lcdui.StringItem;
import javax.microedition.lcdui.TextBox;
import javax.microedition.lcdui.TextField;

/**
 *
 * @author esprit
 */
public class FileBrowser extends Form implements CommandListener, Runnable {

    private static final String[] attrList = {"Read", "Write", "Hidden"};
    private static final String[] typeList = {"Regular File", "Directory"};
    private static final String[] monthList = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    Displayable d;
    /* special string denotes upper directory */
    private static final String UP_DIRECTORY = "..";

    /* special string that denotes upper directory accessible by this browser.
     * this virtual directory contains all roots.
     */
    private static final String MEGA_ROOT = "/";

    /* separator string as defined by FC specification */
    private static final String SEP_STR = "/";

    /* separator character as defined by FC specification */
    private static final char SEP = '/';
    private String currDirName;
    private Command view;
    private Command creat;

    //add delete file functionality
    private Command delete;
    private Command creatOK;
    private Command prop;
    private Command back;
    private Command exit;
    private TextField nameInput; // Input field for new file name
    private ChoiceGroup typeInput; // Input field for file type (regular/dir)
    private Image dirIcon;
    private Image fileIcon;
    private Image[] iconList;
    //private TextBox viewer;
    private TextField viewer2;
    String login;
    String image;

    //Form f2;
    //Form f3;
    HttpConnection hc;
    DataInputStream dis;
    StringBuffer sb = new StringBuffer();
    int ch;

    public FileBrowser() {
        super("Choisir Image");
        view = new Command("View", Command.ITEM, 1);
        creat = new Command("New", Command.ITEM, 2);

        //add delete file functionality
        delete = new Command("Delete", Command.ITEM, 3);
        creatOK = new Command("OK", Command.OK, 1);
        prop = new Command("Properties", Command.ITEM, 2);
        back = new Command("Back", Command.BACK, 2);
        exit = new Command("Exit", Command.EXIT, 3);
   

        currDirName = MEGA_ROOT;

        try {
            dirIcon = Image.createImage("/dir.png");
        } catch (IOException e) {
            dirIcon = null;
        }

        try {
            fileIcon = Image.createImage("/file.png");
        } catch (IOException e) {
            fileIcon = null;
        }

        iconList = new Image[]{fileIcon, dirIcon};

        try {
            showCurrDir();
        } catch (SecurityException e) {
//            Alert alert =
//                new Alert("Error", "You are not authorized to access the restricted API", null,
//                    AlertType.ERROR);
//            alert.setTimeout(Alert.FOREVER);
            utils.StaticMidlet.disp.setCurrent(new Alert("Error", "You are not authorized to access the restricted API", null,
                    AlertType.ERROR));
            //  Form form = new Form("Cannot access FileConnection");
            append(new StringItem("Cannot access FileConnection",
                    "You cannot run this MIDlet with the current permissions. "
                    + "Sign the MIDlet suite, or run it in a different security domain"));
            addCommand(exit);
            setCommandListener(this);
            utils.StaticMidlet.disp.setCurrent(new Alert("Error", "You are not authorized to access the restricted API", null,
                    AlertType.ERROR));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void commandAction(Command c, Displayable d) {
        if (c == view) {
            Thread thread = new Thread();
            thread.start();

        } else if (c == prop) {
            List curr = (List) d;
            String currFile = curr.getString(curr.getSelectedIndex());
            showProperties(currFile);
            System.out.println("currFilepffff"+currFile);
        } else if (c == creat) {
            createFile();
        } else if (c == creatOK) {
            String newName = nameInput.getString();
            image = nameInput.getString().trim();
            if ((newName == null) || newName.equals("")) {
//                Alert alert =
//                    new Alert("Error!", "File Name is empty. Please provide file name", null,
//                        AlertType.ERROR);
//                alert.setTimeout(Alert.FOREVER);
                utils.StaticMidlet.disp.setCurrent(new Alert("Error!", "File Name is empty. Please provide file name", null,
                        AlertType.ERROR));
            } else {
                // Create file in a separate thread and disable all commands
                // except for "exit"
                executeCreateFile(newName, typeInput.getSelectedIndex() != 0);
                //utils.StaticMidlet.disp.getCurrent().removeCommand(creatOK);
                //utils.StaticMidlet.disp.getCurrent().removeCommand(back);
                removeCommand(creatOK);
                removeCommand(back);
            }
        } else if (c == back) {
            showCurrDir();
        } else if (c == exit) {
            this.deleteAll();
        } else if (c == delete) {
            List curr = (List) d;
            String currFile = curr.getString(curr.getSelectedIndex());
            executeDelete(currFile);
        }
    }

    void delete(String currFile) {
        if (!currFile.equals(UP_DIRECTORY)) {
            if (currFile.endsWith(SEP_STR)) {
                checkDeleteFolder(currFile);
            } else {
                deleteFile(currFile);
                showCurrDir();
            }
        } else {
//            Alert cantDeleteFolder =
//                new Alert("Error!",
//                    "Can not delete The up-directory (..) " + "symbol! not a real folder", null,
//                    AlertType.ERROR);
            // cantDeleteFolder.setTimeout(Alert.FOREVER);
            utils.StaticMidlet.disp.setCurrent(new Alert("Error!",
                    "Can not delete The up-directory (..) " + "symbol! not a real folder", null,
                    AlertType.ERROR));
        }
    }

    private void executeDelete(String currFile) {
        Thread thread = new Thread();
        thread.start();
    }

    private void checkDeleteFolder(String folderName) {
        try {
            FileConnection fcdir
                    = (FileConnection) Connector.open("file://localhost/" + currDirName + folderName);
            Enumeration content = fcdir.list("*", true);

            //only empty directory can be deleted
            if (!content.hasMoreElements()) {
                fcdir.delete();
                showCurrDir();
            } else {
//                Alert cantDeleteFolder =
//                    new Alert("Error!", "Can not delete The non-empty folder: " + folderName, null,
//                        AlertType.ERROR);
                // cantDeleteFolder.setTimeout(Alert.FOREVER);
                utils.StaticMidlet.disp.setCurrent(new Alert("Error!", "Can not delete The non-empty folder: " + folderName, null,
                        AlertType.ERROR));
            }
        } catch (IOException ioe) {
            System.out.println(currDirName + folderName);

            ioe.printStackTrace();
        }
    }

    //Starts creatFile with another Thread
    private void executeCreateFile(final String name, final boolean val) {

        Thread thread = new Thread();
        thread.start();
    }

    /**
     * Show file list in the current directory .
     */
    void showCurrDir() {
        Enumeration e;
        FileConnection currDir = null;
        List browser;

        try {
            if (MEGA_ROOT.equals(currDirName)) {
                System.out.println("1");
                e = FileSystemRegistry.listRoots();
                System.out.println("2");
                browser = new List(currDirName, List.IMPLICIT);
                System.out.println("3");
            } else {
                currDir = (FileConnection) Connector.open("file://localhost/" + currDirName);
                System.out.println("4");
                e = currDir.list();
                System.out.println("5");
                browser = new List(currDirName, List.IMPLICIT);
                System.out.println("6");
                // not root - draw UP_DIRECTORY
                //browser.append(UP_DIRECTORY, dirIcon);
                append(UP_DIRECTORY);
                append(dirIcon);
            }

            while (e.hasMoreElements()) {
                System.out.println("*+");
                String fileName = (String) e.nextElement();

                if (fileName.charAt(fileName.length() - 1) == SEP) {
                    System.out.println("1 *+");
                    // This is directory
                    //browser.append(fileName, dirIcon);
                    System.out.println("Title: "+ fileName);
                    append(fileName);
                    System.out.println("icon");
                    //append(dirIcon);
                        System.out.println("2 *+");
                } else {
                    // this is regular file
                    //browser.append(fileName, fileIcon);
                    append(fileName);
                    append(fileIcon);
                }
            }

           // browser.setSelectCommand(view);
            //Do not allow creating files/directories beside root
            if (!MEGA_ROOT.equals(currDirName)) {
             //   browser.addCommand(prop);
                //  browser.addCommand(creat);
                System.out.println("pff");
                addCommand(delete);
                addCommand(prop);
                addCommand(creat);
                addCommand(delete);
            }
            
             else {
             //   browser.addCommand(prop);
                //  browser.addCommand(creat);
                System.out.println("pff");
//                addCommand(delete);
//                addCommand(prop);
//                addCommand(creat);
//                addCommand(delete);
            }

            //browser.addCommand(exit);
            addCommand(exit);

            //browser.setCommandListener(this);
            this.setCommandListener(this);

            if (currDir != null) {
                currDir.close();
            }

            // utils.StaticMidlet.disp.setCurrent(this);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    void traverseDirectory(String fileName) {
        /* In case of directory just change the current directory
         * and show it
         */
        if (currDirName.equals(MEGA_ROOT)) {
            if (fileName.equals(UP_DIRECTORY)) {
                // can not go up from MEGA_ROOT
                return;
            }

            currDirName = fileName;
        } else if (fileName.equals(UP_DIRECTORY)) {
            // Go up one directory
            int i = currDirName.lastIndexOf(SEP, currDirName.length() - 2);

            if (i != -1) {
                currDirName = currDirName.substring(0, i + 1);
            } else {
                currDirName = MEGA_ROOT;
            }
        } else {
            currDirName = currDirName + fileName;
        }

        showCurrDir();
    }

    void showFile(String fileName) {
        try {
            FileConnection fc
                    = (FileConnection) Connector.open("file://localhost/" + currDirName + fileName);

            if (!fc.exists()) {
                throw new IOException("File does not exists");
            }

            InputStream fis = fc.openInputStream();
            byte[] b = new byte[1024];

            int length = fis.read(b, 0, 1024);

            fis.close();
            fc.close();

//           viewer =new TextBox("View File: " + fileName, null, 1024,TextField.ANY | TextField.UNEDITABLE);
            viewer2 = new TextField("View File: " + fileName, null, 1024, TextField.ANY | TextField.UNEDITABLE);

            //viewer.addCommand(back);
            //viewer.addCommand(exit);
            //viewer.setCommandListener(this);
            append(viewer2);
            addCommand(back);
            addCommand(exit);

            this.setCommandListener(this);

            if (length > 0) {
                viewer2.setString(new String(b, 0, length));
            }

            utils.StaticMidlet.disp.setCurrent(this);
        } catch (Exception e) {
            Alert alert
                    = new Alert("Error!",
                            "Can not access file " + fileName + " in directory " + currDirName
                            + "\nException: " + e.getMessage(), null, AlertType.ERROR);
            alert.setTimeout(Alert.FOREVER);
            utils.StaticMidlet.disp.setCurrent(alert);
        }
    }

    void deleteFile(String fileName) {
        try {
            FileConnection fc = (FileConnection) Connector.open("file:///" + currDirName + fileName);
            fc.delete();
        } catch (Exception e) {
            Alert alert
                    = new Alert("Error!",
                            "Can not access/delete file " + fileName + " in directory " + currDirName
                            + "\nException: " + e.getMessage(), null, AlertType.ERROR);
            alert.setTimeout(Alert.FOREVER);
            utils.StaticMidlet.disp.setCurrent(alert);
        }
    }

    void showProperties(String fileName) {
        try {
            if (fileName.equals(UP_DIRECTORY)) {
                return;
            }

            FileConnection fc
                    = (FileConnection) Connector.open("file://localhost/" + currDirName + fileName);

            if (!fc.exists()) {
                throw new IOException("File does not exists");
            }

            //Form props = new Form("Properties: " + fileName);
            ChoiceGroup attrs = new ChoiceGroup("Attributes:", Choice.MULTIPLE, attrList, null);

            attrs.setSelectedFlags(new boolean[]{fc.canRead(), fc.canWrite(), fc.isHidden()});

            //props.append(new StringItem("Location:", currDirName));
            // props.append(new StringItem("Type: ", fc.isDirectory() ? "Directory" : "Regular File"));
            // props.append(new StringItem("Modified:", myDate(fc.lastModified())));
            //props.append(attrs);
            addCommand(back);
            addCommand(exit);
            setCommandListener(this);

            append(new StringItem("Location:", currDirName));
            append(new StringItem("Type: ", fc.isDirectory() ? "Directory" : "Regular File"));
            append(new StringItem("Modified:", myDate(fc.lastModified())));
            append(attrs);

            addCommand(back);
            addCommand(exit);
            this.setCommandListener(this);

            fc.close();

            utils.StaticMidlet.disp.setCurrent(this);
        } catch (Exception e) {
            Alert alert
                    = new Alert("Error!",
                            "Can not access file " + fileName + " in directory " + currDirName
                            + "\nException: " + e.getMessage(), null, AlertType.ERROR);
            alert.setTimeout(Alert.FOREVER);
            utils.StaticMidlet.disp.setCurrent(alert);
        }
    }

    void createFile() {
        //Form creator = new Form("New File");
        nameInput = new TextField("Enter Name", null, 256, TextField.ANY);
        typeInput = new ChoiceGroup("Enter File Type", Choice.EXCLUSIVE, typeList, iconList);
        append(nameInput);
        append(typeInput);
        addCommand(creatOK);
        addCommand(back);
        addCommand(exit);
        this.setCommandListener(this);

    }

    void createFile(String newName, boolean isDirectory) {
        try {
            FileConnection fc = (FileConnection) Connector.open("file:///" + currDirName + newName);

            if (isDirectory) {
                fc.mkdir();
            } else {
                fc.create();
            }

            showCurrDir();
        } catch (Exception e) {
            String s = "Can not create file '" + newName + "'";

            if ((e.getMessage() != null) && (e.getMessage().length() > 0)) {
                s += ("\n" + e);
            }

            Alert alert = new Alert("Error!", s, null, AlertType.ERROR);
            alert.setTimeout(Alert.FOREVER);
            utils.StaticMidlet.disp.setCurrent(alert);
            // Restore the commands that were removed in commandAction()
            addCommand(creatOK);
            addCommand(back);
            this.setCommandListener(this);
        }
    }

    private String myDate(long time) {
        Calendar cal = Calendar.getInstance();

        cal.setTime(new Date(time));

        StringBuffer sb = new StringBuffer();

        sb.append(cal.get(Calendar.HOUR_OF_DAY));
        sb.append(':');
        sb.append(cal.get(Calendar.MINUTE));
        sb.append(':');
        sb.append(cal.get(Calendar.SECOND));
        sb.append(',');
        sb.append(' ');
        sb.append(cal.get(Calendar.DAY_OF_MONTH));
        sb.append(' ');
        sb.append(monthList[cal.get(Calendar.MONTH)]);
        sb.append(' ');
        sb.append(cal.get(Calendar.YEAR));

        return sb.toString();
    }

    public void inserer(String image) {
        try {
            hc = (HttpConnection) Connector.open("http://localhost/parsingBest/ajoutImage.php" + "?username=" + login + "&image=" + "/default.png");
            dis = new DataInputStream(hc.openDataInputStream());
            while ((ch = dis.read()) != -1) {
                sb.append((char) ch);
            }
            if ("OK".equals(sb.toString().trim())) {
                utils.StaticMidlet.disp.setCurrent(new Alert("succes", "Image inseree avec succée", null, AlertType.INFO));
            } else {
                utils.StaticMidlet.disp.setCurrent(new Alert("erreur", "Insertion n'a pas été effectué", null, AlertType.ERROR));
            }
            sb = new StringBuffer();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void run() {
        List curr = (List) d;
        final String currFile = curr.getString(curr.getSelectedIndex());
        if (currFile.endsWith(SEP_STR) || currFile.equals(UP_DIRECTORY)) {
            traverseDirectory(currFile);
        } else {
            // Show file contents
            showFile(currFile);
        }
        final String name = "";
        final boolean val = false;
        createFile(name, val);
        final String file = currFile;
        delete(file);

    }
}
