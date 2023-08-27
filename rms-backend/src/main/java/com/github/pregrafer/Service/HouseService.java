package com.github.pregrafer.Service;

import org.springframework.web.multipart.MultipartFile;


public interface HouseService {
    boolean updateHouses(MultipartFile file);

    boolean deleteHouse(int house_id);
}
