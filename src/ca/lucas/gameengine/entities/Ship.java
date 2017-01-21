package ca.lucas.gameengine.entities;

import java.util.Random;

import ca.lucas.gameengine.gfx.Color;
import ca.lucas.gameengine.gfx.Screen;
import ca.lucas.gameengine.level.Level;

public abstract class Ship extends Mob{
	
	private static final int MOVING_BOTTOM = 1;
	
	// Position of the beginning of the Ship tiles
	private static final int Ship_ROW = 28;
	private static final int Ship_COL = 0;
	

	private int color;
//		= Color.get(new int[] {-1,-1,-1}, new int[] {0,0,0}, new int[] {0,0,255}, new int[] {156,114,72});
	private int scale = 1;
	private int tickCount = 0;
	private static int positionX;
	private static int positionY;
	private Screen screen;
	private int shipId;
	private int shipFlag;
	private int xa = 0;
	private int ya = 0;
	
	
	// Constructor
	public Ship(Level level, Screen screen, int shipID){
		super(level, "Ship",generateShipPosition(),0.01);
		this.screen = screen;
		this.shipId = shipId;
		generateColor();
		generateFlag();
	}
	
	private void generateFlag(){
		Random rand = new Random();
		if(shipId == 1){
			
		}
	}
	
	private void generateColor(){
		Random rand = new Random();
		int color = rand.nextInt(3);
		if(color == 0){
			this.color = Color.get(new int[]{-1,-1,-1}, new int[]{0,0,255}, new int[]{0,0,191}, new int[]{0,0,127});
		}
		if(color == 1){
			this.color = Color.get(new int[]{-1,-1,-1}, new int[]{0,255,0}, new int[]{0,127,0}, new int[]{0,191,0});
		}
		if(color == 2){
			this.color = Color.get(new int[]{-1,-1,-1}, new int[]{255,0,0}, new int[]{191,0,0}, new int[]{127,0,0});
		}
	}

	private static int[] generateShipPosition(){
		Random rand = new Random();
		int  side = rand.nextInt(4);
		
		if(side == 0) {
			positionX = 0;
			positionY = rand.nextInt(200);
		}
		else if(side == 1) {
			positionY = 200;
			positionX = rand.nextInt(200);
		}
		else if(side == 2) {
			positionX = 200;
			positionY = rand.nextInt(200);
		}
		else if(side == 3) {
			positionY = 0;
			positionX = rand.nextInt(200);
		}
		
//		System.out.println("position = "+ position + "\n" + "side = " + side);
		return new int[]{positionX,positionY};
	}
	public void tick() {
		if(positionX < screen.width /2) {
			xa++;
		}
		if(positionX > screen.width /2) {
			xa--;
		}
		if(positionY < screen.height /2) {
			ya++;
		}
		if(positionY > screen.height /2) {
			ya--;
		}
		
	    if(xa != 0 || ya != 0){
	    	move(xa, ya);
	    	System.out.println("X=" + xa + "Y=" + ya);
	    	isMoving = true;
	    }
	    else{
	    	isMoving = false;
	    }
//	    if(level.getTile(this.x >> 3, this.y >> 3).getId() == 3){
//	    	isSwimming = true;
//	    }
//	    if(isSwimming && level.getTile(this.x >> 3, this.y >> 3).getId() != 3){
//	    	isSwimming = false;
//	    }
	  
	    tickCount++;
	    this.scale = 1;
	}

	// Rendering the Ship pixel by pixel
	public void render(Screen screen) {
		int xTile = Ship_COL;
		int yTile = Ship_ROW;
		int walkingSpeed = 1; // The walking steps of the Ship. The real velocity don't change.
		int flipTop = (numSteps >> walkingSpeed) & 1; // Divide numSteps by 2^(walkingSpeed). The '& 1' is to change the number between 0 and 1
		int flipBottom = (numSteps >> walkingSpeed) & 1;;
		
		// If is moving to bottom, move to next tile in tile sheet
		if(movingDir == MOVING_BOTTOM){
			xTile += 2;
		}
		// If is moving left or right
		else if(movingDir > 1){
			// If the Ship is moving horizontally, keep it changing between the walking tiles
			xTile += 4 + ((numSteps >> walkingSpeed) & 1) * 2;
			flipTop = (movingDir - 1) % 2; // Decide the direction of the tile by movingDir
		}
		
		int modifier = Screen.TILE_WEIGHT * scale;
		int xOffset = x - modifier / 2;
		int yOffset = y - modifier / 2 - 4;
		
		// Render the head (2 tiles above)
		screen.render(xOffset + (modifier * flipTop),yOffset, xTile + yTile * Screen.SPRITE_SHEET_WEIGHT, color, flipTop, scale);
		screen.render(xOffset + modifier - (modifier * flipTop), yOffset, (xTile + 1) + yTile * Screen.SPRITE_SHEET_WEIGHT, color, flipTop, scale);
		
		// Render the body (2 tiles below) 
		screen.render(xOffset + (modifier * flipBottom), yOffset + modifier, xTile + (yTile + 1) * Screen.SPRITE_SHEET_WEIGHT, color, flipBottom, scale);
		screen.render(xOffset + modifier - (modifier * flipBottom), yOffset + modifier, (xTile + 1) + (yTile + 1) * Screen.SPRITE_SHEET_WEIGHT, color, flipBottom, scale);
	
	}
	
	// Creating a box collider
	public boolean hasCollided(int xa, int ya) {
		
		// Edges of the Ship's legs
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

