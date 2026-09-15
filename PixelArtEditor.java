package pixelArt;
/*
 * Name: Aiden
 * Date: October 25, 2025
 * Description: create a pixel art gui window where user can use different tools to draw things on the canvas
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

// Link for Mouse listener: //https://docs.oracle.com/javase/8/docs/api/java/awt/event/MouseListener.html
// references this information, does an action based on the click of user's mouse
// https://docs.oracle.com/en/java/javase/11/docs/api/java.desktop/java/awt/event/MouseMotionListener.html
// for mouse motion listener, drag

public class PixelArtEditor extends JFrame implements ActionListener, MouseListener, MouseMotionListener {
	// declare attributes, create private variables
    private PixelCanvas canvas;
    private ColorPalette palette;
    private ArrayList<Tool> tools;
    private Tool currentTool;
    private UndoManager undoManager;
    
    private CanvasPanel canvasPanel;

    // constructor
    public PixelArtEditor() {
    	undoManager = new UndoManager(); // instantiate undo manager class
    	// set title and set size
    	setTitle("Pixel Art");
    	setSize(600, 600);
    	
    	// set to a border layout
    	setLayout(new BorderLayout());
    	
    	// menu option dropdown for save and load
    	//https://docs.oracle.com/en/java/javase/11/docs/api/java.desktop/java/awt/MenuBar.html
    	JMenuBar menuBar = new JMenuBar();
    	// create new file menu drop down that has save and load
    	JMenu fileMenu = new JMenu ("File");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem loadItem = new JMenuItem("Load");
        
        // create new clear button
        ClearTool clearTool = new ClearTool();
        
        // add the different save and load buttons to the file menu drop down
        fileMenu.add(saveItem);
        fileMenu.add(loadItem);
        menuBar.add(fileMenu);
        
        // add clear button
        menuBar.add(clearTool);
        
        setJMenuBar(menuBar);
        
        // uses file dialog for save and load (used in the getFile method)
        // https://docs.oracle.com/en/java/javase/11/docs/api/java.desktop/java/awt/FileDialog.html
        saveItem.addActionListener(this);
        
        loadItem.addActionListener(this);
        
        // add action listener to the clear button
        clearTool.addActionListener(this);
    	
    	// pixel canvas includes colors, actions
    	canvas = new PixelCanvas (100, 100, Color.WHITE);
    	// passes canvas into canvas panel, which has graphics components
    	canvasPanel = new CanvasPanel(canvas);
    	// create a new color palette
        palette = new ColorPalette();
        
        // new border layout for the top panel
        JPanel topPanel = new JPanel(new BorderLayout());
        
        // tools panel
        JPanel controlPanel = new JPanel();
        // create a new control panel from top to bottom
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        
        // new panel for the button panel
        JPanel buttonPanel = new JPanel();
        
        // set grid 2 x 3, for 5 tools
         buttonPanel.setLayout(new GridLayout(2, 3));
         // new pencil tool created
         PencilTool pencil = new PencilTool(palette);
         // new eraser tool created    
         EraserTool eraser = new EraserTool(Color.WHITE);
         // new fill tool created
         FillTool fill = new FillTool(palette);
         // new undo tool created
         UndoTool undo = new UndoTool(palette);
         // new rectangle tool created
         RectangleTool rectangle = new RectangleTool(palette);
         
         
         // add all the tools into the button panel
         buttonPanel.add(pencil);
         buttonPanel.add(eraser);
         buttonPanel.add(fill);
         buttonPanel.add(undo);
         buttonPanel.add(rectangle);
         
         
         // add all the tools into an array list of tools which was already created 
         tools = new ArrayList<Tool>();
         tools.add(pencil);
         tools.add(eraser);
         tools.add(fill);
         tools.add(undo);
         tools.add(rectangle);
       
         // create a new label for tools 
         JLabel toolsLabel = new JLabel("Tools");
         // set it to be aligned in the center
         toolsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
         // add tools label then button panels, tools on top
         controlPanel.add(toolsLabel);
         
         controlPanel.add(buttonPanel);
         
         // in the top panel add the control panel on the west side
         topPanel.add(controlPanel, BorderLayout.WEST);
         /// add the color palette on the east side
         topPanel.add(palette, BorderLayout.EAST);
         // add the top panel north and add the canvas panel in the cetner below the top panel
         add(topPanel, BorderLayout.NORTH);
         
         add(canvasPanel, BorderLayout.CENTER);
        

         // terminate program when window is closed
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        // set the visibility to be true 
        setVisible(true);
      
        // add all action listeners and also mouse listener and mouse click, for buttons and canvas
        pencil.addActionListener(this);
        eraser.addActionListener(this);
        fill.addActionListener(this);
        undo.addActionListener(this);
        rectangle.addActionListener(this);
        // add mouse listener in the canvas panel 
        canvasPanel.addMouseListener(this);
        canvasPanel.addMouseMotionListener(this);
    }
    
    
    // main method
    public static void main(String[] args) {
        PixelArtEditor editor = new PixelArtEditor(); // instantiates this class
    }
    
	@Override
	public void mouseDragged(MouseEvent e) {
		// for debugging, showing you can see what events occur
		System.out.println("mouse dragged");
				
		// get the current mouse position coordinates, divide by cell size, find pixel index
		int col = e.getX() / canvas.getCellSize();
		int row = e.getY() / canvas.getCellSize();
		// execute block of code if the row and col are between 0 and the canvas height and width
		if (row >= 0 && row < canvas.getHeightInCells() && col >= 0 && col < canvas.getWidthInCells() && currentTool != null) {
		
			canvas.setCurrentColor(palette.getSelectedColor());
			
			// debug
			System.out.println("in canvas");			  
			currentTool.onDrag(canvas, row, col);
			// triggers the paintComponent method in canvasPanel
			// https://stackoverflow.com/questions/10768619/paint-and-repaint-in-java
			// reference to this, it draws everything
			repaint();
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// for debugging, showing you can see what events occur
		System.out.println("mouse pressed");
		// get the current mouse position coordinates, divide by cell size, find pixel index
		int col = e.getX() / canvas.getCellSize();
		int row = e.getY() / canvas.getCellSize();
		// execute block of code if the row and col are between 0 and the canvas height and width
		  if (row >= 0 && row < canvas.getHeightInCells() && col >= 0 && col < canvas.getWidthInCells() && currentTool != null) {
			  canvas.setCurrentColor(palette.getSelectedColor());
			  // debug
			  System.out.println("in canvas");
			  // save old state of canvas for undo tool
			  if (currentTool != null) {
				  	// get the name of the current tool
					String name = currentTool.getName();
					// if user is using a tool, get the pushstate, get a copy 
					if (name.equals("Pencil") || name.equals("Eraser") || name.equals("Fill")) {
						undoManager.pushState(canvas.getCopy());
					} 
					
			  }
			  currentTool.onClick(canvas, row, col);
			  // triggers the paintComponent method in canvasPanel
			  // https://stackoverflow.com/questions/10768619/paint-and-repaint-in-java
			  // reference to this, it draws everything
			  repaint();
	      }
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// debugging, show what events occur
		System.out.println("mouse released");
		
		// get where user released mouse, divide by cell size, find pixel index
		int col = e.getX() / canvas.getCellSize();
		int row = e.getY() / canvas.getCellSize();
		// execute block of code if the row and col are between 0 and the canvas height and width
		if (row >= 0 && row < canvas.getHeightInCells() && col >= 0 && col < canvas.getWidthInCells() && currentTool != null) {
			
			// set current color to selected
			canvas.setCurrentColor(palette.getSelectedColor());
			// debug
			System.out.println("in canvas");
			
			if (currentTool != null) { // make sure user selected a tool
				String name = currentTool.getName(); // get tool name
				
				if (name.equals("Rectangle")) { // method only is used for rectangle tool 
					undoManager.pushState(canvas.getCopy()); // save current state of the canvas before drawing rectangle
				}
			}
			
			// pass in that the mouse was released here, at row and col
			currentTool.onRelease(canvas, row, col);
			
			// redraw everything so user sees the rectangle on the screen
			repaint();
			
		} else if (currentTool != null && currentTool.getName().equals("Rectangle")) { //  makes sure the rectangle is in bounds of canvas
			if (row < 0) { // cannot be outside canvas
				row = 0;
			}
			if (row >= canvas.getHeightInCells()) { // if its over height subtract so its inside canvas
				row = canvas.getHeightInCells() - 1;
			}
			// same for col
			if (col >= canvas.getWidthInCells()) {
				col = canvas.getWidthInCells() - 1;
			}
			if (col < 0) {
				col = 0;
			}
			canvas.setCurrentColor(palette.getSelectedColor());
			undoManager.pushState(canvas.getCopy()); // save current state of the canvas before drawing rectangle
			currentTool.onRelease(canvas, row, col);
			repaint();
		}
		
	}

	private File getFile(String action) {
		// create a new file chooser can choose a new file, has a pop up component
		JFileChooser dialog = new JFileChooser();
		int returnValue;
		// load or save check if its load or save
		if (action.equals("Load")) {
			// set the return value, it is saved as approved or canceled, if user loads a file or saves a file
			returnValue = dialog.showOpenDialog(this);
		}
		else {
			returnValue = dialog.showSaveDialog(this);
		}
		// https://docs.oracle.com/en/java/javase/11/docs/api/java.desktop/javax/swing/JFileChooser.html#APPROVE_OPTION
		// used for APPROVE_OPTION
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			// get selected file and return it 
			File file = dialog.getSelectedFile();
			return file;
		}
		return null;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		// also used instanceof in color palette, references shwown there
		// checks for whether a tool is clicked on 
		Object obj = e.getSource();

		// if the obj picked is a tool, set the current tool
        if (obj instanceof Tool) {
        	// set the current tool equal to an object
            currentTool = (Tool) obj;
            // set the current tool of the canvas to that
            canvasPanel.setCurrentTool(currentTool);
            // for debugging
            System.out.println("picked: " + currentTool.getName());
            
            
            // change cursor if its eraser so user can see
            // used link below (referenced) 
            // https://blog.idrsolutions.com/tutorial-change-default-cursor-javafx/
            if (currentTool.getName().equals("Eraser")) {
            	// change cursor if its an eraser
            	canvasPanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
            } else {
            	// default cursor for any other tool
            	canvasPanel.setCursor(Cursor.getDefaultCursor());
            }
	        if (currentTool.getName().equals("Undo")) {
	        	// if the tool selected is undo, create a last canvas that gets the last state of the canvas
	        	PixelCanvas lastCanvas = undoManager.undo();
	        	// make sure the last canvas isnt anull
	        	if (lastCanvas != null) {
	        		// make the canvas equal to last canvas and reset
	        		canvas = lastCanvas;
	        		// reset the canvas to previous canvas
	        		canvasPanel.resetCanvas(lastCanvas);
	        		// repaint
	        		repaint();
	        	}
	        } else if (currentTool.getName().equals("Clear")) {
	        	undoManager.pushState(canvas.getCopy()); // push current state of canvas
	        	canvas.clear();
	        	repaint(); // clear then repaint 
	        }

        } else if (obj instanceof JMenuItem) {
        	// set the item of the menu to object
        	JMenuItem item = (JMenuItem) obj;
        	// debug
        	System.out.println(item.getText());
        	
        	// catch the exception for file io
        	try {
        		// if its save, get the file and save to the file based on its path
	        	if (item.getText().equals("Save")) {
	        		File file = getFile("Save");
	        		if (file != null) { // make sure file is not null
	        			canvas.saveToFile(file.getPath()); // saves if file is not null
	        		}
				// if its load get the file and load from the file path
	        	} else if (item.getText().equals("Load")) {
	        		File file = getFile("Load");
	        		if (file != null) { // loads if the file is not null
	        			canvas.loadFromFile(file.getPath());
	        		}
	        	}
	        	// repaint the canvas based on the file
	        	repaint();
        	} catch (Exception exception) {
        	
        	}
		} 
	}

// below is not implemented 
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
}
