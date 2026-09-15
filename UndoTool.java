package pixelArt;
import java.awt.*;
import javax.swing.*;
public class UndoTool extends JButton implements Tool {
	// implements tool feature
    private ColorPalette palette;
    public UndoTool(ColorPalette palette) { // constructor
    	super("Undo");
    	this.palette = palette; 
    }
    // get name for undo
    public String getName() { return "Undo"; }
    // implements the set of methods from tool interface
    public void onClick(PixelCanvas c, int r, int l) {}
    public void onDrag(PixelCanvas c, int r, int l) {}
    public void onRelease(PixelCanvas canvas, int row, int col) {}
}
