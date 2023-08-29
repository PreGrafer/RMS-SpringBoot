package com.github.pregrafer.Controller;

import com.github.pregrafer.Mapper.HouseMapper;
import com.github.pregrafer.Entity.House;
import com.github.pregrafer.Entity.RestBean;
import com.github.pregrafer.Service.HouseService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/house")
public class HouseController {
    @Resource
    HouseService houseService;
    @Resource
    HouseMapper houseMapper;

    @GetMapping("/get-info")
    public House getHouseInfo(@RequestParam("house_id") int house_id) {
        return houseMapper.findHouseById(house_id);
    }

    @PostMapping("/update-info")
    public RestBean<String> updateHouseInfo(@RequestBody House house) {
        if (houseService.updateHouseInfo(house))
            return RestBean.success("信息保存成功!");
        else
            return RestBean.failure(411, "信息保存失败!");
    }

    @PostMapping("/update-file")
    public RestBean<String> updateHouses(@RequestParam("file") MultipartFile file) {
        if (houseService.updateHouses(file))
            return RestBean.success("上传成功!");
        else
            return RestBean.failure(408, "上传或文件处理失败!");
    }

    @PostMapping("/delete-house")
    public RestBean<String> deleteHouse(@RequestParam("house_id") int house_id) {
        if (houseService.deleteHouse(house_id))
            return RestBean.success("删除成功!");
        else
            return RestBean.failure(409, "删除失败!");
    }

    @GetMapping("/get-houses")
    public List<House> getHouses() {
        return houseMapper.getHouses();
    }

    @GetMapping("/get-unUsedHouses")
    public List<House> getUnUsedHouses() {
        return houseMapper.getUnUsedHouses();
    }
}
