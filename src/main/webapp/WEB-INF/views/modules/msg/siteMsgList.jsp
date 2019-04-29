<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>单表管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/msg/siteMsg/">已发列表</a></li>
		<shiro:hasPermission name="msg:siteMsg:edit"><li><a href="${ctx}/msg/siteMsg/form">发送短信</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="siteMsg" action="${ctx}/msg/siteMsg/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
		<li><label>电话号码：</label>
				<form:input path="phone" name="phone" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>电话号码</th>
				<th>消息内容</th>
				<th>发送时间</th>
				<shiro:hasPermission name="msg:siteMsg:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="siteMsg">
			<tr>
				<td><a href="${ctx}/test/testData/form?id=${testData.id}">
					${siteMsg.phone}
				</a></td>
				<td>
					${siteMsg.message}
				</td>
				<td>
					${siteMsg.createtime}
				</td>
				<shiro:hasPermission name="msg:siteMsg:edit"><td>
					<a href="${ctx}/msg/siteMsg/delete?id=${siteMsg.id}" onclick="return confirmx('确认要删除该单表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>