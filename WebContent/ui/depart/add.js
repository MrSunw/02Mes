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
    	
    	//点击添加
    	$("#addDepartBtn").click(function(){
    		var departCode=$("#departCode").val();
    		var departName=$("#departName").val();
    		var departRemake=$("#departRemake").val();
    		var departNum=$("#departNum").val();
    		var departDesc=$("#departDesc").val();
    		
     		$.ajax({
        		type:"post",
        		dataType:"json",
        		url:"/DepartServlet?method=InsertAjax",
        		data:{
        			"departCode":departCode,
        			"departName":departName,
        			"departDesc":departDesc,
        			"departNum":departNum,
        			"departRemake":departRemake
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
        			if(result.success){
        				window.location.href="/DispatcherServlet?method=departPage";
        				return;
        			}else{
        				layer.msg("添加失败！！！");
        			}
        		}
        	});
   
    	});
    	
     });