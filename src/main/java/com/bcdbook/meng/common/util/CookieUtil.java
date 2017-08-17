package com.bcdbook.meng.common.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author summer
 * @Date 2017/8/13 下午6:34
 * cookie工具类
 */
public class CookieUtil {

    /**
     * @author summer
     * @date 2017/8/13 下午6:36
     * @param response
     * @param name
     * @param value
     * @param maxAge
     * @return void
     * @description 设置cookie的操作
     */
    public static void set(HttpServletResponse response,
                           String name,
                           String value,
                           int maxAge){

        //创建一个cookie对象
        Cookie cookie = new Cookie(name,value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);//设置cookie的有效时长

        //返回cookie
        response.addCookie(cookie);
    }

    /**
     * @author summer
     * @date 2017/8/13 下午6:42
     * @param request
     * @param name
     * @return javax.servlet.http.Cookie
     * @description 获取cookie的操作
     */
    public static Cookie get(HttpServletRequest request,
                             String name){
        //获取cookie的map集合
        Map<String, Cookie> cookieMap = readCookieMap(request);

        //如果和传入的名称一致则返回对应的cookie
        if(cookieMap.containsKey(name)){
            return cookieMap.get(name);
        }
        return null;
    }

    /**
     *
     * @param request
     * @param key
     * @return 根据cookie中的key,获取其value的值
     */
    public static String getValue(HttpServletRequest request,String key){
        Cookie cookie = get(request,key);
        if(cookie==null){
            return null;
        }

        return get(request,key).getValue();
    }

    /**
     * @author summer
     * @date 2017/8/13 下午6:42
     * @param request
     * @return java.util.Map<java.lang.String,javax.servlet.http.Cookie>
     * @description 将cookie封装成Map
     */
    private static Map<String, Cookie> readCookieMap(HttpServletRequest request) {
        //创建一个map对象,用来封装cookie
        Map<String, Cookie> cookieMap = new HashMap<>();
        //从request中获取cookie的数组
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            //循环把cookie放入map对象中
            for (Cookie cookie: cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        //返回cookie
        return cookieMap;
    }
}
