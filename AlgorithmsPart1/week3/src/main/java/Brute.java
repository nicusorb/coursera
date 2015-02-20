import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Brute {
    private List<Point> points = new ArrayList<>();

    private void brute(String inputFilePath) {
        readData(inputFilePath);
        Collections.sort(points);

        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {
                for (int k = j + 1; k < points.size(); k++) {
                    for (int l = k + 1; l < points.size(); l++) {
                        Point p = points.get(i);
                        Point q = points.get(j);
                        Point r = points.get(k);
                        Point s = points.get(l);

                        if (arePointsCollinear(p, q, r, s)) {
                            drawLines(Arrays.asList(p, q, r, s));
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
            Point p = read(in);
            p.draw();
            points.add(p);
            nbOfPoints--;
        }
    }

    private Point read(In in) {
        return new Point(in.readInt(), in.readInt());
    }

    private boolean arePointsCollinear(Point p, Point q, Point r, Point s) {
        return p.slopeTo(q) == p.slopeTo(r) && p.slopeTo(r) == p.slopeTo(s);
    }

    private void drawLines(List<Point> linePoints) {
        Collections.sort(linePoints);

        linePoints.get(0).drawTo(linePoints.get(linePoints.size() - 1));
    }

    private void print(Point p, Point q, Point r, Point s) {
        System.out.println(p + " -> " + q + " -> " + r + " -> " + s);
    }

    public static void main(String[] args) {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);

        new Brute().brute(args[0]);
    }
}
