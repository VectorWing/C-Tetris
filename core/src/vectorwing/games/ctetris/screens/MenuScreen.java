package vectorwing.games.ctetris.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import vectorwing.games.ctetris.CTetris;
import vectorwing.games.ctetris.config.Settings;
import vectorwing.games.ctetris.object.GridTile;
import vectorwing.games.ctetris.object.TetrisGame;

/** The main menu, and the first screen to be displayed. */
public class MenuScreen extends DefaultScreen
{
	private OrthographicCamera camera;
	public TetrisGame tetris;

	public MenuScreen(CTetris game)
	{
		super(game);
		this.tetris = new TetrisGame(Settings.DEFAULT_GRID_COLS, Settings.DEFAULT_GRID_ROWS);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 600);
	}

	@Override
	public void update(float delta)
	{
		if (tetris.getState() == TetrisGame.State.PREGAME) {
			tetris.begin();
			return;
		}

		if (tetris.getState() != TetrisGame.State.GAME_OVER) {
			tetris.update(delta);
		}
	}

	@Override
	public void render(float delta)
	{
		update(delta);

		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
		game.shape.setProjectionMatrix(camera.combined);

		game.shape.begin(ShapeRenderer.ShapeType.Filled);

		game.shape.setColor(0, 0, 0.4f, 1);
		game.shape.rect(0, 0, Settings.DEFAULT_GRID_COLS * 32, Settings.DEFAULT_GRID_ROWS * 32);

		game.shape.setColor(0, 0, 0.9f, 1);
		Array<Array<GridTile>> grid_state = tetris.getGridState();
		for (int i = 0; i < tetris.getGridRows(); i++) {
			for (int j = 0; j < tetris.getGridCols(); j++) {
				if (grid_state.get(i).get(j).isSolid) {
					game.shape.rect((j * 32), (i * 32), 32, 32);
				}
			}
		}

		game.shape.setColor(1, 1, 1, 1);
		Array<Vector2> tiles = tetris.getTetrimino().getAbsoluteTilePositions();
		for (Vector2 tile : tiles) {
			game.shape.rect((tile.x * 32), (tile.y * 32), 32, 32);
		}
		game.shape.end();
	}

	@Override
	public void dispose()
	{

	}
}
