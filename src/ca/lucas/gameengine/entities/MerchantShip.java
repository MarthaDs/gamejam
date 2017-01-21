package ca.lucas.gameengine.entities;

import java.util.Random;

import ca.lucas.gameengine.gfx.Screen;
import ca.lucas.gameengine.level.Level;

public class MerchantShip extends Ship {
	
	public int merchantId;

	public MerchantShip(Level level, Screen screen) {
		super(level, screen, 1);
		generateId();
		tick();
	}
	
	private void generateId(){
		Random rand = new Random();
		int merchantId = rand.nextInt(2) + 1;
		this.merchantId = merchantId;
	}

}
