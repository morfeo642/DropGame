package com.badlogic.drop;

import com.badlogic.gdx.graphics.Color;


public enum DropletType
{
    NORMAL (Color.WHITE, 1),
    DOUBLE (Color.GREEN, 2),
    TRIPLE (Color.RED, 3);
    
    DropletType(Color color, int value)
    {
        this.color = color;
        this.value = value;
    }
    
    public Color getColor()
    {
        return color;
    }
    
    public int getValue()
    {
        return value;
    }
    
    private Color color;
    private int value;
}

