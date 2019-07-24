  //是否模糊查询
    $(function() {
    	//菜单有关内容不需要改动
    	$(".list-group-item").click(function() {
    		if ($(this).find("ul")) {
    			$(this).toggleClass("tree-closed");
    			if ($(this).hasClass("tree-closed")) {
    				$("ul", this).hide("fast");
    			} else {
    				$("ul", this).show("fast");
    			}
    		}
    	});
    
    	
    	//点击重置
    	$("#resetBtn").click(function(){
    		window.location.reload();
    	});
    	//点击修改
    	$("#updateBtn").click(function(){
    		var id=$("#id").val();
    		var departCode=$("#departCode").val();
    		var departName=$("#departName").val();
    		var departRemake=$("#departRemake").val();
    		var departNum=$("#departNum").val();
    		var departDesc=$("#departDesc").val();
    		var pag=$("#pag").val();
     		$.ajax({
        		type:"post",
        		dataType:"json",
        		url:"/DepartServlet?method=updatatAjax",
        		data:{
        			"id":id,
        			"departCode":departCode,
        			"departName":departName,
        			"departDesc":departDesc,
        			"departNum":departNum,
        			"departRemake":departRemake,
        			"pag":pag
        		},
        		
        		beforeSend : function(){// 加载的操作,发送数据前
    				// 使用插件的效果
    				//旋转圈圈旋转状态一直保存在一个变量中，界面显示为一个圈圈一直转
        			layerLoading = layer.msg("处理中", {
    					icon : 16
    				});
    			},
        		success:function(result){
        			//结束layer的效果
        			layer.close(layerLoading);
        			console.log(result)
        			if(result.success){
        				window.location.href="/DispatcherServlet?method=departPage&pag="+pag;
        				return;
        			}else{
        				layer.msg("修改失败！！！");
        			}
        		}
        	});
   
    	});
    	
     });