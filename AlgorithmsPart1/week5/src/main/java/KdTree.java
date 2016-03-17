import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.LinkedList;
import java.util.List;

public class KdTree {
    private Node root;
    private int nbOfNodes = 0;

    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree

        public Node(Point2D p) {
            this.p = p;
        }
    }

    // is the symbol table empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return number of key-value pairs in BST
    public int size() {
        return nbOfNodes;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null)
            throw new NullPointerException();
        put(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null)
            throw new NullPointerException();
        return get(p, true) != null;
    }

    private void put(Point2D key) {
        root = put(root, key, true);
        if (root.rect == null)
            root.rect = new RectHV(0, 0, 1, 1);
    }

    private Node put(Node node, Point2D p, boolean shouldUseXCoordinate) {
        if (node == null) {
            nbOfNodes++;
            return new Node(p);
        }
        if (node.p.equals(p))
            return node;
        if (shouldUseXCoordinate) {
            if (p.x() < node.p.x()) {
                node.lb = put(node.lb, p, !shouldUseXCoordinate);
                if (node.lb.rect == null) {
                    node.lb.rect = new RectHV(node.rect.xmin(), node.rect.ymin(), node.p.x(), node.rect.ymax());
                }
            } else {
                node.rt = put(node.rt, p, !shouldUseXCoordinate);
                if (node.rt.rect == null) {
                    node.rt.rect = new RectHV(node.p.x(), node.rect.ymin(), node.rect.xmax(), node.rect.ymax());
                }
            }
        } else {
            if (p.y() < node.p.y()) {
                node.lb = put(node.lb, p, !shouldUseXCoordinate);
                if (node.lb.rect == null) {
                    node.lb.rect = new RectHV(node.rect.xmin(), node.rect.ymin(), node.rect.xmax(), node.p.y());
                }
            } else {
                node.rt = put(node.rt, p, !shouldUseXCoordinate);
                if (node.rt.rect == null) {
                    node.rt.rect = new RectHV(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.rect.ymax());
                }
            }
        }

        return node;
    }

    private Node get(Point2D p, boolean shouldUseXCoordinate) {
        return get(root, p, shouldUseXCoordinate);
    }

    private Node get(Node node, Point2D p, boolean shouldUseXCoordinate) {
        if (node == null)
            return null;
        if (p.compareTo(node.p) == 0)
            return node;

        if (shouldUseXCoordinate) {
            if (p.x() < node.p.x()) {
                return get(node.lb, p, !shouldUseXCoordinate);
            } else {
                return get(node.rt, p, !shouldUseXCoordinate);
            }
        } else {
            if (p.y() < node.p.y()) {
                return get(node.lb, p, !shouldUseXCoordinate);
            } else {
                return get(node.rt, p, !shouldUseXCoordinate);
            }
        }
    }

    // draw all points to standard draw
    public void draw() {
        draw(this.root, true);
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new NullPointerException();
        List<Point2D> points = new LinkedList<>();

        range(root, rect, points);

        return points;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new NullPointerException("Null argument");
        if (isEmpty())
            return null;
        Point2D champion = root.p;

        return nearest(root, champion, p, true);
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        KdTree pointSET = new KdTree();
        pointSET.insert(new Point2D(0.1, 0.1));

        pointSET.draw();
    }

    private void draw(Node node, boolean shouldUseXCoordinate) {
        if (node == null)
            return;
        if (shouldUseXCoordinate) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(node.p.x(), node.rect.ymin(), node.p.x(), node.rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.p.y());
        }

        draw(node.lb, !shouldUseXCoordinate);
        draw(node.rt, !shouldUseXCoordinate);
    }

    private void range(Node node, RectHV rect, List<Point2D> points) {
        if (node == null)
            return;
        if (node.rect.intersects(rect)) {
            if (rect.contains(node.p)) {
                points.add(node.p);
            }
            range(node.lb, rect, points);
            range(node.rt, rect, points);
        }
    }

    private Point2D nearest(Node node, Point2D champion, Point2D p, boolean shouldUseXCoordinate) {
        if (node.p.distanceSquaredTo(p) < champion.distanceSquaredTo(p))
            champion = node.p;
        if (shouldUseXCoordinate) {
            if (node.p.x() > p.x()) {
                champion = searchForNearestFirstInLeftBottom(node, champion, p, !shouldUseXCoordinate);
            } else {
                champion = searchForNearestFirstInRightTop(node, champion, p, !shouldUseXCoordinate);
            }
        } else {
            if (node.p.y() > p.y()) {
                champion = searchForNearestFirstInLeftBottom(node, champion, p, !shouldUseXCoordinate);
            } else {
                champion = searchForNearestFirstInRightTop(node, champion, p, !shouldUseXCoordinate);
            }
        }
        return champion;
    }

    private Point2D searchForNearestFirstInLeftBottom(Node node, Point2D champion, Point2D p, boolean shouldUseXCoordinate) {
        if (node.lb != null) {
            champion = nearest(node.lb, champion, p, shouldUseXCoordinate);
        }
        if (node.rt != null && node.rt.rect.distanceSquaredTo(p) < champion.distanceSquaredTo(p)) {
            champion = nearest(node.rt, champion, p, shouldUseXCoordinate);
        }
        return champion;
    }

    private Point2D searchForNearestFirstInRightTop(Node node, Point2D champion, Point2D p, boolean shouldUseXCoordinate) {
        if (node.rt != null) {
            champion = nearest(node.rt, champion, p, shouldUseXCoordinate);
        }
        if (node.lb != null && node.lb.rect.distanceSquaredTo(p) < champion.distanceSquaredTo(p)) {
            champion = nearest(node.lb, champion, p, shouldUseXCoordinate);
        }
        return champion;
    }
}