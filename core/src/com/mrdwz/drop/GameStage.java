package com.mrdwz.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mrdwz.drop.voronoi.GraphEdge;
import com.mrdwz.drop.voronoi.Point;
import com.mrdwz.drop.voronoi.Voronoi;

import java.util.List;

/**
 * Created by eiyasushiki on 15/9/16.
 */
public class GameStage extends Stage{
    int pointNum = 0;
    int maxPolygonNum = 500;
    static int polygonNum;
    static Point[] sites;
    static int initRectWidth, initRectHeight;
    static InitRect initRect;
    static int done;

    public GameStage(int pn) {
        super(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        int windowWidth = Gdx.graphics.getWidth(), windowHeight = Gdx.graphics.getHeight();
        this.addActor(new BackGround());


        initRectWidth = windowWidth / 2; initRectHeight = windowHeight / 4;

        polygonNum = pn;
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



        initRect = new InitRect(initRectWidth, initRectHeight);
        initRect.setSitePoints(sites);
        initRect.setSplitEdge(edges);

        this.addActor(new BackGround());
        this.addActor(initRect);

        List<List<GraphEdge>> polygons = initRect.polygons;
        for (int i = 0; i < polygonNum; i++) {
            Polygon polygonTest = new Polygon(polygons.get(i), ps[i], i);
            polygonTest.setPosition((float)(windowWidth * Math.random()), (float)(windowHeight / 2 * Math.random()));
            polygonTest.addListener(new PolygonDragListener(polygonTest));
            this.addActor(polygonTest);
        }
        addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (Drop.gameEnded) {
                    Drop.gameEnded = false;
                    Drop.gameStarted = false;
                    System.out.println("Key Pressed!");
                }
                return super.keyDown(event, keycode);
            }
        });
    }
}
