

package com.badlogic.drop;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;

public class Droplet 
{
    public Droplet(DropletType type, Rectangle rect)
    {
        this.type = type;
        this.rect = rect;
    } 
    
    public DropletType getType() 
    {
        return type;
    }
    
    public Rectangle getRect()
    {
        return rect;
    }
    
    public Color getColor() 
    {
        return getType().getColor();
    }
    
    public int getValue() 
    {
        return getType().getValue();
    }
    
    
    public void draw(Texture tex, SpriteBatch batch)
    {
        batch.setColor(getColor());
        batch.draw(tex, getRect().x, getRect().y);
        batch.setColor(Color.WHITE);
    }
    
    private DropletType type;
    private Rectangle rect;
}
