package com.github.pregrafer.Mapper;

import com.github.pregrafer.Entity.HouseApplication;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UseHouseMapper {
    @Select("SELECT * FROM house_applications")
    List<HouseApplication> getAllApplications();

    @Update("UPDATE house_applications SET status = #{status} WHERE application_id = #{application_id}")
    boolean updateStatus(int application_id, String status);

    @Select("SELECT * FROM house_applications WHERE status = '未审批'")
    List<HouseApplication> getApplications();

    @Select("SELECT * FROM house_applications WHERE user_id = #{user_id}")
    List<HouseApplication> getHouseApplicationsByUserId(int user_id);

    @Insert("INSERT INTO house_applications (user_id, house_id, start_date,end_date,money,note)  VALUES (#{user_id}, #{house_id}, #{start_date}, #{end_date}, #{money}, #{note})")
    boolean postApplication(HouseApplication houseApplication);
}
