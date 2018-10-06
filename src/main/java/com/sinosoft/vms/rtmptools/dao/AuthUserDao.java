package com.sinosoft.vms.rtmptools.dao;

import com.sinosoft.vms.rtmptools.entity.AuthUser;

import java.util.List;

public interface AuthUserDao {

    List<AuthUser> getAuthUserList();
}
