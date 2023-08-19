package com.github.pregrafer.Mapper;

import com.github.pregrafer.Entity.Account;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("select * from user_account where username = #{text} or phone = #{text}")
    Account findAccountByNameOrPhone(String text);

    @Insert("INSERT INTO register_list(username, password, phone, personid, role) VALUES(#{username}, #{password}, #{phone}, #{personid}, #{role})")
    boolean registerPost(String username, String password, String phone, String personid, String role);

    @Select("SELECT COUNT(*) FROM user_account WHERE username = #{username} OR phone = #{phone} OR personid = #{personid}")
    int user_accountCheck(String username, String phone, String personid);

    @Select("SELECT COUNT(*) FROM register_list WHERE username = #{username} OR phone = #{phone} OR personid = #{personid}")
    int register_listCheck(String username, String phone, String personid);
}
