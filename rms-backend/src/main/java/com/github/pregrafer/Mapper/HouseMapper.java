package com.github.pregrafer.Mapper;

import com.github.pregrafer.Entity.House;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface HouseMapper {
    @Update("UPDATE houses SET vacancy_status = #{vacancy_status} WHERE house_id = #{house_id}")
    boolean updateStatus(int house_id, String vacancy_status);

    @Update("UPDATE houses SET " +
            "place_name = #{place_name}, " +
            "unit_number = #{unit_number}, " +
            "building_number = #{building_number}, " +
            "floor = #{floor}, " +
            "door_number = #{door_number}, " +
            "area = #{area}, " +
            "rent_or_sale = #{rent_or_sale}, " +
            "price = #{price}, " +
            "vacancy_status = #{vacancy_status}, " +
            "note = #{note} " +
            "WHERE house_id = #{house_id}")
    boolean updateHouseInfo(House house);

    @Select("SELECT * FROM houses WHERE house_id = #{house_id}")
    House findHouseById(int house_id);

    @Delete("DELETE FROM houses WHERE house_id = #{house_id}")
    boolean deleteHouseById(int house_id);

    @Select("SELECT * FROM houses WHERE vacancy_status = '未使用'")
    List<House> getUnUsedHouses();

    @Select("SELECT * FROM houses")
    List<House> getHouses();

    @Insert("INSERT INTO houses(" +
            "place_name, " +
            "unit_number, " +
            "building_number, " +
            "floor, " +
            "door_number, " +
            "area, " +
            "rent_or_sale, " +
            "price, " +
            "vacancy_status, " +
            "note" +
            ") VALUES(" +
            "#{place_name}, " +
            "#{unit_number}, " +
            "#{building_number}, " +
            "#{floor}, " +
            "#{door_number}, " +
            "#{area}, " +
            "#{rent_or_sale}, " +
            "#{price}, " +
            "#{vacancy_status}, " +
            "#{note})")
    void addHouse(House house);
}
