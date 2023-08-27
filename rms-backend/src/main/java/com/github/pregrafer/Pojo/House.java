package com.github.pregrafer.Pojo;

import lombok.Data;

@Data
public class House {
    int house_id;
    String place_name;
    int unit_number, building_number, floor, door_number;
    double area, rent_or_sale;
    String vacancy_status, note;
}
