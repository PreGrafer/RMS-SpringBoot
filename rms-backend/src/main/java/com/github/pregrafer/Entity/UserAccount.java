package com.github.pregrafer.Entity;

import lombok.Data;

@Data
public class UserAccount {
    int userid;
    String username, password, phone, personid, role, name, gender;
    int age;
    String health_status, education_background, work_experience, old_house, note;
}
