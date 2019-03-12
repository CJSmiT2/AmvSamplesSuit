/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsamplessuit.service.splitanalyzeengine.analyze;

import java.util.ArrayList;

/**
 *
 * @author smit
 */
public class RgbFrame {
    private final ArrayList<Pixel> pixels = new ArrayList();
    private int summPixels = 0;

    public void addPixel(int height, int width, int red, int green, int blue) {
        Pixel pixel = new Pixel(height, width, red, green, blue);
        pixels.add(pixel);
        
        summPixels += red + green + blue;
    }
    
    public int getSummPixels(){
        return summPixels;
    }
    
    public ArrayList<Pixel> getPixels(){
        return pixels;
    }

    @Override
    public String toString() {
        return "RgbFrame{" + "pixels=" + pixels + '}';
    }
    
    public void pringFrame(int width, int height) {
        
        for(int h=0; h<height;h++){
            String line = "";
            for(int w=0; w<width;w++){
                Pixel pixel = getPixel(w, h);
                line += pixel.getSimbol();
            }
            System.out.println(line);
        }
        
    }

    private Pixel getPixel(int width, int height) {
         for (Pixel pixel:pixels){
            if (pixel.getWidth()==width & pixel.getHeight()==height){
                return pixel;
            }
        }
        throw new RuntimeException("Pixel not found! width=" + width + " height=" + height);
    }
    
    
}
