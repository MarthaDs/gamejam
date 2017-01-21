package ca.lucas.gameengine.gfx;

public class Screen {
	
	// The sprite sheet size is 32 x 32 blocs of tiles 
	public static final int SPRITE_SHEET_WEIGHT = 32;

	// Potency that has to elevate to get the total quantity of pixels each tile has
	public static final int TILE_PIXELS_POW = 4;
	
	// The tile size is 8 x 8 pixels
	public static final int TILE_WEIGHT = 16;
	
	public static final int MAP_WIDTH = 256;
	public static final int MAP_WIDTH_MASK = MAP_WIDTH - 1;
	
	public static final byte BIT_MIRROR_X = 0x01;
	public static final byte BIT_MIRROR_Y = 0x02;
	
	public int[] pixels; // Pixel data
	
	// "Camera" position. Where the screen will render of the image.
	public int xOffset = 0;
	public int yOffset = 0;
	
	public int width;
	public int height;
	
	public SpriteSheet sheet;
	
	// Constructor
	public Screen(int width, int height, SpriteSheet sheet){
		this.width = width;
		this.height = height;
		this.sheet = sheet;
		
		pixels = new int[width * height];
	}
	
	public void setOffset(int xOffset, int yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }
		
	public void render(int xPos, int yPos, int tile, int color, int mirrorDir, int scale){
		xPos -= xOffset;
        yPos -= yOffset;
        
        // Mirror the tile to a better performance 
        boolean mirrorX = (mirrorDir & BIT_MIRROR_X) > 0;
        boolean mirrorY = (mirrorDir & BIT_MIRROR_Y) > 0;

        // Getting the size and how many pixels each tile has 
        int xTile = tile % SPRITE_SHEET_WEIGHT;
        int yTile = tile / SPRITE_SHEET_WEIGHT;
        int tileOffset = (xTile << TILE_PIXELS_POW) + (yTile << TILE_PIXELS_POW) * sheet.width; 
        int scaleMap = scale - 1; // Increases screen size
        
        for (int y = 0; y < TILE_WEIGHT; y++) {
        	
        	// Just to secure that not render out of the screen
        	if (y + yPos < 0 || y + yPos >= height) {
        		continue;
            }
        	
            int ySheet = y;
            
            // If mirrorY is true, invert the y vector of the tile
            if(mirrorY){
            	ySheet = 7 - y;
            }
            
            int yPixel = y + yPos + (y * scaleMap) - ((scaleMap << TILE_PIXELS_POW) / 2);
            
            for (int x = 0; x < TILE_WEIGHT; x++) {
            	
            	// Just to secure that not render out of the screen
                if (x + xPos < 0 || x + xPos >= width) {
                    continue;
                }
                
                int xSheet = x;
                
                // 	If mirrorX is true, invert the x vector of the tile
                if(mirrorX){
                	xSheet = 7 - x;
                }
                
                int xPixel = x + xPos + (x * scaleMap) - ((scaleMap << TILE_PIXELS_POW) / 2);
                
                // Get the specific color of the current pixel
                int col = (color >> ( sheet.pixels[xSheet + ySheet
                                * sheet.width + tileOffset] * TILE_WEIGHT)) & 255; // Verify if the number is between 0 and 255
                
                // If the color is not transparent
                if (col < 255) {
                	for(int yScale = 0; yScale < scale; yScale++){
                		if(yPixel + yScale < 0 || yPixel + yScale >= height){
                    		continue;
                    	}
                		
                		for(int xScale = 0; xScale < scale; xScale++){
                    		if(xPixel + xScale < 0 || xPixel + xScale >= width){
                        		continue;
                        	}
                    		
                    		pixels[(xPixel + xScale) + (yPixel + yScale) * width] = col; // Render the current pixel
                    	}
                	}
                }
            }
        }		
	}
}
