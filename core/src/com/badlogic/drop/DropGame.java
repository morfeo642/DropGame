package com.badlogic.drop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;


public class DropGame extends Game {

    @Override
    public void create() 
    {
        batch = new SpriteBatch();
        
        GameScreen gameScreen = new GameScreen(this);

        this.setScreen(new MainScreen(this, gameScreen));
    }
   
    @Override
    public void render() 
    {
        super.render();
    }
    
    SpriteBatch batch;
}

