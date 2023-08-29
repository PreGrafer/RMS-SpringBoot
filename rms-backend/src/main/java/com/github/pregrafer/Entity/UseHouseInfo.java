package com.github.pregrafer.Entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UseHouseInfo {
    int application_id, user_id;
    String username;
    int house_id;
    String place_name;
    int unit_number, building_number, floor, door_number;
    String rent_or_sale;
    LocalDateTime application_date, start_date, end_date;
    String status;
    double money;
    String note;
}
