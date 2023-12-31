package com.github.pregrafer.Mapper;

import com.github.pregrafer.Entity.RegisterAccount;
import com.github.pregrafer.Entity.SimpleUserInfo;
import com.github.pregrafer.Entity.UserAccount;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM user_account WHERE userid= #{userid}")
    UserAccount findUserById(int userid);

    @Select("SELECT userid,username,role FROM user_account WHERE username= #{username}")
    SimpleUserInfo getUserInfo(String username);

    @Delete("DELETE FROM user_account WHERE userid= #{userid}")
    boolean deleteUserById(int userid);

    @Select("SELECT * FROM user_account")
    List<UserAccount> getAccounts();

    @Update("UPDATE user_account SET " +
            "name = #{name}, " +
            "gender = #{gender}, " +
            "age = #{age}, " +
            "health_status = #{health_status}, " +
            "education_background = #{education_background}, " +
            "work_experience = #{work_experience}, " +
            "old_house = #{old_house}, " +
            "note = #{note} " +
            "WHERE username = #{username} AND phone = #{phone} AND personid = #{personid}")
    boolean updateUserAccountInfo(UserAccount userAccount);

    @Insert("INSERT INTO user_account (username, password, phone, personid, role) VALUES " +
            "(#{username}, #{password}, #{phone}, #{personid}, #{role})")
    boolean insertUserAccount(RegisterAccount registerAccount);

    @Select("select * from user_account where username = #{text} or phone = #{text}")
    UserAccount findAccountByNameOrPhone(String text);

    @Select("SELECT COUNT(*) FROM user_account WHERE username = #{username} OR phone = #{phone} OR personid = #{personid}")
    int user_accountCheck(String username, String phone, String personid);

    @Select("SELECT COUNT(*) FROM register_list WHERE username = #{username} OR phone = #{phone} OR personid = #{personid}")
    int register_listCheck(String username, String phone, String personid);

    @Update("UPDATE user_account SET password = #{password} WHERE username = #{username} AND phone = #{phone} AND personid = #{personid}")
    boolean resetPassword(String username, String password, String phone, String personid);

    @Select("SELECT role FROM user_account WHERE username = #{username}")
    String getUseRoleByUserName(String username);
}
