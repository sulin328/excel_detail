<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>吉客印 · 日单统计表</title>


<style type="text/css">
.origin {
	color: rgb(85, 85, 85);
	display: inline-block;
	font-size: 14px;
	height: 30px;
	line-height: 30px;
	vertical-align: middle;
	font-family: "Microsoft YaHei";
	margin: 5px 0px;
	padding: 0px 6px;
	border-width: 1px;
	border-style: solid;
	border-color: rgb(204, 204, 204);
	border-image: initial;
}

 table {
        /*为表格设置合并边框模型*/
        border-collapse: collapse;
        /*列宽由表格宽度和列宽度设定*/
        table-layout: fixed;
   }
             
td {
     border: 1px solid #ddd;
     /*允许在单词内换行。*/
     word-break: break-word;
     /*设置宽度*/
     width: 100px;
 }

/* table tbody {
               display: block;
               height: 400px;
               width: 100%;
               overflow-y: scroll;
               overflow-x: scroll;
            } */
/* 
td  {  
 white-space: nowrap;  
} */
</style>
</head>
<body>
	<div class="bg-fa of">
		<section class="container"> <section class="i-question">
		<div class="fl col-7">

			<section class="mr30"> <header
				class="comm-title all-article-title">
			<h2 class="mt20">
				<span class="mt20">吉客印 · 日单统计录入表</span>
			</h2>
			</header>
			<div class="clear"></div>
			<div class="mt20">
				<tr>
					<td width="25%" align="right"><span class="c-999 fsize16">团队：</span>
					</td>
					<td width="20%" align="center"><select class="origin"
						id="team" onchange="addOriginSaleInput(this.value);">
							<option value="0">请选择团队</option>
							<c:forEach items="${data}" var="team">
								<option value="${team.teamId}">${team.teamName}</option>
							</c:forEach>
					</select></td>
				</tr>
			</div>
			<div class="mt20">
				<form action="" method="post" id="updateRoleFunctionForm"
					onsubmit="return updateRole();">
					<table width="100%" cellspacing="0" cellpadding="0" border="0"
						class="commonTab01">
						<thead>
					    <tr>
					      <td width="25%" align="center"><span class="c-999 fsize16" id="span001">线路</span></td>
					      <td width="25%" align="center"><span class="c-999 fsize16" id="span001">线路</span></td>
					      <td width="25%" align="center"><span class="c-999 fsize16" id="span001">线路</span></td>
					      <td width="25%" align="center"><span class="c-999 fsize16" id="span001">线路</span></td>
					      <td width="25%" align="center"><span class="c-999 fsize16" id="span001">线路</span></td>
					      <td width="25%" align="center"><span class="c-999 fsize16" id="span001">线路</span></td>
					      <td width="25%" align="center"><span class="c-999 fsize16" id="span001">线路</span></td>
					    </tr>
					  </thead>
						
						<tbody>
						
						</tbody>
					</table>
					</div>
					<dd>
						<section class="pt10"> <a href="javascript:void(0)"
							onclick="addQuestions()" title=""
							class="comm-btn c-btn-4 bg-orange">提 交</a> </section>
					</dd>
				</form>
			</section>
		</div>
		<aside class="fl col-3">
		<div class="mt30 pl10">
			<h5 class="pt10">
				<span class="fsize18 c-333 vam" style="color: #FF238D">亲，这些需要注意一下哦
					^_^ <br>
				</span>
			</h5>
			<div class="clear"></div>
			<dl class="mt20">
				<dt>
					<h3>
						<strong class="fsize14 c-666">一、关于这个页面：</strong>
					</h3>
				</dt>
				<dd class="pl10">
					<p class="c-999 mt10">1、这并不是一个很高大上的网站，所以功能一点也不丰富，也没有做很多非常复杂的验证，多以呢，请您提交内容一定规范啊，很是拜托了，也灰常感谢啦。</p>
					<p class="c-999 mt10">2、第一条一定要好好看噢，不介意您多看几次的。</p>
					<p class="c-999 mt10">3、哎呀，我也不知道说啥了，就这么多吧。</p>
				</dd>
			</dl>
			<dl class="mt20">
				<dt>
					<h3>
						<strong class="fsize14 c-666">二、这里没重点，我还想念首诗给你听：</strong>
					</h3>
				</dt>
				<dd class="pl10">
					<p class="c-999 mt10">1、本着为方便大家统计订单做的，如果用的方便，以后有什么需求再添加吧。</p>
					<p class="c-999 mt10">2、申明一下，我很厉害，真的。</p>
				</dd>
			</dl>
		</div>
		</aside> </section> </section>
	</div>
	<script type="text/javascript">
		window.onload = function() {
			// var userAgentInfo = navigator.userAgent;
			var a = document.getElementsByTagName("section");
			a[a.length - 1].style.display = 'none';
		}
		function addOriginSaleInput(teamId) {
			var tab = document.getElementsByTagName("tbody");
			var tbody = tab[0];
			$.ajax({
						url : baselocation + "/mark_origin",
						data : {
							"teamId" : teamId
						},
						dataType : "json",
						type : "post",
						async : false,
						success : function(data) {
							tbody.innerHTML = '';
							var width = "20%";
							for (var i = 0; i <= data.length; i++) {
								var origin = data[i];
								var tr = tbody.insertRow(i);
								tr.setAttribute("height", "40px");
								tr.setAttribute("width", "70%");
								var td_1 = tr.insertCell(0);
								td_1.setAttribute("width", width);
								td_1.setAttribute("align", "center");
								//td_1.innerHTML = '<span class="c-999 fsize16">'
								td_1.innerHTML = '<span class="c-999 fsize16">'
										+ origin.originName + ':</span>';
								var td_2 = tr.insertCell(1);
								td_2.setAttribute("width", width);
								td_2.setAttribute("align", "center");
								//td_2.innerHTML = '<input type="number" style="height:22px" class="h-r-s-box" name = "'+ origin.originId+'" value="" />';
								td_2.innerHTML = '<input type="number" style="height:22px" class="c-999 fsize16" name = "'+ origin.originId+'" value="" />';
								
								var td_3 = tr.insertCell(2);
								td_3.setAttribute("width", width);
								td_3.setAttribute("align", "center");
								td_3.innerHTML = '<input type="number" style="height:22px" class="c-999 fsize16" name = "'+ origin.originId+'" value="" />';
								
								var td_4 = tr.insertCell(3);
								td_4.setAttribute("width", width);
								td_4.setAttribute("align", "center");
								td_4.innerHTML = '<input type="number" style="height:22px" class="c-999 fsize16" name = "'+ origin.originId+'" value="" />';
								
								var td_5 = tr.insertCell(4);
								td_5.setAttribute("width", width);
								td_5.setAttribute("align", "center");
								td_5.innerHTML = '<input type="number" style="height:22px" class="c-999 fsize16" name = "'+ origin.originId+'" value="" />';
								
								var td_6 = tr.insertCell(5);
								td_6.setAttribute("width", width);
								td_6.setAttribute("align", "center");
								td_6.innerHTML = '<input type="number" style="height:22px" class="c-999 fsize16" name = "'+ origin.originId+'" value="" />';
								
								var td_7 = tr.insertCell(6);
								td_7.setAttribute("width", width);
								td_7.setAttribute("align", "center");
								td_7.innerHTML = '<input type="number" style="height:22px" class="c-999 fsize16" name = "'+ origin.originId+'" value="" />';
								
								
								var td_8 = tr.insertCell(7);
								//td_3.setAttribute("width", "50%");
								td_8.innerHTML = '<input type="hidden" value="'+teamId+'" name="teamId" />';
							}
						}
					})
		}
	</script>
</body>
</html>