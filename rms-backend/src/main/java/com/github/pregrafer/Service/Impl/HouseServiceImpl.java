package com.github.pregrafer.Service.Impl;

import com.github.pregrafer.Entity.HouseApplication;
import com.github.pregrafer.Entity.UseHouseInfo;
import com.github.pregrafer.Mapper.HouseMapper;
import com.github.pregrafer.Entity.House;
import com.github.pregrafer.Mapper.UseHouseMapper;
import jakarta.annotation.Resource;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.github.pregrafer.Service.HouseService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.temporal.ChronoUnit;

@Service
public class HouseServiceImpl implements HouseService {

    @Resource
    HouseMapper houseMapper;
    @Resource
    UseHouseMapper useHouseMapper;

    @Override
    public boolean passApplication(UseHouseInfo useHouseInfo) {
        return houseMapper.updateStatus(useHouseInfo.getHouse_id(), "已使用:" + useHouseInfo.getUsername()) && useHouseMapper.updateStatus(useHouseInfo.getApplication_id(), "已审批");
    }

    @Override
    public boolean rejectApplication(UseHouseInfo useHouseInfo) {
        return houseMapper.updateStatus(useHouseInfo.getHouse_id(), "未使用") && useHouseMapper.updateStatus(useHouseInfo.getApplication_id(), "未通过");
    }

    @Override
    public boolean postApplication(HouseApplication houseApplication) {
        House houseById = houseMapper.findHouseById(houseApplication.getHouse_id());
        String rentOrSale = houseById.getRent_or_sale();
        if (rentOrSale.equals("出租")) {
            double pricePerMonth = houseById.getPrice();
            long monthsBetween = ChronoUnit.MONTHS.between(
                    houseApplication.getStart_date(), houseApplication.getEnd_date());

            // 如果不足一个月，按一个月计算租金
            if (houseApplication.getStart_date().plusMonths(monthsBetween).isBefore(houseApplication.getEnd_date())) {
                monthsBetween++;
            }

            double totalAmount = pricePerMonth * monthsBetween;
            houseApplication.setMoney(totalAmount);
        } else if (rentOrSale.equals("售卖")) {
            houseApplication.setMoney(houseById.getPrice());
        }
        return houseMapper.updateStatus(houseApplication.getHouse_id(), "审批中") && useHouseMapper.postApplication(houseApplication);
    }

    @Override
    public boolean updateHouseInfo(House house) {
        return houseMapper.updateHouseInfo(house);
    }

    @Override
    public boolean deleteHouse(int house_id) {
        return houseMapper.deleteHouseById(house_id);
    }

    @Override
    public boolean updateHouses(MultipartFile file) {
        try {
            Workbook workbook = new XSSFWorkbook(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);

            // 跳过标题行，从第二行开始读取数据
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    House house = new House();
                    house.setPlace_name(row.getCell(0).toString());
                    house.setUnit_number((int) row.getCell(1).getNumericCellValue());
                    house.setBuilding_number((int) row.getCell(2).getNumericCellValue());
                    house.setFloor((int) row.getCell(3).getNumericCellValue());
                    house.setDoor_number((int) row.getCell(4).getNumericCellValue());
                    house.setArea(row.getCell(5).getNumericCellValue());
                    house.setRent_or_sale(row.getCell(6).toString());
                    house.setPrice(row.getCell(7).getNumericCellValue());
                    house.setVacancy_status(row.getCell(8).toString());
                    house.setNote(row.getCell(9).toString());

                    houseMapper.addHouse(house);
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
