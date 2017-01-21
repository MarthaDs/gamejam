package ca.lucas.gameengine.entities;

import ca.lucas.gameengine.gfx.Screen;
import ca.lucas.gameengine.level.Level;
import ca.lucas.gameengine.level.tiles.Tile;

// Generic class for characters that move on the game
public abstract class Mob extends Entity{

	protected String name;
	protected int speed;
	protected int numSteps = 0;
	protected int movingDir = 1; // The direction of the character. For 'y' 0 is up, 1 is down
								// for 'x' 2 is left, 3 is right
	protected boolean isMoving;
	protected int scale = 1;
	
	// Constructor
	public Mob(Level level, String name, int x, int y, int speed){
		super(level);
		this.name = name;
		this.x = x;
		this.y = y;
		this.speed = speed;
	}
	
	// 'xa' and 'ya' just verify which direction the character is moving
	public void move(int xa, int ya){
		if(xa != 0 && ya != 0){
			move(xa, 0);
			move(0, ya);
			numSteps--;
			return;
		}
		
		numSteps++;
		
		// if the character not collided
		if(!hasCollided(xa, ya)){
			if(ya < 0){
				movingDir = 0;
			}
			if(ya > 0){
				movingDir = 1;
			}
			if(xa < 0){
				movingDir = 2;
			}
			if(xa > 0){
				movingDir = 3;
			}
			
			x += xa * speed;
			y += ya * speed;
		}
	}
	
	public abstract boolean hasCollided(int xa, int ya);
	
	// Get last tile the player was stand and compare to the next tile it will move.
	protected boolean isSolidTile(int xa, int ya, int x, int y){
		
		if(level == null){
			return false;
		}
		
		// Getting the tiles
		Tile lastTile = level.getTile((this.x + x) >> Screen.TILE_PIXELS_POW, (this.y + y) >> Screen.TILE_PIXELS_POW);
		Tile newTile = level.getTile((this.x + x + xa) >> Screen.TILE_PIXELS_POW, (this.y + y + ya) >> Screen.TILE_PIXELS_POW);
		
		// Comparing if the last tile is equal to new
		if(!lastTile.equals(newTile) && newTile.isSolid()){
			return true;
		}
		
		return false;
	}
	
	public String getName(){
		return name;
	}
}
