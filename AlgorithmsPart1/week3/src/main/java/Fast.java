
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Fast {
    private List<Point> points = new ArrayList<>();
    private Set<CollinearPoints> collinearPointsSet = new LinkedHashSet<>();

    private void fast(String inputFilePath) {
        readData(inputFilePath);

        for (Point point : points) {
            List<Point> orderedPoints = new ArrayList<>(points);
            orderedPoints.remove(point);
            Collections.sort(orderedPoints, point.SLOPE_ORDER);

            for (int k = 0; k < orderedPoints.size(); k++) {
                List<Point> pointsOnTheSameSegment = new LinkedList<>();
                pointsOnTheSameSegment.add(point);
                pointsOnTheSameSegment.add(orderedPoints.get(k));

                while (k < orderedPoints.size() - 1 && point.slopeTo(orderedPoints.get(k)) == point.slopeTo(orderedPoints.get(k + 1))) {
                    pointsOnTheSameSegment.add(orderedPoints.get(k + 1));
                    k++;
                }
                if (moreThan3CollinearPointsFound(pointsOnTheSameSegment)) {
                    saveOrderedCollinearPoints(pointsOnTheSameSegment);
                }
            }

        }

        drawAndPrintCollinearPoints();
    }

    private void readData(String arg) {
        In in = new In(arg);
        int nbOfPoints = in.readInt();

        while (nbOfPoints > 0) {
            Point p = read(in);
            p.draw();
            points.add(p);
            nbOfPoints--;
        }
    }

    private Point read(In in) {
        return new Point(in.readInt(), in.readInt());
    }

    private boolean moreThan3CollinearPointsFound(List<Point> collinearPoints) {
        return collinearPoints.size() > 3;
    }

    private void saveOrderedCollinearPoints(List<Point> collinearPoints) {
        Collections.sort(collinearPoints);
        collinearPointsSet.add(new CollinearPoints(collinearPoints));
    }

    private void drawAndPrintCollinearPoints() {
        for (CollinearPoints collinearPoints : collinearPointsSet) {
            drawLines(collinearPoints.points);
            print(collinearPoints.points);
        }
    }

    private void drawLines(List<Point> linePoints) {
        Collections.sort(linePoints);

        linePoints.get(0).drawTo(linePoints.get(linePoints.size() - 1));
    }

    private void print(List<Point> collinearPoints) {
        for (int i = 0; i < collinearPoints.size(); i++) {
            if (i == collinearPoints.size() - 1) {
                System.out.println(collinearPoints.get(i));
            } else {
                System.out.print(collinearPoints.get(i) + " -> ");
            }
        }
    }

    private class CollinearPoints {
        private List<Point> points;

        private CollinearPoints(List<Point> points) {
            this.points = points;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            CollinearPoints that = (CollinearPoints) o;

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
