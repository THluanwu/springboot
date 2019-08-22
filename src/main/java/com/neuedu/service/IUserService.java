package com.neuedu.service;

import com.neuedu.common.ServerResponse;
import com.neuedu.exception.MyException;
import com.neuedu.pojo.UserInfo;

import java.util.List;

public interface IUserService {

     ServerResponse login(String username, String password);
     ServerResponse register(UserInfo userInfo);
     ServerResponse checkValid(String str,String type);
     ServerResponse forgetGetQuestion(String username);
     ServerResponse forgetCheckAnswer(String username, String question, String answer);
     ServerResponse forgetResetPassword(String username,String passwordNew, String forgetToken);
     ServerResponse ResetPassword(String passwordOld, String passwordNew,String username);
     ServerResponse updateInformation(UserInfo userInfo);
     ServerResponse manager_Login(String username, String password);
//    public List<UserInfo> findAll()throws MyException;
//    public int deleteUser(int userId)throws MyException;
//    public int addUser(UserInfo userInfo)throws MyException;
//    public int updateUser(UserInfo userInfo)throws MyException;
      UserInfo findUserById(Integer userid);
}
