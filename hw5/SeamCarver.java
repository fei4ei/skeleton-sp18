import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.Stack;

import java.awt.*;

public class SeamCarver {
    Picture pic;
    double[][] energy;
    Picture tpic;
    double[][] transposed;
    int[][] vpointer;
    double[][] mcp; // minimal cost path ending at i,j

    public SeamCarver(Picture picture) {
        pic = picture;
        tpic = transpose(pic);
        energy = new double[width()][height()];
        transposed = new double[height()][width()];
        vpointer = new int[width()][height()];
        for (int i = 0; i < width(); i++) {
            for (int j = 0; j < height(); j++) {
                energy[i][j] = energy(i, j);
                transposed[j][i] = energy[i][j];
            }
        }
    }

    public Picture picture() {
        return pic;
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
    private Picture transpose(Picture picture) {
        Picture tpic = new Picture(height(), width());
        for (int i = 0; i < height(); i++) {
            for (int j = 0; j < width(); j++) {
                tpic.setRGB(i, j, picture.getRGB(j, i));
            }
        }
        return tpic;
    }

    // return the square of the y gradient, exploring the idea of transposing followed by xgradsqu()
    private int ygradsquTr(int x, int y) {
        return xgradsqu(tpic, y, x);
    }

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
    private void VedgeTo(int x, int y) {
        if (y == 0) { // top row
            mcp[x][y] = energy[x][y];
        } else {
            if (x-1>=0 && mcp[x-1][y-1]<mcp[x][y-1] && mcp[x-1][y-1]<mcp[x+1][y-1]) {
                vpointer[x][y] = -1;
                mcp[x][y] = mcp[x-1][y-1] + energy[x][y];
            } else if (x+1<width() && mcp[x+1][y-1]<mcp[x][y-1] && mcp[x+1][y-1]<mcp[x-1][y-1]) {
                vpointer[x][y] = 1;
                mcp[x][y] = mcp[x+1][y-1] + energy[x][y];
            }
        }
    }

   public int[] findHorizontalSeam() {
        return null;
    }

    public int[] findVerticalSeam() {
        double mcpbest = mcp[0][height()-1];
        Stack<Integer> output = new Stack<>();
        for (int i = 0; i < height(); i++) {
            for (int j = 0; j < width(); j++) {
                VedgeTo(j, i);
            }
        }
        int pointer = 0;
        for (int j = 1; j < width()-1; j++) {
            if (mcp[j][height()-1] < mcpbest) {
                pointer = j;
                mcpbest = mcp[j][height()-1];
            }
        }
        output.push(pointer);
        for (int i = height()-1; i > 0 ; i--) {
            pointer = pointer + vpointer[pointer][i];
            output.push(pointer);
        }
        int[] seam = new int[height()];
        for (int i = 0; i < height(); i++) {
            seam[i] = output.pop();
        }
        return seam;
    }

    private boolean checkSeamValidity(int[] seam) {
        for (int i = 0; i < seam.length - 1; i++) {
            if (Math.abs(seam[i] - seam[i+1]) > 1) {
                return false;
            }
        }
        return true;
    }

    public void removeHorizontalSeam(int[] seam) {
        if (seam.length!=width() || checkSeamValidity(seam)) {
            throw new IllegalArgumentException();
        }
        pic = SeamRemover.removeHorizontalSeam(pic, seam);

    }

    public void removeVerticalSeam(int[] seam) {
        if (seam.length!=height() || checkSeamValidity(seam)) {
            throw new IllegalArgumentException();
        }
        pic = SeamRemover.removeVerticalSeam(pic, seam);

    }
}

