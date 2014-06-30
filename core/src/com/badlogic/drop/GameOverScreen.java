
package com.badlogic.drop;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.Input.Keys;


/**
 *
 * @author victor
 */
public class GameOverScreen extends ScreenAdapter
{
    public GameOverScreen(final DropGame dropGame)
    {
        this.dropGame = dropGame;
        batch = dropGame.batch;
        font = new BitmapFont();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 400, 400);
    }
    
    @Override
    public void show()
    {
        
    }
    
    @Override
    public void render(float delta)
    {
        if(Gdx.input.isKeyPressed(Keys.ENTER))
            dropGame.setScreen(new GameScreen(dropGame));
        
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        batch.setProjectionMatrix(camera.combined);
        
        batch.begin();
        batch.setColor(Color.RED);
        font.draw(batch, "You lose the game!!", 145, 230);
        font.draw(batch, "Press [ENTER] to play again", 130, 200);
        batch.setColor(Color.WHITE);
        batch.end();
    }
    
    SpriteBatch batch;
    DropGame dropGame;
    BitmapFont font;
    OrthographicCamera camera;
}
