import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class KdTree {
    private Node root;             // root of BST

    private class Node {
        private Point2D key;           // sorted by key
        private Node left, right;  // left and right subtrees
        private int N;             // number of nodes in subtree

        public Node(Point2D key, int N) {
            this.key = key;
            this.N = N;
        }
    }

    private class ColoredNode {
        Node node;
        boolean color;

        public ColoredNode(Node node, boolean color) {
            this.node = node;
            this.color = color;
        }
    }

    // is the symbol table empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return number of key-value pairs in BST
    public int size() {
        return size(root);
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

        Boolean exists = contains(root, p, Point2D.X_ORDER);
        return exists != null;
    }

    // draw all points to standard draw
    public void draw() {
        Queue<ColoredNode> queue = new Queue<>();
        queue.enqueue(new ColoredNode(root, true));

        while (!queue.isEmpty()) {
            ColoredNode coloredNode = queue.dequeue();
            if (coloredNode.node == null)
                continue;
            drawNode(coloredNode);
            queue.enqueue(new ColoredNode(coloredNode.node.left, !coloredNode.color));
            queue.enqueue(new ColoredNode(coloredNode.node.right, !coloredNode.color));
        }
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new NullPointerException();
        List<Point2D> pointsInRectangle = new LinkedList<>();
        range(root, rect, Point2D.X_ORDER, pointsInRectangle);
        return pointsInRectangle;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        RectHV rectHV = new RectHV(0, 0, Double.MAX_VALUE, Double.MAX_VALUE);
        return nearest(root, p, root.key, rectHV, Point2D.X_ORDER);
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        KdTree pointSET = new KdTree();
        pointSET.insert(new Point2D(0.1, 0.1));

        pointSET.draw();
    }

    private Boolean contains(Node node, Point2D key, Comparator<Point2D> comparator) {
        if (node == null)
            return null;
        Comparator<Point2D> newComparator = switchComparator(comparator);
        int cmp = comparator.compare(key, node.key);
        if (cmp < 0)
            return contains(node.left, key, newComparator);
        else if (cmp > 0)
            return contains(node.right, key, newComparator);
        else
            return true;
    }

    private void put(Point2D key) {
        root = put(root, key, Point2D.X_ORDER);
    }

    private Node put(Node node, Point2D key, Comparator<Point2D> comparator) {
        if (node == null)
            return new Node(key, 1);
        Comparator<Point2D> newComparator = switchComparator(comparator);
        int cmp = comparator.compare(key, node.key);
        if (cmp < 0)
            node.left = put(node.left, key, newComparator);
        else if (cmp >= 0)
            node.right = put(node.right, key, newComparator);
        node.N = 1 + size(node.left) + size(node.right);
        return node;
    }

    private int size(Node node) {
        if (node == null)
            return 0;
        else
            return node.N;
    }

    private void drawNode(ColoredNode coloredNode) {
        StdDraw.setPenRadius(0.03);
        if (shouldBeRed(coloredNode.color))
            setPenRed();
        else
            setPenBlue();
        StdDraw.point(coloredNode.node.key.x(), coloredNode.node.key.y());
    }

    private boolean shouldBeRed(boolean color) {
        return color == true;
    }

    private void setPenRed() {
        StdDraw.setPenColor(255, 0, 0);
    }

    private void setPenBlue() {
        StdDraw.setPenColor(0, 0, 255);
    }

    private void range(Node node, RectHV rect, Comparator<Point2D> comparator, List<Point2D> points) {
        Comparator<Point2D> newComparator = switchComparator(comparator);
        if (node == null)
            return;
        if (rect.contains(node.key)) {
            points.add(node.key);
            searchBothChildren(node, rect, points, newComparator);
        } else if (comparator.equals(Point2D.X_ORDER)) {
            if (rect.xmin() < node.key.x() && node.key.x() < rect.xmax()) {
                searchBothChildren(node, rect, points, newComparator);
            } else if (rect.xmax() < node.key.x()) {
                range(node.left, rect, newComparator, points);
            } else if (rect.xmin() > node.key.x()) {
                range(node.right, rect, newComparator, points);
            }
        } else if (comparator.equals(Point2D.Y_ORDER)) {
            if (rect.ymin() < node.key.y() && node.key.y() < rect.ymax()) {
                searchBothChildren(node, rect, points, newComparator);
            } else if (rect.ymax() < node.key.y()) {
                range(node.left, rect, newComparator, points);
            } else if (rect.ymin() > node.key.y()) {
                range(node.right, rect, newComparator, points);
            }
        }
    }

    private Comparator<Point2D> switchComparator(Comparator<Point2D> comparator) {
        Comparator<Point2D> newComparator;
        if (comparator == Point2D.X_ORDER)
            newComparator = Point2D.Y_ORDER;
        else
            newComparator = Point2D.X_ORDER;
        return newComparator;
    }

    private void searchBothChildren(Node node, RectHV rect, List<Point2D> points, Comparator<Point2D> newComparator) {
        range(node.left, rect, newComparator, points);
        range(node.right, rect, newComparator, points);
    }

    private Point2D nearest(Node node, Point2D p, Point2D currentNearest, RectHV rectHV, Comparator<Point2D> comparator) {
        if (node == null)
            return currentNearest;
        if (node.key.distanceTo(p) < currentNearest.distanceTo(p))
            currentNearest = node.key;

        if (comparator.equals(Point2D.X_ORDER)) {
            if (node.key.x() > p.x()) {
                RectHV leftRect = new RectHV(rectHV.xmin(), rectHV.ymin(), node.key.x(), rectHV.ymax());
                Point2D nearestLeft = nearest(node.left, p, currentNearest, leftRect, switchComparator(comparator));

                RectHV rightSubtreeRect = new RectHV(node.key.x(), rectHV.ymin(), rectHV.xmax(), rectHV.ymax());
                if (p.distanceTo(nearestLeft) <= rightSubtreeRect.distanceTo(p))
                    return nearestLeft;
                else {
                    //search right subtree
                    return nearest(node.right, p, nearestLeft, rightSubtreeRect, switchComparator(comparator));
                }
            } else {
                RectHV rightRect = new RectHV(node.key.x(), rectHV.ymin(), rectHV.xmax(), rectHV.ymax());
                Point2D nearestRight = nearest(node.right, p, currentNearest, rightRect, switchComparator(comparator));

                RectHV leftSubtreeRect = new RectHV(rectHV.xmin(), rectHV.ymin(), node.key.x(), rectHV.ymax());
                if (p.distanceTo(nearestRight) <= leftSubtreeRect.distanceTo(p))
                    return nearestRight;
                else {
                    //search left subtree
                    return nearest(node.left, p, nearestRight, leftSubtreeRect, switchComparator(comparator));
                }
            }
        } else { //Y_ORDER
            if (node.key.y() < p.y()) {
                RectHV leftRect = new RectHV(rectHV.xmin(), rectHV.ymin(), rectHV.xmax(), node.key.y());
                Point2D nearestLeft = nearest(node.left, p, currentNearest, leftRect, switchComparator(comparator));

                RectHV rightSubtreeRect = new RectHV(rectHV.xmin(), node.key.y(), rectHV.xmax(), rectHV.ymax());
                if (p.distanceTo(nearestLeft) <= rightSubtreeRect.distanceTo(p))
                    return nearestLeft;
                else {
                    //search right subtree
                    return nearest(node.right, p, nearestLeft, rightSubtreeRect, switchComparator(comparator));
                }
            } else {
                RectHV rightRect = new RectHV(rectHV.xmin(), node.key.y(), rectHV.xmax(), rectHV.ymax());
                Point2D nearestRight = nearest(node.right, p, currentNearest, rightRect, switchComparator(comparator));

                RectHV leftSubtreeRect = new RectHV(rectHV.xmin(), rectHV.ymin(), rectHV.xmax(), node.key.y());
                if (p.distanceTo(nearestRight) <= leftSubtreeRect.distanceTo(p))
                    return nearestRight;
                else {
                    //search left subtree
                    return nearest(node.left, p, nearestRight, leftSubtreeRect, switchComparator(comparator));
                }
            }
        }
    }
}