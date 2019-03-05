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
 <script language="javascript" type="text/javascript" src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
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

function gettype(){
    
    var types="";
    var	assetname=document.getElementById('assetname').value;
    assetname = encodeURI(assetname); 
    var xmlhttp;
    var time=new Date().getTime();
	if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
		xmlhttp = new XMLHttpRequest();
	} else {// code for IE6, IE5
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}	
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			types=xmlhttp.responseText;
			//alert(yesorno);
				
				//document.getElementById("chutuan").innerHTML=arr[0];
			    typename(types);
		}				
	} 
	xmlhttp.open("GET","getzichansearchleixing.action?assetname="+assetname+"&nowtime="+time,true);
	//xmlhttp.open("GET","login.action",true);
	xmlhttp.send();
}


function typename(types){

var arry= new Array();
arry=types.split("|"); //字符分割 
var obj=document.getElementById('assettype'); 
obj.options.length=0;
obj.options.add(new Option("-请选择资产型号-","wu"));
for (var i=1;i<arry.length;i++){	
	obj.options.add(new Option(arry[i],arry[i])); //这个兼容IE与firefox 
}
} 


function tijiao() {
	 
		  var assetname = document.getElementById("assetname").value; 
		  var assettype = document.getElementById("assettype").value;
		  var assetchu = document.getElementById("assetchu").value;
        
          	if((assettype!="wu")&&(assetname=="wu"))
          	   {
          	   	 alert("请选择资产名称！");
          	   	 return;
          	   }
          	
	
		      with(document.forms[0]) {
						
						action='viewzcsearchallzx.action';
						method="post";
						submit();
					}
				
			
	  }
	 
	function importinfo()
	{
			window.location = "<%=path%>/page/zcgl/zcinfodr.jsp";
	}
	 
	function expressout()
	{
		var assettype = document.getElementById("assettype").value;
		var assetname = document.getElementById("assetname").value;
		var assetchu = document.getElementById("assetchu").value;
		window.location = "<%=path%>/exportassetinfo.action?assettype="+assettype+"&assetname="+assetname+"&assetchu="+assetchu;
	 }
	 
	function expressoutdetail()
	{
	
		window.location = "<%=path%>/exportassetinfodetail.action";
	 }
	 
   function show(name,type,chu,status)
	{
		var namec = encodeURI(name); 
		var typec = encodeURI(type);
		var chuc = chu;
		var statusc = status
		window.location = "<%=path%>/showsearchdetail.action?name="+namec+"&type="+typec+"&chu="+chuc+"&status="+statusc;
	}

</script>
 <link href="<%=path%>/css/table_back.css" rel="stylesheet" type="text/css">
  </head>
  
  <body>
  ${daohang}
  <form action="<%=path%>/viewzcsearchallzx.action" method="post" name="fm1">
					<br>
					<br>
					<table height="80" align="center" cellpadding="0"
						cellspacing="2" border: 0px;">
						<tr>
							<td
								style="color: #1778C2; padding-top: 0px; padding-bottom: 5px; border: 0px; font-size: 26px; font-family: '黑体';"
								colspan="15" align="center" bordercolor="#FFFFFF"><b>中心资产</b>
							</td>
						</tr>
						<tr>
							<td colspan="26" height="10px">
								<div align="center">
				
									资产名称
									<select id="assetname" name="assetname"  style="width: 135px" onchange="gettype()">
			                            <option value="wu">-请选择资产名称-</option>
			                          
			                            <c:forEach items="${listinfo}" var="info" varStatus="status">
        						          <option value="${info}">${info}</option>
        				                 </c:forEach>
		                            </select>
		                       
		           
		                            
		                                                                           型号
		                            <select id="assettype" name="assettype"  style="width: 135px" >
			                        <option value="wu">-请选择资产型号-</option>
			                        
		                            </select>
		                            
		                                                                          处室团队
									<select id="assetchu" name="assetchu" style="width: 135px" >
										<option value="wu">-请选择处室名称-</option>
										
										<option value="1">综合与生产管理处</option>
										<option value="2">合规与监督二处</option>
										<option value="3">通用业务二处</option>
										<option value="4">员工响应团队</option>
										<option value="5">研发支持二处</option>
										<option value="6">专业处理二处</option>
										<option value="7">库房</option>
									</select>
									资产/物品
									<select id="iswupin" name="iswupin" style="width: 135px" >
										<option value="wu">-请选择资产/物品-</option>
										<option value="1">资产</option>
										<option value="2">物品</option>
										
									
									</select>						
									<input style="width:70px" type="button" onclick="tijiao()" value="查询"/>
									<c:if test="${authoM=='M'}">
								    <input style="width:70px" type="button" value="全量导入" onclick="importinfo()"/>
								    </c:if>
									<input style="width:70px" type="button" value="导出汇总" onclick="expressout()"/>
								
									<input style="width:70px" type="button" value="导出明细" onclick="expressoutdetail()"/>
							</div></td>	
						</tr>
						<tr height="50px" class="表格表头背景1" id="hang">
								<td  width="40px" align="center" valign="middle" nowrap
								bordercolor=none><div align="center">
									<p>序号</p>
								</div></td>
							
							<td  width="200px" align="center" valign="middle" nowrap
								bordercolor=none><div align="center">
									<p>资产名称</p>
								</div></td>
							<td  width="250px"  align="center" valign="middle" nowrap
								bordercolor=none><div align="center">
									<p>资产型号</p>
								</div></td>	
							<td  width="150px" align="center" valign="middle" nowrap
								bordercolor=none><div align="center">
									<p>使用处室</p>
								</div></td>
							<td  width="100px"  align="center" valign="middle" nowrap
								bordercolor=none><div align="center">
									<p>使用状态</p>
								</div></td>
							<td  width="100px"  align="center" valign="middle" nowrap
								bordercolor=none><div align="center">
									<p>数量</p>
								</div></td>
						   	<td  width="100px"  align="center" valign="middle" nowrap
								bordercolor=none><div align="center">
									<p>详情</p>
								</div></td>
											
						   </tr>
						
							
							<c:forEach items="${listshow}" var="zcsearch" varStatus="status">
							<tr class="btbj" id="hang" style="height:20px">
								
								<td width="40px" height="25" align="center" valign="middle"  nowrap >
								
								<div
										align="center">${status.index+1+(currentPage-1)*pageSize}</div></td>
								<td width="200px" height="25" align="center" valign="middle"  nowrap><div
										align="center">${zcsearch.name}</div></td>
								
								<td width="250px" height="25" align="center" valign="middle" nowrap>
							
									<table align="center" cellpadding="0">
										<c:forEach items="${zcsearch.type}" var="zcsearchtype" varStatus="status">
										<tr class="btbj" >
										<td>
										${zcsearchtype.type}
										</td>
										</tr>
										</c:forEach>
									</table>	
										
								</td>
								
							
								<td width="150px" height="25" align="center" valign="middle"   nowrap>
										<table align="center" cellpadding="0">
										<c:forEach items="${zcsearch.chu}" var="zcsearchchu" varStatus="status">
										<tr class="btbj" >
										<td>
										${fb:chutostring(zcsearchchu.chu)}
										</td>
										</tr>
										</c:forEach>
									    </table>	
										
									</td>
															
								
								<td width="100px" height="25" align="center" valign="middle" nowrap>
										<table align="center" cellpadding="0">
										<c:forEach items="${zcsearch.status}" var="zcsearchstatus" varStatus="status">
										<tr class="btbj" >
										<td>
										${fb:assetstatustostring(zcsearchstatus)}
										</td>
										</tr>
										</c:forEach>
									    </table>	
										
									</td>
								
								
								<td width="100px" height="25" align="center" valign="middle" nowrap>
										<table align="center" cellpadding="0">
										<c:forEach items="${zcsearch.num}" var="zcsearchnum" varStatus="status">
										<tr class="btbj" >
										<td>
										${fb:assetstatustostring(zcsearchnum)}
										</td>
										</tr>
										</c:forEach>
									    </table>	
										
										
										</td>
								
								
							    <td width="100px" height="25" align="center" valign="middle" nowrap>
										<table align="center" cellpadding="0">
										<c:forEach items="${zcsearch.searchinfo}" var="zcsearchinfo" varStatus="status">
										<tr class="btbj" >
										<td>
										<input style="width:70px" type="button" onclick="show('${zcsearchinfo.name}','${zcsearchinfo.type}','${zcsearchinfo.chu}','${zcsearchinfo.status}')"  value="查看详情"/>
										</td>
										</tr>
										</c:forEach>
									    </table>	
										
										</td>
							   
							</tr>
							
							</c:forEach>
							
						
			              
							
												
		</table>
		
		                    <input type="hidden" id="type" name="type" value="${type}"/>
						    <input type="hidden" id="newnumber" name="newnumber" value="${newnumber}"/>
		</form>
  </body>
</html>
