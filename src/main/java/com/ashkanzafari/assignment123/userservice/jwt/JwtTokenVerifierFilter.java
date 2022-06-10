package com.ashkanzafari.assignment123.userservice.jwt;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.shaded.json.JSONArray;
import com.nimbusds.jwt.EncryptedJWT;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * JwtTokenVerifierFilter.
 *
 * <p></p>
 */
public class JwtTokenVerifierFilter extends BasicAuthenticationFilter {

    private String HEADER_STRING;
    private String TOKEN_PREFIX;
    private String SECRET;
    private RsaKeyManager rsaKeyManager;

    public JwtTokenVerifierFilter(AuthenticationManager authManager, String header, String prefix,
                                  RsaKeyManager rsaKeyManager) {
        super(authManager);
        this.HEADER_STRING=header;
        this.TOKEN_PREFIX=prefix;
        this.rsaKeyManager=rsaKeyManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(HEADER_STRING);

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        String token = header.replace(TOKEN_PREFIX, "");
        try{

            EncryptedJWT jwt = EncryptedJWT.parse(token);
            RSADecrypter decrypter = new RSADecrypter(rsaKeyManager.getPrivateKey());
            jwt.decrypt(decrypter);


//            Jws<Claims> claimsJws = Jwts.parser()
//                .setSigningKey(rsaKeyManager.getPublicKey())
//                .parseClaimsJws(token);

            String username = jwt.getJWTClaimsSet().getSubject();
            JSONArray authorities = (JSONArray) jwt.getJWTClaimsSet().getClaim("authorities");
            Set<SimpleGrantedAuthority> grantedAuthorities = authorities.stream().map(m -> new SimpleGrantedAuthority((String)m)).collect(Collectors.toSet());
            Authentication auth = new UsernamePasswordAuthenticationToken(username,null, grantedAuthorities);
            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (JOSEException e) {
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }


        chain.doFilter(req, res);
    }

}
