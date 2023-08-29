package com.github.pregrafer.Service;

import com.github.pregrafer.Entity.HouseApplication;
import com.github.pregrafer.Entity.RegisterAccount;

public interface RegisterService {
    boolean passRegister(RegisterAccount registerAccount);

    boolean rejectRegister(String username);

}
