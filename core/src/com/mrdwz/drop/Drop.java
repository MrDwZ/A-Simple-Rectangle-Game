package com.mrdwz.drop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mrdwz.drop.voronoi.GraphEdge;
import com.mrdwz.drop.voronoi.Point;
import com.mrdwz.drop.voronoi.Voronoi;

import java.util.List;

class polygonsDragListener extends DragListener {
    private Polygon poly;
    float X, Y, dx, dy;

    public polygonsDragListener(Polygon p) {
        this.poly = p;
    }

    @Override
    public void dragStart(InputEvent event, float x, float y, int pointer) {
        X = x;
        Y = y;
    }

    @Override
    public void drag(InputEvent event, float x, float y, int pointer) {
        Point site = Drop.sites[poly.index];
        Point tmp1 = new Point(Drop.initRect.getX() + site.x,
                Drop.initRect.getY() + Drop.initRectHeight - site.y);
        Point tmp = new Point(poly.site.x + poly.getX(), poly.getY() + poly.height - poly.site.y);

        setTapSquareSize(0);
        poly.moveBy(x - X, y - Y);
    }

    @Override
    public void dragStop(InputEvent event, float x, float y, int pointer) {
        Point site = Drop.sites[poly.index];
        Point tmp1 = new Point(Drop.initRect.getX() + site.x,
                Drop.initRect.getY() + Drop.initRectHeight - site.y);
        Point tmp = new Point(poly.site.x + poly.getX(), poly.getY() + poly.height - poly.site.y);

        if (tmp.dist(tmp1) <= 20) {
            double dx = tmp1.x - tmp.x;
            double dy = tmp1.y - tmp.y;
            poly.setPosition((int) (poly.getX() + dx), (int) (dy + poly.getY()));
            Drop.done++;
            if (Drop.done == Drop.polygonNum) System.out.println("Congratulaition!");
        }
    }
}

public class Drop extends ApplicationAdapter {
    private Stage stage;
    int pointNum = 0;
    int maxPolygonNum = 500;
    static int polygonNum;
    static Point[] sites;
    static int initRectWidth, initRectHeight;
    static InitRect initRect;
    static int done;
    @Override
    public void create() {
        int windowWidth = Gdx.graphics.getWidth(), windowHeight = Gdx.graphics.getHeight();
        initRectWidth = windowWidth / 2; initRectHeight = windowHeight / 4;

        polygonNum = 5;
        done = 0;

        double[] X = new double[polygonNum];
        double[] Y = new double[polygonNum];
        for (int i = 0; i < polygonNum; i++) {
            X[i] = initRectWidth  * Math.random(); Y[i] =  initRectHeight / 2 * Math.random();
        }
        Voronoi graphic = new Voronoi(0);
        List<GraphEdge> edges = graphic.generateVoronoi(X, Y, 0, initRectWidth, 0, initRectHeight);

        Point[] ps= new Point[polygonNum];
        for (int i = 0; i < polygonNum; i++)
            ps[i] = new Point(X[i], Y[i]);
        sites = new Point[ps.length];
        for (int i = 0; i < ps.length; i++)
            sites[i] = new Point(ps[i].x, ps[i].y);



        stage = new Stage(new StretchViewport(windowWidth, windowHeight));
        initRect = new InitRect(initRectWidth, initRectHeight);
        initRect.setSitePoints(sites);
        initRect.setSplitEdge(edges);

        stage.addActor(new BackGround());
        stage.addActor(initRect);

        List<List<GraphEdge>> polygons = initRect.polygons;
        for (int i = 0; i < polygonNum; i++) {
            Polygon polygonTest = new Polygon(polygons.get(i), ps[i], i);
            polygonTest.setPosition((float)(windowWidth * Math.random()), (float)(windowHeight / 2 * Math.random()));
            polygonTest.addListener(new polygonsDragListener(polygonTest));
            stage.addActor(polygonTest);
        }

        Gdx.input.setInputProcessor(stage);

    }

    PolygonSprite poly;

    @Override
    public void render() {
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void dispose() {
    }

}
