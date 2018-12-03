<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>吉客印 · 日单统计表 </title>
</head>
<body>
	<div class="bg-fa of">
		<section class="container">
			<section class="i-question">
				<div class="fl col-7">
					<section class="mr30">
						<header class="comm-title all-article-title">
							<h2 class="fl tac">
								<span class="c-333">吉客印 · 日单统计表</span>
							</h2>
						</header>
						<div class="clear"></div>
						<!-- /录入开始 -->
						<div class="q-c-list">
							<dl>
								<dt>
									<span class="c-999 fsize14">标题：</span>
								</dt>
								<dd class="pr">
									<label class=""><input type="text" name="questions.title" placeholder="问题标题不少于16个字" onkeyup="checkTitleLength(this)" value=""></label>
									<aside class="q-c-jy"></aside>
								</dd>
							</dl>
							<dl>
								<dt>
									<span class="c-999 fsize14">内容：</span>
								</dt>
								<dd class="pr">
									<textarea name="questions.content" placeholder="简洁，明了，能引起思考和讨论的知识性的内容。" onkeyup="checkQuestionContent(this)"></textarea>
									<aside class="q-c-jy"></aside>
								</dd>
							</dl>
						</div>
						<!-- /提问题 结束 -->
					</section>
				</div>
				<aside class="fl col-3">
					<div class="mt30 pl10">
						<h5 class="pt10">
							<span class="fsize18 c-333 vam">亲，您要提问吧？<br>
							<br>要知道这些哦！
							</span>
						</h5>
						<div class="clear"></div>
						<dl class="mt20">
							<dt>
								<h6>
									<strong class="fsize14 c-666">一、需要了解的事情：</strong>
								</h6>
							</dt>
							<dd class="pl10">
								<p class="c-999 mt10">1、您是想来吐槽的吧，没事，随便发吧。有人会跟你一起吐槽的。</p>
								<p class="c-999 mt10">2、您是来解决问题？请先搜索是否已经有同类问题吧。这样您就省心少打字。</p>
								<p class="c-999 mt10">3、没找到是么？就在发问题时精确描述你的问题，不要写与问题无关的内容哟。</p>
								<p class="c-999 mt10">4、因酷问答更热衷于解达您想要的答案。能引起思考和讨论的知识性问题；</p>
							</dd>
						</dl>
						<dl class="mt20">
							<dt>
								<h6>
									<strong class="fsize14 c-666">二、要注意的事情：</strong>
								</h6>
							</dt>
							<dd class="pl10">
								<p class="c-999 mt10">1、禁止发布求职、交易、推广、广告类与问答无关信息将一律清理。</p>
								<p class="c-999 mt10">2、尽可能详细描述您的问题，如标题与内容不符，或与问答无关的信息将被关闭。</p>
								<p class="c-999 mt10">3、问答刷屏用户一律冻结帐号。</p>
							</dd>
						</dl>
					</div>
				</aside>
				<!-- <div class="clear"></div> -->
			</section>
		</section>
		<!-- /提问题 结束 -->
	</div>
	<script type="text/javascript" src="${ctx}/static/inxweb/questions/questions_add.js"></script>
</body>
</html>