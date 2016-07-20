package vectorwing.games.ctetris.util;

import com.badlogic.gdx.math.Vector2;

/** Contains methods for helping in grid coordinate math. */
public class TileUtils
{
	/** Verifies if the given tile coordinate resides outside of the given grid bounds. */
	public static boolean isOutOfBounds(Vector2 tile, int grid_cols, int grid_rows)
	{
		if (tile.x < 0 || tile.x >= grid_cols) {
			return true;
		}
		if (tile.y < 0 || tile.y >= grid_rows) {
			return true;
		}
		return false;
	}

	/** Returns 'value' if it lies between 'min' and 'max'. Returns 'min' if too low, or 'max' if too high. */
	public static int limit(int min, int value, int max)
	{
		int result = value > min ? value : min;
		result = result < max ? result : max;
		return result;
	}
}
