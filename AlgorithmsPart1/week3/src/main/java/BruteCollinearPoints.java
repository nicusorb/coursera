import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class BruteCollinearPoints {
    private List<LineSegment> lineSegments = new LinkedList<>();

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] pointsArg) {
        Point[] points = Arrays.copyOf(pointsArg, pointsArg.length);

        checkForNullArguments(points);
        Arrays.sort(points);
        checkForDuplicatedArguments(points);

        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                for (int k = j + 1; k < points.length; k++) {
                    for (int l = k + 1; l < points.length; l++) {
                        Point p = points[i];
                        Point q = points[j];
                        Point r = points[k];
                        Point s = points[l];

                        if (arePointsCollinear(p, q, r, s)) {
                            lineSegments.add(new LineSegment(p, s));
                        }
                    }
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return lineSegments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return lineSegments.toArray(new LineSegment[lineSegments.size()]);
    }

    private void checkForNullArguments(Point[] points) {
        if (points == null) {
            throw new NullPointerException();
        }
        for (Point point : points) {
            if (point == null)
                throw new NullPointerException();
        }
    }

    private void checkForDuplicatedArguments(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0)
                throw new IllegalArgumentException();
        }
    }

    private boolean arePointsCollinear(Point p, Point q, Point r, Point s) {
        return p.slopeTo(q) == p.slopeTo(r) && p.slopeTo(r) == p.slopeTo(s);
    }
}