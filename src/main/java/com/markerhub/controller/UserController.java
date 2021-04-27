package com.markerhub.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.SecureUtil;
import com.markerhub.common.lang.Result;
import com.markerhub.entity.User;
import com.markerhub.service.UserService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 关注公众号：MarkerHub
 * @since 2020-12-18
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @RequiresAuthentication
    @GetMapping("/index")
    public Result index(){
        User user=userService.getById(1L);
        return Result.succ(user);
    }
    @PostMapping("/save")
    public Result save(@Validated @RequestBody User user) {

        return Result.succ(user);
    }
    @PostMapping("/register")
    public Result register(@Validated @RequestBody User user){
        User temp=new User();
        temp.setUsername(user.getUsername());
        temp.setPassword(SecureUtil.md5(user.getPassword()));
        temp.setEmail(user.getEmail());
        temp.setCreated(LocalDateTime.now());
        temp.setStatus(1);
        userService.save(temp);
        return Result.succ(200,"操作成功",null);
    }
}
