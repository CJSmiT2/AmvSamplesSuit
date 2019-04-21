/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsampler.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author smit
 */
public class StringUtil {

    public static String getWithAllowedSymbols(String text) {
        text = text.replaceAll("\\s+", "_");
        StringBuilder normalized = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            String symbol = text.substring(i, i + 1);
            if (isLetter(symbol)) {
                normalized.append(symbol);
            }
        }
        return normalized.toString();
    }

    private static boolean isLetter(String symbol) {
        Pattern p = Pattern.compile("^[а-яА-ЯёЁa-zA-Z0-9_]+$"); // ^[a-zA-Z0-9]+$
        Matcher m = p.matcher(symbol);
        return m.matches();
    }
}
