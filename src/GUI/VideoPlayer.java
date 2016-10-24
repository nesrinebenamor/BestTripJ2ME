package GUI;

import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;
import javax.microedition.media.*;
import javax.microedition.media.control.*;


public class VideoPlayer implements CommandListener ,ItemStateListener ,PlayerListener  {

   Display disp;
    public String[] elements ={"1","2"};
    //public List list =new List("Select Audio File", List.IMPLICIT, elements,null);
    public List list;
    public StringItem si,time;
    private Gauge gauge = new Gauge("Volume: 50", true, 100, 50);
    private VolumeControl volume;
    private Form volume1 =new Form("Adjust volume");

    public Form form = new Form("VideoPlayer");
    private Command Play =new Command("Play",Command.SCREEN,1);
    private Command exitCommand = new Command("Exit", Command.EXIT, 1);
    private Command vol =new Command("Volume",Command.SCREEN,1);
    private Command back =new Command("back",Command.BACK,2);
    private Command pause =new Command("Pause",Command.SCREEN,1);
    private Command Resume =new Command("Resume",Command.SCREEN,1);

    private Player player;
   String id_exp;
   String[] vid;
   int id_ut;
    public VideoPlayer(Display disp, String id_exp, String[] vid,int id_ut) {
        this.disp = disp;
        this.id_exp = id_exp;
        this.vid = vid;
        this.id_ut=id_ut;
        list =new List("Select Audio File", List.IMPLICIT, vid,null);
        startApp();
    }

   
    public VideoPlayer()
    {
//    list.addCommand(Play);
//    list.addCommand(exitCommand);
//    list.setCommandListener(this);
//    form.addCommand(pause);
//    form.addCommand(Resume);
//    form.addCommand(vol);
//    form.addCommand(back);
//    form.setCommandListener(this);
//    volume1.append(gauge);
//    volume1.addCommand(back);
//    volume1.setItemStateListener(this);
//    volume1.setCommandListener(this);

    }
    public void startApp() {
        list.addCommand(Play);
    list.addCommand(exitCommand);
    list.setCommandListener(this);
    form.addCommand(pause);
    form.addCommand(Resume);
    form.addCommand(vol);
    form.addCommand(back);
    form.setCommandListener(this);
    volume1.append(gauge);
    volume1.addCommand(back);
    volume1.setItemStateListener(this);
    volume1.setCommandListener(this);
        disp.setCurrent(list);
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }

    public void commandAction(Command c, Displayable d) {
       if (c == exitCommand)
       {
       cleanUp();
//       return;
           new Thread(
            new Runnable() {
                public void run() {
                    
              new experienceParsing(disp, id_ut);
                }
            }
            ).start();
       }
       if (c==Play)
       {
           String s=list.getString(list.getSelectedIndex());
           s="/media/"+s;
           si=new StringItem("Playing",s);
           System.out.println(s);
           playMedia(s);
           form.append(si);
          }
       if(c==vol)
       {
           disp.setCurrent(volume1);
       }
       if((c==back)&&(d==form))
       {
           try
        {
            player.stop();
        }
        catch (Exception e)
        {
        }
           form.deleteAll();
           cleanUp();
           disp.setCurrent(list);
       }
       if((c==back)&&(d==volume1))
       {
           disp.setCurrent(form);
       }
           if(c==pause)
       {
        try
        {
            player.stop();
        }
        catch (Exception e)
        {
        }
       }
        if(c==Resume)
       {
         try {
         player.start();
         } catch (Exception e) {
         }
       }
    }
    public void cleanUp() {
    if (player != null) {
      player.close();
      player = null;
    }
  }

     public void playMedia(String locator)
     {
         try{
             player = Manager.createPlayer(getClass().getResourceAsStream(locator),"video/mpeg");
             player.realize();
             player.addPlayerListener(this);
             volume = (VolumeControl) player.getControl("VolumeControl");
             volume.setLevel(70);
             gauge.setValue(volume.getLevel());
             gauge.setLabel("Volume: " + volume.getLevel());
             player.setLoopCount(2);
             float t= player.getMediaTime();
             String c=String.valueOf(t);
             time=new StringItem("Time",c);
             form.append(time);
             player.start();
         }
         catch (Exception e) {
             e.printStackTrace();
         }
     }

    public void itemStateChanged(Item item) {
            volume.setLevel(gauge.getValue());
            gauge.setLabel("Volume: " + volume.getLevel());
    }

   public void playerUpdate(Player player, String event, Object eventData) {
    if(event.equals(PlayerListener.STARTED) ) {
      VideoControl vc = (VideoControl)player.getControl("VideoControl");
        Item videoDisp = (Item)vc.initDisplayMode(vc.USE_GUI_PRIMITIVE, null);
        form.append(videoDisp);


      disp.setCurrent(form);
    } else if(event.equals(PlayerListener.CLOSED)) {
      form.deleteAll();
    }
  }
}
