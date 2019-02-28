package org.apache.jsp.html.notice;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class addhnotice_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("<div class=\"modal  inmodal fade\" id=\"adduser\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">\r\n");
      out.write("<div class=\"modal-dialog\">\r\n");
      out.write("\t\t<div class=\"modal-content\">\r\n");
      out.write("\t\t\t<div class=\"modal-header\">\r\n");
      out.write("\t\t\t\t<button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">\r\n");
      out.write("\t\t\t\t\t&times;\r\n");
      out.write("\t\t\t\t</button>\r\n");
      out.write("\t\t\t\t<h4 class=\"modal-title\" id=\"myModalLabel\">\r\n");
      out.write("\t\t\t\t\t新增即时公告\r\n");
      out.write("\t\t\t\t</h4>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t<div class=\"modal-body\" >\r\n");
      out.write("\t\t\t<div class=\"form-horizontal\" role=\"form\" id=\"form_adduser\">\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t\t\t<div class=\"form-group\">\r\n");
      out.write("\t\t\t\t\t<label for=\"righttitle\" class=\"col-sm-2 control-label\">菜单名称</label>\r\n");
      out.write("\t\t\t\t\t<div class=\"col-sm-10\">\r\n");
      out.write("\t\t\t\t\t<select type=\"text\" id=\"target\"  >\r\n");
      out.write("\t\t\t\t\t<option value=\"0\"  selected>所有人</option>\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t<option value=\"1\"  >所有用户  </option>\r\n");
      out.write("\t\t\t\t\t<option value=\"2\"  >所有公司 </option>\r\n");
      out.write("\t\t\t\t\t<option value=\"3\"  >指定公司 </option>\r\n");
      out.write("\t\t\t\t\t</select>\r\n");
      out.write("\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t <div class=\"form-group\" style=\"display:none\" id=\"ndiv\">\r\n");
      out.write("\t\t\t\t\t<label for=\"righttitle\" class=\"col-sm-2 control-label\">公司名称</label>\r\n");
      out.write("\t\t\t\t\t<div class=\"col-sm-10\">\r\n");
      out.write("\t\t\t\t\t<select  id=\"target2\" class=\"selectpicker\" multiple data-hide-disabled=\"true\" data-size=\"5\" >\r\n");
      out.write("\t\t\t\t\t\t<option value=\"0\" selected>请选择</option>\r\n");
      out.write("\t\t\t\t\t</select>\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t</div> \r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t<!-- \t\t<tr>\r\n");
      out.write("\t\t\t<td><label for=\"fsupplier\" class=\"control-label\"  style=\"margin-left:5px;\">公司名称：</label></td><td><select type=\"text\" id=\"target2\" ><option value=\"-1\"  selected>请选择</option></select></td>\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t</tr> -->\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t\t\t <div class=\"form-group\">\r\n");
      out.write("\t\t\t\t\t<label for=\"noticename\" class=\"col-sm-2 control-label\">公告标题</label>\r\n");
      out.write("\t\t\t\t\t<div class=\"col-sm-10\">\r\n");
      out.write("\t\t\t\t\t<input type=\"text\" class=\"form-control\" name=\"noticename\" msg=\"请输入标题\" id=\"noticename\" placeholder=\"\">\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t<div class=\"form-group\">\r\n");
      out.write("\t\t\t\t\t<label for=\"noticecontent\" class=\"col-sm-2 control-label\">公告内容</label>\r\n");
      out.write("\t\t\t\t\t<div class=\"col-sm-10\">\r\n");
      out.write("\t\t\t\t\t<textarea  class=\"form-control\" name=\"noticecontent\" msg=\"请输入内容\" id=\"noticecontent\" placeholder=\"\"></textarea>\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t<!-- <div class=\"form-group\">\r\n");
      out.write("\t\t\t\t\t<label for=\"msgconent\" class=\"col-sm-2 control-label\">图片</label>\r\n");
      out.write("\t\t\t\t\t<div class=\"col-sm-10\">\r\n");
      out.write("\t\t\t\t\t<div id=\"goodspreview\" class=\"upload_preview\" style=\"border:1px dashed  #bbb\"></div>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t</div> -->\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t<div class=\"modal-footer\">\r\n");
      out.write("\t\t\t\t<button type=\"button\" class=\"btn btn-default btn-sm\" data-dismiss=\"modal\">关闭</button>\r\n");
      out.write("\t\t\t\t<button type=\"button\" class=\"btn btn-primary btn-sm\" id=\"saveuser\">提交</button>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div><!-- /.modal-content -->\r\n");
      out.write("\t</div><!-- /.modal -->\r\n");
      out.write("</div>\r\n");
      out.write("\r\n");
      out.write("<script>\r\n");
      out.write("querysupplier(\"#target2\");\r\n");
      out.write("function querysupplier(selectedid){\r\n");
      out.write("\t$._ajax({\r\n");
      out.write("\t\turl: Q.url+\"/notic/querycompony.action\",\r\n");
      out.write("\t\tdata:{},\r\n");
      out.write("\t\tasync:false,\r\n");
      out.write("\t\tsuccess: function(data){\r\n");
      out.write("\t\t\tif( typeof(data) == 'object' && data.op == 'timeout' ){\r\n");
      out.write("\t\t\t\tlocation.reload();\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\tvar opts = $(selectedid).html();\r\n");
      out.write("\t\t\tfor(var i=0;i<data.rows.length;i++){\r\n");
      out.write("\t\t\t\tvar company = data.rows[i].company;\r\n");
      out.write("\t\t\t\topts+= '<option value=\"'+company.id+'\">'+company.company+'</option>';\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\t$(selectedid).html(opts);\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t}\r\n");
      out.write("\t});\r\n");
      out.write("\t\r\n");
      out.write("}\r\n");
      out.write("$(\"#target\").change(function(){\r\n");
      out.write("\tif($(\"#target\").val()==3){\r\n");
      out.write("\t\t$(\"#ndiv\").show()\r\n");
      out.write("\t}else{\r\n");
      out.write("\t\t$(\"#ndiv\").hide();\r\n");
      out.write("\t}\r\n");
      out.write("\t});\r\n");
      out.write("$('#target2').selectpicker({\r\n");
      out.write("    'selectedText': 'cat'\r\n");
      out.write("});\r\n");
      out.write("$('.dropdown-menu').css('overflow-y','')\r\n");
      out.write("$('.dropdown-menu').css('max-height','')\r\n");
      out.write("$('.dropdown-menu span').removeClass('pull-left')\r\n");
      out.write("$('#target2 option').eq(0).hide()\r\n");
      out.write("\r\n");
      out.write("$('#adduser').modal('show');\r\n");
      out.write("\r\n");
      out.write("$('#saveuser').click(function(){\r\n");
      out.write("\tif(!$('#form_adduser').checkForm())return;\r\n");
      out.write("\tif(!$('#form_adduser').checkformatForm())return;\r\n");
      out.write("\t\r\n");
      out.write("\t\r\n");
      out.write("\tvar o = $('#form_adduser').serializeObject();\r\n");
      out.write("\to.target=$(\"#target\").val();\r\n");
      out.write("\to.noticename=$(\"#noticename\").val();\r\n");
      out.write("\tif(o.noticename==null||o.noticename==\"\"){\r\n");
      out.write("\t\tQ.toastr(\"提示\",\"操作失败\",'error');\r\n");
      out.write("\t\treturn;\r\n");
      out.write("\t}\r\n");
      out.write("\to.noticecontent=$(\"#noticecontent\").val();\r\n");
      out.write("\tif(o.noticecontent==null||o.noticecontent==\"\"){\r\n");
      out.write("\t\tQ.toastr(\"提示\",\"操作失败\",'error');\r\n");
      out.write("\t\treturn;\r\n");
      out.write("\t}\r\n");
      out.write("\to.array=$(\"#target2\").val()+\"\";\r\n");
      out.write("\t\r\n");
      out.write("\t$._ajax({\r\n");
      out.write("\t\turl: Q.url+\"/notic/addhnotice.action\",\r\n");
      out.write("\t\tdata:o,\r\n");
      out.write("\t\tsuccess: function(data){\r\n");
      out.write("\t\t\tif( typeof(data) == 'object'){\r\n");
      out.write("\t\t\t\tif( data.op == 'success' ){\r\n");
      out.write("\t\t\t\t\tQ.toastr(\"提示\",\"操作成功\",'success');\r\n");
      out.write("\t\t\t\t\t//reloadgrid\r\n");
      out.write("\t\t\t\t\t$(\"#\"+Q.grid_selector).setGridParam({postData:postData,page:1}).trigger(\"reloadGrid\"); \r\n");
      out.write("\t\t\t\t}else if(data.op == 'fail')\r\n");
      out.write("\t\t\t\t\tQ.toastr(\"提示\",data.msg,'error');\r\n");
      out.write("\t\t\t\telse\r\n");
      out.write("\t\t\t\t\tQ.toastr(\"提示\",\"操作失败\",'error');\r\n");
      out.write("\t\t\t}else {\r\n");
      out.write("\t\t\t\tQ.toastr(\"提示\",\"操作失败\",'error');\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t}\r\n");
      out.write("\t});\r\n");
      out.write("\t\r\n");
      out.write("\t\r\n");
      out.write("})\r\n");
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
