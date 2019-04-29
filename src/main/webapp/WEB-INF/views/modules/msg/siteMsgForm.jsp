<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>单表管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/msg/siteMsg/">已发列表</a></li>
		<li class="active"><a href="${ctx}/msg/siteMsg/form?id=${siteMsg.id}">短信<shiro:hasPermission name="msg:siteMsg:edit">${not empty siteMsg.id?'修改':'发送'}</shiro:hasPermission><shiro:lacksPermission name="msg:siteMsg:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="siteMsg" action="${ctx}/msg/siteMsg/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">电话号码：</label>
			<div class="controls">
				<form:input path="phone" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">消息内容：</label>
			<div class="controls">
				<form:textarea style="height:100px;" path="message" htmlEscape="false" maxlength="2000" class="input-xlarge "/>
			</div>
		</div>
<!-- 		<div class="control-group"> -->
<!-- 			<label class="control-label">创建时间：</label> -->
<!-- 			<div class="controls"> -->
<!-- 				<input name="createtime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate " -->
<%-- 					value="<fmt:formatDate value="${siteMsg.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/>" --%>
<!-- 					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> -->
<!-- 			</div> -->
<!-- 		</div> -->
		<div class="form-actions">
			<shiro:hasPermission name="msg:siteMsg:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>