package ca.lucas.gameengine.level.tiles;

import ca.lucas.gameengine.gfx.Color;
import ca.lucas.gameengine.gfx.Screen;
import ca.lucas.gameengine.level.Level;

public abstract class Tile {
	
	private static final int WATER_DELAY = 300;
	 
    public static final Tile[] tiles = new Tile[256];
    public static final Tile VOID = new BasicSolidTile(0, 0, 0, Color.get(new int[] {0,0,0}, new int[] {-1,-1,-1},
    		new int[] {-1,-1,-1}, new int[] {-1,-1,-1}), 0xFF000000);
    public static final Tile STONE = new BasicSolidTile(1, 1, 0, Color.get(new int[] {-1,-1,-1},
    		new int[] {150,150,150}, new int[] {-1,-1,-1}, new int[] {-1,-1,-1}), 0xFF555555);
    public static final Tile GRASS = new BasicTile(2, 2, 0, Color.get(new int[] {-1,-1,-1},
    		new int[] {50,150,50}, new int[] {50,200,50}, new int[] {-1,-1,-1}), 0xFF00FF00);
    public static final Tile WATER_UP_LEFT = new AnimatedTile(3, new int[][]{{0, 4}, {2, 4}, // Coordenates of the tile on sprite sheet
    	{4, 4}, {6, 4}} , Color.get(new int[] {-1,-1,-1}, new int[] {0,0,200}, new int[] {50,50,250}, new int[] {255,225,225}), 0xFF0000FF, WATER_DELAY);
    public static final Tile WATER_UP_RIGHT = new AnimatedTile(4, new int[][]{{1, 4}, {3, 4}, // Coordenates of the tile on sprite sheet
    	{5, 4}, {7, 4}} , Color.get(new int[] {-1,-1,-1}, new int[] {0,0,200}, new int[] {50,50,250}, new int[] {255,225,225}), 0xFF8000FF, WATER_DELAY);
    public static final Tile WATER_DOWN_LEFT = new AnimatedTile(5, new int[][]{{0, 5}, {2, 5}, // Coordenates of the tile on sprite sheet
    	{4, 5}, {6, 5}}, Color.get(new int[] {-1,-1,-1}, new int[] {0,0,200}, new int[] {50,50,250}, new int[] {255,225,225}), 0xFF00FFFF, WATER_DELAY);
    public static final Tile WATER_DOWN_RIGHT = new AnimatedTile(6, new int[][]{{1, 5}, {3, 5}, // Coordenates of the tile on sprite sheet
    	{5, 5}, {7, 5}}, Color.get(new int[] {-1,-1,-1}, new int[] {0,0,200}, new int[] {50,50,250}, new int[] {255,225,225}), 0xFF80FFFF, WATER_DELAY);

    protected byte id;
    protected boolean solid;
    protected boolean emitter;
    private int levelColor;
    
    // Constructor
    public Tile(int id, boolean isSolid, boolean isEmitter, int levelColor) {
        this.id = (byte) id;
        if (tiles[id] != null) {
                throw new RuntimeException("Duplicate tile id on" + id);
        }
        this.solid = isSolid;
        this.emitter = isEmitter;
        this.levelColor = levelColor;
        tiles[id] = this;
    }

    public byte getId() {
        return id;
    }

    public boolean isSolid() {
        return solid;
    }

    public boolean isEmitter() {
        return emitter;
    }
    
    public int getLevelColor(){
    	return levelColor;
    }
    
    public abstract void tick();

    public abstract void render(Screen screen, Level level, int x, int y);

}
