package ca.lucas.gameengine.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {
	public String path;
	public int width;
	public int height;
	
	public int[] pixels; // Pixel data of the current sprite sheet
	
	// Constructor gets the path of the sprite sheet
	public SpriteSheet(String path){
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(SpriteSheet.class.getResourceAsStream(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(image == null){
			return;
		}
		
		this.path = path;
		this.width = image.getWidth(); // Sprite sheet width
		this.height = image.getHeight(); // Sprite sheet height
		
		// getRGB(xStart, yStart, sprite width, sprite height, pixel array [rgb array], offset, scansize)
		pixels = image.getRGB(0, 0, width, height, null, 0, width);
		
		// The rgb is represented by 0xAARRGGBB (Alfa, Red, Green, Blue).
		// ff represents white, and 00 represents black
		for( int i = 0; i < pixels.length; i++){
			// We are using 4 colors to represent all colors, so we have to divide 255/4 ≃ 64
			pixels[i] = (pixels[i] & 0xff) / 64; 
		}
		
		// For calculate the gray scale in this case, we have to divide 255 by 3 and multiply by 0, 1, 2 and 3
		
		
		// Debbug
//		for(int i = 0; i < 8; i++){
//			System.out.println(pixels[i]);
//		}
	}
}
