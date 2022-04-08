package com.hailas.common.jwt;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Calendar;
import java.util.Date;

/**
 * @author wulin
 * @version 1.0.0
 * @created 2019/8/23.
 */
public class JwtHelper {

    /**
     * 获取jwt
     * @param subject
     * @param key
     * @return
     */
    public static String getToken(String subject, Date expiration, String key){
        return Jwts.builder().setSubject(subject)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512, key).compact();
    }

    /**
     * 验证jwt
     * @param token
     * @param key
     * @return
     */
    public static String getSubject(String token,String key) throws InvalidJwtException {
        try {
            return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject();
        } catch (JwtException e){
            throw new InvalidJwtException(e.getMessage());
        }
    }

    public static void main(String[] args) throws InvalidJwtException {
//        String key = UUIDHelper.get();
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.MINUTE,10);
//        String token = getToken("wulin",calendar.getTime(),key);
//        String subject = getSubject(token,key);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR,24);
        String token = JwtHelper.getToken(String.valueOf("3:2"), calendar.getTime(), "waguo888waguo888waguo888waguo888");
        System.out.println(token);
    }
}
