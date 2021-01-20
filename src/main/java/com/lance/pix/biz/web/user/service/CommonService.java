package com.lance.pix.biz.web.user.service;

import com.lance.pix.basic.verification.domain.EmailBindingVerificationCode;
import com.lance.pix.biz.web.common.exception.BusinessException;
import com.lance.pix.biz.web.common.po.User;
import com.lance.pix.biz.web.user.mapper.CommonMapper;
import com.lance.pix.biz.web.user.util.PasswordUtil;
import com.lance.pix.common.exception.UserCommonException;
import com.lance.pix.common.util.email.EmailUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * @Author lancer1126
 * @Date 2020-12-8
 * @Description CommonService
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CommonService {
    private final static String AVATAR_PRE = "https://static.pixivic.net/avatar/299x299/";
    private final static String AVATAR_POS = ".jpg";
    private final static String PIX = "Pix老兄";
    private final static String CONTENT_1 = "点击以下按钮以验证邮箱";
    private final static String CONTENT_2 = "点击以下按钮以重置密码";
    private final static String QQ_BIND_URL_PRE = "https://graph.qq.com/oauth2.0/me?access_token=";
    private final CommonMapper userMapper;
    private final PasswordUtil passwordUtil;
    private final VerificationCodeService verificationCodeService;
    private final EmailUtil emailUtil;

    /**
     * 判断数据库中是否已经存在该邮箱
     * @param email 邮箱
     * @return      返回判断
     */
    public boolean checkEmail(String email) {
        return userMapper.checkUserEmail(email) == 1;
    }

    /**
     * 判断用户名是否重复
     * @param username  用户名
     * @return          判断
     */
    public boolean checkUserName(String username) {
        return userMapper.checkUserName(username) == 1;
    }

    /**
     * 注册账号
     * @param user  user对象
     * @return      user
     */
    public User signUp(User user) {
        if (userMapper.checkUserName(user.getUsername()) == 1 || userMapper.checkUserEmail(user.getEmail()) == 1) {
            throw new UserCommonException(HttpStatus.CONFLICT, "用户名或邮箱已存在");
        }
        user.setPassword(passwordUtil.encrypt(user.getPassword()));
        user.init();
        userMapper.insertUser(user);

        //发送验证邮件,待完成
        EmailBindingVerificationCode emailVerificationCode = verificationCodeService.getEmailVerificationCode(user.getEmail());
        emailUtil.senEmail();

        user = userMapper.queryUserByusernameAndPassword(user.getUsername(), user.getPassword());
        userMapper.setAvatar(AVATAR_PRE + user.getId() + AVATAR_POS, user.getId());

        //初始化汇总
        userMapper.initSummary(user.getId());
        user.setAvatar(AVATAR_PRE + user.getId() + AVATAR_POS);
        return user;
    }

    /**
     * 用户登录
     * @param username  用户名
     * @param password  密码
     * @return          用户
     */
    public User signIn(String username, String password) {
        User user = userMapper.queryUserByusernameAndPassword(username, passwordUtil.encrypt(password));
        if (user == null) {
            throw new UserCommonException(HttpStatus.BAD_REQUEST, "用户名或密码不正确");
        }
        return user;
    }

    /**
     * 永userId查询数据库中的user
     * @param userId    userId
     * @return          user
     */
    public User queryUser(Integer userId) {
        User user = userMapper.queryUserByUserId(userId);
        if (user == null) {
            throw new BusinessException(HttpStatus.NOT_FOUND, "用户不存在");
        }
        return user;
    }

    /**
     * 判断用户所属邮箱是否已经验证过
     * @param userId    userId
     * @return          boolean
     */
    public boolean queryEmailIsCheck(int userId) {
        User user = queryUser(userId);
        if (user != null) {
            return user.getIsCheckEmail();
        }
        throw new UserCommonException(HttpStatus.BAD_REQUEST, "用户不存在");
    }

}















