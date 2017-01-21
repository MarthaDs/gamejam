package ca.lucas.gameengine.gfx;

public class Font {
	
	// Position of the beginning of chars in the sprite sheet
	private static final int CHAR_ROW = 30;
	
	// Has to be equal to the tile sheet
	private static String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ      " +
			"0123456789.,:;'\"!?$%()-=+/      ";
	
	public static void render(String msg, Screen screen, int x, int y, int color, int scale){
		msg = msg.toUpperCase(); // Convert all chars to Caps
		
		for(int i = 0; i < msg.length(); i++){
			int charIndex = chars.indexOf(msg.charAt(i));
			
			// Just render the characters that are in the String 'chars'
			if(charIndex >= 0){
				screen.render(x+(i*8), y, charIndex + CHAR_ROW * Screen.SPRITE_SHEET_WEIGHT, color, 0x00, scale);
			}
		}
	}
}
