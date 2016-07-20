package vectorwing.games.ctetris.object;

import com.badlogic.gdx.math.Vector2;

/**
 * Represents a singular tile in a TetrisGame's grid. Can either be solid or not.
 * Also contains a visual style to display if solid.
 */
public class GridTile
{
	public boolean isSolid;
	public TetriminoColor color;

	public GridTile()
	{
		isSolid = false;
		color = TetriminoColor.values()[0];
	}
}
