import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.awt.*;
import java.util.Arrays;

public class SeamCarver {
    private Picture pic;
    private double[][] energy;
    private double[][] transposed;
    // Picture tpic;

    public SeamCarver(Picture picture) {
        pic = picture;
        // tpic = transpose(pic);
        initialize();
    }

    private void initialize() {
        energy = new double[width()][height()];
        transposed = new double[height()][width()];
        for (int i = 0; i < width(); i++) {
            for (int j = 0; j < height(); j++) {
                energy[i][j] = energy(i, j);
                transposed[j][i] = energy[i][j];
            }
        }
    }

    public Picture picture() {
        return new Picture(pic);
        // return pic;
    }

    // width of the current picture
    public int width() {
       return pic.width();
    }

    // height of the current picture
    public int height() {
        return pic.height();
    }

    private boolean checkinBound(int x, int y) {
        if (x < 0 || x > width() - 1) {
            return false;
        }
        if (y < 0 || y > height() - 1) {
            return false;
        }
        return true;
    }

    // calculate the square of the x-gradient
    private int xgradsqu(Picture picture, int x, int y) {
        Color prev = x > 0 ? picture.get(x-1,y) : picture.get(width()-1, y);
        // int RGB = pic.getRGB(x, y);
        int pr = prev.getRed();
        int pg = prev.getGreen();
        int pb = prev.getBlue();
        Color next = x < width() - 1 ? picture.get(x+1, y) : picture.get(0, y);
        int nr = next.getRed();
        int ng = next.getGreen();
        int nb = next.getBlue();
        return (pr-nr)*(pr-nr) + (pg-ng)*(pg-ng) + (pb-nb)*(pb-nb);
    }

    // transpose the pic; to be finished;
    /**
    private Picture transpose(Picture picture) {
        Picture tpic = new Picture(height(), width());
        for (int i = 0; i < height(); i++) {
            for (int j = 0; j < width(); j++) {
                tpic.setRGB(i, j, picture.getRGB(j, i));
            }
        }
        return tpic;
    }
     */

    // return the square of the y gradient, exploring the idea of transposing followed by xgradsqu()
    /**
    private int ygradsquTr(int x, int y) {
        return xgradsqu(tpic, y, x);
    }
     */

    // return the square of the y gradient, by modifying the x gradient codes
    private int ygradsqu(Picture picture, int x, int y) {
        Color prev = y > 0 ? picture.get(x,y-1) : picture.get(x, height()-1);
        int pr = prev.getRed();
        int pg = prev.getGreen();
        int pb = prev.getBlue();
        Color next = y < height() - 1 ? picture.get(x, y+1) : picture.get(x, 0);
        int nr = next.getRed();
        int ng = next.getGreen();
        int nb = next.getBlue();
        return (pr-nr)*(pr-nr) + (pg-ng)*(pg-ng) + (pb-nb)*(pb-nb);
    }

    public double energy(int x, int y) {
        if (checkinBound(x,y) == false) {
            throw new IndexOutOfBoundsException();
        }
        return xgradsqu(pic, x, y) + ygradsqu(pic, x, y);
     }

     // subroutine to find vertical minimal cost path ending at pixel(x,y)
     // to to completed: this algorithm is rather verbose
    private void VedgeTo(int x, int y, double[][] eng, double[][] vmcp, int[][] vpointer) {
        if (y == 0) { // cornercase: top row of picture
            vmcp[x][y] = eng[x][y];
        } else {
            if (x==0) {
                if (eng.length == 1) { // cornercase: only one x of the picture (pic width of 1)
                    vmcp[x][y] = vmcp[x][y-1] + eng[x][y];
                } else if (vmcp[x+1][y-1]<vmcp[x][y-1]) {
                    vpointer[x][y] = 1;
                    vmcp[x][y] = vmcp[x+1][y-1] + eng[x][y];
                } else {
                    vmcp[x][y] = vmcp[x][y-1] + eng[x][y];
                }
            } else if (x == eng.length-1){
                if (vmcp[x-1][y-1]<=vmcp[x][y-1]) {
                    vpointer[x][y] = -1;
                    vmcp[x][y] = vmcp[x-1][y-1] + eng[x][y];
                } else {
                    vmcp[x][y] = vmcp[x][y-1] + eng[x][y];
                }
            } else if (vmcp[x-1][y-1]<=vmcp[x][y-1] && vmcp[x-1][y-1]<=vmcp[x+1][y-1]) {
                // <= to avoid the cornercase of vmcp[x-1][y-1] = vmcp[x+1][y-1] < vmcp[x][y-1]
                vpointer[x][y] = -1;
                vmcp[x][y] = vmcp[x-1][y-1] + eng[x][y];
            } else if (vmcp[x+1][y-1]<vmcp[x][y-1] && vmcp[x+1][y-1]<vmcp[x-1][y-1]) {
                vpointer[x][y] = 1;
                vmcp[x][y] = vmcp[x+1][y-1] + eng[x][y];
            } else {
                vmcp[x][y] = vmcp[x][y-1] + eng[x][y];
            }
        }
    }

    private int[] findVerticalSeam(double[][] energies) {
        double mcpbest = Double.POSITIVE_INFINITY;
        Stack<Integer> output = new Stack<>();
        int pointer = 0;
        int width = energies.length; // x: column number; y: row number
        int height = energies[0].length;
        double[][] vmcp = new double[width][height];
        // vmcp is a matrix tracking the cost of vertical minimum cost path *ending* at (i, j)
        int[][] vpointer = new int[width][height];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                VedgeTo(j, i, energies, vmcp, vpointer);
                // System.out.print(vmcp[j][i] + " ");
            } // System.out.println(" ");
        }

        // for debugging purpose: print out the vmcp matrix
//        for (int row = 0; row < height; row++) {
//            for (int col = 0; col < width; col++)
//                StdOut.printf("%9.0f ", vmcp[col][row]);
//            StdOut.println();
//        }
//        StdOut.println();

        for (int j = 0; j < width; j++) {
            if (vmcp[j][height-1] < mcpbest) {
                pointer = j;
                mcpbest = vmcp[j][height-1];
            }
        }

        output.push(pointer);
        for (int i = height-1; i > 0 ; i--) {
            pointer = pointer + vpointer[pointer][i];
            output.push(pointer);
        }

        int[] seam = new int[height];
        for (int i = 0; i < height; i++) {
            seam[i] = output.pop();
        }
        return seam;
    }

    // find a minimal energy path vertically
    public int[] findVerticalSeam() {
        return findVerticalSeam(energy);
    }

    public int[] findHorizontalSeam() {
        return findVerticalSeam(transposed);
    }

    private boolean checkSeamValidity(int[] seam) {
        for (int i = 0; i < seam.length-1; i++) {
            if (Math.abs(seam[i] - seam[i+1]) > 1) {
                return false;
            }
        }
        return true;
    }

    public void removeHorizontalSeam(int[] seam) {
        if (seam.length!=width() || !checkSeamValidity(seam)) {
            throw new IllegalArgumentException();
        }
        pic = SeamRemover.removeHorizontalSeam(pic, seam);
        initialize();

    }

    public void removeVerticalSeam(int[] seam) {
        if (seam.length!=height() || !checkSeamValidity(seam)) {
            throw new IllegalArgumentException();
        }
        pic = SeamRemover.removeVerticalSeam(pic, seam);
        initialize();
    }

//    public static void main(String[] args) {

//        double[][] energ = new double[][]{{12,27,11,28}, {16,11,27,20}, {13,19,13,7},
//                {27,19,22,25},{15,4,8,13},{9,11,23,14}};
//        double[][] transp = new double[][]{{12,16,13,27,15,9}, {27,11,19,19,4,11}, {11,27,13,22,8,23}, {28,20,7,25,13,14}};
//        Picture pic = SCUtility.doubleToPicture(transp);
//        SeamCarver sc = new SeamCarver(pic);
//        int[] seam = sc.findVerticalSeam(transp);

//        Picture p = new Picture("images/6x5.png");
//        SeamCarver sc = new SeamCarver(p);
        // int[] seam = sc.findVerticalSeam();
        // int[] expected = {3, 4, 3, 2, 2};
        // System.out.println(sc.height() + " " + sc.width() + " " + Arrays.toString(seam));
        // SeamRemover.removeVerticalSeam(p, seam);
        // sc.removeVerticalSeam(seam);
//        sc.picture().save("output.png");

//    }
}

