/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsampler.service.splitanalyzeengine.analyze;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author smit
 */
public class ImageToNumbers {

    public static RgbFrame toFrame(File imgFrame, int maxWidth, int maxHeight) {
        RgbFrame frame = new RgbFrame();
        try {
            BufferedImage img = ImageIO.read(imgFrame);

            for (int height = 0; height < maxHeight; height++) {
                for (int width = 0; width < maxWidth; width++) {
                    int clr = img.getRGB(width, height);
                    int red = (clr & 0x00ff0000) >> 16;
                    int green = (clr & 0x0000ff00) >> 8;
                    int blue = clr & 0x000000ff;
                    frame.addPixel(height, width, red, green, blue);
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(ImageToNumbers.class.getName()).log(Level.SEVERE, null, ex);
        }
        return frame;
    }
}
