package com.github.pregrafer.Entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HouseApplication {
    int application_id, user_id, house_id;
    LocalDateTime application_date, start_date, end_date;
    String status;
    double money;
    String note;
}
