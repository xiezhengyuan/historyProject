package com.hxy.isw.service;

import java.util.Map;

import com.hxy.isw.entity.AccountInfo;

public interface PermissionsService {
	
	Map<String, Object> findPermissionsInfo(AccountInfo ai,int start,int limit);
	
	Map<String, Object> addPermissions(AccountInfo ai,String id,String username,String password,String mobile,String name,String menu);

	Map<String, Object> queryMenu(AccountInfo ai,String id);
	
	void ban(AccountInfo ai,String id);
	
	void unban(AccountInfo ai,String id);
}
