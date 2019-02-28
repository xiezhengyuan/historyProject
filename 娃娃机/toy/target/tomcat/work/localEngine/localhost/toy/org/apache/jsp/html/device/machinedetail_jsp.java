package org.apache.jsp.html.device;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class machinedetail_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("<style>\r\n");
      out.write("input{width:50%}\r\n");
      out.write("/* input:nth-child(2){width:80%} */\r\n");
      out.write("</style>\r\n");
      out.write("<div class=\"page-header\">\r\n");
      out.write("\t<h1>\r\n");
      out.write("\t\t娃娃机-查看/修改\r\n");
      out.write("\t\t\r\n");
      out.write("\t</h1>\r\n");
      out.write("</div><!-- /.page-header -->\r\n");
      out.write("<ul class=\"nav nav-tabs\" role=\"tablist\">\r\n");
      out.write("  <li style=\"position: relative;float: right;\">\r\n");
      out.write("\t<button  class=\"btn btn-info btn-sm\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"返回\" id=\"returnbfbtn\" onclick=\"returnbfbtn(this)\">返回</button>\r\n");
      out.write("  </li>\r\n");
      out.write("</ul>\r\n");
      out.write("<div class=\"tab-content\">\r\n");
      out.write("\t<!-- panel1 start -->\r\n");
      out.write("  <div role=\"tabpanel\" class=\"tab-pane active\" id=\"tab_machineinfo\">\r\n");
      out.write("   <form class=\"form-horizontal\" role=\"form\" id=\"form_addmachineinfo\" style=\"\">\r\n");
      out.write("   <input type=\"hidden\" class=\"form-control\" id=\"id\" name=\"id\" placeholder=\"\">\r\n");
      out.write("   \r\n");
      out.write("   \t\t\t\t<div class=\"form-group\">\r\n");
      out.write("\t\t\t\t\t<label for=\"ftoysid\" class=\"col-sm-2 control-label\">设备编号</label>\r\n");
      out.write("\t\t\t\t\t<div class=\"col-sm-10\" style=\"width: 20%\">\r\n");
      out.write("\t\t\t\t\t\t<input type=\"text\" class=\"form-control\" name=\"machineno\" msg=\"请输入设备编号\" id=\"machineno\"  placeholder=\"\">\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("   \t\t\t\t\r\n");
      out.write("   \t\t\t\t<div class=\"form-group\">\r\n");
      out.write("\t\t\t\t\t<label for=\"ftoysid\" class=\"col-sm-2 control-label\">绑定玩具</label>\r\n");
      out.write("\t\t\t\t\t<div class=\"col-sm-10\" style=\"width: 20%\">\r\n");
      out.write("\t\t\t\t\t\t<select  class=\"form-control\" id=\"ftoysid\" name=\"ftoysid\" msg=\"请选择玩具\" vtype=\"combo\">\r\n");
      out.write("\t\t\t\t\t\t</select> \r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t<div class=\"form-group\">\r\n");
      out.write("\t\t\t\t\t<label for=\"ftoysid\" class=\"col-sm-2 control-label\">价格</label>\r\n");
      out.write("\t\t\t\t\t<div class=\"col-sm-10\" style=\"width: 20%\">\r\n");
      out.write("\t\t\t\t\t\t<input type=\"text\" class=\"form-control\" name=\"price\"  msg=\"请输入价格\" id=\"price\"  placeholder=\"\">\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t<div class=\"form-group\">\r\n");
      out.write("\t\t\t\t\t<label for=\"stock\" class=\"col-sm-2 control-label\">玩具数量</label>\r\n");
      out.write("\t\t\t\t\t<div class=\"col-sm-10\" style=\"width: 20%\">\r\n");
      out.write("\t\t\t\t\t<input type=\"text\" class=\"form-control\" name=\"stock\" msg=\"请输入玩具数量\" id=\"stock\"  placeholder=\"\">\r\n");
      out.write("\t\t\t\t\t</div>\t\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t<div class=\"form-group\">\r\n");
      out.write("\t\t\t\t\t<label for=\"ftoysid\" class=\"col-sm-2 control-label\">人气</label>\r\n");
      out.write("\t\t\t\t\t<div class=\"col-sm-10\" style=\"width: 20%\">\r\n");
      out.write("\t\t\t\t\t\t<input type=\"text\" class=\"form-control\" name=\"popularity\" id=\"popularity\"  placeholder=\"\">\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t<div class=\"form-group\">\r\n");
      out.write("\t\t\t\t\t<label for=\"ftoysid\" class=\"col-sm-2 control-label\">状态</label>\r\n");
      out.write("\t\t\t\t\t<div class=\"col-sm-10\" style=\"width: 20%\">\r\n");
      out.write("\t\t\t\t\t\t<!-- <input type=\"text\" class=\"form-control\" name=\"state\" id=\"state\"  placeholder=\"\"> -->\r\n");
      out.write("\t\t\t\t\t\t<select id=\"state\" name=\"state\">\r\n");
      out.write("\t\t\t\t\t\t<option value=\"-1\">已下架</option>\r\n");
      out.write("\t\t\t\t\t\t<option value=\"0\">空闲中</option>\r\n");
      out.write("\t\t\t\t\t\t<option value=\"1\">正在使用</option>\r\n");
      out.write("\t\t\t\t\t\t</select>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t<div class=\"form-group\">\r\n");
      out.write("\t\t\t\t\t<label for=\"ftoysid\" class=\"col-sm-2 control-label\">观看人数</label>\r\n");
      out.write("\t\t\t\t\t<div class=\"col-sm-10\" style=\"width: 20%\">\r\n");
      out.write("\t\t\t\t\t\t<input type=\"text\" class=\"form-control\" name=\"views\" readonly=\"readonly\"  id=\"views\"  placeholder=\"\">\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t<div class=\"form-group\">\r\n");
      out.write("\t\t\t\t\t<label for=\"ftoysid\" class=\"col-sm-2 control-label\">预约人数</label>\r\n");
      out.write("\t\t\t\t\t<div class=\"col-sm-10\" style=\"width: 20%\">\r\n");
      out.write("\t\t\t\t\t\t<input type=\"text\" class=\"form-control\"  name=\"subscribe\" readonly=\"readonly\" id=\"subscribe\"  placeholder=\"\">\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t<div class=\"form-group\">\r\n");
      out.write("\t\t\t\t\t<label for=\"facevideo\" class=\"col-sm-2 control-label\">正面直播流地址</label>\r\n");
      out.write("\t\t\t\t\t<div class=\"col-sm-10\">\r\n");
      out.write("\t\t\t\t\t<input type=\"text\" class=\"form-control\" value=\"正面直播流地址\" name=\"facevideo\"  readonly=\"readonly\" msg=\"请输入正面直播流地址\" id=\"macfacevideohineno\"  placeholder=\"\">\r\n");
      out.write("\t\t\t\t\t</div>\t\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t\t  <div class=\"form-group\">\r\n");
      out.write("\t\t\t\t    <label for=\"sidevideo\" class=\"col-sm-2 control-label\">侧面直播流地址</label>\r\n");
      out.write("\t\t\t\t    <div class=\"col-sm-10\">\r\n");
      out.write("\t\t\t\t      <input type=\"text\" class=\"form-control\" value=\"正面直播流地址\" name=\"sidevideo\" readonly=\"readonly\" msg=\"请输入侧面直播流地址\" id=\"sidevideo\"  placeholder=\"\">\r\n");
      out.write("\t\t\t\t    </div>\r\n");
      out.write("\t\t\t\t  </div>\r\n");
      out.write("\t\t\t\t  \r\n");
      out.write("  <div class=\"modal-footer\" style=\"text-align: center;\">\r\n");
      out.write("\t\t<button type=\"button\" class=\"btn btn-primary btn-sm\" onclick=\"returnbfbtn(this)\">返回</button>&nbsp;&nbsp;&nbsp;&nbsp;\r\n");
      out.write("\t\t<button type=\"button\" class=\"btn btn-primary btn-sm\" id=\"addtoys\">确认修改</button>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t</form>\r\n");
      out.write("  </div>\r\n");
      out.write("  \r\n");
      out.write("  <!-- panel2 start -->\r\n");
      out.write("  <div role=\"tabpanel\" class=\"tab-pane\" id=\"tab_lower\"></div>\r\n");
      out.write("  \r\n");
      out.write("   <!-- panel3 start -->\r\n");
      out.write("  <div role=\"tabpanel\" class=\"tab-pane\" id=\"tab_transaction\"></div>\r\n");
      out.write("</div>\r\n");
      out.write("<script>\r\n");
      out.write("Q.logimgs = [];\r\n");
      out.write("Q.viewlogimg(\"#toysspreview\");\r\n");
      out.write("\r\n");
      out.write("//玩具类型\r\n");
      out.write("/* Q.getToys1(\"#ftoysid\"); */\r\n");
      out.write(" $._ajax({\r\n");
      out.write("\t\turl: Q.url+\"/device/findAllToys.action\",\r\n");
      out.write("\t\tdata:{},\r\n");
      out.write("\t\tasync:false,\r\n");
      out.write("\t\tsuccess: function(data){\r\n");
      out.write("\t\t\tif( typeof(data) == 'object' && data.op == 'timeout' ){\r\n");
      out.write("\t\t\t\tlocation.reload();\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\tvar opts = '<option value=\"0\">请选择</option>';\r\n");
      out.write("\t\t\tfor(var i=0;i<data.rows.length;i++){\r\n");
      out.write("\t\t\t\tvar row = data.rows[i];\r\n");
      out.write("\t\t\t\topts+= '<option value=\"'+row.id+'\">'+row.name+'</option>';\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\t$(\"#ftoysid\").html(opts);\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\tQ.toys = data;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t});\r\n");
      out.write(" \r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("var machineinfoid = '");
      out.print(request.getParameter("machineinfoid"));
      out.write("'; \r\n");
      out.write("\r\n");
      out.write("$(\"#id\").val(machineinfoid);\r\n");
      out.write("\r\n");
      out.write("Q.getObjById(\"MachineInfo\",machineinfoid,function(d){\r\n");
      out.write("\t$('#form_addmachineinfo').resetObjectForm(d);\r\n");
      out.write("})\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("//根据ID查询部门信息\r\n");
      out.write(" /* Q.getObjById(\"UserInfo\",userinfoid,function(d){\r\n");
      out.write("\t\r\n");
      out.write("\t$('#form_editemp').resetObjectForm(d);\r\n");
      out.write("})  */\r\n");
      out.write("\r\n");
      out.write("function returnbfbtn(){\r\n");
      out.write("\tvar from = \"device/machine.jsp\";\r\n");
      out.write("\tQ.viewJsp(Q.page_content,from);\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("$('#addtoys').click(function(){\r\n");
      out.write("\tif(!$('#form_addmachineinfo').checkForm())return;\r\n");
      out.write("\tvar o = $('#form_addmachineinfo').serializeObject();\r\n");
      out.write("\t\r\n");
      out.write("if($(\"input[name='isnew']\").is(':checked')){\r\n");
      out.write("\t\to.isnew = 1;\r\n");
      out.write("\t}else{o.isnew = 0;}\r\n");
      out.write("\t/* \r\n");
      out.write("\tif(Q.logimgs.length==0){\r\n");
      out.write("\t\tQ.toastr(\"提示\",\"请至少上传一张图片\",'warning');\r\n");
      out.write("\t\treturn;\r\n");
      out.write("\t}\t\r\n");
      out.write("\to.imgarr = $.toJSON(Q.logimgs);\r\n");
      out.write("\to.thumbnail = Q.logimgs[0].url;\r\n");
      out.write("\to.ffileinfoid = Q.logimgs[0].id; */\r\n");
      out.write("\tif(o.state==1){\r\n");
      out.write("\t\tQ.toastr(\"提示\",'不能手动将机器状态改为正在使用','error');\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\t$._ajax({\r\n");
      out.write("\t\turl: Q.url+\"/device/bindToys.action\",\r\n");
      out.write("\t\tdata:o,\r\n");
      out.write("\t\tsuccess: function(data){\r\n");
      out.write("\t\t\tif( typeof(data) == 'object'){\r\n");
      out.write("\t\t\t\tif( data.op == 'success' ){\r\n");
      out.write("\t\t\t\t\tQ.toastr(\"提示\",\"操作成功\",'success');\r\n");
      out.write("\t\t\t\t\tvar from = \"device/machine.jsp\";\r\n");
      out.write("\t\t\t\t\tQ.viewJsp(Q.page_content,from);\r\n");
      out.write("\t\t\t\t}else if(data.op == 'fail')\r\n");
      out.write("\t\t\t\t\tQ.toastr(\"提示\",data.msg,'error');\r\n");
      out.write("\t\t\t\telse\r\n");
      out.write("\t\t\t\t\tQ.toastr(\"提示\",\"操作失败\",'error');\r\n");
      out.write("\t\t\t}else {\r\n");
      out.write("\t\t\t\tQ.toastr(\"提示\",\"操作失败\",'error');\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t}\r\n");
      out.write("\t});\r\n");
      out.write("})\r\n");
      out.write("\r\n");
      out.write("</script>");
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
