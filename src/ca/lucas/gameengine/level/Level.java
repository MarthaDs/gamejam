package ca.lucas.gameengine.level;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.imageio.ImageIO;

import ca.lucas.gameengine.entities.Entity;
import ca.lucas.gameengine.gfx.Screen;
import ca.lucas.gameengine.level.tiles.Tile;

public class Level {
	 
    private byte[] tiles;
    public int width;
    public int height;
    public List<Entity> entities = new ArrayList<Entity>();
    private String imagePath;
    private BufferedImage image;
    
    private Comparator<Entity> renderSorter = new Comparator<Entity>(){
    	
    	@Override
    	public int compare(Entity a, Entity b){
    		if(a.y < b.y){
    			return -1;
    		}
    		return 1;
    	}
    };

    // Constructor
    public Level(String imagePath) {
		if(imagePath != null){
			this.imagePath = imagePath;
			this.loadLevelFromFile();
		}
		else{
	        this.width = 64;
	        this.height = 64;
	        tiles = new byte[width * height];
	        this.generateLevel();
		}
    }
    
    private void loadLevelFromFile(){
    	try{
    		this.image = ImageIO.read(Level.class.getResource(this.imagePath));
    		this.width = image.getWidth();
    		this.height = image.getHeight();
    		tiles = new byte[width * height];
    		this.loadTiles();
    	}
    	catch(IOException e){
    		e.printStackTrace();
    	}
    }
    
    private void loadTiles(){
    	int[] tileColors = this.image.getRGB(0, 0, width, height, null, 0, width);
    	for(int y = 0; y < height; y++){
    		for(int x = 0; x < width; x++){
        		for(Tile t : Tile.tiles){
        			tileCheck: if(t != null && t.getLevelColor() == tileColors[x + y * width]){
        				this.tiles[x + y * width] = t.getId();
        				break tileCheck;
        			}
        		}
        	}
    	}
    }

    private void saveLevelToFile(){
    	try{
    		ImageIO.write(image, "png", new File(Level.class.getResource(this.imagePath).getFile()));
    	}
    	catch(IOException e){
    		e.printStackTrace();
    	}
    }
    
    public void alterTile(int x, int y, Tile newTile){
    	this.tiles[x + y * width] = newTile.getId();
    	image.setRGB(x, y, newTile.getLevelColor());
    }
    
    
    // Put elements in all levels
    public void generateLevel() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
            	tiles[x + y * width] = Tile.GRASS.getId();
            }
        }
    }

    public void tick() {
    	for(Entity e : entities){
    		e.tick();
    	}
    	
    	//entities.sort(renderSorter); // The entities above are rendered before the below
    	
    	for(Tile t: Tile.tiles){
    		if(t == null){
    			break;
    		}
    		t.tick();
    	}
    }

    public void renderTiles(Screen screen, int xOffset, int yOffset) {
    	// If the "camera" move out of the level, it backs to the level 
        if (xOffset < 0){
        	xOffset = 0;
        }
        if (xOffset > ((width << Screen.TILE_PIXELS_POW) - screen.width)){
        	xOffset = ((width << Screen.TILE_PIXELS_POW) - screen.width); // If xOffset is bigger than board of the sprite sheet, it will return to its max
        }
            
        if (yOffset < 0){
        	yOffset = 0;
        }
        if (yOffset > ((height << Screen.TILE_PIXELS_POW) - screen.height)){
        	yOffset = ((height << Screen.TILE_PIXELS_POW) - screen.height); // If yOffset is bigger than board of the sprite sheet, it will return to its max
        }

        // Show on the screen
        screen.setOffset(xOffset, yOffset);

        for (int y = (yOffset >> Screen.TILE_PIXELS_POW); y < (yOffset + screen.height >> Screen.TILE_PIXELS_POW) + 1; y++) {
        	for (int x = (xOffset >> Screen.TILE_PIXELS_POW); x < (xOffset + screen.width >> Screen.TILE_PIXELS_POW) + 1; x++) {
                getTile(x, y).render(screen, this, x << Screen.TILE_PIXELS_POW, y << Screen.TILE_PIXELS_POW);
            }
        }
    }
    
    public void renderEntities(Screen screen){
    	for(Entity e : entities){
    		e.render(screen);
    	}
    }

	public Tile getTile(int x, int y) {
		// If the position is out of the sprite sheet rendered, it will return void, else, returns the current tile
	    if (0 > x || x >= width || 0 > y || y >= height){
	    	return Tile.VOID;
	    }
	    return Tile.tiles[tiles[x + y * width]];
	}
	
	public void addEntity(Entity entity){
		this.entities.add(entity);
	}
}
