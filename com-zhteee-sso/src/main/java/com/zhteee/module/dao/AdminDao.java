package com.zhteee.module.dao;

import com.zhteee.module.entity.Admin;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface AdminDao {

	Map<String, Object> login(Admin admin);

	Map<String, Object> valiPwd(Admin admin);

}
