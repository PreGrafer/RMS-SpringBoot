package com.github.pregrafer.Service;

import com.github.pregrafer.Entity.UserAccount;

public interface AccountService {
    boolean deleteUser(int userid);

    boolean updateUserInfo(UserAccount userAccount);
}
