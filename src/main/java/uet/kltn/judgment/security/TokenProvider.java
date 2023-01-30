package uet.kltn.judgment.security;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import uet.kltn.judgment.config.AppProperties;
import uet.kltn.judgment.constant.RoleUser;
import uet.kltn.judgment.dto.common.TokenData;
import uet.kltn.judgment.model.User;
import io.jsonwebtoken.*;
import uet.kltn.judgment.util.JsonUtil;


import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class TokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

    private CustomUserDetailsService customUserDetailsService;
    private final AppProperties appProperties;

    public TokenProvider(CustomUserDetailsService customUserDetailsService, AppProperties appProperties) {
        this.customUserDetailsService = customUserDetailsService;
        this.appProperties = appProperties;
    }

    public String createToken(User user, Integer roleId) {

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + appProperties.getAuth().getTokenExpirationMsec());
        TokenData tokenData = new TokenData(user.getUid(), roleId);
        return Jwts.builder()
                .setSubject(user.getUid())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .setIssuer(new Gson().toJson(tokenData))
                .signWith(SignatureAlgorithm.HS512, appProperties.getAuth().getTokenSecret())
                .compact();
    }

    public Integer getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(appProperties.getAuth().getTokenSecret())
                .parseClaimsJws(token)
                .getBody();

        return Integer.parseInt(claims.getSubject());
    }

    public TokenData getTokenData(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(appProperties.getAuth().getTokenSecret())
                .parseClaimsJws(token)
                .getBody();
        return JsonUtil.fromJsonStringToObject(claims.get("iss").toString(), TokenData.class);
    }

    public UsernamePasswordAuthenticationToken generateAuthentication(HttpServletRequest request, String token) {
        TokenData tokenData = this.getTokenData(token);
        String userId = tokenData.getUserUid();
        int isOtp = tokenData.getIsOtp();
        Integer role = tokenData.getRole();
        UserDetails userDetails = customUserDetailsService.loadUserById(userId);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
                null, userDetails.getAuthorities());
        if (request != null) {
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        }
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        userPrincipal.setIsOtp(isOtp);
        User user = userPrincipal.getUser();
        List<GrantedAuthority> authorities = null;
        if (role == RoleUser.ROLE.getId()) {
            authorities = Arrays.stream(RoleUser.values()).map(roleUser -> new SimpleGrantedAuthority(roleUser.name())).collect(Collectors.toList());
//                    authorities = Collections.singletonList(new SimpleGrantedAuthority(RoleUser.ROLE_BGLOBAL.name()));
        } else if (role == RoleUser.ROLE_GROUP_ADMIN_OWNER.getId()) {
            authorities = Collections.singletonList(new SimpleGrantedAuthority(RoleUser.ROLE_GROUP_ADMIN_OWNER.name()));
        } else {
            authorities = Collections.singletonList(new SimpleGrantedAuthority(RoleUser.ROLE_CONSUMER.name()));
        }
        authentication = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
        if (request != null) {
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        }
        return authentication;
    }
}
