package com.mrdwz.drop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by eiyasushiki on 15/9/16.
 */
public class DrawableActor extends Actor {
    Texture texture;
    int X, Y;

    public DrawableActor(Texture texture, int X, int Y) {
        this.texture = texture;
        this.X = X; this.Y = Y;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(texture, X, Y);
    }
}
