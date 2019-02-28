package com.hxy.isw.service;

import java.util.List;
import java.util.Map;

import com.hxy.isw.entity.AccountInfo;

public interface ReportformService {

	public int countmoneyreportforms(String companyid, String gengalid, String salesmanid, String starttime,
			String endtime, String userinfo, AccountInfo acc) throws Exception;

	public List<Map<String, Object>> moneyreportforms(String companyid, String gengalid, String salesmanid,
			String starttime, String endtime, String userinfo, AccountInfo acc, int start, int limit) throws Exception;

	public int countgoldsreportforms(String companyid, String gengalid, String salesmanid, String userinfo,
			AccountInfo acc) throws Exception;

	public List<Map<String, Object>> goldsreportforms(String companyid, String gengalid, String salesmanid,
			String starttime, String endtime, String userinfo, AccountInfo acc, int start, int limit) throws Exception;

	String outportcashlog(String companyid, String gengalid,String salesmanid,String starttime,String endtime,String userinfo,AccountInfo acc) throws Exception;
	
	String outportinmoneylog(String companyid, String gengalid,String salesmanid,String starttime,String endtime,String userinfo,AccountInfo acc) throws Exception;
}
