import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.sort;

public class Brute {
    private List<BrutePoint> points = new ArrayList<BrutePoint>(1000);

    private void brute(String inputFilePath) {
        readData(inputFilePath);
        sort(points);

        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {
                for (int k = j + 1; k < points.size(); k++) {
                    for (int l = k + 1; l < points.size(); l++) {
                        BrutePoint p = points.get(i);
                        BrutePoint q = points.get(j);
                        BrutePoint r = points.get(k);
                        BrutePoint s = points.get(l);

                        if (arePointsCollinear(p, q, r, s)) {
                            drawLines(p, q, r, s);
                            print(p, q, r, s);
                        }
                    }
                }
            }
        }
    }

    private void readData(String arg) {
        In in = new In(arg);
        int nbOfPoints = in.readInt();

        while (nbOfPoints > 0) {
            BrutePoint p = read(in);
            p.draw();
            points.add(p);
            nbOfPoints--;
        }
    }

    private BrutePoint read(In in) {
        return new BrutePoint(in.readInt(), in.readInt());
    }

    private boolean arePointsCollinear(BrutePoint p, BrutePoint q, BrutePoint r, BrutePoint s) {
        return p.slopeTo(q) == p.slopeTo(r) && p.slopeTo(r) == p.slopeTo(s);
    }

    private void drawLines(BrutePoint p, BrutePoint q, BrutePoint r, BrutePoint s) {
        p.drawTo(q);
        q.drawTo(r);
        r.drawTo(s);
    }

    private void print(BrutePoint p, BrutePoint q, BrutePoint r, BrutePoint s) {
        System.out.println(p + " -> " + q + " -> " + r + " -> " + s);
    }

    public static void main(String[] args) {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);

        new Brute().brute(args[0]);
    }
}

class BrutePoint implements Comparable<BrutePoint> {
    private final int x;
    private final int y;

    public BrutePoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(BrutePoint that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(BrutePoint that) {
        if (compareTo(that) == 0) {
            return Double.NEGATIVE_INFINITY;
        }
        if (isVerticalPoint(that)) {
            return Double.POSITIVE_INFINITY;
        }
        return ((double) (that.y - this.y)) / ((double) (that.x - this.x));
    }

    private boolean isVerticalPoint(BrutePoint that) {
        return that.x - this.x == 0;
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(BrutePoint that) {
        if (this.y < that.y)
            return -1;
        else if (this.y > that.y)
            return 1;
        else
            return this.x - that.x;
    }

    // return string representation of this point
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}



