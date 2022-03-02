package com.cyx.utils;

import com.cyx.model.LoginUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class JwtUtil {
    /**
     * 主题
     */
    private static final String SUBJECT = "short-link";

    private static final String SECRET = "short-link-secret";

    private static final String TOKEN_PREFIX = "short-link";

    private static final long EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;

    /**
     * 生成token
     *
     * @param loginUser
     * @return
     */
    public static String generateJsonWebToken(LoginUser loginUser) {
        if (loginUser == null) {
            throw new NullPointerException("账号为空");
        }
        String token = Jwts.builder().setSubject(SUBJECT)
                .claim("head_img", loginUser.getHeadImg())
                .claim("mail", loginUser.getMail())
                .claim("account_no", loginUser.getAccountNo())
                .claim("username", loginUser.getUsername())
                .claim("auth", loginUser.getAuth())
                .claim("phone",loginUser.getPhone())
                .setIssuedAt(new Date())
                .setExpiration(new Date(CommonUtil.getCurrentTimestamp() + EXPIRE_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET).compact();
        return TOKEN_PREFIX + token;
    }

    /**
     * 解密jwt
     *
     * @param token
     * @return
     */
    public static Claims checkJwt(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody();
            return claims;
        } catch (Exception e) {
            log.error("token解析失败", e);
            return null;
        }
    }
}
