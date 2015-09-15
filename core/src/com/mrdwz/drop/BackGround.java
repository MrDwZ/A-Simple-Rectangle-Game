package com.mrdwz.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by eiyasushiki on 15/9/15.
 */
public class BackGround extends Actor {
    Texture texture;
    String pic = "fabric8.jpg";

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        texture = new Texture(Gdx.files.internal(pic));
        for (int i = 0; i <= 1; i++)
        batch.draw(texture, 0, i * texture.getHeight());
    }
}
