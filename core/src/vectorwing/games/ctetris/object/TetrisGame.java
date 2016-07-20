package vectorwing.games.ctetris.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import vectorwing.games.ctetris.config.Settings;

/** Controls an instance of a Tetris game. Contains all the logic necessary for the game's execution. */
public class TetrisGame
{
	public enum State
	{
		PREGAME,	// Default state
		PLAYING,	// Game started and ticking
		PAUSED,		// Game halted, data preserved
		GAME_OVER	// Player lost
	}

	private int grid_cols;
	private int grid_rows;

	// Data //
	private Array<Array<GridTile>> grid;
	private Tetrimino current_piece;
	private TetriminoShape next_piece;
	private int score;

	// Logic //
	private State state;
	private int piece_speed;
	private float piece_cascade_time;
	/** Did the piece just spawn atop? Used to provide finer control when pressing the DOWN key to fall faster. */
	private boolean piece_just_spawned;

	public TetrisGame(int cols, int rows)
	{
		this.state = State.PREGAME;
		this.grid_cols = cols;
		this.grid_rows = rows;
		this.grid = new Array<Array<GridTile>>();
		for (int i = 0; i < grid_rows; i++) {
			Array<GridTile> row = new Array<GridTile>();
			for (int j = 0; j < grid_cols; j++) {
				GridTile tile = new GridTile();
				row.add(tile);
			}
			grid.add(row);
		}
		this.current_piece = new Tetrimino(TetriminoShape.getRandomShape(), TetriminoColor.BLUE, this, 5, 16);
		System.out.println(current_piece.toString());
	}

	/** Initiates a brand new round of Tetris, with an empty grid. */
	public void begin()
	{
		updateCurrentNextPiece();
		piece_cascade_time = 100;
		piece_speed = 200;
		state = State.PLAYING;
	}

	public void update(float delta)
	{
		switch (state) {
			case PREGAME:
				break;
			case PLAYING:
				updateTetrimino(delta);
				break;
			case PAUSED:
				break;
			case GAME_OVER:
				break;
		}
	}

	private void updateCurrentNextPiece()
	{
		if (next_piece != null) {
			current_piece.setType(next_piece);
		} else {
			current_piece.setType(TetriminoShape.getRandomShape());
		}
		next_piece = TetriminoShape.getRandomShape();
	}

	private void updateTetrimino(float delta)
	{
		if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
			current_piece.rotate();
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
			piece_just_spawned = false;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && !piece_just_spawned) {
			this.piece_speed = Settings.PIECE_MAX_SPEED;
		} else {
			this.piece_speed = 200;
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
			current_piece.translate(-1, 0);
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
			current_piece.translate(1, 0);
		}

		piece_cascade_time -= piece_speed * delta;
		if (piece_cascade_time <= 0) {
			if (!current_piece.translate(0, -1)) {
				placePiece();
			}
			piece_cascade_time = 100;
		}
	}

	public void placePiece()
	{
		Array<Vector2> current_tiles = current_piece.getAbsoluteTilePositions();
		for (Vector2 tile : current_tiles) {
			grid.get((int)tile.y).get((int)tile.x).isSolid = true;
			grid.get((int)tile.y).get((int)tile.x).color = current_piece.getColor();
		}
		current_piece.setGridPosition(5, 16);

		checkFullLines();
		updateCurrentNextPiece();
		piece_just_spawned = true;
	}

	/** Searches the grid for fully solid rows, and clears them. */
	public void checkFullLines()
	{
		// Check which rows are fully solid, and add their indexes to an array.
		Array<Integer> row_indexes = new Array<Integer>();
		for (int i = 0; i < grid_rows; i++) {
			boolean isRowFull = true;
			for (int j = 0; j < grid_cols; j++) {
				if (!grid.get(i).get(j).isSolid) {
					isRowFull = false;
				}
			}
			if (isRowFull) {
				row_indexes.add(i);
			}
		}

		// Clear the lines!
		row_indexes.reverse();
		for (int i : row_indexes) {
			grid.removeIndex(i);
			Array<GridTile> row = new Array<GridTile>();
			for (int j = 0; j < grid_cols; j++) {
				GridTile tile = new GridTile();
				row.add(tile);
			}
			grid.add(row);
		}
	}

	public Array<Array<GridTile>> getGridState()
	{
		Array<Array<GridTile>> grid_state = this.grid;
		return grid_state;
	}

	public int getGridCols() { return this.grid_cols; }

	public int getGridRows() { return this.grid_rows; }

	public Tetrimino getTetrimino() { return this.current_piece; }

	public State getState() { return this.state; }
}
