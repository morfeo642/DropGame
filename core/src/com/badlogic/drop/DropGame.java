package com.badlogic.drop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class DropGame extends Game {

    @Override
    public void create() 
    {
        batch = new SpriteBatch();
        
        this.setScreen(new MainScreen(this));
    }
   
    @Override
    public void render() 
    {
        super.render();
    }
    
    SpriteBatch batch;
}
