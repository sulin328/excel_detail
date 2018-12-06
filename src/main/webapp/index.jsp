<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>吉客印日报平台</title>


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
	<div class="bg-fa of" >
		<section class="container"> <section class="i-question">
		<div class="fl col-7">

			<section class="mr30"> <header
				class="comm-title all-article-title">
			<h2 class="mt20">
				<span class="mt20">吉客印 • 日报录入</span>
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
					<section class="pt10"> 
						<a href="javascript:void(0)" onclick="submitDetails()" title="" class="comm-btn c-btn-4 bg-orange">提 交</a> 
						<a href="javascript:void(0)" onclick="trunToFileManager()"  class="comm-btn c-btn-4 bg-orange">报 表 管 理</a>
					</section>
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
					<p class="c-999 mt10">1、这不是一个完整的网站，最多算一个页面，谨为方便吉客印日报相关同学每天数据提交和报表生成；</p>
					<p class="c-999 mt10">2、目前可支持工作日和节假日每天的数据录入和日报生成，不支持补单；原因是突然想懒一下，没加日期选择；</p>
					<p class="c-999 mt10">3、数据只会保存最后一次提交，所以，感觉提交错了自觉重新提交一下就行了；</p>
					<p class="c-999 mt10">4、千万！每个团队自觉提交自己的数据，因为没有检验身份，所以默认同学们每次录入都是正确的；</p>
					<p class="c-999 mt10">5、千万！技术大牛们别尝试验证这个页面的健壮性，因为服务器的性能，压测和DOS攻击奴家都是经不起的；</p>
				</dd>
			</dl>
			<dl class="mt20">
				<dt>
					<h3> 
						<strong class="fsize14 c-666">二、还有这些提醒</strong>
					</h3>
				</dt>
				<dd class="pl10">
					<p class="c-999 mt10">1、其实也没什么交代得了，只是觉得写两大条比较帅；如果同学你看到了这里，一定记得再多看一眼第一条；</p>
					<p class="c-999 mt10">2、Every man is the architect of his own fate. The proper function of man is to live, not live;</p>
				</dd>
			</dl>
		</div>
		</aside> </section> </section>

	</div>
	<script type="text/javascript">
		window.onload = function() {
			var a = document.getElementsByTagName("section");
			a[a.length - 1].style.display = 'none';
		}
		
		function trunToFileManager() {
			window.location.href=baselocation+'/toFileManagerPage';
		}
		
		function submitDetails() {
			var data = [];
			//var index = 0;
			var trs = $('#detail_input_tbody').find('tr');
			if(trs.length == '' || trs.length ==0 ){
				alertMsg("提示信息", "请至少选择一个团队并录入正确的值后再提交！")
				return false;
			}
			for(var i = 0; i<trs.length;i++){
				var item = {};
				var tr = trs[i];
				item.order_amount = ($(tr).find('td:eq(1)').find('input'))[0].value;
				item.order_amount = item.order_amount == ""? 0 : item.order_amount;
				if(!isIntNum(item.order_amount)){
					alertMsg("输入提示", "[今日订单]必须为非负整数值！")
					return false;
				}
				
				item.roi_index = ($(tr).find('td:eq(2)').find('input'))[0].value;
				item.roi_index = item.roi_index == ""? 0 : item.roi_index;
				if(!isNumber(item.roi_index)){
					alertMsg("输入提示", "请在[ROI]处输入有效的数值！")
					return false;
				}
				
				item.ground_pro = ($(tr).find('td:eq(3)').find('input'))[0].value;
				item.ground_pro = (item.ground_pro =='' ? 0 : item.ground_pro);
				if(!isIntNum(item.ground_pro)){
					alert( item.ground_pro);
					alertMsg("输入提示", "[上新]必须为非负整数值！")
					return false;
				}
				
				item.alive_pro = ($(tr).find('td:eq(4)').find('input'))[0].value;
				item.alive_pro = item.alive_pro == ""? 0 : item.alive_pro;
				if(!isIntNum(item.alive_pro)){
					alertMsg("输入提示", "请在[在线活跃产品]必须为非负整数值！")
					return false;
				}
				
				item.single_earn = ($(tr).find('td:eq(5)').find('input'))[0].value;
				item.single_earn = item.single_earn == ""? 0 : item.single_earn;
				if(!isNumber(item.single_earn)){
					alertMsg("输入提示", "请在[单品产能]处输入有效的数值！")
					return false;
				}
				
				item.sales_amount = ($(tr).find('td:eq(6)').find('input'))[0].value;
				item.sales_amount = item.sales_amount == ""? 0 : item.sales_amount;
				if(!isNumber(item.sales_amount)){
					alertMsg("输入提示", "请在[销售额]处输入有效的数值！")
					return false;
				}
				
				item.cost_amount = ($(tr).find('td:eq(7)').find('input'))[0].value;
				item.cost_amount = item.cost_amount == ""? 0 : item.cost_amount;
				if(!isNumber(item.cost_amount)){
					alertMsg("输入提示", "请在[花费]处输入有效的数值！")
					return false;
				}
				
				item.teamId = ($(tr).find('td:eq(8)').find('input'))[0].value;
				item.originId = ($(tr).find('td:eq(9)').find('input'))[0].value;
				data[i] = item;
			}
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
					if(data.status){
						layer.open({
							title : [ "提交成功", 'background-color:#25E712; color:#fff;' ],
							anim : 'up',
							content : data.msg,
							btn : [ '确认' ]
						});
					}else{
						alertMsg("输入提示", data.msg)
					}
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
								td_2.innerHTML = '<input type="number" style="height:22px;width:95px;" class="c-999 fsize16" defaultValue ="0.00" value= ="'+( origin.orderAmount>0 ? origin.orderAmount :"") + '"/>';

								var td_3 = tr.insertCell(2);
								td_3.className = "wenm";
								td_3.setAttribute("align", "center");
								td_3.innerHTML = '<input type="number" style="height:22px;width:95px;" class="c-999 fsize16" defaultValue ="0.00" value="'+( origin.roiIndex>0 ? origin.roiIndex :"") + '" />';

								var td_4 = tr.insertCell(3);
								td_4.className = "wenm";
								td_4.setAttribute("align", "center");
								td_4.innerHTML = '<input type="number" style="height:22px;width:95px;" class="c-999 fsize16" defaultValue ="0.00" value="'+( origin.groundPro>0 ? origin.groundPro :"") + '" />';

								var td_5 = tr.insertCell(4);
								td_5.className = "wenm";
								td_5.setAttribute("align", "center");
								td_5.innerHTML = '<input type="number" style="height:22px;width:95px;" class="c-999 fsize16" defaultValue ="0.00" value="'+( origin.alivePro>0 ? origin.alivePro :"") + '" />';

								var td_6 = tr.insertCell(5);
								td_6.className = "wenm";
								td_6.setAttribute("align", "center");
								td_6.innerHTML = '<input type="number" style="height:22px;width:95px;" class="c-999 fsize16" defaultValue ="0.00" value="'+( origin.singleEarn>0 ? origin.singleEarn :"") + '" />';

								var td_7 = tr.insertCell(6);
								td_7.className = "wenm";
								td_7.setAttribute("align", "center");
								td_7.innerHTML = '<input type="number" style="height:22px;width:95px;" class="c-999 fsize16" defaultValue ="0.00" value="'+( origin.salesAmount>0 ? origin.salesAmount :"") + '" />';

								var td_8 = tr.insertCell(7);
								td_8.className = "wenm";
								td_8.setAttribute("align", "center");
								td_8.innerHTML = '<input type="number" style="height:22px;width:95px;" class="c-999 fsize16" defaultValue ="0.00"  value="'+( origin.costAmount>0 ? origin.costAmount :"") + '" />';

								var td_9 = tr.insertCell(8);
								td_9.innerHTML = '<input type="hidden" value="'+teamId+'" name="teamId" />';
								var td_10 = tr.insertCell(9);
								td_10.innerHTML = '<input type="hidden" value="'+origin.originId+'" name="originId" />';
							}
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
		    var regPos = /^[1-9]\d*|0$/;;
		    return regPos.test(val);
		}
		
		function alertMsg(titleTxt, msg) {
			layer.open({
				title : [ titleTxt, 'background-color:#F3752C; color:#fff;' ],
				anim : 'up',
				content : msg,
				btn : [ '确认' ]
			});
		}
	</script>
</body>
</html>