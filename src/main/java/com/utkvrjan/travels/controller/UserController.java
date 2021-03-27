package com.utkvrjan.travels.controller;


import com.utkvrjan.travels.entity.Result;
import com.utkvrjan.travels.entity.User;
import com.utkvrjan.travels.service.UserService;
import com.utkvrjan.travels.utils.ValidateImageCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("user")
@CrossOrigin  //允许跨域，因为前后端分离导致域不一样
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;



    @RequestMapping("login")
    public Result login(@RequestBody User user,HttpServletRequest request) {
        Result result = new Result();
        log.info("user: " + user);
        try {
            User userDB = userService.login(user);
            //登录成功之后保存用户的标记 ServletContext application Redis userid userdb
            request.getServletContext().setAttribute(userDB.getId(),userDB);
            redisTemplate.opsForValue().set(userDB.getId(), userDB);

            result.setMsg("登录成功~~").setUserId(userDB.getId()).setUserName(user.getUsername());
        } catch (Exception e) {
            result.setState(false).setMsg(e.getMessage());
        }
        return result;
    }


   @PostMapping("register")
   //@ResponseBody  //以json格式发送
    public Result register(String code, String key, @RequestBody User user, HttpServletRequest request){
       Result result=new Result();
        log.info("接受的验证码："+code);
        log.info("验证码的key："+key);
        log.info("接受的对象："+user);
        //获取验证码
       String keyCode=(String)request.getServletContext().getAttribute(key);
       log.info(keyCode);

           try{
               if(code.equalsIgnoreCase(keyCode)) {
                   //注册用户
                   userService.register(user);
                   result.setMsg("注册成功！！！");
               }else {
                   throw new RuntimeException("验证码错误");

               }
           } catch (Exception e){
               e.printStackTrace();
               result.setMsg(e.getMessage()).setState(false);
           }
       return result;
    }

    /**
     * 生成验证码
     * @throws IOException
     */
    @GetMapping("getImage")
   // @ResponseBody
    public Map<String,String> getImage(HttpServletRequest request) throws IOException {
        Map<String ,String> result= new HashMap<>();

        //获取验证码
        String securityCode =  ValidateImageCodeUtils.getSecurityCode();
        //验证码存入ServletContext,ServletContext是最大的作用域在什么地方都能访问到
        String key= new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        request.getServletContext().setAttribute(key,securityCode);
        //生成图片
        BufferedImage image=ValidateImageCodeUtils.createImage(securityCode);
        //进行base64的编码
        //Base64是网络上最常见的用于传输8Bit字节码的编码方式之一，Base64就是一种基于64个可打印字符来表示二进制数据的方法。
        ByteArrayOutputStream bos = new ByteArrayOutputStream();//内存中的byte数组
        ImageIO.write(image,"png",bos);//把土拍你写到bos数组中
        String str= Base64Utils.encodeToString(bos.toByteArray());
        result.put("key",key);
        result.put("image",str);
        return result;

    }
}
