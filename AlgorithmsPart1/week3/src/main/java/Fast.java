
import java.util.*;

import static java.util.Collections.sort;

public class Fast {
    private List<FastPoint> points = new ArrayList<FastPoint>(2000);
    private Set<EqualFastPoints> equalFastPointsSet = new LinkedHashSet<EqualFastPoints>();

    private void fast(String inputFilePath) {
        readData(inputFilePath);

        for (int i = 0; i < points.size(); i++) {
            FastPoint p = points.get(i);
            sort(points, p.SLOPE_ORDER);

            for (int j = 0; j < points.size(); ) {
                if (points.get(j) == p) {
                    j++;
                    continue;
                }
                List<FastPoint> equalFastPoints = new LinkedList<FastPoint>();
                equalFastPoints.add(p);
                double slope = p.slopeTo(points.get(j));

                while (j < points.size() && slope == p.slopeTo(points.get(j))) {
                    equalFastPoints.add(points.get(j));
                    j++;
                }

                if (moreThan3CollinearPointsFound(equalFastPoints)) {
                    saveOrderedCollinearPoints(equalFastPoints);
                }
            }
        }

        drawAndPrintCollinearPoints();
    }

    private void readData(String arg) {
        In in = new In(arg);
        int nbOfPoints = in.readInt();

        while (nbOfPoints > 0) {
            FastPoint p = read(in);
            p.draw();
            points.add(p);
            nbOfPoints--;
        }
    }

    private FastPoint read(In in) {
        return new FastPoint(in.readInt(), in.readInt());
    }

    private boolean moreThan3CollinearPointsFound(List<FastPoint> equalFastPoints) {
        return equalFastPoints.size() > 3;
    }

    private void saveOrderedCollinearPoints(List<FastPoint> equalFastPoints) {
        sort(equalFastPoints);
        equalFastPointsSet.add(new EqualFastPoints(equalFastPoints));
    }

    private void drawAndPrintCollinearPoints() {
        for (EqualFastPoints equalFastPoints : equalFastPointsSet) {
            drawLines(equalFastPoints.points);
            print(equalFastPoints.points);
        }
    }

    private void drawLines(List<FastPoint> points) {
        for (int i = 0; i < points.size() - 1; i++) {
            points.get(i).drawTo(points.get(i + 1));
        }
    }

    private void print(List<FastPoint> points) {
        for (int i = 0; i < points.size(); i++) {
            if (i == points.size() - 1) {
                System.out.println(points.get(i));
            } else {
                System.out.print(points.get(i) + " -> ");
            }
        }
    }

    private class EqualFastPoints {
        private List<FastPoint> points;

        private EqualFastPoints(List<FastPoint> points) {
            this.points = points;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            EqualFastPoints that = (EqualFastPoints) o;

            return points.equals(that.points);
        }

        @Override
        public int hashCode() {
            return points.hashCode();
        }
    }

    public static void main(String[] args) {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);

        new Fast().fast(args[0]);
    }
}

class FastPoint implements Comparable<FastPoint> {
    // compare points by slope
    public final Comparator<FastPoint> SLOPE_ORDER = new Comparator<FastPoint>() {
        @Override
        public int compare(FastPoint p1, FastPoint p2) {
            double p1Slope = slopeTo(p1);
            double p2Slope = slopeTo(p2);
            if (p1Slope < p2Slope)
                return -1;
            else if (p1Slope > p2Slope)
                return 1;
            return 0;
        }
    };

    private final int x;
    private final int y;

    public FastPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(FastPoint that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(FastPoint that) {
        if (compareTo(that) == 0) {
            return Double.NEGATIVE_INFINITY;
        }
        if (isVerticalPoint(that)) {
            return Double.POSITIVE_INFINITY;
        }
        return ((double) (that.y - this.y)) / ((double) (that.x - this.x));
    }

    private boolean isVerticalPoint(FastPoint that) {
        return that.x - this.x == 0;
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    @Override
    public int compareTo(FastPoint that) {
        if (this.y < that.y)
            return -1;
        else if (this.y > that.y)
            return 1;
        else
            return this.x - that.x;
    }

    // return string representation of this point
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}



