package ca.lucas.gameengine.gfx;

public class Color {
	// color1 = white, color2 = light grey, color3 = dark grey, color 4 = black
	
	public static int get(int[] color1, int[] color2, int[] color3, int[] color4){
		
		// '<<' is used to access individual bit information on a single integer.
        // in currentColor has 4 bytes (aa, rr, gg and bb) and each byte has 8 bits
        // so, for change the right byte you have to use '<<'
		
		int shadeWhite = convertColor(color1[0], color1[1], color1[2]);
		int shadeLightGrey = convertColor(color2[0], color2[1], color2[2]);
		int shadeDarkGrey = convertColor(color3[0], color3[1], color3[2]);
		int shadeBlack = convertColor(color4[0], color4[1], color4[2]);
		
		int currentColor = ( (get(shadeBlack) << 24) + (get(shadeDarkGrey) << 16) + (get(shadeLightGrey) << 8) + (get(shadeWhite))); 
		return currentColor;
	}
	
	// Basically, the calculation for select a current color is: (r/10,g/10,b/10)/5
	private static int convertColor(int r, int g, int b){
		
		if(r == -1 || g == -1 || b == -1){
			return -1;
		}
		
		int currentColor = ((r/50) * 100) + ((g/50) * 10) + (b/50);
		return currentColor;
	}
	
	public static int get(int color){
		
		// If the color is out of spectrum, it will not be rendered.
		if(color < 0){
			return 255;
		}
		
		// %10 to guarantee that the number is between 0 and 10.
		int r = color / 100 % 10; // First part of the color
		int g = color / 10 % 10; // Second part of the color
		int b = color % 10; // Third part of the color
		
		return r * 36 + g * 6 + b; // 6 because are six shades of color
	}
}
