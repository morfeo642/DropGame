
package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;

/**
 *
 * @author victor
 */
public class MainScreen extends ScreenAdapter
{
    public MainScreen(final DropGame dropGame)
    {
        font = new BitmapFont();
        batch = dropGame.batch;
        this.dropGame = dropGame;
    }
    
    @Override
    public void show()
    {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 400, 400);
    }
    
    @Override
    public void render(float delta)
    {
        if(Gdx.input.isTouched())
        {
            /* cambiamos la pantalla */
            dropGame.setScreen(new GameScreen(dropGame));
        }
        
        /* dibujamos el menú principal */
        Gdx.gl20.glClearColor(0, 0, 0, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        camera.update();
        
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        font.draw(batch, "Touch the screen to play", 150, 200);
        batch.end();
    }
   
    
    @Override
    public void dispose()
    {
        font.dispose();
    }
    
    private BitmapFont font;
    private SpriteBatch batch;
    private DropGame dropGame;
    private OrthographicCamera camera;
}
