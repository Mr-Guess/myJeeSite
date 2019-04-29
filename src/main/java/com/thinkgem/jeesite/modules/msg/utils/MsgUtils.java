package com.thinkgem.jeesite.modules.msg.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MsgUtils {
	
	public static String unifyUrl = "http://221.237.182.5:8089/urlif/HttpSms.aspx";

    public static String sendMessage(String phoneNumber,String message) throws UnsupportedEncodingException{
        String pass = URLEncoder.encode("Da2#336?");
        String uid = URLEncoder.encode("89");
        String sentOut = URLEncoder.encode(message,"UTF-8");
        String path = unifyUrl+"?phone="+phoneNumber+"&msg="+sentOut+"&op=sendsms&uid="+uid+"&upass="+pass;
        try {
            URL url = new URL(path.trim());
            //打开连接
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            if(200 == urlConnection.getResponseCode()){
                //得到输入流
                InputStream is =urlConnection.getInputStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = 0;
                while(-1 != (len = is.read(buffer))){
                    baos.write(buffer,0,len);
                    baos.flush();
                }
                return baos.toString("utf-8");
            }
        }  catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
