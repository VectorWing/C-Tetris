package vectorwing.games.ctetris.screens;

import com.badlogic.gdx.Screen;
import vectorwing.games.ctetris.CTetris;

/** Common template for all screens in C-Tetris. */
public abstract class DefaultScreen implements Screen
{
	protected final CTetris game;

	public DefaultScreen(CTetris game)
	{
		this.game = game;
	}

	/** Runs once every tick. For organization, place game logic here. */
	public abstract void update(float delta);

	@Override
	public void render(float delta)
	{

	}

	@Override
	public void dispose()
	{

	}

	@Override
	public void show()
	{

	}

	@Override
	public void resize(int width, int height)
	{

	}

	@Override
	public void pause()
	{

	}

	@Override
	public void resume()
	{

	}

	@Override
	public void hide()
	{

	}
}
