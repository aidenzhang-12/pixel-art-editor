package pixelArt;
public interface Tool { // interface for all tools
	// contract of behaviours, name, click, drag, release
	// used for all tools
    String getName();
    void onClick(PixelCanvas canvas, int row, int col);
    void onDrag(PixelCanvas canvas, int row, int col);
    void onRelease(PixelCanvas canvas, int row, int col);
}
