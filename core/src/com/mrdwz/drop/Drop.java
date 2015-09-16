package com.mrdwz.drop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Drop extends ApplicationAdapter {
    public static Stage gameStage, startStage;
    public static int polygonNum;
    public static boolean gameStarted = false, gameEnded = false;

    @Override
    public void create() {
        startStage = new StartStage();
        Gdx.input.setInputProcessor(startStage);
    }

    PolygonSprite poly;

    @Override
    public void render() {
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        if (!gameStarted) {
            Gdx.input.setInputProcessor(startStage);
            startStage.act(Gdx.graphics.getDeltaTime());
            startStage.draw();
        } else {
            Gdx.input.setInputProcessor(gameStage);
            gameStage.act(Gdx.graphics.getDeltaTime());
            gameStage.draw();
        }
    }

    @Override
    public void dispose() {
        startStage.dispose();
        gameStage.dispose();
    }

}
