package pixelArt;
import java.util.*;

public class UndoManager  {
	private ArrayList<PixelCanvas> storedState; // array list of pixel canvas
	// constructor
	public UndoManager() {
		storedState = new ArrayList<PixelCanvas>();
	}
	// returns the most recent saved canvas
	public PixelCanvas undo() {
		if (storedState.size() > 0) { // make sure the array list has a size above 0
			PixelCanvas temp = storedState.get(storedState.size() - 1); // assigns most recent canvas to temp
			storedState.remove(storedState.size() - 1); // remove
			return temp; // returns most recent saved
		}
		return null; // return null if no saved canvas
		
	}
	//pushes a saved snapshot onto the undo array stack
	public void pushState(PixelCanvas newCanvas) {
		if (newCanvas != null) { // only execute if new canvas is not null
			if (storedState.size() > 0) { // execute if array list above 0
				PixelCanvas lastCanvas = storedState.get(storedState.size()-1); // gets the last saved canvas
				
				// if its not equals means theres a change
				if (lastCanvas != newCanvas) {
					storedState.add(newCanvas); // add new canvas to stored state
				}
			
			} else {
				// no last canvas in the state
				storedState.add(newCanvas);
			}
		} 
		
		
	}
	
	
	
	
}
