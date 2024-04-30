package kr.co.tbell.mm.utils;

public interface Constants {
    String SESSION_ADMIN_ID = "session_admin_id";
    
    long ACCESS_EXPIRED_MS = 1800000L; // 30분
    long REFRESH_EXPIRED_MS = 86400000L; // 24시간
    int REFRESH_TOKEN_COOKIE_MAX_AGE = 24 * 60 * 60; // 24시간(초단위)
    String HEADER_KEY_ACCESS_TOKEN = "Access-Token";
    String HEADER_KEY_REFRESH_TOKEN = "Refresh-Token";
}
