package com.mrdwz.drop;

import com.mrdwz.drop.voronoi.Point;

import java.util.*;

/**
 * Created by eiyasushiki on 15/9/10.
 */
public class MathUtil {
    static final double delta = 1e-10;

    public static  List<Point> getConvexHull(Point[] p) {
        Arrays.sort(p, (Point p1, Point p2)->p1.compare(p2));
        List<Point> ch = new LinkedList<>();

        int n = p.length;
        int m = 0;
        for (int i = 0; i < n; i++) {
            while (m > 1 && ch.get(m-1).minus(ch.get(m-2)).cross(p[i].minus(ch.get(m-2))) < 0.0) {m--; ch.remove(m); }
            ch.add(p[i]); m++;
        }

        int k = m;
        for (int i = n - 2; i >= 0; i--) {
            while (m > k && ch.get(m-1).minus(ch.get(m-2)).cross(p[i].minus(ch.get(m-2))) < 0.0) {m--; ch.remove(m); }
            ch.add(p[i]); m++;
        }

        if (n > 1) { ch.remove(m-1); m--; }
        return ch;
    }
}
