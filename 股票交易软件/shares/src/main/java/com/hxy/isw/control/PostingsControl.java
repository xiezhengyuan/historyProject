package com.hxy.isw.control;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.JsonArray;
import com.hxy.isw.entity.UserInfo;
import com.hxy.isw.service.PostingsService;
import com.hxy.isw.util.ConstantUtil;
import com.hxy.isw.util.JsonUtil;

@Controller
@RequestMapping("/postings")
public class PostingsControl {
	
	@Autowired
	PostingsService postingsService;
	//查找帖子
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	@RequestMapping(method = RequestMethod.POST, value = "/querypostings")
	public void querypicture(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			int start = request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page"));
			int limit = request.getParameter("rows")==null?ConstantUtil.LIMIT:Integer.parseInt(request.getParameter("rows"));
			String keyword =request.getParameter("keyword");
			System.out.println(keyword);
			Map<String,Object> map = postingsService.searchPostings(start, keyword, limit);
			JsonUtil.mapToJson(map, response);
		} catch (Exception e) {
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\"\"}");
		}
		
	}
	//查找禁言用户
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	@RequestMapping(method = RequestMethod.POST, value = "/querybanneruser")
	public void querybanneruser(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			int start = request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page"));
			int limit = request.getParameter("rows")==null?ConstantUtil.LIMIT:Integer.parseInt(request.getParameter("rows"));
			String keyword =request.getParameter("keyword");
			System.out.println(keyword);
			Map<String,Object> map = postingsService.querybanneruser(start, keyword, limit);
			JsonUtil.mapToJson(map, response);
		} catch (Exception e) {
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\"\"}");
		}
		
	}
	//将用户禁言
	@RequestMapping(method = RequestMethod.POST, value = "/disable")
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void disable(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			String id = request.getParameter("id");
			postingsService.disable(id);
			JsonUtil.success2page(response);
		} catch (Exception e) {
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\"\"}");
		}
	}
	
	//话题列表
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	@RequestMapping(method = RequestMethod.POST, value = "/queryTopic")
	public void queryTopic(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			int start = request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page"));
			int limit = request.getParameter("rows")==null?ConstantUtil.LIMIT:Integer.parseInt(request.getParameter("rows"));
			String keyword =request.getParameter("keyword");
			String fpostingstyleid=request.getParameter("fpostingstyleid");
			Map<String,Object> map = postingsService.searchTopic(start, keyword, limit,fpostingstyleid);
			JsonUtil.mapToJson(map, response);
		} catch (Exception e) {
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\"\"}");
		}
	}
	//删除帖子
	@RequestMapping(method = RequestMethod.POST, value = "/deleteTopic")
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void deleteTopic(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			String id = request.getParameter("id");
			postingsService.deleteTopic(id);
			JsonUtil.success2page(response);
		} catch (Exception e) {
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\"\"}");
		}
	}
	//根据id查找帖子详情图片
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	@RequestMapping(method = RequestMethod.POST, value = "/queryTopicbyid")
	public void queryTopicbyid(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			
			String id =request.getParameter("id");
			JsonArray arr = postingsService.queryTopicbyid(id);
			JsonUtil.listToJson(arr, arr.size(), response);
		} catch (Exception e) {
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\"\"}");
		}
	}
	
	//根据id查找帖子详情
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
		@RequestMapping(method = RequestMethod.POST, value = "/queryTopicbyid2")
		public void queryTopicbyid2(HttpServletRequest request,HttpServletResponse response,HttpSession session){
			try {
				
				String id =request.getParameter("id");
				Map<String, Object> map = postingsService.queryTopicbyid2(id);
				JsonUtil.mapToJson(map, response);
			} catch (Exception e) {
				e.printStackTrace();
				JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\"\"}");
			}
		}
		//修改帖子
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
		@RequestMapping(method = RequestMethod.POST, value = "/modifypostings")
		public void modifypostings(HttpServletRequest request,HttpServletResponse response,HttpSession session){
			try {
				
				String id =request.getParameter("id");
				String imgarr =request.getParameter("imgarr");
				String flag =request.getParameter("flag");
				String postingscontent =request.getParameter("postingscontent");
				
				
				postingsService.modifypostings(imgarr, flag, postingscontent, id);
				JsonUtil.success2page(response);
			} catch (Exception e) {
				e.printStackTrace();
				JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\"\"}");
			}
		}
		
		//接触禁言
		@RequestMapping(method = RequestMethod.POST, value = "/relievebanner")
		@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
		public void relievebanner(HttpServletRequest request,HttpServletResponse response,HttpSession session){
			try {
				String id = request.getParameter("id");
				postingsService.relievebanner(id);
				JsonUtil.success2page(response);
			} catch (Exception e) {
				e.printStackTrace();
				JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\"\"}");
			}
		}
		//新增帖子
		@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
		@RequestMapping(method = RequestMethod.POST, value = "/addpostings")
		public void addpostings(HttpServletRequest request,HttpServletResponse response,HttpSession session){
			try {
				
				
				/*String id =request.getParameter("id");*/
				String id =request.getParameter("id");
				String imgarr =request.getParameter("imgarr");
				String flag =request.getParameter("flag");
				String praise=request.getParameter("praise");
				String reward=request.getParameter("reward");
				String share=request.getParameter("share");
				String title=request.getParameter("title");
				String fpostingstyleid=request.getParameter("fpostingstyleid");
				System.out.println("share"+"wwwwwwwwwwwwwwwwww");
				String postingscontent =request.getParameter("postingscontent");
				
				
				postingsService.addpostings(imgarr, flag, postingscontent, id, praise, reward, share,title,fpostingstyleid);
				JsonUtil.success2page(response);
			} catch (Exception e) {
				e.printStackTrace();
				JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\"\"}");
			}
		}
		//评论列表
		@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
		@RequestMapping(method = RequestMethod.POST, value = "/querycomment")
		public void querycomment(HttpServletRequest request,HttpServletResponse response,HttpSession session){
			try {
				int start = request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page"));
				int limit = request.getParameter("rows")==null?ConstantUtil.LIMIT:Integer.parseInt(request.getParameter("rows"));
				String keyword =request.getParameter("keyword");
				String id =request.getParameter("id");
				Map<String, Object> map = postingsService.querycomment(start, keyword, limit,id);
				JsonUtil.mapToJson(map, response);
			} catch (Exception e) {
				e.printStackTrace();
				JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\"\"}");
			}
		}
		
		//删除评论
				@RequestMapping(method = RequestMethod.POST, value = "/deleteComment")
				@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
				public void deleteComment(HttpServletRequest request,HttpServletResponse response,HttpSession session){
					try {
						String id = request.getParameter("id");
						postingsService.deleteComment(id);
						JsonUtil.success2page(response);
					} catch (Exception e) {
						e.printStackTrace();
						JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\"\"}");
					}
				}
				//根据id查找评论内容
				@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
				@RequestMapping(method = RequestMethod.POST, value = "/querycommentbyid")
				public void querycommentbyid(HttpServletRequest request,HttpServletResponse response,HttpSession session){
					try {
						
						String id =request.getParameter("id");
						
						Map<String, Object> map =postingsService.querycommentbyid(id);
						
						
						
						JsonUtil.mapToJson(map, response);
					} catch (Exception e) {
						e.printStackTrace();
						JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\"\"}");
					}
				}
				//修改评论
				@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
				@RequestMapping(method = RequestMethod.POST, value = "/modifypostingcoment")
				public void modifypostingcoment(HttpServletRequest request,HttpServletResponse response,HttpSession session){
					try {
						
						String id =request.getParameter("id");
						
						String postingscontent =request.getParameter("postingscontent");
						
						
						postingsService.modifypostingcoment(postingscontent, id);
						JsonUtil.success2page(response);
					} catch (Exception e) {
						e.printStackTrace();
						JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\"\"}");
					}
				}
				
				//查找分类名字
				@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
				@RequestMapping(method = RequestMethod.POST, value = "/queryPostingStyle")
				public void queryPostingStyle(HttpServletRequest request,HttpServletResponse response,HttpSession session){
					try {
						
						
						

						JsonArray arr = postingsService.queryPostingStyle();
						
						JsonUtil.listToJson(arr, arr.size(), response);
					} catch (Exception e) {
						e.printStackTrace();
						JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\"\"}");
					}
					
				}
				
				//分类名称
				@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
				@RequestMapping(method = RequestMethod.POST, value = "/queryStyle")
				public void queryStyle(HttpServletRequest request,HttpServletResponse response,HttpSession session){
					try {
						int start = request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page"));
						int limit = request.getParameter("rows")==null?ConstantUtil.LIMIT:Integer.parseInt(request.getParameter("rows"));
						
						Map<String,Object> map = postingsService.queryStyle(start,limit);
						JsonUtil.mapToJson(map, response);
					} catch (Exception e) {
						e.printStackTrace();
						JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\"\"}");
					}
				}
				
				//删除分类名称
				@RequestMapping(method = RequestMethod.POST, value = "/deleteStyle")
				@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
				public void deleteStyle(HttpServletRequest request,HttpServletResponse response,HttpSession session){
					try {
						String id = request.getParameter("id");
						postingsService.deleteStyle(id);
						JsonUtil.success2page(response);
					} catch (Exception e) {
						e.printStackTrace();
						JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\"\"}");
					}
				}
				
				//新增分类名称
				@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
				@RequestMapping(method = RequestMethod.POST, value = "/addStyle")
				public void addStyle(HttpServletRequest request,HttpServletResponse response,HttpSession session){
					try {
						String name = request.getParameter("name");
						Map<String, Object> map=postingsService.addStyle(name);
						JsonUtil.mapToJson(map, response);
					} catch (Exception e) {
						e.printStackTrace();
						JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\"\"}");
					}
				}
				
				//根据id 来查找分类名称
				@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
				@RequestMapping(method = RequestMethod.POST, value = "/queryStyleById")
				public void queryStyleById(HttpServletRequest request,HttpServletResponse response,HttpSession session){
					try {
						String id = request.getParameter("id");
						Map<String, Object> map=postingsService.queryStyleById(id);
						JsonUtil.mapToJson(map, response);
					} catch (Exception e) {
						e.printStackTrace();
						JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\"\"}");
					}
				}
				
				//修改分类名称
				@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
				@RequestMapping(method = RequestMethod.POST, value = "/modifypostingStyle")
				public void modifypostingStyle(HttpServletRequest request,HttpServletResponse response,HttpSession session){
					try {
						
						String id =request.getParameter("id");
						
						String name =request.getParameter("postingscontent");
						
						
						postingsService.modifypostingStyle(name, id);
						JsonUtil.success2page(response);
					} catch (Exception e) {
						e.printStackTrace();
						JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\"\"}");
					}
				}
}
