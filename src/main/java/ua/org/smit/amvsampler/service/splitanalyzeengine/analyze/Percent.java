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
public class Percent {

    public static int difference(double x, double y) {
        if (x == 0) {
            x = 0.1;
        }
        if (y == 0) {
            y = 0.1;
        }

        double percent = x / y * 100;
        double difference = (double) 100 - percent;
        if (difference < 0) {
            difference = difference * (-1);
        }
        return (int) difference;
    }
}
