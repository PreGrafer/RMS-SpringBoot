package com.github.pregrafer.Mapper;

import com.github.pregrafer.Entity.RegisterAccount;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RegisterMapper {
    @Insert("INSERT INTO register_list(username, password, phone, personid, role) VALUES(#{username}, #{password}, #{phone}, #{personid}, #{role})")
    boolean registerPost(String username, String password, String phone, String personid, String role);

    @Delete("DELETE FROM register_list WHERE username = #{username}")
    boolean deleteByUsername(String username);

    @Select("SELECT * FROM register_list")
    List<RegisterAccount> getRegisterAccounts();

    @Select("SELECT * FROM register_list WHERE role = #{role}")
    List<RegisterAccount> getRegisterAccountsByRole(String role);
}
