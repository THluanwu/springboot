package com.neuedu.service.impl;

import com.neuedu.common.ServerResponse;
import com.neuedu.dao.UserInfoMapper;
import com.neuedu.exception.MyException;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IUserService;
import com.neuedu.utils.MD5Utils;
import com.neuedu.utils.TokenCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    UserInfoMapper userInfoMapper;

    @Override
    public ServerResponse login(String username, String password) throws MyException {
        if (username == null || username.equals("")) {
            return ServerResponse.createServerResponseByFail(100, "用户名不能为空");
        }
        if (password == null || password.equals("")) {
            return ServerResponse.createServerResponseByFail(100, "密码不能为空");
        }
        //step2:判断用户名是否存在

        int username_result = userInfoMapper.exsitsUsername(username);

        if (username_result == 0) {//用户名不存在
            return ServerResponse.createServerResponseByFail(101, "用户名不存在");
        }

        //step3: 根据用户名和密码登录
        UserInfo userinfo_result = userInfoMapper.findUser(username,MD5Utils.getMD5Code(password));
        if (userinfo_result == null) {

            return ServerResponse.createServerResponseByFail(1, "密码错误");
        }

        userinfo_result.setPassword("");



        //step4: 判断权限

        if(userinfo_result.getRole()==1){//普通用户
            return ServerResponse.createServerResponseBySucess(userinfo_result);
        }

        return ServerResponse.createServerResponseByFail(2,"没有权限访问");
    }

    @Override
    public ServerResponse register(UserInfo userInfo) {
        if ((userInfo.getUsername() == null || userInfo.getUsername().equals("")) || (userInfo.getPassword() == null || userInfo.getPassword().equals("")) || (userInfo.getEmail() == null || userInfo.getEmail().equals("")) || (userInfo.getAnswer() == null || userInfo.getAnswer().equals("")) || (userInfo.getQuestion() == null || userInfo.getQuestion().equals("")) || (userInfo.getPhone() == null || userInfo.getPhone().equals(""))) {
            return ServerResponse.createServerResponseByFail(100, "注册信息不能为空");
        }
        int result_username = userInfoMapper.exsitsUsername(userInfo.getUsername());
        if (result_username != 0) {
            return ServerResponse.createServerResponseByFail(1, "用户名已存在");
        }
        int result_email = userInfoMapper.exsitsEmail(userInfo.getEmail());
        if (result_email != 0) {
            return ServerResponse.createServerResponseByFail(2, "邮箱已注册");
        }
        userInfo.setRole(1);
        userInfo.setPassword(MD5Utils.getMD5Code(userInfo.getPassword()));
        int count = userInfoMapper.insert(userInfo);
        if (count > 0) {
            return ServerResponse.createServerResponseBySucess("注册成功",null);
        }

        return ServerResponse.createServerResponseByFail(101, "注册失败");
    }

    @Override
    public ServerResponse checkValid(String str, String type) {
        if (str==null||str.equals("")){
            return ServerResponse.createServerResponseByFail(100,"用户名或邮箱不能为空");
        }
        if (type==null||type.equals("")){
            return ServerResponse.createServerResponseByFail(100,"校验参数不能为空");
        }
        if (type.equals("username")){
            int result=userInfoMapper.exsitsUsername(str);
            if (result!=0){
                return ServerResponse.createServerResponseByFail(1,"用户名已存在");
            }else{
                return ServerResponse.createServerResponseBySucess("校验成功",null);
            }
        }else if (type.equals("email")){
            int result=userInfoMapper.exsitsEmail(str);
            if (result!=0){
                return ServerResponse.createServerResponseByFail(2,"邮箱已注册");
            }else{
                return ServerResponse.createServerResponseBySucess("校验成功",null);
            }
        }else {
            return ServerResponse.createServerResponseByFail(100,"参数类型错误");
        }
    }

    @Override
    public ServerResponse forgetGetQuestion(String username) {
        //1.参数校验
        if(username==null||username.equals("")){
            return ServerResponse.createServerResponseByFail(100,"用户名不能为空");
        }
        //2.校验用户名
        int result=userInfoMapper.exsitsUsername(username);
        if (result==0){
            return ServerResponse.createServerResponseByFail(101,"用户名不存在");
        }
        //3.查找密保问题
        String question=userInfoMapper.selectQuestionByUsername(username);
        if (question==null||question.equals("")){
            return ServerResponse.createServerResponseByFail(1,"该用户未设置找回密码问题");
        }
        return ServerResponse.createServerResponseBySucess(question,null);
    }

    @Override
    public ServerResponse forgetCheckAnswer(String username, String question,String answer) {
        //1.参数校验
        if (username== null || username.equals("")) {
            return ServerResponse.createServerResponseByFail(100, "用户名不能为空");
        }
        if (question == null || question.equals("")) {
            return ServerResponse.createServerResponseByFail(100, "问题不能为空");
        }
        if (answer==null||answer.equals("")){
            return ServerResponse.createServerResponseByFail(100,"答案不能为空");
        }
        //2.根据用户名,
        int result=userInfoMapper.selectByUsernameAndQuestionAndAnswer(username,question,answer);
        if (result==0){
            return ServerResponse.createServerResponseByFail(1,"问题答案错误");
        }
        //3.服务端生成一个token保存并将token返回给客户端
        String forgetToken= UUID.randomUUID().toString();
        TokenCache.set(username,forgetToken);
        return ServerResponse.createServerResponseBySucess(forgetToken,null);
    }

    @Override
    public ServerResponse forgetResetPassword(String username, String passwordNew, String forgetToken) {
        //1.参数校验
        if (username == null || username.equals("")) {
            return ServerResponse.createServerResponseByFail(100, "用户名不能为空");
        }
        if (passwordNew == null || passwordNew.equals("")) {
            return ServerResponse.createServerResponseByFail(100, "密码不能为空");
        }
        if (forgetToken == null || forgetToken.equals("")) {
            return ServerResponse.createServerResponseByFail(100, "Token不能为空");

        }

        //2.token校验
        String token=TokenCache.get(username);
        if (token==null){
            return ServerResponse.createServerResponseByFail(103,"token已失效");
        }
        if (!token.equals(forgetToken)){
            return ServerResponse.createServerResponseByFail(104,"非法的token");
        }
        //3.修改密码

        int result=userInfoMapper.updateUserPassword(username,MD5Utils.getMD5Code(passwordNew));
        if (result>0){
            return ServerResponse.createServerResponseBySucess("修改成功",null);
        }


        return ServerResponse.createServerResponseByFail(1,"修改失败");
    }

    @Override
    public ServerResponse ResetPassword(String passwordOld, String passwordNew,String username) {
        if (passwordOld == null || passwordOld.equals("")) {
            return ServerResponse.createServerResponseByFail(100, "旧密码不能为空");
        }
        if (passwordNew == null || passwordNew.equals("")) {
            return ServerResponse.createServerResponseByFail(100, "新密码不能为空");
        }
        UserInfo userInfo=userInfoMapper.findUser(username,MD5Utils.getMD5Code(passwordOld));
        if (userInfo==null){
            return ServerResponse.createServerResponseByFail(1,"旧密码输入错误");
        }

        userInfo.setPassword(MD5Utils.getMD5Code(passwordNew));
        int count=userInfoMapper.updateByPrimaryKey(userInfo);
        if (count>0){
            return ServerResponse.createServerResponseBySucess("修改成功",null);
        }
        return ServerResponse.createServerResponseByFail(2,"修改失败");
    }

    @Override
    public ServerResponse updateInformation(UserInfo userInfo) {
        if (userInfo.getEmail() == null || userInfo.getEmail().equals("")) {
            return ServerResponse.createServerResponseByFail(100, "邮箱不能为空");
        }
        if (userInfo.getPhone() == null || userInfo.getPhone().equals("")) {
            return ServerResponse.createServerResponseByFail(100, "电话不能为空");
        }
        if (userInfo.getQuestion() == null || userInfo.getQuestion().equals("")) {
            return ServerResponse.createServerResponseByFail(100, "密保不能为空");
        }
        if (userInfo.getAnswer() == null || userInfo.getAnswer().equals("")) {
            return ServerResponse.createServerResponseByFail(100, "答案不能为空");
        }
//        if (userInfo==null){
//            return ServerResponse.createServerResponseByFail(100,"参数不能为空");
//        }
        int id=userInfo.getId();
        UserInfo user=userInfoMapper.selectByPrimaryKey(id);
        user.setEmail(userInfo.getEmail());
        user.setPhone(userInfo.getPhone());
        user.setQuestion(userInfo.getQuestion());
        user.setAnswer(userInfo.getAnswer());
        int result = userInfoMapper.updateByPrimaryKey(user);
        if (result > 0) {
            return ServerResponse.createServerResponseBySucess("修改成功", null);
        }
            return ServerResponse.createServerResponseByFail(2, "修改失败");

    }

    @Override
    public ServerResponse manager_Login(String username, String password) {
        if (username == null || username.equals("")) {
            return ServerResponse.createServerResponseByFail(100, "用户名不能为空");
        }
        if (password == null || password.equals("")) {
            return ServerResponse.createServerResponseByFail(100, "密码不能为空");
        }
        //step2:判断用户名是否存在

        int username_result = userInfoMapper.exsitsUsername(username);

        if (username_result == 0) {//用户名不存在
            return ServerResponse.createServerResponseByFail(101, "用户名不存在");
        }

        //step3: 根据用户名和密码登录
        UserInfo userinfo_result = userInfoMapper.findUser(username,password);
        if (userinfo_result == null) {

            return ServerResponse.createServerResponseByFail(1, "密码错误");
        }

        userinfo_result.setPassword("");



        //step4: 判断权限

        if(userinfo_result.getRole()==0){//管理员
            return ServerResponse.createServerResponseBySucess(userinfo_result);
        }

        return ServerResponse.createServerResponseByFail(2,"没有权限访问");
    }

    @Override
    public UserInfo findUserById(Integer userid) {
        return userInfoMapper.selectByPrimaryKey(userid);
    }
}


