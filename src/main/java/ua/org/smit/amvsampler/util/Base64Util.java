/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsampler.util;

import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author smit
 */
public class Base64Util {

    public String encode(String text) {
        byte[] encodedBytes = Base64.encodeBase64(text.getBytes());
        return new String(encodedBytes);
    }

    public String decode(String text) {
        byte[] decodedBytes = Base64.decodeBase64(text);
        return new String(decodedBytes);
    }
}
