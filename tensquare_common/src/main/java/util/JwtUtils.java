package util;

import io.jsonwebtoken.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

//指定读取配置的前缀,自动为类中的属性读取配置
//ignoreUnknownFields:如果属性没有找到对应的配置,那么不读取
@Component
@ConfigurationProperties(value = "jwt.config",ignoreUnknownFields = true)
public class JwtUtils {

    private Long ttl;//过期时间(小时),读取配置
    private String secret; //密钥.读取配置

    //生成Token
    public String generateJwt(String id,String subject,String roles){
        //取当前系统时间
        long currenttime = System.currentTimeMillis();
        //计算过期时间
        long expirationtime = currenttime + 1000*60*60*ttl; //20秒后过期

        String token = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, secret)//指定头部分(加密方式和密钥)
                .setId(id) //设置载荷(预定义载荷)
                .setSubject(subject)//设置载荷(预定义载荷)
                .setIssuedAt(new Date())//设置载荷(预定义载荷) => token签发时间
                .claim("roles", roles)//向载荷中添加自定义键值对
                .setExpiration(new Date(expirationtime))//指定过期时间
                .compact();//生成加密token

        return token;
    }

    //解析token
    public Claims parseJwt(String token){

        Claims body = null;//获得载荷部分
        try {
            body = Jwts.parser()
                    .setSigningKey(secret) //指定密钥
                    .parseClaimsJws(token)// 解析token
                    .getBody();
        } catch (ExpiredJwtException e) {
           throw new RuntimeException("登录超时,请重新登录!");
        }catch (IllegalArgumentException e) {
            throw new RuntimeException("登录过期,请重新登录!");
        } catch (Exception e) {
            throw new RuntimeException("非法操作!");
        }

        return body;
    }


    public Long getTtl() {
        return ttl;
    }

    public void setTtl(Long ttl) {
        this.ttl = ttl;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
