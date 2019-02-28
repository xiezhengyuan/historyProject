package com.hxy.isw.thread;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.hxy.isw.entity.FileInfo;
import com.hxy.isw.util.ConstantUtil;
import com.hxy.isw.util.DatabaseHelper;
import com.hxy.isw.util.Sys;

public class CheckFileState implements Runnable {
	SimpleDateFormat sdf = null;
	public static DatabaseHelper databaseHelper;

	@Override
	public void run() {
		// TODO Auto-generated method stub

		try {
			long diff = ConstantUtil.setThreadStartTimer("00", "30", null);

			if (diff < 0) {// 凌晨0点30分之前则等待
				Thread.sleep(0 - diff);
				checkFileState();
			} else {// 凌晨0点30分后则立即启动
				checkFileState();
			}

			while (true) {
				long diff11 = ConstantUtil.setThreadStartTimer("00", "30", null);
				Thread.sleep(1000l * 60 * 60 * 24 - diff11);
				checkFileState();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void checkFileState() {
		Date now = new Date();
		Long l_now = now.getTime();
		String hql = "select fi from FileInfo fi where fi.state = 0";
		List<Object> lst = databaseHelper.getResultListByHql(hql);
		for (Object object : lst) {
			FileInfo fi = (FileInfo) object;
			Long time = fi.getTime().getTime();
			// 将超过3天没有被引用的文件删除
			if (l_now - time > 1000l * 60 * 60 * 24 * 3) {
				//Sys.out("not use file exipres::need to del:filename..." + fi.getFilename());

				String path = ConstantUtil.environment.equals("tomcat")
						? ConstantUtil.PROJECT_PATH.replace("/WEB-INF/classes/", "")
						: ConstantUtil.PROJECT_PATH.replace("/target/classes/", "/src/main/webapp");

				boolean flag = ConstantUtil.deleteFile(path + fi.getUrl());
				if (flag) {
					fi.setState(-1);
					databaseHelper.updateObject(fi);
					//Sys.out("not use file exipres:::filename..." + fi.getFilename() + "....del..success");
				}
			}
		}
	}

}
