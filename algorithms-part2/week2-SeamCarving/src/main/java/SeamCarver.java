import edu.princeton.cs.algs4.Picture;

import java.awt.Color;

public class SeamCarver {
    private Picture picture;
    private double[][] energy;
    private boolean transposed;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null)
            throw new NullPointerException();
        this.picture = new Picture(picture);
        computeEnergyMatrix();
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
        double[][] distTo = new double[height()][width()];
        int[][] edgeTo = new int[height()][width()];

        transposed = true;
        return findVerticalSeam(distTo, edgeTo);
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        double[][] distTo = new double[width()][height()];
        int[][] edgeTo = new int[width()][height()];

        transposed = false;
        return findVerticalSeam(distTo, edgeTo);
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        transposeImage();
        removeVerticalSeam(seam);
        transposeImage();
        computeEnergyMatrix();
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        Picture newPicture = new Picture(width() - 1, height());
        for (int j = 0; j < height(); j++) {
            for (int i = 0; i < width() - 1; i++) {
                if (i >= seam[j])
                    newPicture.set(i, j, picture.get(i + 1, j));
                else
                    newPicture.set(i, j, picture.get(i, j));
            }
        }
        this.picture = newPicture;
        computeEnergyMatrix();
    }

    private void computeEnergyMatrix() {
        this.energy = new double[width()][height()];

        for (int i = 0; i < energy.length; i++) {
            for (int j = 0; j < energy[i].length; j++) {
                energy[i][j] = energy(i, j);
            }
        }
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

    private int[] findVerticalSeam(double[][] distTo, int[][] edgeTo) {
        init(distTo);
        relaxAllEdgesBasedOnEnergyLevel(distTo, edgeTo);

        return getMinimumEnergySeam(distTo, edgeTo);
    }

    private void init(double[][] distTo) {
        for (int i = 0; i < distTo.length; i++) {
            for (int j = 0; j < distTo[i].length; j++) {
                distTo[i][j] = Double.POSITIVE_INFINITY;
            }
        }
    }

    private void relaxAllEdgesBasedOnEnergyLevel(double[][] distTo, int[][] edgeTo) {
        final int width = distTo.length;
        final int height = distTo[0].length;

        for (int j = 0; j < width; j++) {
            distTo[j][0] = energyMatrix(j, 0);
        }

        for (int j = 0; j < height - 1; j++) {
            for (int i = 0; i < width; i++) {
                if ((i - 1 >= 0) && (distTo[i - 1][j + 1] > distTo[i][j] + energyMatrix(i - 1, j + 1))) {
                    distTo[i - 1][j + 1] = distTo[i][j] + energyMatrix(i - 1, j + 1);
                    edgeTo[i - 1][j + 1] = i;
                }
                if ((distTo[i][j + 1] > distTo[i][j] + energyMatrix(i, j + 1))) {
                    distTo[i][j + 1] = distTo[i][j] + energyMatrix(i, j + 1);
                    edgeTo[i][j + 1] = i;
                }
                if ((i + 1 < width) && (distTo[i + 1][j + 1] > distTo[i][j] + energyMatrix(i + 1, j + 1))) {
                    distTo[i + 1][j + 1] = distTo[i][j] + energyMatrix(i + 1, j + 1);
                    edgeTo[i + 1][j + 1] = i;
                }
            }
        }
    }

    private double energyMatrix(int row, int col) {
        return (transposed) ? energy[col][row] : energy[row][col];
    }

    private int[] getMinimumEnergySeam(double[][] distTo, int[][] edgeTo) {
        final int height = distTo[0].length;
        int minColumn = getColumnFromLastRowWithMinimumAccumulatedEnergy(distTo);

        int[] seam = new int[height];
        seam[height - 1] = minColumn;

        for (int j = height - 1; j > 0; j--) {
            minColumn = edgeTo[minColumn][j];
            seam[j - 1] = minColumn;
        }
        return seam;
    }

    private int getColumnFromLastRowWithMinimumAccumulatedEnergy(double[][] distTo) {
        double min = Double.POSITIVE_INFINITY;
        int minColumn = 0;
        int width = distTo.length;
        int height = distTo[0].length;

        for (int j = 0; j < width; j++) {
            if (min > distTo[j][height - 1]) {
                min = distTo[j][height - 1];
                minColumn = j;
            }
        }
        return minColumn;
    }

    private void transposeImage() {
        Picture newPicture = new Picture(height(), width());

        for (int i = 0; i < picture.width(); i++) {
            for (int j = 0; j < picture.height(); j++) {
                newPicture.set(j, i, picture.get(i, j));
            }
        }
        this.picture = newPicture;
    }
}