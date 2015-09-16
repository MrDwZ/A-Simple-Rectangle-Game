package com.mrdwz.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;

/**
 * Created by eiyasushiki on 15/9/16.
 */
public class StartStage extends Stage {
    public void addButton(Texture texture, int X, int Y, int width, int height, int num) {
        TextureRegion region = new TextureRegion(texture);
        TextureRegionDrawable tmp = new TextureRegionDrawable(region);
        ImageButton button = new ImageButton(tmp, tmp);
        button.setPosition(X, Y);
        button.setSize(width, height);
        button.setTouchable(Touchable.enabled);
        button.addListener(
            new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    Drop.gameStage = new GameStage(num);
                    Drop.gameStarted = true;
                    System.out.println("touched!");
                    return super.touchDown(event, x, y, pointer, button);
                }
            }
        );
        this.addActor(button);
    }

    public StartStage() {
        super(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        addActor(new BackGround());

        String titlePath = "Drop_0006_A-Simple-Rectangle-Game.png";
        Texture title = new Texture(Gdx.files.internal(titlePath));
        this.addActor(new DrawableActor(title, 105, Gdx.graphics.getHeight() - 235));
        titlePath = "Drop_0007_Difficuties.png";
        title = new Texture(Gdx.files.internal(titlePath));
        this.addActor(new DrawableActor(title, 272, Gdx.graphics.getHeight() - 407));

        Texture texture = new Texture(Gdx.files.internal("Drop_0004_Simple.png"));
        this.addButton(texture, 336, Gdx.graphics.getHeight() - 500, 75, 68, 5);
        texture = new Texture(Gdx.files.internal("Drop_0003_Normal.png"));
        this.addButton(texture, 332, Gdx.graphics.getHeight() - 520, 86, 32, 10);
        texture = new Texture(Gdx.files.internal("Drop_0002_Hard.png"));
        this.addButton(texture, 340, Gdx.graphics.getHeight() - 555, 70, 19, 30);
        texture = new Texture(Gdx.files.internal("Drop_0001_WTF.png"));
        this.addButton(texture, 340, Gdx.graphics.getHeight() - 595, 69, 20, 100);
    }
}
