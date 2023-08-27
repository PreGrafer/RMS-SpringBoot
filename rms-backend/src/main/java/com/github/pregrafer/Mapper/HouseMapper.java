package com.github.pregrafer.Mapper;

import com.github.pregrafer.Pojo.House;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface HouseMapper {
    @Delete("DELETE FROM houses WHERE house_id = #{house_id}")
    boolean deleteHouseById(int house_id);

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
            "#{vacancy_status}, " +
            "#{note})")
    void addHouse(House house);
}
