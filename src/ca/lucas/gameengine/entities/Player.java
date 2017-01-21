package ca.lucas.gameengine.entities;

import ca.lucas.gameengine.InputHandler;
import ca.lucas.gameengine.gfx.Color;
import ca.lucas.gameengine.gfx.Screen;
import ca.lucas.gameengine.level.Level;

public class Player extends Mob{
	
	// Position of the beginning of the player tiles
	private static final int MOVING_BOTTOM = 1;
	private static final int PLAYER_ROW = 28;
	private static final int PLAYER_COL = 0;
	
	private InputHandler input;
	private int color = Color.get(new int[] {-1,-1,-1}, new int[] {0,0,0}, new int[] {255,0,0}, new int[] {156,114,72});
	private int scale = 1;
	protected boolean isSwimming = false; 
	private int tickCount = 0;
	
	// Constructor
	public Player(Level level, int x, int y, InputHandler input){
		super(level, "Player", x, y, 1);
		this.input = input;
	}

	public void tick() {
		int xa = 0;
		int ya = 0;
		
		if (input.up.isPressed()) {
            ya--;
	    }
	    if (input.down.isPressed()) {
	    	ya++;
	    }
	    if (input.left.isPressed()) {
	    	xa--;
	    }
	    if (input.right.isPressed()) {
	    	xa++;
	    }
	    
	    if(xa != 0 || ya != 0){
	    	move(xa, ya);
	    	isMoving = true;
	    }
	    else{
	    	isMoving = false;
	    }
	    if(level.getTile(this.x >> 3, this.y >> 3).getId() == 3){
	    	isSwimming = true;
	    }
	    if(isSwimming && level.getTile(this.x >> 3, this.y >> 3).getId() != 3){
	    	isSwimming = false;
	    }
	    tickCount++;
	    this.scale = 1;
	}

	// Rendering the player pixel by pixel
	public void render(Screen screen) {
		int xTile = PLAYER_COL;
		int yTile = PLAYER_ROW;
		int walkingSpeed = 3; // The walking steps of the player. The real velocity don't change.
		int flipTop = (numSteps >> walkingSpeed) & 1; // Divide numSteps by 2^(walkingSpeed). The '& 1' is to change the number between 0 and 1
		int flipBottom = (numSteps >> walkingSpeed) & 1;;
		
		// If is moving to bottom, move to next tile in tile sheet
		if(movingDir == MOVING_BOTTOM){
			xTile += 2;
		}
		// If is moving left or right
		else if(movingDir > 1){
			// If the player is moving horizontally, keep it changing between the walking tiles
			xTile += 4 + ((numSteps >> walkingSpeed) & 1) * 2;
			flipTop = (movingDir - 1) % 2; // Decide the direction of the tile by movingDir
		}
		
		int modifier = Screen.TILE_WEIGHT * scale;
		int xOffset = x - modifier / 2;
		int yOffset = y - modifier / 2 - 4;
		if(isSwimming){
			int waterColor = 0;
			yOffset += 4;
			if(tickCount % 60 < 15){
				waterColor = Color.get(new int[] {-1,-1,-1}, new int[] {-1,-1,-1}, new int[] {100,100,250}, new int[] {-1,-1,-1});
			}
			else if(15 <= tickCount % 60 && tickCount % 60 < 30){
				yOffset -= 1;
				waterColor = Color.get(new int[] {-1,-1,-1}, new int[] {100,100,250}, new int[] {50,50,250}, new int[] {-1,-1,-1});
			}
			else if(30 <= tickCount % 60 && tickCount % 60 < 45){
				waterColor = Color.get(new int[] {-1,-1,-1}, new int[] {50,50,250}, new int[] {-1,-1,-1}, new int[] {100,100,250});
			}
			else{
				yOffset -= 1;
				waterColor = Color.get(new int[] {-1,-1,-1}, new int[] {100,100,250}, new int[] {50,50,250}, new int[] {-1,-1,-1});
			}
			screen.render(xOffset, yOffset + 3, 0 + 27 * Screen.SPRITE_SHEET_WEIGHT, waterColor, 0x00, 1);
			screen.render(xOffset + 8, yOffset + 3, 0 + 27 * Screen.SPRITE_SHEET_WEIGHT, waterColor, 0x01, 1);
		}
		
		// Render the head (2 tiles above)
		screen.render(xOffset + (modifier * flipTop),yOffset, xTile + yTile * Screen.SPRITE_SHEET_WEIGHT, color, flipTop, scale);
		screen.render(xOffset + modifier - (modifier * flipTop), yOffset, (xTile + 1) + yTile * Screen.SPRITE_SHEET_WEIGHT, color, flipTop, scale);
		
		// Render the body (2 tiles below) 
		if(!isSwimming){
			screen.render(xOffset + (modifier * flipBottom), yOffset + modifier, xTile + (yTile + 1) * Screen.SPRITE_SHEET_WEIGHT, color, flipBottom, scale);
			screen.render(xOffset + modifier - (modifier * flipBottom), yOffset + modifier, (xTile + 1) + (yTile + 1) * Screen.SPRITE_SHEET_WEIGHT, color, flipBottom, scale);
		}
	}
	
	// Creating a box collider
	public boolean hasCollided(int xa, int ya) {
		
		// Edges of the player's legs
		int xMin = 0;
		int xMax = 7;
		int yMin = 3;
		int yMax = 7;
		
		for(int x = xMin; x < xMax; x++){
			if(isSolidTile(xa, ya, x, xMin)){
				return true;
			}
		}
		for(int x = xMin; x < xMax; x++){
			if(isSolidTile(xa, ya, x, yMax)){
				return true;
			}
		}
		for(int y = yMin; y < yMax; y++){
			if(isSolidTile(xa, ya, xMin, y)){
				return true;
			}
		}
		for(int y = yMin; y < yMax; y++){
			if(isSolidTile(xa, ya, xMax, y)){
				return true;
			}
		}
		
		return false;
	}
}
