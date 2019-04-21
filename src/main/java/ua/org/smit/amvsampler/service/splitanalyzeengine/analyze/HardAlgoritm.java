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
public class HardAlgoritm {

    private static RgbFrame previousFrame;

    private static int frameCount = 0;

    public static int result(RgbFrame frame, int MAX_WIDTH, int MAX_HEIGHT) {

        try {

            if (previousFrame == null) {
                previousFrame = frame;
                return 0;
            }

            int percentSumm = 0;

            int pixelsSumm = 0;
            for (int width = 0; width < MAX_WIDTH; width++) {
                for (int height = 0; height < MAX_HEIGHT; height++) {

                    Pixel pixel = frame.getPixels().get(pixelsSumm);
                    Pixel previousPixel = previousFrame.getPixels().get(pixelsSumm);
                    int currColorsSumm = pixel.getCollorsSumm();
                    int prevColorsSumm = previousPixel.getCollorsSumm();
                    int differentPercent = Percent.difference(prevColorsSumm, currColorsSumm);
                    percentSumm += differentPercent;

                    pixelsSumm++;

                }
            }

            previousFrame = frame;
            int averagePercent = percentSumm / pixelsSumm;
            return averagePercent;

        } catch (IndexOutOfBoundsException ex) {
            System.err.println(ex);
        }
        return 0;
    }

    private static void printFrameIfNeed(RgbFrame frame, int MAX_WIDTH, int MAX_HEIGHT) {
        frameCount++;
        if (frameCount >= 300) {
            frame.pringFrame(MAX_WIDTH, MAX_HEIGHT);
            frameCount = 0;
        }
    }
}
