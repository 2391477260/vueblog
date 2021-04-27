package com.markerhub.shiro;

import cn.hutool.core.bean.BeanUtil;
import com.markerhub.entity.User;
import com.markerhub.service.UserService;
import com.markerhub.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Slf4j
@Component
public class AccountRealm extends AuthorizingRealm {
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserService userService;
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    //获取权限返回给shiro
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    //密码校验逻辑
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
       JwtToken jwtToken=(JwtToken) token;
        log.info("jwt----------------->{}", jwtToken);
       String userId=jwtUtils.getClaimByToken((String) jwtToken.getPrincipal()).getSubject();
       User user=userService.getById(Long.valueOf(userId));
       if(user==null){
           throw new UnknownAccountException("账户不存在");
       }
       if(user.getStatus()==-1){
           throw new LockedAccountException("账户已被锁定");
       }
       AccountProfile profile=new AccountProfile();
        BeanUtil.copyProperties(user,profile);
        return new SimpleAuthenticationInfo(profile ,jwtToken.getCredentials(),getName());
    }
}
