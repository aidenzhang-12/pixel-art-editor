package pixelArt;
import java.awt.*;
import javax.swing.*;
public class FillTool extends JButton implements Tool {
	// attribute
    private ColorPalette palette;
    public FillTool(ColorPalette palette) { // constructor
    	super("Fill"); 
    	this.palette = palette; 
    }
    // get name method that returns the name
    public String getName() { return "Fill"; }
    // onclick, the fill tool, calls the flood fill method
    public void onClick(PixelCanvas c, int r, int l) {
    	c.floodFill(r, l, c.getColorAt(r, l), c.getCurrentColor()); // calls the recursive method flood fill to fill
    }
    // implements tool, so has these but no implementation
    public void onRelease(PixelCanvas canvas, int row, int col) {}
    public void onDrag(PixelCanvas c, int r, int l) {}
}
