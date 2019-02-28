package org.apache.jsp.html.notice;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.hxy.isw.entity.AccountInfo;

public final class notice_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("\r\n");
      out.write("<link rel=\"stylesheet\" href=\"css/bootstrap-select.css\">\r\n");
      out.write("  <script src=\"js/bootstrap-select.js\"></script>\r\n");
      out.write("<div class=\"page-header\">\r\n");
      out.write("\t<h1>\r\n");
      out.write("\t\t发布公告\r\n");
      out.write("\t\t<small>\r\n");
      out.write("\t\t\t<i class=\"ace-icon fa fa-angle-double-right\"></i>\r\n");
      out.write("\t\t\t列表\r\n");
      out.write("\t\t</small>\r\n");
      out.write("\t</h1>\r\n");
      out.write("\t <div class=\"modal-header\" style=\"margin-top:20px;\">\r\n");
      out.write("\t\t\t<table  style=\"float: left;margin-right: 5px;\">\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t<td><label for=\"username\" class=\"control-label\" style=\"margin-left:5px;\">公告标题：</label></td><td><input type=\"text\" id=\"keyword\" /></td>\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t</table>\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t<button class=\"btn btn-info btn-sm\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"查询\" onclick=\"queryempbycondition()\">\r\n");
      out.write("\t<span class=\"glyphicon glyphicon-search\"></span>\r\n");
      out.write("\t</button>\r\n");
      out.write("\t\r\n");
      out.write("\r\n");
      out.write("</div>\r\n");
      out.write("<div style=\"float:right;\">\r\n");
      out.write("<label Style=\"color:red\" id=\"abcd\">新增定时公告:</label>\r\n");
      out.write("\t<button id=\"addempbtn1\" class=\"btn btn-purple btn-sm\" onclick=\"addnotice1()\" data-placement=\"top\" title=\"新增定时公告\" data-toggle=\"modal\" data-target=\"#addnotice1\">\r\n");
      out.write("\t<span class=\"glyphicon glyphicon-plus\"></span>\r\n");
      out.write("\t</button> \r\n");
      out.write("\t<label Style=\"color:red\" id=\"abcdd\">新增即时公告:</label>\r\n");
      out.write("\t<button id=\"addempbtn\" class=\"btn btn-purple btn-sm\" onclick=\"addnotice()\" data-placement=\"top\" title=\"新增即时公告\" data-toggle=\"modal\" data-target=\"#addnotice\">\r\n");
      out.write("\t<span class=\"glyphicon glyphicon-plus\"></span>\r\n");
      out.write("\t</button> \r\n");
      out.write("\t\r\n");
      out.write("\t<!-- <button id=\"filemanagerbtn\" class=\"btn btn-danger btn-sm\" onclick=\"filemanager()\" data-placement=\"top\" title=\"文件管理\" data-toggle=\"modal\" data-target=\"#filemanager\">\r\n");
      out.write("\t<span class=\"glyphicon glyphicon-file\"></span>\r\n");
      out.write("\t</button> --> \r\n");
      out.write("\t</div>\r\n");
      out.write("</div><!-- /.page-header -->\r\n");
      out.write("\r\n");
      out.write("<ul class=\"nav nav-tabs\" role=\"tablist\" >\r\n");
      out.write("  <li role=\"presentation\" class=\"active\"><a href=\"#tab_orderinfo_state0\" role=\"tab\" data-toggle=\"tab\">公告列表</a></li>\r\n");
      out.write("  <li id=\"panel_state1\" role=\"presentation\"><a href=\"#tab_orderinfo_state1\" role=\"tab\" data-toggle=\"tab\">待发布</a></li>\r\n");
      out.write("  \r\n");
      out.write(" \r\n");
      out.write(" \r\n");
      out.write("</ul>\r\n");
      out.write("\r\n");
      out.write("<div class=\"tab-content\">\r\n");
      out.write("<!-- panel1 start -->\r\n");
      out.write("  <div role=\"tabpanel\" class=\"tab-pane active\" id=\"tab_orderinfo_state0\">\r\n");
      out.write("  \t\t<!-- jqgrid 用户列表-->\r\n");
      out.write("\t\t\t\t\t\t\t<table id=\"order_grid_table\"></table>\r\n");
      out.write("\t\t\t\t\t\t\t<!-- jqgrid 页码栏-->\r\n");
      out.write("\t\t\t\t\t\t\t<div id=\"order_grid_pager\"></div>\r\n");
      out.write("\t\t\t\t\t\t\t<div>\r\n");
      out.write("\t\r\n");
      out.write("\t\r\n");
      out.write("</div>\r\n");
      out.write("  \t</div>\r\n");
      out.write("  \t\r\n");
      out.write("  \t<!-- panel2 start -->\r\n");
      out.write("  <div role=\"tabpanel\" class=\"tab-pane\" id=\"tab_orderinfo_state1\"></div>\r\n");
      out.write("  \r\n");
      out.write("   <!-- panel3 start -->\r\n");
      out.write(" <!--  <div role=\"tabpanel\" class=\"tab-pane\" id=\"tab_orderinfo_state2\"></div> -->\r\n");
      out.write("\r\n");
      out.write("  \t\r\n");
      out.write("</div>\t\t\r\n");
      out.write("<!-- 新增即时公告（Modal） -->\r\n");
      out.write("<div id=\"addnotice\" ></div>\r\n");
      out.write("<!-- 新增定时公告（Modal） -->\r\n");
      out.write("<div id=\"addnotice1\" ></div>\r\n");
      out.write("<script>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("//加载部门下拉框\r\n");
      out.write("/* Q.getProxylevel(\"#proxylevel\"); */\r\n");
      out.write("\r\n");
      out.write("Q.grid_selector = \"order_grid_table\";\r\n");
      out.write("Q.pager_selector = \"order_grid_pager\";\r\n");
      out.write("\r\n");
      out.write("var url = Q.url+\"/notic/queryhnotice.action\";\r\n");
      out.write("\r\n");
      out.write("var colModel = [\r\n");
      out.write("                { label: 'ID', name: 'id',align :'center',hidden:true,sortable : false,key:true },\r\n");
      out.write("                { label: '公告标题', name: 'noticename',align :'center', sortable : false },\r\n");
      out.write("                { label: '发布类型', name: 'target',align :'center', sortable : false },\r\n");
      out.write("                { label: '消息类型', name: 'state',align :'center', sortable : false },\r\n");
      out.write("                { label: '创建时间', name: 'createtime',align :'center', sortable : false },\r\n");
      out.write("                { label: '发送时间', name: 'sendtime',align :'center', sortable : false },\r\n");
      out.write("                {  name: '操作' ,align :'center', fixed: true, sortable: false, resize: false, width:200, \r\n");
      out.write("                    //formatter:'actions',  \r\n");
      out.write("                    formatter: function (value, grid, rows, state) { \r\n");
      out.write("                    \t\r\n");
      out.write("                    \tvar btn = \"\";\r\n");
      out.write("                    \tbtn += Q.gridbtn_detail(Q.grid_selector,rows.id);\r\n");
      out.write("                    \t\r\n");
      out.write("                    \treturn  btn;\r\n");
      out.write("                    }  \r\n");
      out.write("                }\r\n");
      out.write("                \r\n");
      out.write("            \t];\r\n");
      out.write("\r\n");
      out.write("var caption = \"历史发布\";\r\n");
      out.write("\r\n");
      out.write("var postData = {};\r\n");
      out.write("postData = getcondition();\r\n");
      out.write("\r\n");
      out.write("/**\r\n");
      out.write(" * Q.grid_selector  tableid\r\n");
      out.write(" * Q.pager_selector 页码栏id\r\n");
      out.write(" * url 请求地址\r\n");
      out.write(" * postData 请求参数\r\n");
      out.write(" * colModel 表头\r\n");
      out.write(" * caption 表名 \r\n");
      out.write(" */\r\n");
      out.write("/*  Q.extJqGrid(Q.grid_selector,Q.pager_selector,url,postData,colModel,caption,null,function(d){\r\n");
      out.write("\t});  */\r\n");
      out.write("\t \r\n");
      out.write("Q.ExtJqGrid({\r\n");
      out.write("\t\"grid\":Q.grid_selector,\r\n");
      out.write("\t\"pager\":Q.pager_selector,\r\n");
      out.write("\t\"url\":url,\r\n");
      out.write("\t\"param\":postData,\r\n");
      out.write("\t\"model\":colModel,\r\n");
      out.write("\t\"caption\":caption\r\n");
      out.write("});\r\n");
      out.write("\r\n");
      out.write("//Q.pageNavbutton(grid_selector,pager_selector);\r\n");
      out.write("function getcondition(){\r\n");
      out.write("\tvar o = {};\r\n");
      out.write("\t/* $('#keyword').value=\"\"; */\r\n");
      out.write("\to.keyword= $('#keyword').val();\r\n");
      out.write("\to.state = 0;\r\n");
      out.write("\treturn o;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("function queryempbycondition(){\r\n");
      out.write("\tpostData = getcondition();\r\n");
      out.write("\t$(\"#\"+Q.grid_selector).setGridParam({postData:postData,page:1}).trigger(\"reloadGrid\"); \r\n");
      out.write("\t//Q.extJqGrid(grid_selector,pager_selector,url,postData,colModel,caption);\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("/* function filemanager(){\r\n");
      out.write("\tvar _url= 'zyUpload/uploadimg.jsp';\r\n");
      out.write("\t\r\n");
      out.write("\tQ.viewJsp('#div_img',_url);\r\n");
      out.write("} */\r\n");
      out.write("\r\n");
      out.write("$('#panel_state1').click(function(){\r\n");
      out.write("\tQ.viewJsp(\"#tab_orderinfo_state1\",\"notice/waitnotice.jsp\");\r\n");
      out.write("})\r\n");
      out.write("function addnotice(){\r\n");
      out.write("\tvar _url= 'notice/addhnotice.jsp';\r\n");
      out.write("\t\r\n");
      out.write("\tQ.viewJsp('#addnotice',_url);\r\n");
      out.write("}\r\n");
      out.write("function addnotice1(){\r\n");
      out.write("\tvar _url= 'notice/addtimnotice.jsp';\r\n");
      out.write("\t\r\n");
      out.write("\tQ.viewJsp('#addnotice1',_url);\r\n");
      out.write("}\r\n");
      out.write("function detail_order_grid_table(id){\r\n");
      out.write("\tvar _url= 'notice/noticeDetail.jsp?id='+id;\r\n");
      out.write("\t\r\n");
      out.write("\tQ.viewJsp(Q.page_content,_url);\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("var roles = ");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${loginEmp.role}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write(";\r\n");
      out.write("if(roles==2||roles==4||roles==5||role==3){\r\n");
      out.write("\t$(\"#addempbtn1\").css(\"display\", \"none\");\r\n");
      out.write("\t$(\"#addempbtn\").css(\"display\", \"none\");\r\n");
      out.write("\t$(\"#panel_state1\").css(\"display\", \"none\");\r\n");
      out.write("\t$(\"#abcd\").css(\"display\", \"none\");\r\n");
      out.write("\t$(\"#abcdd\").css(\"display\", \"none\");\r\n");
      out.write("}\r\n");
      out.write("/* //禁言用户\r\n");
      out.write("$('#panel_state2').click(function(){\r\n");
      out.write("\tQ.viewJsp(\"#tab_orderinfo_state2\",\"postings/postingsbanned.jsp\");\r\n");
      out.write("})\r\n");
      out.write("\r\n");
      out.write("//新增帖子\r\n");
      out.write("$('#panel_state3').click(function(){\r\n");
      out.write("\tQ.viewJsp(\"#tab_orderinfo_state3\",\"postings/addpostings.jsp\");\r\n");
      out.write("})\r\n");
      out.write("//评论列表\r\n");
      out.write("$('#panel_state4').click(function(){\r\n");
      out.write("\tQ.viewJsp(\"#tab_orderinfo_state4\",\"postings/postingscomments.jsp\");\r\n");
      out.write("}) */\r\n");
      out.write("</script>\r\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
