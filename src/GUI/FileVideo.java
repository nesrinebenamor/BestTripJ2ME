package GUI;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.*;

import java.util.*;

import javax.microedition.io.*;
import javax.microedition.io.file.*;
import javax.microedition.lcdui.*;


/**
 * Demonstration MIDlet for File Connection API. This MIDlet implements simple
 * file browser for the filesystem available to the J2ME applications.
 *
 */
public class FileVideo implements CommandListener {
    
    Display disp ;
    int id=1;
    private static final String[] attrList = { "Read", "Write", "Hidden" };
    private static final String[] typeList = { "Regular File", "Directory" };
    private static final String[] monthList =
        { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };

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
    private Command view = new Command("View", Command.ITEM, 1);
    private Command creat = new Command("New", Command.ITEM, 2);

    //add delete file functionality
    private Command delete = new Command("Delete", Command.ITEM, 3);
    private Command creatOK = new Command("OK", Command.OK, 1);
    private Command prop = new Command("Properties", Command.ITEM, 2);
    private Command back = new Command("Back", Command.BACK, 2);
    private Command exit = new Command("Exit", Command.EXIT, 3);
    private TextField nameInput; // Input field for new file name
    private ChoiceGroup typeInput; // Input field for file type (regular/dir)
    private Image dirIcon;
    private Image fileIcon;
    private Image[] iconList;
    String login;
    String image;
    
    Form f2=new Form("Image inseree avec succes");
    Form f3=new Form("Image non inseree");
    
    HttpConnection hc;
    DataInputStream dis;
    StringBuffer sb = new StringBuffer();
    int ch;
    String description;
    String id_exp;
    
    public FileVideo(Display disp,String id_exp,String description) {
        this.disp=disp;
        this.id_exp=id_exp;
        this.description=description;
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

        iconList = new Image[] { fileIcon, dirIcon };
        startApp();
    }
    
    public FileVideo(String login)
    {
        this.login=login;
    }

 

    
    public void startApp() {
        try {
            showCurrDir();
        } catch (SecurityException e) {
            Alert alert =
                new Alert("Error", "You are not authorized to access the restricted API", null,
                    AlertType.ERROR);
            alert.setTimeout(Alert.FOREVER);

            Form form = new Form("Cannot access FileConnection");
            form.append(new StringItem(null,
                    "You cannot run this MIDlet with the current permissions. " +
                    "Sign the MIDlet suite, or run it in a different security domain"));
            form.addCommand(exit);
            form.setCommandListener(this);
            disp.setCurrent(alert, form);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean cond) {
        
    }

    public void commandAction(Command c, Displayable d) {
        if (c == view) {
            List curr = (List)d;
            final String currFile = curr.getString(curr.getSelectedIndex());
            new Thread(new Runnable() {
                    public void run() {
                        if (currFile.endsWith(SEP_STR) || currFile.equals(UP_DIRECTORY)) {
                            traverseDirectory(currFile);
                        } else {
                            // Show file contents
                            //showFile(currFile);
                            
                            inserer(currFile);
                        }
                    }
                }).start();
        } else if (c == prop) {
            List curr = (List)d;
            String currFile = curr.getString(curr.getSelectedIndex());

            showProperties(currFile);
        } else if (c == creat) {
            createFile();
        } else if (c == creatOK) {
            String newName = nameInput.getString();
            image=nameInput.getString().trim();

            if ((newName == null) || newName.equals("")) {
                Alert alert =
                    new Alert("Error!", "File Name is empty. Please provide file name", null,
                        AlertType.ERROR);
                alert.setTimeout(Alert.FOREVER);
                disp.setCurrent(alert);
            } else {
                // Create file in a separate thread and disable all commands
                // except for "exit"
                executeCreateFile(newName, typeInput.getSelectedIndex() != 0);
                disp.getCurrent().removeCommand(creatOK);
                disp.getCurrent().removeCommand(back);
            }
        } else if (c == back) {
            showCurrDir();
        } else if (c == exit) {
            destroyApp(false);
        } else if (c == delete) {
            List curr = (List)d;
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
            Alert cantDeleteFolder =
                new Alert("Error!",
                    "Can not delete The up-directory (..) " + "symbol! not a real folder", null,
                    AlertType.ERROR);
            cantDeleteFolder.setTimeout(Alert.FOREVER);
            disp.setCurrent(cantDeleteFolder);
        }
    }

    private void executeDelete(String currFile) {
        final String file = currFile;
        new Thread(new Runnable() {
                public void run() {
                    delete(file);
                }
            }).start();
    }

    private void checkDeleteFolder(String folderName) {
        try {
            FileConnection fcdir =
                (FileConnection)Connector.open("file://localhost/" + currDirName + folderName);
            Enumeration content = fcdir.list("*", true);

            //only empty directory can be deleted
            if (!content.hasMoreElements()) {
                fcdir.delete();
                showCurrDir();
            } else {
                Alert cantDeleteFolder =
                    new Alert("Error!", "Can not delete The non-empty folder: " + folderName, null,
                        AlertType.ERROR);
                cantDeleteFolder.setTimeout(Alert.FOREVER);
                disp.setCurrent(cantDeleteFolder);
            }
        } catch (IOException ioe) {
            System.out.println(currDirName + folderName);

            ioe.printStackTrace();
        }
    }

    //Starts creatFile with another Thread
    private void executeCreateFile(final String name, final boolean val) {
        new Thread(new Runnable() {
                public void run() {
                    createFile(name, val);
                }
            }).start();
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
                e = FileSystemRegistry.listRoots();
                browser = new List(currDirName, List.IMPLICIT);
            } else {
                currDir = (FileConnection)Connector.open("file://localhost/" + currDirName);
                e = currDir.list();
                browser = new List(currDirName, List.IMPLICIT);
                // not root - draw UP_DIRECTORY
                browser.append(UP_DIRECTORY, dirIcon);
            }

            while (e.hasMoreElements()) {
                String fileName = (String)e.nextElement();

                if (fileName.charAt(fileName.length() - 1) == SEP) {
                    // This is directory
                    browser.append(fileName, dirIcon);
                } else {
                    // this is regular file
                    browser.append(fileName, fileIcon);
                }
            }

            browser.setSelectCommand(view);

            //Do not allow creating files/directories beside root
            if (!MEGA_ROOT.equals(currDirName)) {
                browser.addCommand(prop);
                browser.addCommand(creat);
                browser.addCommand(delete);
            }

            browser.addCommand(exit);

            browser.setCommandListener(this);

            if (currDir != null) {
                currDir.close();
            }

            disp.setCurrent(browser);
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
            FileConnection fc =
                (FileConnection)Connector.open("file://localhost/" + currDirName + fileName);

            if (!fc.exists()) {
                throw new IOException("File does not exists");
            }

            InputStream fis = fc.openInputStream();
            byte[] b = new byte[1024];

            int length = fis.read(b, 0, 1024);

            fis.close();
            fc.close();

            TextBox viewer =
                new TextBox("View File: " + fileName, null, 1024,
                    TextField.ANY | TextField.UNEDITABLE);

            viewer.addCommand(back);
            viewer.addCommand(exit);
            viewer.setCommandListener(this);

            if (length > 0) {
                viewer.setString(new String(b, 0, length));
            }

            disp.setCurrent(viewer);
        } catch (Exception e) {
            Alert alert =
                new Alert("Error!",
                    "Can not access file " + fileName + " in directory " + currDirName +
                    "\nException: " + e.getMessage(), null, AlertType.ERROR);
            alert.setTimeout(Alert.FOREVER);
            disp.setCurrent(alert);
        }
    }

    void deleteFile(String fileName) {
        try {
            FileConnection fc = (FileConnection)Connector.open("file:///" + currDirName + fileName);
            fc.delete();
        } catch (Exception e) {
            Alert alert =
                new Alert("Error!",
                    "Can not access/delete file " + fileName + " in directory " + currDirName +
                    "\nException: " + e.getMessage(), null, AlertType.ERROR);
            alert.setTimeout(Alert.FOREVER);
            disp.setCurrent(alert);
        }
    }

    void showProperties(String fileName) {
        try {
            if (fileName.equals(UP_DIRECTORY)) {
                return;
            }

            FileConnection fc =
                (FileConnection)Connector.open("file://localhost/" + currDirName + fileName);

            if (!fc.exists()) {
                throw new IOException("File does not exists");
            }

            Form props = new Form("Properties: " + fileName);
            ChoiceGroup attrs = new ChoiceGroup("Attributes:", Choice.MULTIPLE, attrList, null);

            attrs.setSelectedFlags(new boolean[] { fc.canRead(), fc.canWrite(), fc.isHidden() });

            props.append(new StringItem("Location:", currDirName));
            props.append(new StringItem("Type: ", fc.isDirectory() ? "Directory" : "Regular File"));
            props.append(new StringItem("Modified:", myDate(fc.lastModified())));
            props.append(attrs);

            props.addCommand(back);
            props.addCommand(exit);
            props.setCommandListener(this);

            fc.close();

            disp.setCurrent(props);
        } catch (Exception e) {
            Alert alert =
                new Alert("Error!",
                    "Can not access file " + fileName + " in directory " + currDirName +
                    "\nException: " + e.getMessage(), null, AlertType.ERROR);
            alert.setTimeout(Alert.FOREVER);
            disp.setCurrent(alert);
        }
    }

    void createFile() {
        Form creator = new Form("New File");
        nameInput = new TextField("Enter Name", null, 256, TextField.ANY);
        typeInput = new ChoiceGroup("Enter File Type", Choice.EXCLUSIVE, typeList, iconList);
        creator.append(nameInput);
        creator.append(typeInput);
        creator.addCommand(creatOK);
        creator.addCommand(back);
        creator.addCommand(exit);
        creator.setCommandListener(this);
        disp.setCurrent(creator);
    }

    void createFile(String newName, boolean isDirectory) {
        try {
            FileConnection fc = (FileConnection)Connector.open("file:///" + currDirName + newName);

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
            disp.setCurrent(alert);
            // Restore the commands that were removed in commandAction()
            disp.getCurrent().addCommand(creatOK);
            disp.getCurrent().addCommand(back);
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
    
    public void inserer(String video) {
        try {
            hc = (HttpConnection) Connector.open("http://localhost/experience/ajoutVideo.php"
                    +"?video="+video
                    +"&description="+description.trim()
                    +"&id_exp="+id_exp.trim());
            dis = new DataInputStream(hc.openDataInputStream());
            while ((ch = dis.read()) != -1) {
                sb.append((char) ch);
            }
            if ("OK".equals(sb.toString().trim())) {
                
                //disp.setCurrent(f2);
           new Thread(
            new Runnable() {

                public void run() {
                  new AjoutMedia(disp, id_exp,id);
                }
            }
            ).start();
            } else {
                disp.setCurrent(f3);
            }
            sb = new StringBuffer();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
   
}
