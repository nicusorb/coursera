import java.util.*;

public class FastCollinearPoints {
    private List<LineSegment> segments = new ArrayList<>();

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] pointsArg) {
        Point[] points = Arrays.copyOf(pointsArg, pointsArg.length);

        checkForNullArguments(points);
        Arrays.sort(points);
        checkForDuplicatedArguments(points);

        Point[] orderedPoints = Arrays.copyOf(points, points.length);

        for (Point point : points) {
            Arrays.sort(orderedPoints, point.slopeOrder());

            for (int k = 0; k < orderedPoints.length - 1; ) {
                if (arePointsOnTheSameSegment(point, orderedPoints[k], orderedPoints[k + 1])) {
                    List<Point> pointsOnTheSameSegment = new LinkedList<>();
                    pointsOnTheSameSegment.add(point);
                    pointsOnTheSameSegment.add(orderedPoints[k]);
                    pointsOnTheSameSegment.add(orderedPoints[k + 1]);

                    double slope = point.slopeTo(orderedPoints[k]);
                    k += 2;
                    while ((k < orderedPoints.length) && (slope == point.slopeTo(orderedPoints[k]))) {
                        pointsOnTheSameSegment.add(orderedPoints[k]);
                        k++;
                    }

                    if (validSegmentFound(pointsOnTheSameSegment)) {
                        Point firstPointOfTheSegment = pointsOnTheSameSegment.get(0);
                        Point lastPointOfTheSegment = pointsOnTheSameSegment.get(pointsOnTheSameSegment.size() - 1);
                        segments.add(new LineSegment(firstPointOfTheSegment, lastPointOfTheSegment));
                    }
                } else {
                    k++;
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[segments.size()]);
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

    private boolean arePointsOnTheSameSegment(Point point, Point p1, Point p2) {
        return point.slopeTo(p1) == point.slopeTo(p2);
    }

    private boolean validSegmentFound(List<Point> collinearPoints) {
        if (collinearPoints.size() < 4)
            return false;
        Point origin = collinearPoints.get(0);
        Collections.sort(collinearPoints);
        if (origin == collinearPoints.get(0))
            return true;
        return false;
    }
}