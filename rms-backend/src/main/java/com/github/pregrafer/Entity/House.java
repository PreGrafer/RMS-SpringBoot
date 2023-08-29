package com.github.pregrafer.Entity;

import lombok.Data;

@Data
public class House {
    int house_id;
    String place_name;
    int unit_number, building_number, floor, door_number;
    double area;
    String rent_or_sale;
    double price;
    String vacancy_status, note;
}
