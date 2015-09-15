package com.mrdwz.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mrdwz.drop.voronoi.*;
import com.mrdwz.drop.voronoi.Point;

import java.util.LinkedList;
import java.util.List;


/**
 * Created by eiyasushiki on 15/9/8.
 */
public class InitRect extends Actor {
    private GraphEdge[] borderEdges;
    private List<GraphEdge> spliteEdge;
    private OrthographicCamera camera = new OrthographicCamera();
    private Pixmap pixmap;
    private TextureRegion region;
    private int windowWidth = Gdx.graphics.getWidth(), windowHeight = Gdx.graphics.getHeight();

    public List<List<GraphEdge>> polygons;
    public Point[] sites;
    public int positionX, positionY;
    public int width, height;

    private List<List<GraphEdge> > getPolygons(List<GraphEdge> edges) {
        List<List<GraphEdge>> ret = new LinkedList<>();
        List<Point> ps = new LinkedList<>();

        for (GraphEdge e : edges) {
            Point p = new Point(e.x1, e.y1);
            if (!ps.contains(p))  ps.add(p);
            p = new Point(e.x2, e.y2);
            if (!ps.contains(p))  ps.add(p);
        }
        if (!ps.contains(new Point(0, 0))) ps.add(new Point(0,0));
        if (!ps.contains(new Point(0, height))) ps.add(new Point(0,height));
        if (!ps.contains(new Point(width, 0))) ps.add(new Point(width,0));
        if (!ps.contains(new Point(width, height))) ps.add(new Point(width,height));

        List<Point> ch = MathUtil.getConvexHull(ps.toArray(new Point[ps.size()]));

        borderEdges = new GraphEdge[ch.size()];
        for (int i = 1; i < borderEdges.length; i++)
            borderEdges[i-1] = new GraphEdge(ch.get(i-1).x, ch.get(i-1).y,
                                             ch.get(i).x, ch.get(i).y);
        borderEdges[ch.size() - 1] = new GraphEdge(ch.get(ch.size() - 1).x, ch.get(ch.size() -1).y,
                ch.get(0).x, ch.get(0).y);

        for (int i = 0; i < sites.length; i++) {
            List<GraphEdge> tmp = new LinkedList<>();
            for (GraphEdge e : spliteEdge)
                if ((e.site1 == i || e.site2 == i) && e.length() >= 1.0) tmp.add(e);
            ret.add(tmp);
        }

        int[] mark = new int[borderEdges.length];
        for (int j = 0; j < borderEdges.length; j++) {
            GraphEdge e = borderEdges[j];
            Point px = new Point((e.x2 + e.x1) / 2, (e.y2 + e.y1) / 2);
            double min1 = 1000000;

            for (int i = 0; i < sites.length; i++) {
                Point p = sites[i];
                double tmp1 = (px.x - p.x) * (px.x - p.x) + (px.y - p.y) * (px.y - p.y);
                if (tmp1 < min1) {
                    min1 = tmp1;
                    mark[j] = i;
                }
            }
        }
        for (int i = 0; i < borderEdges.length; i++)
            if (borderEdges[i].length() >= 1.0)ret.get(mark[i]).add(borderEdges[i]);
        for (GraphEdge e : borderEdges)
            pixmap.drawLine((int)e.x1 , (int)e.y1 , (int)e.x2 , (int)e.y2);
        return ret;
    }

    public InitRect (int width, int height) {
        this.width = width; this.height = height;
        setWidth(width); setHeight(height);
        camera.position.set(windowWidth / 2, windowHeight / 2, 0);
        positionX = windowWidth / 2 - width / 2;
        positionY = windowHeight / 2 + height / 2;
        setX(positionX);
        setY(positionY);
        pixmap = new Pixmap(width+1, height+1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
    }

    public void setSplitEdge(List<GraphEdge> edges) {
        this.spliteEdge = edges;
        polygons = getPolygons(spliteEdge);
    }


    public void setSitePoints(Point[] ps) {
        this.sites = ps;
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        region = new TextureRegion(new Texture(pixmap));

        region.setRegionHeight(height+1);
        region.setRegionWidth(width+1);
        batch.draw(region,  positionX, positionY);
    }
}
