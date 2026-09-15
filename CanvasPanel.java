package pixelArt;

import javax.swing.*;
import java.awt.*;
public class CanvasPanel extends JPanel {
	// create the private attributes
	private PixelCanvas pixelCanvas; 
	private Tool currentTool;
	
	// constructor
	public CanvasPanel (PixelCanvas pixelCanvas) {
		this.pixelCanvas = pixelCanvas;
		
	}
	// reset canvas for undo tool
	public void resetCanvas(PixelCanvas pixelCanvas) {
		this.pixelCanvas = pixelCanvas;
	}
	// set the current tool
	public void setCurrentTool(Tool tool) {
		this.currentTool = tool;
	}
	// graphics is a class that was imported
	// https://docs.oracle.com/en/java/javase/11/docs/api/java.desktop/java/awt/Graphics.html
	// abstract base class to draw onto components 
	public void paintComponent (Graphics g) {
		super.paintComponent(g);
		// debug
		System.out.println("paint component");
		 for (int i = 0; i < pixelCanvas.getHeightInCells(); i++) {
			 for (int j = 0; j < pixelCanvas.getWidthInCells(); j++) {
	            	
                g.setColor(pixelCanvas.getColorAt(i, j));
                
                // used the link: https://docs.oracle.com/en/java/javase/11/docs/api/java.desktop/java/awt/Graphics.html#fillRect(int,int,int,int)
                // for fillRect
                // fills the colour of each pixel, each rectngle is one cell in the pixel grid, fills with the selected color
                // swing class can only draw shapes so use rectangle to represent
                int size = pixelCanvas.getCellSize();
                // draw the graphics 
                g.fillRect(j * size, i * size, size, size);
	         }
		 }
		 // if its rectangle tool execute the code, draw the rectangle temporily when user is dragging
		 if (currentTool instanceof RectangleTool) {
			 // debug
			 System.out.println("Rectangle draw");
			 // set the rect variable
			 RectangleTool rect = (RectangleTool) currentTool;
			 // if user is dragging
			 if (rect.isDragging()) {
				 // set the color of the borders to the current color 
				 g.setColor(pixelCanvas.getCurrentColor());
				 // draw the rectangle
				 g.drawRect(rect.getStartCol(), rect.getStartRow(), rect.getCurrentWidth(), rect.getCurrentHeight());
			 }
		 }
	}
	
}
