package com.github.pregrafer.Controller;

import com.github.pregrafer.Entity.HouseApplication;
import com.github.pregrafer.Mapper.HouseMapper;
import com.github.pregrafer.Mapper.UseHouseMapper;
import jakarta.annotation.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import java.time.LocalDateTime;
import java.util.List;

@Component
public class SchedulingTask {
    @Resource
    UseHouseMapper useHouseMapper;
    @Resource
    HouseMapper houseMapper;

    @Scheduled(cron = "1 0 16 * * ?")
    private void updateApplicationStatus() {
        List<HouseApplication> applications = useHouseMapper.getAllApplications();
        for (HouseApplication app : applications) {
            if (houseMapper.findHouseById(app.getHouse_id()).getRent_or_sale().equals("出租") && app.getStatus().equals("已审批")) {
                if (app.getEnd_date().isBefore(LocalDateTime.now())) {
                    houseMapper.updateStatus(app.getHouse_id(), "未使用");
                    useHouseMapper.updateStatus(app.getApplication_id(), "已过期");
                }
            }
            if (app.getStatus().equals("未审批")) {
                if ((app.getApplication_date().plusDays(7)).isBefore(LocalDateTime.now())) {
                    houseMapper.updateStatus(app.getHouse_id(), "未使用");
                    useHouseMapper.updateStatus(app.getApplication_id(), "已过期");
                }
            }
        }
    }

}
