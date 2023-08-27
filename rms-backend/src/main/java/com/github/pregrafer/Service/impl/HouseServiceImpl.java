package com.github.pregrafer.Service.impl;

import com.github.pregrafer.Mapper.HouseMapper;
import com.github.pregrafer.Pojo.House;
import jakarta.annotation.Resource;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.github.pregrafer.Service.HouseService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class HouseServiceImpl implements HouseService {

    @Resource
    HouseMapper houseMapper;

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
                    house.setRent_or_sale(row.getCell(6).getNumericCellValue());
                    house.setVacancy_status(row.getCell(7).toString());
                    house.setNote(row.getCell(8).toString());

                    houseMapper.addHouse(house);
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
