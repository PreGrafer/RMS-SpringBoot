package com.github.pregrafer.Controller;

import com.github.pregrafer.Entity.*;
import com.github.pregrafer.Mapper.HouseMapper;
import com.github.pregrafer.Mapper.UseHouseMapper;
import com.github.pregrafer.Mapper.UserMapper;
import com.github.pregrafer.Service.HouseService;
import jakarta.annotation.Resource;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/use-house")
public class UseHouseController {
    @Resource
    UseHouseMapper useHouseMapper;
    @Resource
    HouseMapper houseMapper;
    @Resource
    UserMapper userMapper;
    @Resource
    HouseService houseService;

    @PostMapping("/pass-application")
    public RestBean<String> passApplication(@RequestBody UseHouseInfo useHouseInfo) {
        if (houseService.passApplication(useHouseInfo))
            return RestBean.success("审批成功!已通过");
        else
            return RestBean.failure(412, "审批失败!");
    }

    @PostMapping("/reject-application")
    public RestBean<String> rejectApplication(@RequestBody UseHouseInfo useHouseInfo) {
        if (houseService.rejectApplication(useHouseInfo))
            return RestBean.success("审批成功!已拒绝");
        else
            return RestBean.failure(413, "审批失败!");
    }

    @GetMapping("/get-applications")
    public List<UseHouseInfo> getApplications() {
        List<HouseApplication> apps = useHouseMapper.getApplications();
        List<UseHouseInfo> applications = new ArrayList<>();

        for (HouseApplication app : apps) {
            House house = houseMapper.findHouseById(app.getHouse_id());
            UserAccount user = userMapper.findUserById(app.getUser_id());
            UseHouseInfo useHouseInfo = new UseHouseInfo();
            useHouseInfo.setApplication_id(app.getApplication_id());
            useHouseInfo.setUser_id(app.getUser_id());
            useHouseInfo.setUsername(user.getUsername());
            useHouseInfo.setHouse_id(app.getHouse_id());
            useHouseInfo.setPlace_name(house.getPlace_name());
            useHouseInfo.setUnit_number(house.getUnit_number());
            useHouseInfo.setBuilding_number(house.getBuilding_number());
            useHouseInfo.setFloor(house.getFloor());
            useHouseInfo.setDoor_number(house.getDoor_number());
            useHouseInfo.setRent_or_sale(house.getRent_or_sale());
            useHouseInfo.setApplication_date(app.getApplication_date());
            useHouseInfo.setStart_date(app.getStart_date());
            useHouseInfo.setEnd_date(app.getEnd_date());
            useHouseInfo.setStatus(app.getStatus());
            useHouseInfo.setMoney(app.getMoney());
            useHouseInfo.setNote(app.getNote());
            
            applications.add(useHouseInfo);
        }

        return applications;
    }

    @PostMapping("/post-application")
    public RestBean<String> postApplication(@RequestBody HouseApplication houseApplication) {
        if (houseService.postApplication(houseApplication))
            return RestBean.success("申请成功!");
        else
            return RestBean.failure(412, "申请失败!");
    }

    @GetMapping("/get-house-applications")
    public List<UseHouseInfo> getHouseApplication(@RequestParam("userid") int userid) {
        List<HouseApplication> houseApplications = useHouseMapper.getHouseApplicationsByUserId(userid);
        List<UseHouseInfo> useHouseInfos = new ArrayList<>();

        for (HouseApplication app : houseApplications) {
            House house = houseMapper.findHouseById(app.getHouse_id());
            UserAccount user = userMapper.findUserById(app.getUser_id());
            UseHouseInfo useHouseInfo = new UseHouseInfo();
            useHouseInfo.setApplication_id(app.getApplication_id());
            useHouseInfo.setUser_id(app.getUser_id());
            useHouseInfo.setUsername(user.getUsername());
            useHouseInfo.setHouse_id(app.getHouse_id());
            useHouseInfo.setPlace_name(house.getPlace_name());
            useHouseInfo.setUnit_number(house.getUnit_number());
            useHouseInfo.setBuilding_number(house.getBuilding_number());
            useHouseInfo.setFloor(house.getFloor());
            useHouseInfo.setDoor_number(house.getDoor_number());
            useHouseInfo.setRent_or_sale(house.getRent_or_sale());
            useHouseInfo.setApplication_date(app.getApplication_date());
            useHouseInfo.setStart_date(app.getStart_date());
            useHouseInfo.setEnd_date(app.getEnd_date());
            useHouseInfo.setStatus(app.getStatus());
            useHouseInfo.setMoney(app.getMoney());
            useHouseInfo.setNote(app.getNote());

            useHouseInfos.add(useHouseInfo);
        }

        return useHouseInfos;
    }

}
