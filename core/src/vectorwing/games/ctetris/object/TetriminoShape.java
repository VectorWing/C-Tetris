package vectorwing.games.ctetris.object;

import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/** Determines the relative coordinates of a Tetrimino's secondary tiles around the origin (0, 0). */
public enum TetriminoShape
{
	I(new Vector2(0, 1), new Vector2(0, -1), new Vector2(0, -2), 2),
	O(new Vector2(0, -1), new Vector2(1, 0), new Vector2(1, -1), 0),
	T(new Vector2(-1, 0), new Vector2(0, -1), new Vector2(1, 0), 4),
	L(new Vector2(0, 1), new Vector2(0, -1), new Vector2(1, -1), 4),
	J(new Vector2(0, 1), new Vector2(0, -1), new Vector2(-1, -1), 4),
	S(new Vector2(0, 1), new Vector2(1, 1), new Vector2(-1, 0), 2),
	Z(new Vector2(0, 1), new Vector2(-1, 1), new Vector2(1, 0), 2);

	public final Vector2 tile1;
	public final Vector2 tile2;
	public final Vector2 tile3;
	/** How many unique rotations does this shape have? */
	public final int rotations;

	TetriminoShape(Vector2 tile1, Vector2 tile2, Vector2 tile3, int rotations)
	{
		this.tile1 = tile1;
		this.tile2 = tile2;
		this.tile3 = tile3;
		this.rotations = rotations;
	}

	/** Randomly selects a Tetrimino shape and returns it. */
	public static TetriminoShape getRandomShape()
	{
		Random select = new Random();
		return values()[select.nextInt(7)];
	}
}
