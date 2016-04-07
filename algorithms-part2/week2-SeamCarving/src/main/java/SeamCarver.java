import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.Stack;

import java.awt.*;

public class SeamCarver {
    private Picture picture;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null)
            throw new NullPointerException();
        this.picture = picture;
    }

    // current picture
    public Picture picture() {
        return picture;
    }

    // width of current picture
    public int width() {
        return picture.width();
    }

    // height of current picture
    public int height() {
        return picture.height();
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        validateColumnAndRow(x, y);
        if (pixelIsOnTheImageBorder(x, y))
            return 1000;

        double deltax = computeDelta(picture.get(x + 1, y), picture.get(x - 1, y));
        double deltay = computeDelta(picture.get(x, y + 1), picture.get(x, y - 1));

        return Math.sqrt(deltax + deltay);
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        return null;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        double[][] energy = new double[width()][height()];
        for (int i = 0; i < energy.length; i++) {
            for (int j = 0; j < energy[i].length; j++) {
                energy[i][j] = energy(i, j);
            }
        }

        double[][] relaxed = new double[width()][height()];
        for (int i = 0; i < relaxed.length; i++) {
            for (int j = 0; j < relaxed[i].length; j++) {
                relaxed[i][j] = Double.POSITIVE_INFINITY;
            }
        }

        double[] distTo = new double[width() * height()];
        Stack indexesToRelax = new Stack();
        indexesToRelax.push(0);


        while (!indexesToRelax.isEmpty()) {

        }


        return null;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
    }

    private void validateColumnAndRow(int x, int y) {
        if (x < 0 || x >= width())
            throw new IndexOutOfBoundsException();
        if (y < 0 || y >= height())
            throw new IndexOutOfBoundsException();
    }

    private boolean pixelIsOnTheImageBorder(int x, int y) {
        return x == 0 || x == width() - 1 || y == 0 || y == height() - 1;
    }

    private double computeDelta(Color nextPixelColor, Color previousPixelColor) {
        double rx = nextPixelColor.getRed() - previousPixelColor.getRed();
        double gx = nextPixelColor.getGreen() - previousPixelColor.getGreen();
        double bx = nextPixelColor.getBlue() - previousPixelColor.getBlue();

        return Math.pow(rx, 2) + Math.pow(gx, 2) + Math.pow(bx, 2);
    }
}