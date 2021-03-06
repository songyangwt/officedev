<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fb" uri="http://template.fb.com/article/taglib"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>acceptance_data</title>
		<script type="text/javascript" src="<%=path%>/js/jquery-1.2.6.js" charset= "gbk"></script>
		<script language="javascript" type="text/javascript" src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
		<link href="<%=path%>/css/add.css" type="text/css"
			rel="stylesheet" />
			
		<script type="text/javascript">
	$(document).ready(function(){ 
	   
       $('#week').attr('value','${week}');
       $('#isrest').attr('value','${isrest}');
       $('#plan').attr('value','${plan}');
      
     });
   
	</script>
	</head>
	<body>
		<form action="<%=path%>/ppmodify.action" method="post">
			<div class="layout">
				<div class="title">
					单独排班人员修改（<b style="color:red">*</b>为必填项）
				</div>

				<div id="content">

					<div class="four_columns">
						<div class="four_columns_text">
							<b style="color:red">*</b>姓名:
						</div>
						<div class="four_columns_input">
						 	<input type="text" id="name" name="name" style="width:50px" value="${name}" maxlength="3"/>
						</div>
					<div class="clear"></div>
				</div>
			
				<div class="four_columns">
						<div class="four_columns_text">
							<b style="color:red">*</b>日期:
						</div>
						<div>
						 	<input size=10 type="text" name="date" id="date" value="${date}" class="Wdate" onClick="WdatePicker()" >
						</div>
					<div class="clear"></div>
				</div>
				
				
					<div class="four_columns">
						<div class="four_columns_text">
							<b style="color:red">*</b>班次:
						</div>
						<div>
						 	<select id="plan" name="plan" style="width: 100px">
						 	 <option value="0">调休</option>
								<c:forEach items="${listplan}" var="plans" varStatus="status">
        						     <option value="${plans.no}">班次${plans.no}</option>
        				        </c:forEach> 
							 </select>
						</div>
					<div class="clear"></div>
				</div>
				
				<div class="button">
						<input type="button" onclick="javascript:history.go(-1);" value="返回" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="hidden" name="id" value="${id}"/>
						<input type="submit" class="but" value="提交" />
					</div>
				</div>
			</div>
		</form>
	</body>

</html>


