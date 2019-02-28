package org.apache.jsp.html.device;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class machine_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("<div class=\"page-header\">\r\n");
      out.write("\t<h1>\r\n");
      out.write("\t\t娃娃机管理\r\n");
      out.write("\t\t<small>\r\n");
      out.write("\t\t\t<i class=\"ace-icon fa fa-angle-double-right\"></i>\r\n");
      out.write("\t\t\t列表\r\n");
      out.write("\t\t</small>\r\n");
      out.write("\t</h1>\r\n");
      out.write("</div><!-- /.page-header -->\r\n");
      out.write("\r\n");
      out.write("\t<div class=\"row\">\r\n");
      out.write("\t\t<div class=\"col-xs-12\">\r\n");
      out.write("\t\t\t<!-- PAGE CONTENT BEGINS -->\r\n");
      out.write("\t\t\t<!-- <div class=\"alert alert-info\">\r\n");
      out.write("\t\t\t\t<button class=\"close\" data-dismiss=\"alert\">\r\n");
      out.write("\t\t\t\t\t<i class=\"ace-icon fa fa-times\"></i>\r\n");
      out.write("\t\t\t\t</button>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t<i class=\"ace-icon fa fa-hand-o-right\"></i>\r\n");
      out.write("\t\t\t\tPlease note that demo server is not configured to save the changes, therefore you may see an error message.\r\n");
      out.write("\t\t\t</div> -->\r\n");
      out.write("\t\t\t<div class=\"modal-header\">\r\n");
      out.write("\t\t\t<table  style=\"float: left;margin-right: 5px;\">\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t<!-- <td><label for=\"department\" class=\"control-label\" >部门：</label></td><td><select type=\"text\" id=\"department\" ></select></td> -->\r\n");
      out.write("\t\t\t<td><label for=\"machineno\" class=\"control-label\" style=\"margin-left:5px;\">设备编号：</label></td><td><input type=\"text\" id=\"machineno\" /></td>\r\n");
      out.write("\t\t\t<td><label for=\"ftoysid\" class=\"control-label\" style=\"margin-left:5px;\">玩具：</label></td><td><select type=\"text\" id=\"ftoysid\" ></select></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t</table>\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t<button class=\"btn btn-info btn-sm\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"查询\" onclick=\"queryempbycondition()\">\r\n");
      out.write("\t<span class=\"glyphicon glyphicon-search\"></span>\r\n");
      out.write("\t</button>\r\n");
      out.write("\t<div style=\"float:right;\">\r\n");
      out.write("\t<button id=\"addmachinebtn\" class=\"btn btn-purple btn-sm\" onclick=\"addmachine()\" data-placement=\"top\" title=\"新增\" data-toggle=\"modal\" data-target=\"#addmachine\">\r\n");
      out.write("\t新增<span class=\"glyphicon glyphicon-plus\"></span>\r\n");
      out.write("\t</button> \r\n");
      out.write("\t<!-- <button id=\"importempbtn\" class=\"btn btn-danger btn-sm\" onclick=\"$('input[id=importemp_excel]').click();\" data-placement=\"top\" title=\"导入\">\r\n");
      out.write("\t导入<span class=\"glyphicon glyphicon-import\"></span>\r\n");
      out.write("\t</button>\r\n");
      out.write("\t<a id=\"personnelmodelclick\" target=\"_blank\" href=\"mould/emp_model.xls\" style=\"padding-left:5px;\">没有模板?</a>\r\n");
      out.write("\t\r\n");
      out.write("\t<form id=\"upload_emp_file_form\" name=\"upload_emp_file_form\" method=\"POST\"  enctype=\"multipart/form-data\"> \r\n");
      out.write("\t<input  id=\"importemp_excel\" type=\"file\" name=\"filePath\" onchange=\"uploademp_excel()\" style=\"display:none\">\r\n");
      out.write("\t</form> -->\r\n");
      out.write("\t</div>\r\n");
      out.write("\r\n");
      out.write("</div>\r\n");
      out.write("\t\t\t\t\t\t\t\t<!-- jqgrid 用户列表-->\r\n");
      out.write("\t\t\t\t\t\t\t<table id=\"mc_grid_table\"></table>\r\n");
      out.write("\t\t\t\t\t\t\t<!-- jqgrid 页码栏-->\r\n");
      out.write("\t\t\t\t\t\t\t<div id=\"mc_grid_pager\"></div>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t<!-- PAGE CONTENT ENDS -->\r\n");
      out.write("\t\t\t\t\t\t</div><!-- /.col -->\r\n");
      out.write("\t\t\t\t\t</div><!-- /.row -->\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("<!-- 新增人员（Modal） -->\r\n");
      out.write("<div id=\"div_addmachine\" ></div>\r\n");
      out.write("\t\t\t\r\n");
      out.write("<!-- 导入（Modal） -->\r\n");
      out.write("<div id=\"div_import_emp\" ></div>\t\t\r\n");
      out.write("<script>\r\n");
      out.write("\r\n");
      out.write("//加载玩具下拉框\r\n");
      out.write("Q.getToys1(\"#ftoysid\");\r\n");
      out.write("\r\n");
      out.write("Q.grid_selector = \"mc_grid_table\";\r\n");
      out.write("Q.pager_selector = \"mc_grid_pager\";\r\n");
      out.write("\r\n");
      out.write("var url = Q.url+\"/device/machineList.action\";\r\n");
      out.write("\r\n");
      out.write("var colModel = [\r\n");
      out.write("                { label: 'ID', name: 'id',align :'center',hidden:true,sortable : false,key:true },\r\n");
      out.write("                { label: '设备编号', name: 'machineno',align :'center', sortable : false },\r\n");
      out.write("                { label: '玩具', name: 'toysname',align :'center', sortable : false },\r\n");
      out.write("                { label: '价格', name: 'price',align :'center', sortable : false },\r\n");
      out.write("                { label: '人气', name: 'popularity',align :'center', sortable : false },\r\n");
      out.write("                { label: '喵币', name: 'balance',align :'center', sortable : false },\r\n");
      out.write("                { label: '时间', name: 'createtime',align :'center', sortable : false },\r\n");
      out.write("                { label: '状态', name: 'state',align :'center',sortable : false,  \r\n");
      out.write("                    formatter: function (value, grid, rows, state) { \r\n");
      out.write("                    \tvar status = rows.state==-1?'已下架':rows.state==0?'空闲中':'正在使用';\r\n");
      out.write("                    \treturn status;\r\n");
      out.write("                    }  \r\n");
      out.write("                },\r\n");
      out.write("                {  \r\n");
      out.write("                    name: '操作' ,align :'center', fixed: true, sortable: false, resize: false,  \r\n");
      out.write("                    //formatter:'actions',  \r\n");
      out.write("                    formatter: function (value, grid, rows, state) { \r\n");
      out.write("                    \t\r\n");
      out.write("                    \tvar btn = \"\";\r\n");
      out.write("                    \tif(rows.ftoysid==null || rows.ftoysid==''){\r\n");
      out.write("                    \t\tbtn += Q.gridbtn_bindtoy(Q.grid_selector,rows.id) ;\r\n");
      out.write("                    \t}else{\r\n");
      out.write("                    \t\tbtn += Q.gridbtn_detail(Q.grid_selector,rows.id) ;\r\n");
      out.write("                    \t}\r\n");
      out.write("                    \t//btn += Q.gridbtn_modify(Q.grid_selector,rows.id) ;\r\n");
      out.write("                    \t//btn += Q.gridbtn_del(Q.grid_selector,rows.id) ;\r\n");
      out.write("                    \t\r\n");
      out.write("                    \treturn  btn;\r\n");
      out.write("                    }  \r\n");
      out.write("                }\r\n");
      out.write("            \t];\r\n");
      out.write("\r\n");
      out.write("var caption = \"娃娃机列表\";\r\n");
      out.write("\r\n");
      out.write("var postData ={};\r\n");
      out.write("\r\n");
      out.write("/**\r\n");
      out.write(" * Q.grid_selector  tableid\r\n");
      out.write(" * Q.pager_selector 页码栏id\r\n");
      out.write(" * url 请求地址\r\n");
      out.write(" * postData 请求参数\r\n");
      out.write(" * colModel 表头\r\n");
      out.write(" * caption 表名 \r\n");
      out.write(" */\r\n");
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
      out.write("\to.machineno = $('#machineno').val();\r\n");
      out.write("\to.ftoysid = $('#ftoysid').val();\r\n");
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
      out.write("//详情函数----命名规则  detail_tableid\r\n");
      out.write("function detail_mc_grid_table(id){\r\n");
      out.write("\tvar _url= 'device/machinedetail.jsp?machineinfoid='+id;\r\n");
      out.write("\t\r\n");
      out.write("\tQ.viewJsp(Q.page_content,_url);\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function bind_mc_grid_table(id){\r\n");
      out.write("var _url= 'device/bindtoys.jsp?machineinfoid='+id;\r\n");
      out.write("\t\r\n");
      out.write("\tQ.viewJsp(Q.page_content,_url);\r\n");
      out.write("\t\r\n");
      out.write("\t\r\n");
      out.write("\t\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function addmachine(){\r\n");
      out.write("\tvar _url= 'device/addmachine.jsp';\r\n");
      out.write("\t\r\n");
      out.write("\tQ.viewJsp('#div_addmachine',_url);\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function filemanager(){\r\n");
      out.write("\tvar _url= 'zyUpload/uploadimg.jsp';\r\n");
      out.write("\t\r\n");
      out.write("\tQ.viewJsp('#div_img',_url);\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("</script>\r\n");
      out.write("<script src=\"js/jqgridresize.js\"></script>");
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
