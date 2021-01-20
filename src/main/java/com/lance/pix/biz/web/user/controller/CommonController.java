package com.lance.pix.biz.web.user.controller;

import com.lance.pix.basic.auth.annotation.PermissionRequired;
import com.lance.pix.basic.auth.util.JWTUtil;
import com.lance.pix.basic.verification.annotation.CheckVerification;
import com.lance.pix.biz.web.common.po.User;
import com.lance.pix.biz.web.user.dto.SignUpDTO;
import com.lance.pix.biz.web.user.service.CommonService;
import com.lance.pix.common.constant.AuthConstant;
import com.lance.pix.common.context.AppContext;
import com.lance.pix.common.po.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @Author lancer1126
 * @Date 2020-12-8
 * @Description 用户相关controller
 */
@RestController
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/users")
public class CommonController {

    private final CommonService userService;
    private final JWTUtil jwtUtil;

    /**
     * 判断邮箱是否已经存在
     * @param email 邮箱
     * @return      返回boolean
     */
    @GetMapping("/emails/{email:.+}")
    public ResponseEntity<Result<Boolean>> checkEmail(@Email @NotBlank @PathVariable("email") String email) {
        if (userService.checkEmail(email)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new Result<>("邮箱已存在"));
        }
        return ResponseEntity.ok().body(new Result<>("邮箱可用"));
    }

    /**
     * 检查用户名是否重复
     * @param username   用户名
     * @return           判断
     */
    public ResponseEntity<Result<Boolean>> checkUserName(@NotBlank @PathVariable("username") @Size(min = 2, max = 50) String username) {
        if (userService.checkUserName(username)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new Result<>("用户名已存在"));
        }
        return ResponseEntity.ok().body(new Result<>("用户名可用"));
    }

    /**
     * 注册用户
     * @param userInfo  用户信息
     * @param vid       vid
     * @param value     value
     * @return          user
     */
    @PostMapping
    @CheckVerification
    public ResponseEntity<Result<User>> signUp(@RequestBody SignUpDTO userInfo,@RequestParam("vid") String vid, @RequestParam("value") String value) {
        User user = userInfo.castToUser();
        user = userService.signUp(user);
        return ResponseEntity.ok().header("Authorization",jwtUtil.getToken(user)).body(new Result<>("注册成功", user));
    }

    /**
     * 用户登录
     * @param userInfo  用户信息
     * @param vid       vid
     * @param value     value
     * @return          user
     */
    @PostMapping("/token")
    @CheckVerification
    public ResponseEntity<Result<User>> signIn(@RequestBody SignUpDTO userInfo, @RequestParam("vid") String vid, @RequestParam("value") String value) {
        User user = userService.signIn(userInfo.getUsername(), userInfo.getPassword());
        return ResponseEntity.ok().header("Authorization",jwtUtil.getToken(user)).body(new Result<>("登录成功", user));
    }

    /**
     * 获取用户邮箱是否验证
     * @param userId    userid
     * @param token     token
     * @return          boolean
     */
    @GetMapping("/{userId}/email/isCheck")
    @PermissionRequired
    public ResponseEntity<Result<Boolean>> queryEmailIsCheck(@PathVariable("userId") int userId, @RequestHeader("Authorization") String token) {
        boolean isCheck = userService.queryEmailIsCheck((Integer) AppContext.get().get(AuthConstant.USER_ID));
        return ResponseEntity.ok().body(new Result<>("获取邮箱验证状态成功", isCheck));
    }


}












