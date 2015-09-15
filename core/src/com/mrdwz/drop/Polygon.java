package com.mrdwz.drop;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.mrdwz.drop.voronoi.GraphEdge;
import com.mrdwz.drop.voronoi.Point;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by eiyasushiki on 15/9/12.
 */
public class Polygon extends Actor{
    public List<GraphEdge> edges, _edges;
    public Point site;
    public double width, height;
    public int index;
    private Pixmap pixmap;
    TextureRegion region;

    public Polygon(List<GraphEdge> edges, Point site, int index) {
        this.edges = edges;
        this.site = site;
        this.index = index;

        setTouchable(Touchable.enabled);
        double minX = 1000000.0, minY = 100000.0;
        for (GraphEdge e1 : edges) {
            minX = Math.min(Math.min(e1.x1, e1.x2), minX);
            minY = Math.min(Math.min(e1.y1, e1.y2), minY);
            for (GraphEdge e2: edges) {
                if (Math.abs(e1.x1 - e2.x1) > width) width = Math.abs(e1.x1 - e2.x1);
                if (Math.abs(e1.x2 - e2.x1) > width) width = Math.abs(e1.x2 - e2.x1);
                if (Math.abs(e1.y1 - e2.y1) > height) height = Math.abs(e1.y1 - e2.y1);
                if (Math.abs(e1.y1 - e2.y2) > height) height = Math.abs(e1.y1 - e2.y2);
            }
        }
        setSize((int)width, (int)height);
        site.x -=  minX; site.y -= minY;
        _edges = new LinkedList<>(edges);
        for(int i = 0; i < _edges.size(); i++) {
            GraphEdge e1 = _edges.get(i);
            e1 = new GraphEdge(e1.x1, e1.y1, e1.x2, e1.y2);
            e1.x1 -= minX; e1.x2 -= minX;
            e1.y1 -= minY; e1.y2 -= minY;
            _edges.set(i, e1);
        }
        pixmap = new Pixmap((int)width + 1, (int)height+1, Pixmap.Format.RGBA8888);
        OrthographicCamera camera = new OrthographicCamera();
        camera.position.set(400, 400, 0);
        pixmap.setColor(Color.WHITE);
        for (GraphEdge e: _edges) {
            pixmap.drawLine((int) e.x1, (int) e.y1, (int) e.x2, (int) e.y2);
        }
        pixmap.drawCircle((int)site.x, (int)site.y, 3);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        region = new TextureRegion(new Texture(pixmap));
        region.setRegionWidth((int)width+1);
        region.setRegionHeight((int)height+1);
        batch.draw(region, this.getX(), this.getY());
    }
}
