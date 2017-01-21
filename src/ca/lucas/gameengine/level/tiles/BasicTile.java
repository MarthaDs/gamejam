package ca.lucas.gameengine.level.tiles;

import ca.lucas.gameengine.gfx.Screen;
import ca.lucas.gameengine.level.Level;

public class BasicTile extends Tile{
	
	protected int tileId;
	protected int tileColor;
	
	// Constructor
	public BasicTile(int id, int x, int y, int tileColor, int levelColor) {
		super(id, false, false, levelColor); // Setting the arguments for Tile class constructor
		this.tileId = x + y * 32;
		this.tileColor = tileColor;
	}
	
	public void tick(){
		
	}

	public void render(Screen screen, Level level, int x, int y) {
		screen.render(x, y, tileId, tileColor, 0x00, 1);
	}

}
