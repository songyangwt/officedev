<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fb" uri="http://template.fb.com/article/taglib"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>My JSP 'list.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript" src="<%=path%>/js/jquery-1.2.6.js" charset= "gbk"></script>
 <style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	background-color: #ffffff;
}
-->
</style>
<script type="text/javascript">
 $(document).ready(function(){ 

$("tr.btbj:odd").css({"background-color":"#F0F0F0","font-family": "黑体","font-size": "14px","font-weight":"lighter" }); 
$("tr.btbj:even").css({"background-color":"#bfd3fc","font-family": "黑体","font-size": "14px","font-weight":"lighter" }); 
 });
function modify(id)
{
	window.location = "<%=path%>/uschCOSTtoupdate.action?id="+id;
}
function del(id)
{
	window.location = "<%=path%>/uschCOSTdel.action?id="+id;
}
 </script>
 <link href="<%=path%>/css/table_back.css" rel="stylesheet" type="text/css">
  </head>
  
  <body>
   <form action="<%=path%>/userlist.action" method="post" name="fm1">
					<table height="80" align="center" cellpadding="0"
						cellspacing="2" >
						<tr>
							<td
								style="color: #1778C2; padding-top: 15px; padding-bottom: 15px; border: 0px; font-size: 26px; font-family: '黑体';"
								colspan="5" align="center" bordercolor="#FFFFFF"><b>COST代码管理</b>
							</td>
						</tr>
						<tr height="50px" class="表格表头背景1" id="hang">
							<td  width="100px" align="center" valign="middle" nowrap
								bordercolor=none><div align="center">
									<p>编号</p>
								</div></td>
							<td  width="100px" align="center" valign="middle" nowrap
								bordercolor=none><div align="center">
									<p>任务池</p>
								</div></td>
							<td  width="120px" align="center" valign="middle" nowrap
								bordercolor=none><div align="center">
									<p>代码</p>
								</div></td>
							<td  width="200px" align="center" valign="middle" nowrap
								bordercolor=none><div align="center">
									<p>名称</p>
								</div></td>
							<td  width="100px"  align="center" valign="middle" nowrap
								bordercolor=none><div align="center">
									<p>操作</p>
								</div></td>	
						</tr>
						<c:forEach items="${list}" var="ui" varStatus="status">
							<tr class="btbj" id="hang" style="height:20px">
								
								<td height="25" align="center" valign="middle" nowrap><div
										align="center">${ui.paixu}</div></td>
								<td height="25" align="center" valign="middle" nowrap><div
										align="center">${ui.pool}</div></td>
								<td height="25" align="center" valign="middle" nowrap><div
										align="center">${ui.code}</div></td>
								<td height="25" align="center" valign="middle" nowrap><div
										align="center">${ui.detail}</div></td>
								<td height="25" align="center" valign="middle" nowrap><div
										align="center">
										<input type="button" value="删除" onclick="del('${ui.id}')"/>
										<input type="button" value="修改" onclick="modify('${ui.id}')"/>
											</div>
								</td>
							</tr>
						</c:forEach>	
		</table>
		</form>
  </body>
</html>
