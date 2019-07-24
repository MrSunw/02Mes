function dologin() {
	  		//非空校验
	  		var loginacct=$("#loginacct").val();//取出元素的值
	  		//loginacct==null--表单元素的value取值不会是null，取值是空字符串
	  		if(loginacct==""){
//	  			alert("用户的账号不能为空，请输入");使用layer提高用户使用体验
	  			layer.msg("The user's account cannot be empty. Please enter",{time:1000,icon:5,shift:6},function(){
	  				//alert("回调方法");指代的是关闭的时候会执行一些方法
	  				//{time:1000,icon:5,shift:6}  弹出来的图保留多少时间，icon的第几个，从左往右数
	  			});
	  			return;//如果不想让程序的逻辑往下执行，直接返回return
	  		}
	  		//提交表单
	  		var userpasswd=$("#userpasswd").val();
	  		if(userpasswd==""){
//	  			alert("用户登录密码不能为空,请输入");
	  			layer.msg("password cannot be empty, please enter",{time:1000,icon:5,shift:6},function(){
	  			});
	  			return;
	  		}
	  		//提交表单
//	  		alert("提交表单");
//	  		$("#loginForm").submit();
	  		//使用ajax提交数据
	  		var loadingIndex=null;//layer插件的等待界面的状态变量
	  		$.ajax({
	  			type:"POST",
	  			dataType:"json",
	  			url:"UserServlet?method=loginAjax",
	  			data:{
	  				"loginacct":loginacct,
	  				"userpasswd":userpasswd
	  			},
	  		//考虑到发送数据过程中网络延迟等情况，加入layer的等待效果，用户体验良好
	  			beforeSend:function(){//加载的操作,发送数据前
	  				//使用插件的效果
	  				loadingIndex=layer.msg("处理中",{icon:16});
	  			},//服务器返回结果后,采取动作
	  			//jsondata--"{"success":"true","result":"{"":"","":""}","message":"info---"}"
	  			success:function(result){
	  				//Json.parse(result.result)
	  				layer.close(loadingIndex);//拿到数据了就应该关闭等待效果
	  				if(result.success){
	  					window.location.href="/DispatcherServlet?method=mainPage";
	  				}else{
	  					layer.msg("The user's login account or password is incorrect. Please re-enter it.",{time:1000,icon:5,shift:6},function(){
	  		  			});	  				
	  				}
	  			}
	  		});//所谓使用json对象，简单理解就是大括号，键值对，各种值的表达形式
}