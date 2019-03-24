/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsampler.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.org.smit.amvsampler.util.Base64Util;

/**
 *
 * @author smit
 */
@Controller
public class GifController {
    
        private final Base64Util base64 = new Base64Util();
    
        @ResponseBody 
        @RequestMapping(value ="/gif")
        public ResponseEntity gif(@RequestParam("path") String path) throws FileNotFoundException, IOException {
            
            path = base64.decode(path);
            File gif = new File(path);
            if (!gif.exists()){
                System.err.println("Path not exist! " + gif.getAbsolutePath());
            }
            InputStream inputStream = new FileInputStream(gif);
            byte[] fileBytes = org.apache.commons.io.IOUtils.toByteArray(inputStream);
            inputStream.close();
            HttpHeadersPreset headers = new HttpHeadersPreset(gif.getName(), "image/gif", gif.length());
            return new ResponseEntity(fileBytes, headers.getData(), HttpStatus.OK);
        }
        
        class HttpHeadersPreset{
            private final String fileName;
            private final String contentType;
            private final String length;

            public HttpHeadersPreset(String fileName, String contentType, long length) {
                this.fileName = fileName;
                this.contentType = contentType;
                this.length = String.valueOf(length);
            }

            public HttpHeaders getData(){
                HttpHeaders responseHeaders = new HttpHeaders();
                responseHeaders.add("content-disposition", "attachment; filename=" + fileName);
                responseHeaders.add("Content-Type", contentType);
                responseHeaders.add("accept-ranges", "bytes");
                responseHeaders.add("Cache-Control", "max-age=3600");
                responseHeaders.add("Content-Length", length);
                return responseHeaders;
            }
        }
}
