<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>吉客印日报平台</title>

<link rel="stylesheet"
	href="${ctx}/static/inxweb/upload-js/easy-upload.css">
<script src="${ctx}/static/inxweb/upload-js/vendor/jquery-1.12.4.min.js"></script>
<!-- 视实际需要决定是否引入jquery.cookie-1.4.1.min.js-->
<script
	src="${ctx}/static/inxweb/upload-js/vendor/jquery.cookie-1.4.1.min.js"></script>
<script src="${ctx}/static/inxweb/upload-js/easyUpload.js"></script>
<script src="${ctx}/static/inxweb/js/FileSaver.js"></script>
</head>
<body>
	<div class="bg-fa of">
		<section class="container"> <section class="i-question">
		<div class="fl col-7">
			<section class="mr30"> <header
				class="comm-title all-article-title">
			<h2 class="mt20">
				<span class="mt20">吉客印 • 报表和模板管理</span>
			</h2>
			</header>
			<div class="mt20" style="height: 380px;">
					<div id="easyContainer" ></div>
			</div>
			<div class="mt20">
					<div id="easyContainer" ></div>
			</div>
			<dd>
				<section class="pt10">
				<a href="javascript:void(0)" onclick="creatReport()" title="" class="comm-btn c-btn-4 bg-orange">生 成 报 表</a> 
				<a href="javascript:void(0)" onclick="downloadReport()" title="" class="comm-btn c-btn-4 bg-orange">下 载 日 报</a> 
				<a href="javascript:void(0)" onclick="trunToIndex()" class="comm-btn c-btn-4 bg-orange">返 回</a> </section>
			</dd>
			</section>
		</div>
		</section> </section>
	</div>
	<script type="text/javascript">
		window.onload = function() {
			// var userAgentInfo = navigator.userAgent;
			var a = document.getElementsByTagName("section");
			a[a.length - 1].style.display = 'none';
			var type = '${type}';
			if('upload' == type){
				$('#easyContainer').easyUpload({
			        allowFileTypes: '*.xlsx',//允许上传文件类型，格式';*.doc;*.pdf'
			        allowFileSize: 10240,//允许上传文件大小(KB)
			        selectText: '选择文件',//选择文件按钮文案
			        multi: false,//是否允许多文件上传
			        multiNum: 5,//多文件上传时允许的文件数
			        showNote: true,//是否展示文件上传说明
			        note: '提示：请选择一个.xlsx文件，文件不大于10M',//文件上传说明
			        showPreview: true,//是否显示文件预览
			        url: baselocation + '/doUpload',//上传文件地址
			        fileName: 'file',//文件filename配置参数
			        formParam: {
			          token: $.cookie('token_cookie')//不需要验证token时可以去掉
			        },//文件filename以外的配置参数，格式：{key1:value1,key2:value2}
			        timeout: 30000,//请求超时时间
			        okCode: 200,//与后端返回数据code值一致时执行成功回调，不配置默认200
			        successFunc: function(res) {
			        	successMsg("上传成功",res.msg)
			        },//上传成功回调函数
			        errorFunc: function(res) {
			        	errorMsg("上传失败",res.msg)
			        },//上传失败回调函数
			        deleteFunc: function(res) {
			          console.log('删除回调', res);
			        }//删除文件回调函数
			      });
			}
	    	
		}

		function trunToIndex() {
			window.location.href = baselocation + '/';
		}
		
		
		
		function downloadReport() {
			var day1 = new Date();
			day1.setTime(day1.getTime()-24*60*60*1000);
			var s1 = day1.getFullYear()+"-" + (day1.getMonth()+1) + "-" + day1.getDate();
			var fileName = day1.getFullYear()+'年'+(day1.getMonth()+1)+"月订单日报.xlsx";
			var src = baselocation+'/donwloadfile?fileName='+fileName;
			//download(src,fileName,null);
			var myFrame= document.createElement('iframe'); 
			myFrame.src = src; 
			myFrame.style.display = 'none'; 
			document.body.appendChild(myFrame);
		}
	    
	    function creatReport() {
			$.ajax({
				url : baselocation + "/creat_report",
				data : {},
				dataType : "json",
				type : "post",
				async : false,
				success : function(data) {
					if(data.status){
						successMsg("成功", data.msg)
					}else{
						errorMsg("输入提示", data.msg)
					}
				}
			})
		}
	    
		function errorMsg(titleTxt, msg) {
			layer.open({
				title : [ titleTxt, 'background-color:#F3752C; color:#fff;' ],
				anim : 'up',
				content : msg,
				btn : [ '确认' ]
			});
		}
		
		function successMsg(titleTxt, msg) {
			layer.open({
				title : [ titleTxt, 'background-color:#25E712; color:#fff;' ],
				anim : 'up',
				content : msg,
				btn : [ '确认' ]
			});
		}
		
		Date.prototype.Format = function (fmt) { //author: meizz 
		    var o = {
		        "M+": this.getMonth() + 1, //月份 
		        "d+": this.getDate(), //日 
		        "h+": this.getHours(), //小时 
		        "m+": this.getMinutes(), //分 
		        "s+": this.getSeconds(), //秒 
		        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
		        "S": this.getMilliseconds() //毫秒 
		    };
		    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
		    for (var k in o)
		    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
		    return fmt;
		}
	</script>
</body>
</html>