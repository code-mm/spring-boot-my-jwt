package com.my.app.utils;


import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;

@SuppressWarnings("all")
public class TokenUtils {
    public static final String APP_SECRET = "1e5503b52dd944eef7ebab10fd9ae2221e5d53b52f0944eef7ebab97fd9ae105";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final Pattern BEARER_PATTERN = Pattern.compile("^Bearer (.+?)$");
    public static final String USER_ID = "user_id";

    public static final Duration EXPIRE_IN = Duration.ofDays(7);

    public static void main(String[] args) {
        JwtBuilder builder = Jwts.builder()
                .setHeaderParam("version", "2.0")
                .setHeaderParam("typ", "JWT").setHeaderParam("alg", "HS256")
                .setSubject("my-token").setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_IN.toMillis()));

        builder.claim("a", "a");
        builder.signWith(SignatureAlgorithm.HS256, APP_SECRET);
        String tokenValue = builder.compact();
        System.out.println(tokenValue);
    }

    public static Optional<String> getToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(AUTHORIZATION_HEADER))
                .filter(org.apache.commons.lang3.StringUtils::isNotEmpty).map(BEARER_PATTERN::matcher)
                .filter(Matcher::find).map(matcher -> matcher.group(1));
    }

    /**
     * 判断token是否存在与有效
     *
     * @param token
     * @return
     */
    public static boolean checkToken(String token) {
        if (org.springframework.util.StringUtils.isEmpty(token)) {
            return false;
        }
        try {
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean checkToken(HttpServletRequest httpServletRequest) {
        Optional<String> token = getToken(httpServletRequest);
        if (token.isEmpty()) {
            return false;
        }
        String s = token.get();

        if (s == null) {
            return false;
        }
        return checkToken(s);
    }
}
