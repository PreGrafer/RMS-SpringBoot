package com.github.pregrafer.Service;

import com.github.pregrafer.Pojo.RegisterAccount;

public interface RegisterService {
    boolean passRegister(RegisterAccount registerAccount);

    boolean rejectRegister(String username);
}
