package vectorwing.games.ctetris.config;

/** The collection of configurations and general variables used by everything in C-Tetris. */
public class Settings
{
	// TODO: These values are not final. Plan a different default size.
	public static final int DESKTOP_SCREEN_WIDTH = 800;
	public static final int DESKTOP_SCREEN_HEIGHT = 600;

	/** Determines the pixel size of a square 'tile'. It is used as both width and height. */
	public static final int TILE_SIZE = 32;
	public static final int DEFAULT_GRID_ROWS = 18;
	public static final int DEFAULT_GRID_COLS = 10;
	public static final int MINIMUM_GRID_ROWS = 4;
	public static final int MINIMUM_GRID_COLS = 4;

	public static final int PIECE_MAX_SPEED = 2000;
}
