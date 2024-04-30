package kr.co.tbell.mm.utils;

import jakarta.servlet.http.Cookie;

public class Utils {

    public static Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(Constants.REFRESH_TOKEN_COOKIE_MAX_AGE);
        cookie.setHttpOnly(true);

        return cookie;
    }
}
