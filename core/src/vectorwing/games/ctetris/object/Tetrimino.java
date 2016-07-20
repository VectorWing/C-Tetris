package vectorwing.games.ctetris.object;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.StringBuilder;
import vectorwing.games.ctetris.util.TileUtils;

/**
 * A Tetrimino is a four-tile piece, used in every Tetris game. They come in seven varieties, defined by the
 * TetriminoShape enum, and are displayed with a sprite defined by the TetriminoColor enum. Tetriminos store data
 * regarding the TileGrid they operate in, as well as their position within such grid.
 */
public class Tetrimino
{
	/** The absolute location of the origin tile (0, 0), determining the whole group's location in the grid. */
	private Vector2 grid_position;
	/** The relative grid locations for the other three tiles in the Tetrimino. The fourth is the origin (0, 0).*/
	private Array<Vector2> secondary_tiles;
	/** The visual style to apply to any grid tiles filled by this Tetrimino. */
	private TetriminoColor color;
	/** The shape used to determine this Tetrimino's secondary tiles. */
	private TetriminoShape shape;

	private final TetrisGame game;

	private int grid_cols;
	private int grid_rows;

	public Tetrimino(TetriminoShape type, TetriminoColor color, TetrisGame game, int startX, int startY)
	{
		this.grid_position = new Vector2(startX, startY);
		this.color = color;
		this.shape = type;
		this.game = game;
		this.grid_cols = game.getGridCols();
		this.grid_rows = game.getGridRows();
		this.secondary_tiles = new Array<Vector2>();
		secondary_tiles.add(type.tile1);
		secondary_tiles.add(type.tile2);
		secondary_tiles.add(type.tile3);
	}

	/** Redefines the type of Tetrimino to simulate. */
	public Tetrimino setType(TetriminoShape type)
	{
		this.shape = type;
		this.secondary_tiles = new Array<Vector2>();
		secondary_tiles.add(shape.tile1);
		secondary_tiles.add(shape.tile2);
		secondary_tiles.add(shape.tile3);

		return this;
	}

	/** Rotates the piece clockwise, shifting all relative tiles around. */
	public void rotate()
	{
		// The O piece can't spin! Go back, dummy!
		if (this.shape == TetriminoShape.O) {
			return;
		}

		// If the piece's rotation will end up overlapping boundaries or solids, DON'T ROTATE.
		Vector2 compare = new Vector2();
		Array<Array<GridTile>> grid = game.getGridState();
		for (Vector2 tile : secondary_tiles) {
			compare.set(tile.y, -tile.x).add(grid_position);
			if (TileUtils.isOutOfBounds(compare, grid_cols, grid_rows)) {
				return;
			}
			if (grid.get((int)compare.y).get((int)compare.x).isSolid) {
				return;
			}
		}

		// But if it won't, rotate it!
		for (Vector2 tile : secondary_tiles) {
			tile.set(tile.y, -tile.x);
		}
	}

	/** Moves the piece by the given X and Y amounts in the grid. Returns if it could translate or not. */
	public boolean translate(int incrementX, int incrementY)
	{
		Array<Vector2> compare = this.getAbsoluteTilePositions();
		Array<Array<GridTile>> grid = game.getGridState();
		for (Vector2 tile : compare) {
			tile.add(incrementX, incrementY);
			if (TileUtils.isOutOfBounds(tile, grid_cols, grid_rows)) {
				return false;
			}
			if (grid.get((int)tile.y).get((int)tile.x).isSolid) {
				return false;
			}
		}
		this.grid_position.add(incrementX, incrementY);
		return true;
	}

	/** Returns an array with all four absolute tile positions, using the current grid position. */
	public Array<Vector2> getAbsoluteTilePositions()
	{
		Array<Vector2> result = new Array<Vector2>();
		result.add(new Vector2(0 + grid_position.x, 0 + grid_position.y));
		for (Vector2 tile : secondary_tiles) {
			result.add(new Vector2(tile.x + grid_position.x, tile.y + grid_position.y));
		}
		return result;
	}

	/** Returns the origin's current absolute position in the tile grid. */
	public Vector2 getGridPosition()
	{
		return this.grid_position;
	}

	public void setGridPosition(int x, int y)
	{
		this.grid_position.set(Math.min(x, this.grid_cols - 1), Math.min(y, this.grid_rows - 1));
	}

	public TetriminoColor getColor()
	{
		return this.color;
	}

	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		string.append("Shape: ").append(this.shape.name()).append("\n");
		string.append("Location: ").append(this.grid_position);
		return string.toString();
	}
}
