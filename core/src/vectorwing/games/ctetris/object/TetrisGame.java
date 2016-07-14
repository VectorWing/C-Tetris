package vectorwing.games.ctetris.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.Array;

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

	private Array<Array<GridTile>> grid;
	private Tetrimino current_piece;
	private TetriminoShape next_piece;

	private State state;
	private int piece_speed;
	private float piece_cascade_time;

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
	}

	/** Initiates a brand new round of Tetris, with an empty grid. */
	public void begin()
	{
		updateTetriminoTypes();
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

	private void updateTetriminoTypes()
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
		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			current_piece.rotate();
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
			current_piece.translate(0, 1);
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
			current_piece.translate(0, -1);
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
			current_piece.translate(-1, 0);
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
			current_piece.translate(1, 0);
		}
		piece_cascade_time -= piece_speed * delta;
		if (piece_cascade_time <= 0) {
			current_piece.translate(0, -1);
			piece_cascade_time = 100;
		}
	}

	public int getGridCols() { return this.grid_cols; }

	public int getGridRows() { return this.grid_rows; }

	public Tetrimino getTetrimino() { return this.current_piece; }

	public State getState() { return this.state; }
}
