package vectorwing.games.ctetris.object;

import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/** Determines the relative coordinates of a Tetrimino's secondary tiles around the origin (0, 0). */
public enum TetriminoShape
{
	I(new Vector2(-1, 0), new Vector2(1, 0), new Vector2(2, 0)),
	O(new Vector2(0, -1), new Vector2(1, 0), new Vector2(1, -1)),
	T(new Vector2(-1, 0), new Vector2(0, -1), new Vector2(1, 0)),
	L(new Vector2(0, 1), new Vector2(0, -1), new Vector2(1, -1)),
	J(new Vector2(0, 1), new Vector2(0, -1), new Vector2(-1, -1)),
	S(new Vector2(0, 1), new Vector2(1, 1), new Vector2(-1, 0)),
	Z(new Vector2(0, 1), new Vector2(-1, 1), new Vector2(1, 0));

	public final Vector2 tile1;
	public final Vector2 tile2;
	public final Vector2 tile3;

	TetriminoShape(Vector2 tile1, Vector2 tile2, Vector2 tile3)
	{
		this.tile1 = tile1;
		this.tile2 = tile2;
		this.tile3 = tile3;
	}

	/** Randomly selects a Tetrimino shape and returns it. */
	public static TetriminoShape getRandomShape()
	{
		Random select = new Random();
		return values()[select.nextInt(7)];
	}
}
