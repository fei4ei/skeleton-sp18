import edu.princeton.cs.algs4.Picture;

import java.awt.*;

public class SeamCarver {
    Picture pic;
    public SeamCarver(Picture picture) {
        pic = picture;
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

    // transpose the pic
    private static Picture transpose(Picture picture) {
        Picture tpic = picture;
        return tpic;
    }

    // return the square of the y gradient
    private int ygradsqu(Picture picture, int x, int y) {
        Picture npic = transpose(picture);
        return xgradsqu(npic, y, x);
    }

    public double energy(int x, int y) {

        // check if x and y are in bounds
        if (checkinBound(x,y) == false) {
            throw new IndexOutOfBoundsException();
        }



        return 0.0;
    }

    public int[] findHorizontalSeam() {
        return null;
    }

    public int[] findVerticalSeam() {
        return null;
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

