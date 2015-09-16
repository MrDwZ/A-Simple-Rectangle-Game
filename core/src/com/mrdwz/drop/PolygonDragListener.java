package com.mrdwz.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.mrdwz.drop.voronoi.Point;

/**
 * Created by eiyasushiki on 15/9/16.
 */
public class PolygonDragListener extends DragListener {
    private Polygon poly;
    float X, Y, dx, dy;

    public PolygonDragListener(Polygon p) {
        this.poly = p;
    }

    @Override
    public void dragStart(InputEvent event, float x, float y, int pointer) {
        X = x;
        Y = y;
    }

    @Override
    public void drag(InputEvent event, float x, float y, int pointer) {
        setTapSquareSize(0);
        poly.moveBy(x - X, y - Y);
    }

    @Override
    public void dragStop(InputEvent event, float x, float y, int pointer) {
        Point site = GameStage.sites[poly.index];
        Point tmp1 = new Point(GameStage.initRect.getX() + site.x,
                GameStage.initRect.getY() + GameStage.initRectHeight - site.y);
        Point tmp = new Point(poly.site.x + poly.getX(), poly.getY() + poly.height - poly.site.y);

        if (tmp.dist(tmp1) <= 20) {
            double dx = tmp1.x - tmp.x;
            double dy = tmp1.y - tmp.y;
            poly.setPosition((int) (poly.getX() + dx), (int) (dy + poly.getY()));
            poly.removeListener(this);
            GameStage.done++;
            if (GameStage.done == GameStage.polygonNum) {
                Texture title = new Texture(Gdx.files.internal("Drop_0001_Congratulations-_).png"));
                Drop.gameStage.addActor(new DrawableActor(title, 164, Gdx.graphics.getHeight() - 540));
                title = new Texture(Gdx.files.internal("Drop_0000_Press-any-key-to-continue.png"));
                Drop.gameStage.addActor(new DrawableActor(title, 227, Gdx.graphics.getHeight() - 569));
                Drop.gameEnded = true;
            }
        }
    }
}

