package com.hxy.isw.service;

import java.util.Map;

import com.hxy.isw.entity.AccountInfo;
import com.hxy.isw.entity.UserInfo;

public interface ExampleService {

	Map<String, Object> findExampleList(AccountInfo ai, String keyword, int start, int limit);

	Map<String, Object> findAuthstrList(AccountInfo ai, String keyword, int start, int limit);

	void ban(AccountInfo ai, String id);

	void unban(AccountInfo ai, String id);

	void pass(AccountInfo ai, String id);

	void notpass(AccountInfo ai, String id);

	Map<String, Object> addExample(AccountInfo ai, UserInfo ui);

	Map<String, Object> panicBuying(AccountInfo ai, String id, String type, int start, int limit);

	Map<String, Object> planRunning(AccountInfo ai,String id ,String type);

	Map<String, Object> pastSuccess(AccountInfo ai);

	void select(AccountInfo ai, String id);
	
}
