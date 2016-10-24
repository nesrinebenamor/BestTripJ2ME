package GUI;





import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.ImageItem;
import javax.microedition.lcdui.Item;
import javax.microedition.midlet.MIDlet;

import com.jappit.midmaps.googlemaps.GoogleMaps;
import com.jappit.midmaps.googlemaps.GoogleMapsCoordinates;
import com.jappit.midmaps.googlemaps.GoogleStaticMap;
import com.jappit.midmaps.googlemaps.GoogleStaticMapHandler;

public class GoogleMapsTestForm extends Form implements GoogleStaticMapHandler, CommandListener
{
	Command back;
	
	GoogleMaps gMaps = null;
	GoogleStaticMap map = null;
	
	Displayable testListScreen;
	MIDlet midlet;
	
	ImageItem mapItem;
	
	public GoogleMapsTestForm(MIDlet m, Displayable testListScreen)
	{
		super("J2Maps");
		
		this.midlet = m;
		this.testListScreen = testListScreen;
		
		addCommand(back = new Command("Back", Command.BACK, 1));
		
		setCommandListener(this);
		
		mapItem = new ImageItem("Loading map...", null, Item.LAYOUT_TOP, "Sample map");
		
		append(mapItem);
		
		gMaps = new GoogleMaps();
		
		map = gMaps.createMap(getWidth(), getHeight(), GoogleStaticMap.FORMAT_PNG);
		
		map.setHandler(this);
		
		map.setCenter(new GoogleMapsCoordinates(36.899313, 10.188750));
		
		map.setZoom(15);
		
		map.update();
	}
	public void GoogleStaticMapUpdateError(GoogleStaticMap map, int errorCode, String errorMessage)
	{
		System.out.println("map error: " + errorCode + ", " + errorMessage);
	}
	public void GoogleStaticMapUpdated(GoogleStaticMap map)
	{
		System.out.println("map ok");
		
		mapItem.setImage(map.getImage());
		
		mapItem.setLabel("Map loaded!");
	}
	public void commandAction(Command c, Displayable d)
	{
		if(c == back)
		{
			Display.getDisplay(midlet).setCurrent(testListScreen);
		}
	}
}
