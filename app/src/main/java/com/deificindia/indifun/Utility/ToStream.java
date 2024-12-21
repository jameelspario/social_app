package com.deificindia.indifun.Utility;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ToStream {

    public static ToStream instance(){
        return new ToStream();
    }

    public InputStream calcStream(String link, String from) throws Exception {
        if(from.equals("url")){
            return fromUrl(link);
        }else if(from.equals("assets")){
            return fromAssets(link);
        } else if (from.equals("raw")){
            return fromRaw(link);
        }

        return null;
    }

    public InputStream fromUrl(String fileURL) throws IOException {

        URL u = new URL(fileURL);
        HttpURLConnection c = (HttpURLConnection) u.openConnection();
        c.setRequestMethod("GET");
        c.setDoOutput(true);
        c.connect();

        return c.getInputStream();
    }

    public InputStream fromAssets(String file) throws Exception{


        throw new Exception("m");
    }

    public InputStream fromRaw(String raw) throws Exception {
        throw new Exception("m");
    }


}
