<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>aaaaaaa</title>


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
</style>
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
</style>
</head>
<body>
	<div class="bg-fa of">
		<section class="container"> <section class="i-question">
		<div class="fl col-7">

			<section class="mr30"> <header
				class="comm-title all-article-title">
			<h2 class="mt20">
				<span class="mt20">cccccccccccc</span>
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
			<div class="mt20" style="overflow-x: scroll; height: 380px;">
				<form action="" method="post" id="updateRoleFunctionForm"
					onsubmit="return updateRole();">
					<table width="100%" cellspacing="0" cellpadding="0" border="0"
						class="commonTab01">
						<thead>
							<tr>
								<td class="wenm" align="center"><span class="c-999 fsize16"
									id="span001">线路</span></td>
								<td class="wenm" align="center"><span class="c-999 fsize16"
									id="span002">今日订单</span></td>
								<td class="wenm" align="center"><span class="c-999 fsize16"
									id="span003">ROI</span></td>
								<td class="wenm" align="center"><span class="c-999 fsize16"
									id="span004">上新</span></td>
								<td class="wenm" align="center"><span class="c-999 fsize16"
									id="span005">在线活跃产品</span></td>
								<td class="wenm" align="center"><span class="c-999 fsize16"
									id="span006">单品产能</span></td>
								<td class="wenm" align="center"><span class="c-999 fsize16"
									id="span007">销售额</span></td>
								<td class="wenm" align="center"><span class="c-999 fsize16"
									id="span008">花费</span></td>
							</tr>
						</thead>

						<tbody id="detail_input_tbody">

						</tbody>
					</table>
			</div>
			<dd>
				<section class="pt10"> <a href="javascript:void(0)"
					onclick="submitDetails()" title=""
					class="comm-btn c-btn-4 bg-orange">提 交</a> <a
					href="javascript:void(0)" onclick="creatReport()" title=""
					class="comm-btn c-btn-4 bg-orange">创 建</a></section>
				<!-- <section class="pt10">  </section> -->
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
					<p class="c-999 mt10">1、**********</p>
					<p class="c-999 mt10">2、************</p>
					<p class="c-999 mt10">3、************</p>
				</dd>
			</dl>
			<dl class="mt20">
				<dt>
					<h3>
						<strong class="fsize14 c-666">二、**********</strong>
					</h3>
				</dt>
				<dd class="pl10">
					<p class="c-999 mt10">1、*****************</p>
					<p class="c-999 mt10">2、*************</p>
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
		
		function submitDetails() {
			var data = [];
			var index = 0;
			var trs = $('#detail_input_tbody').find('tr');
			trs.each(function() {
						var item = {};
						item.order_amount = ($(this).find('td:eq(1)').find('input'))[0].value;
						if(!isIntNum(item.order_amount)){
							alertMsg("输入提示", "[今日订单]必须为非负整数值！")
						}
						
						item.roi_index = ($(this).find('td:eq(2)').find('input'))[0].value;
						if(!isNumber(item.roi_index)){
							alertMsg("输入提示", "请在[ROI]处输入有效的数值！")
						}
						
						item.ground_pro = ($(this).find('td:eq(3)').find('input'))[0].value;
						if(!isIntNum(item.ground_pro)){
							alertMsg("输入提示", "[上新]必须为非负整数值！")
						}
						
						item.alive_pro = ($(this).find('td:eq(4)').find('input'))[0].value;
						if(!isIntNum(item.alive_pro)){
							alertMsg("输入提示", "请在[在线活跃产品]必须为非负整数值！")
						}
						
						item.single_earn = ($(this).find('td:eq(5)').find('input'))[0].value;
						if(!isNumber(item.single_earn)){
							alertMsg("输入提示", "请在[单品产能]处输入有效的数值！")
						}
						
						item.sales_amount = ($(this).find('td:eq(6)').find('input'))[0].value;
						if(!isNumber(item.sales_amount)){
							alertMsg("输入提示", "请在[销售额]处输入有效的数值！")
						}
						
						item.cost_amount = ($(this).find('td:eq(7)').find('input'))[0].value;
						if(!isNumber(item.cost_amount)){
							alertMsg("输入提示", "请在[花费]处输入有效的数值！")
						}
						
						item.teamId = ($(this).find('td:eq(8)').find('input'))[0].value;
						item.originId = ($(this).find('td:eq(9)').find('input'))[0].value;
						data[index] = item;
						index = index + 1;
					});
			data = JSON.stringify(data)
			$.ajax({
				url : baselocation + "/sava_detail",
				data : {
					"details" : data
				},
				dataType : "json",
				type : "post",
				async : false,
				success : function(data) {
					alert(data);
				}
			})

		}
		function addOriginSaleInput(teamId) {
			var tbody = document.getElementById("detail_input_tbody");
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
							for (var i = 0; i < data.length; i++) {
								var origin = data[i];
								var tr = tbody.insertRow(i);
								tr.setAttribute("height", "40px");
								var td_1 = tr.insertCell(0);
								td_1.className = "wenm";
								td_1.setAttribute("align", "center");
								//td_1.innerHTML = '<span class="c-999 fsize16">'
								td_1.innerHTML = '<span class="c-999 fsize16" style ="display: block;width: 80px;">'
										+ origin.originName + ':</span>';
								var td_2 = tr.insertCell(1);
								td_2.setAttribute("align", "center");
								td_2.className = "wenm";
								//td_2.innerHTML = '<input type="number" style="height:22px" class="h-r-s-box" name = "'+ origin.originId+'" value="" />';
								td_2.innerHTML = '<input type="number" style="height:22px;width:95px;" class="c-999 fsize16" value="'+( origin.orderAmount>0 ? origin.orderAmount :"") + '" />';

								var td_3 = tr.insertCell(2);
								td_3.className = "wenm";
								td_3.setAttribute("align", "center");
								td_3.innerHTML = '<input type="number" style="height:22px;width:95px;" class="c-999 fsize16" value="'+( origin.roiIndex>0 ? origin.roiIndex :"") + '" />';

								var td_4 = tr.insertCell(3);
								td_4.className = "wenm";
								td_4.setAttribute("align", "center");
								td_4.innerHTML = '<input type="number" style="height:22px;width:95px;" class="c-999 fsize16" value="'+( origin.groundPro>0 ? origin.groundPro :"") + '" />';

								var td_5 = tr.insertCell(4);
								td_5.className = "wenm";
								td_5.setAttribute("align", "center");
								td_5.innerHTML = '<input type="number" style="height:22px;width:95px;" class="c-999 fsize16" value="'+( origin.alivePro>0 ? origin.alivePro :"") + '" />';

								var td_6 = tr.insertCell(5);
								td_6.className = "wenm";
								td_6.setAttribute("align", "center");
								td_6.innerHTML = '<input type="number" style="height:22px;width:95px;" class="c-999 fsize16" value="'+( origin.singleEarn>0 ? origin.singleEarn :"") + '" />';

								var td_7 = tr.insertCell(6);
								td_7.className = "wenm";
								td_7.setAttribute("align", "center");
								td_7.innerHTML = '<input type="number" style="height:22px;width:95px;" class="c-999 fsize16" value="'+( origin.salesAmount>0 ? origin.salesAmount :"") + '" />';

								var td_8 = tr.insertCell(7);
								td_8.className = "wenm";
								td_8.setAttribute("align", "center");
								td_8.innerHTML = '<input type="number" style="height:22px;width:95px;" class="c-999 fsize16" value="'+( origin.costAmount>0 ? origin.costAmount :"") + '" />';

								var td_9 = tr.insertCell(8);
								td_9.innerHTML = '<input type="hidden" value="'+teamId+'" name="teamId" />';
								var td_10 = tr.insertCell(9);
								td_10.innerHTML = '<input type="hidden" value="'+origin.originId+'" name="originId" />';
							}
						}
					})
		}

		function creatReport() {
			$.ajax({
				url : baselocation + "/creat_report",
				data : {},
				dataType : "json",
				type : "post",
				async : false,
				success : function(data) {
					alert(data);
				}
			})
		}
		
		function isNumber(val){
		    var regPos = /^\d+(\.\d+)?$/; //非负浮点数
		    var regNeg = /^((([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*)))$/; //负浮点数
		    if(regPos.test(val) || regNeg.test(val)){
		        return true;
		    }else{
		        return false;
		    }
		}
		
		function isIntNum(val){
		    var regPos = / ^\d+$/; // 非负整数
		    return regPos.test(val);
		}
		
		function alertMsg(titleTxt, msg) {
			layer.open({
				title : [ titleTxt, 'background-color:#8DCE16; color:#fff;' ],
				anim : 'up',
				content : msg,
				btn : [ '确认' ]
			});
		}
		
		
	</script>
</body>
</html>