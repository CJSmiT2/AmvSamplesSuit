/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsampler.service.splitanalyzeengine.analyze;

/**
 *
 * @author smit
 */
public class Pixel {

    private final int height;
    private final int width;
    private final int red;
    private final int green;
    private final int blue;

    public Pixel(int height, int width, int red, int green, int blue) {
        this.height = height;
        this.width = width;
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public int getCollorsSumm() {
        return red + green + blue;
    }

    @Override
    public String toString() {
        return "Pixel{" + "height=" + height + ", width=" + width + ", red=" + red + ", green=" + green + ", blue=" + blue + '}';
    }

    int getWidth() {
        return width;
    }

    int getHeight() {
        return height;
    }

    String getSimbol() {
        // # * - .
        int all = red + green + blue;
        String simbol = "";
        if (all <= 255) {
            return "#";
        }
        if (all > 255 & all <= 510) {
            return "*";
        }
        if (all > 510) {
            return "-";
        }
        return "e";
    }

}
