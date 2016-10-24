package GUI;





import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;
import javax.microedition.midlet.MIDlet;

import com.jappit.midmaps.googlemaps.GoogleMaps;
import com.jappit.midmaps.googlemaps.GoogleMapsCoordinates;
import com.jappit.midmaps.googlemaps.GoogleMapsGeocoder;
import com.jappit.midmaps.googlemaps.GoogleMapsGeocoderHandler;
import com.jappit.midmaps.googlemaps.GoogleMapsMarker;
import com.jappit.midmaps.googlemaps.GoogleMapsPath;
import com.jappit.midmaps.googlemaps.GoogleStaticMap;
import com.jappit.midmaps.googlemaps.GoogleStaticMapHandler;

public class GoogleMapsZoomCanvas extends GoogleMapsTestCanvas implements GoogleStaticMapHandler
{
	GoogleMaps gMaps = null;
	GoogleStaticMap map = null;
	
	Command zoomInCommand, zoomOutCommand;
	
	public GoogleMapsZoomCanvas(MIDlet m, Displayable testListScreen, double Lat, double Lng, double Lat1, double Lng1)
	{
		super(m, testListScreen);
	
		addCommand(zoomInCommand = new Command("Zoom in", Command.OK, 1));
		addCommand(zoomOutCommand = new Command("Zoom out", Command.OK, 2));
		
		gMaps = new GoogleMaps();
		
		map = gMaps.createMap(getWidth(), getHeight(), GoogleStaticMap.FORMAT_PNG);
		
		map.setHandler(this);
    
		map.setCenter(new GoogleMapsCoordinates(Lat, Lng));

                GoogleMapsMarker redMarker = new GoogleMapsMarker(new GoogleMapsCoordinates(Lat, Lng));
		redMarker.setColor(GoogleStaticMap.COLOR_RED);
		redMarker.setSize(GoogleMapsMarker.SIZE_MID);
                
                GoogleMapsMarker redMarker2 = new GoogleMapsMarker(new GoogleMapsCoordinates(Lat1, Lng1));
		redMarker2.setColor(GoogleStaticMap.COLOR_BLUE);
		redMarker2.setSize(GoogleMapsMarker.SIZE_MID);

                /*GoogleMapsCoordinates g1 = new GoogleMapsCoordinates(36.898482, 10.189761999999973);
                GoogleMapsCoordinates g2 = new GoogleMapsCoordinates(37.266667, 9.866667000000007);
                GoogleMapsPath path = new GoogleMapsPath();
                path.addPoint(g1);
                path.addPoint(g2);
                path.setColor(GoogleStaticMap.COLOR_RED);
                path.setWeight(10);
                map.addPath(path);*/
                
         
                
                map.addMarker(redMarker);
                map.addMarker(redMarker2);
                
		map.setZoom(8);
		
		map.update();
	}
	
	protected void paint(Graphics g)
	{
		map.draw(g, 0, 0, Graphics.TOP | Graphics.LEFT);
	}
	public void GoogleStaticMapUpdateError(GoogleStaticMap map, int errorCode, String errorMessage)
	{
		showError("map error: " + errorCode + ", " + errorMessage);
	}
	public void GoogleStaticMapUpdated(GoogleStaticMap map)
	{
		repaint();
	}
	protected void keyPressed(int key)
	{
		int gameAction = getGameAction(key);
		
		if(gameAction == Canvas.UP || gameAction == Canvas.RIGHT || gameAction == Canvas.DOWN || gameAction == Canvas.LEFT)
		{
			map.move(gameAction);
		}
	}
	public void commandAction(Command c, Displayable d)
	{
		super.commandAction(c, d);
		
		if(c == zoomInCommand)
			map.zoomIn();
		else if(c == zoomOutCommand)
			map.zoomOut();
	}
}