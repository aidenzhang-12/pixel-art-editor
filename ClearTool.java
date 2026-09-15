package pixelArt;
import javax.swing.*;

public class ClearTool extends JButton implements Tool { // implements tool

    public ClearTool() {
    	super("Clear");
    }
    // get name
    public String getName() { return "Clear"; }
    
    public void onClick(PixelCanvas c, int r, int l) {}
    
    public void onDrag(PixelCanvas c, int r, int l) {}

    public void onRelease(PixelCanvas canvas, int row, int col) {}
}