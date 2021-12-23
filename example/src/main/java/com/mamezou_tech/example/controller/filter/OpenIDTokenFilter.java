package com.mamezou_tech.example.controller.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class OpenIDTokenFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(OpenIDTokenFilter.class);

    private final String jwksJsonUrl;

    public OpenIDTokenFilter(String region, String poolid) {
        this.jwksJsonUrl = "https://cognito-idp." + region + ".amazonaws.com/" + poolid + "/.well-known/jwks.json";
        logger.info(jwksJsonUrl);
    }

    protected Map<String, KeyItem> openIDConnectGetKeys() throws URISyntaxException, IOException {
        try (InputStream input = (new URI(jwksJsonUrl)).toURL().openStream()) {
            Map<String, KeyItem> map = new HashMap();
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            while (true) {
                int ch = input.read();
                if (ch == -1) {
                    break;
                }
                buffer.write(ch);
            }
            ObjectMapper mapper = new ObjectMapper();
            Keys keys = mapper.readValue(buffer.toByteArray(), Keys.class);
            for (KeyItem key: keys.keys) {
                map.put(key.kid, key);
            }
            return map;
        }
    }

    /**
     * バイナリ表現の整数を BigInteger に変換
     * @param value バイナリ表現の整数を Base64 エンコードした文字列
     * @return 整数
     */
    protected BigInteger decodeBase64UrlUInt(String value) {
        byte[] uintBinary = Base64.getUrlDecoder().decode(value);
        return new BigInteger(1, uintBinary);
    }

    protected Map<String, Algorithm> toAlgorithms(Map<String, KeyItem> map) throws NoSuchAlgorithmException, InvalidKeySpecException {
        HashMap<String, Algorithm> algorithms = new HashMap<>();
        for (Map.Entry<String, KeyItem> entry: map.entrySet()) {
            BigInteger modulus = decodeBase64UrlUInt(entry.getValue().n);
            BigInteger publicExponent = decodeBase64UrlUInt(entry.getValue().e);
            KeySpec spec = new RSAPublicKeySpec(modulus, publicExponent);
            RSAPublicKey publicKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(spec);
            Algorithm algorithm = Algorithm.RSA256(publicKey, null);
            algorithms.put(entry.getKey(), algorithm);
        }
        return algorithms;
    }

    protected HashMap<String, Object> verify(String idToken, Map<String, Algorithm> keys) throws Exception {
        DecodedJWT jwt = JWT.decode(idToken);
        if (!keys.containsKey(jwt.getKeyId())) {
            // FIXME 公開鍵リストは毎回取得するのではなく、キャッシュしておくべき
            // このブロックに入るときは、キャッシュを取り直すこと必要があることを意味している
            Map<String, KeyItem> keyItems = openIDConnectGetKeys();
            keys = toAlgorithms(keyItems);
        }

        Algorithm algorithm = keys.get(jwt.getKeyId());
        if (algorithm == null) {
            // 必要な公開鍵が取得できない場合は、Cognito 以外などの不正な ID Token の可能性がある
            return null;
        }

        // JWT の検証。署名だけではなく、有効期限等も検証します。
        Verification verification = JWT.require(algorithm);
        verification.build().verify(jwt);

        // JWT の ペーロード部分を HashMap にして返す場合。
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {};
        return mapper.readValue(Base64.getUrlDecoder().decode(jwt.getPayload()), typeRef);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String idToken = req.getHeader("Authorization");
        logger.info(idToken);

        try {
            Map<String, Object> payload = verify(idToken, new HashMap<String, Algorithm>());
            logger.info((String) payload.get("custom:role"));
        } catch (Exception e) {
            logger.error("error", e);
            HttpServletResponse res = (HttpServletResponse) response;
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        chain.doFilter(request, response);
    }
}
