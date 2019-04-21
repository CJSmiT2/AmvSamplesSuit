/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsampler.util;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author smit
 */
public class Access {

    public static void check(HttpServletRequest request, boolean localHostOnly) {
        if (localHostOnly) {
            String host = getHost(request);
            if (!host.equals("127.0.0.1") && !host.equals("localhost")) {
                throw new RuntimeException("You try connect to program from '" + host + "'. "
                        + "If you want to allow access to the program from the outside, "
                        + "enable access in the settings 'localhost only = false'.");
            }
        }
    }

    private static String getHost(HttpServletRequest request) {
        String host = request.getHeader("host");
        if (host == null) {
            host = request.getRemoteAddr();
        }
        return getWithoutPort(host);
    }

    private static String getWithoutPort(String host) {
        if (host.contains(":")) {
            String[] split = host.split(":");
            return split[0];
        } else {
            return host;
        }
    }
}
