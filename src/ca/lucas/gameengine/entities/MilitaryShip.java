package ca.lucas.gameengine.entities;

import java.util.Random;

import ca.lucas.gameengine.gfx.Screen;
import ca.lucas.gameengine.level.Level;

public class MilitaryShip extends Ship {
	
	public int merchantId;

	public MilitaryShip(Level level, int x, int y, Screen screen) {
		super(level, x, y, screen, 2);
		changeFlag();
	}
	
	public void changeFlag(){
	}

}
