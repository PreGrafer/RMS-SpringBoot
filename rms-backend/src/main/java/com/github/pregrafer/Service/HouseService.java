package com.github.pregrafer.Service;

import com.github.pregrafer.Entity.House;
import com.github.pregrafer.Entity.HouseApplication;
import com.github.pregrafer.Entity.UseHouseInfo;
import org.springframework.web.multipart.MultipartFile;


public interface HouseService {
    boolean updateHouses(MultipartFile file);

    boolean deleteHouse(int house_id);

    boolean updateHouseInfo(House house);

    boolean postApplication(HouseApplication houseApplication);

    boolean passApplication(UseHouseInfo useHouseInfo);

    boolean rejectApplication(UseHouseInfo useHouseInfo);
}
