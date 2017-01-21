package ca.lucas.gameengine.entities;

import ca.lucas.gameengine.gfx.Screen;
import ca.lucas.gameengine.level.Level;

public abstract class Entity {

	public int x, y;
	protected Level level;
	
	// Constructor
	public Entity(Level level){
		init(level);
	}
	
	public final void init(Level level){
		this.level = level;
	}
	
	public abstract void tick();
	
	public abstract void render(Screen screen);
}
