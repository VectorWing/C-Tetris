package vectorwing.games.ctetris;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import vectorwing.games.ctetris.screens.MenuScreen;

public class CTetris extends Game
{
	/** Graphic rendering element to be used by all screens. */
	public SpriteBatch batch;
	public ShapeRenderer shape;

	@Override
	public void create () {
		batch = new SpriteBatch();
		shape = new ShapeRenderer();
		this.setScreen(new MenuScreen(this));
	}
}
