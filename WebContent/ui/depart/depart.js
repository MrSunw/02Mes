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
    	//****写代码需要改动的地方
    	
    	var pagess=getQueryString("pag");
    	if(pagess!=null){
    		pageQuery(pagess);
    	}else{ 
    	pageQuery(1);
    	}
    	
    	
    	$("#queryBtn").click(function(){
    		var search=$("#queryText").val();
    		if(''!=search){
    		$.ajax({
        		type:"post",
        		dataType:"json",
        		url:"/DepartServlet?method=pageQueryForParamsAjax",
        		data:{
        			"pageno" : 1,//当前页
        			"pagesize" : 2,//当前页的条数
        			"search":search
        		},
        		beforeSend : function() {// 加载的操作,发送数据前
    				// 使用插件的效果
    				//旋转圈圈旋转状态一直保存在一个变量中，界面显示为一个圈圈一直转
        			layerLoading = layer.msg("处理中", {
    					icon : 16
    				});
    			},
        		success:function(result){
        			//结束layer的效果
                    console.log(result)
         			layer.close(layerLoading);
        			// 局部刷新页面数据
        			var tableContent="";//列表
        			var pageContent="";//页码
        			var page =JSON.parse(result.result);
    				var datas = page.datas;
        			$.each(datas,function(i,target){
        				tableContent+=renderPage(target,1);
        			});
        			$("#departData").html(tableContent);
        			//////////////////////////////////////
        			pageContent=renderPageiation(1,page);
        			$(".pagination").html(pageContent);
        		}
        	});
    		}else{
    			window.location.href="/DispatcherServlet?method=departPage";
    		}
    	});
    	//复选框
    	$("#allSelBox").click(function(){
//    		if(this.checked){
//    			$(":checkbox[name='departid']").prop("checked",true);
//    		}else{
//    			$(":checkbox[name='departid']").prop("checked",false);
//    		}
    		$(":checkbox[name='departid']").prop("checked",this.checked);
    	}); 
    });
  
    //获取地址栏信息
	function getQueryString(name){
	     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	     var r = window.location.search.substr(1).match(reg);
	     if(r!=null)return  unescape(r[2]); return null;
	}
    
    //修改
    function goUpdatePage(id,pag){

// 	   window.location.href = "/DispatcherServlet?method=departUpdatePage&id="+id;
 	  window.location.href = "/DepartServlet?method=pageUpdateAjax&id="+id+"&pag="+pag;
 	 }
   
    //删除
    function  godeletePage(id){
    	var layereLoadings=null;
    	//询问框
 	   layer.confirm('是否确认删除',{
 		   btn:['确定','取消']
 	   },function(){
 	    	$.ajax({
 	    		type:"post",
 	    		dataType:"json",
 	    		url:"/DepartServlet?method=deletetAjax",
 	    	    data:{
 	    	    	"id":id
 	    	    },
 	    	    beforeSend:function(){
 	    	    layereLoadings=layer.msg('删除中',{time:4000});
 	    	    },
 	    	    success:function(result){
 	    	     
 	    	    if(result.success){
 	    	    	layereLoadings= layer.msg("删除成功",{icon:1,time:4000});
 	    	    window.location.href="/DispatcherServlet?method=departPage";
 	    	    }else{
 	    	    	layereLoadings=layer.msg("删除失败！",{time:3000});
 	    	    }
 	    	    }
 	    	}); 
 	   })
    	
    }
    //分页查询
    function pageQuery(pageno) {
    	var layerLoading=null;
    	$.ajax({
    		type:"post",
    		dataType:"json",
    		url:"/DepartServlet?method=pageQueryAjax",
    		data:{
    			"pageno" : pageno,//当前页
    			"pagesize" : 2//当前页的条数
    		},
    		beforeSend : function() {// 加载的操作,发送数据前
				// 使用插件的效果
				//旋转圈圈旋转状态一直保存在一个变量中，界面显示为一个圈圈一直转
    			layerLoading = layer.msg("处理中", {
					icon : 16
				});
			},
    		success:function(result){
    			//结束layer的效果
    			layer.close(layerLoading);
    			// 局部刷新页面数据
    			var tableContent="";//列表
    			var pageContent="";//页码
    			var page =JSON.parse(result.result);
    			var datas = page.datas;
    			//当前页数
    			var pag=page.pageno;
    			$.each(datas,function(i,target){
    				
    			tableContent+=renderPage(target,pag);
    			});
    			$("#departData").html(tableContent);
    			//////////////////////////////////////
    			pageContent=renderPageiation(pageno,page);
    			$(".pagination").html(pageContent);
    		}
    	});

    }
    
    //页码渲染
    function renderPageiation(pageno,page){
    	var pageContent="";
		// 页码处理
		// 有上一页
		if (pageno > 1) {
			pageContent += "<li><a href='#' onclick='pageQuery("
					+ (pageno - 1) + ")'>上一页</a></li>";
		}
		// 遍历中间页码
		for (var i = 1; i <= page.totalno; i++) {
			if (pageno == i) {
				pageContent += "<li  class='active'><a href='#' onclick='pageQuery("
						+ i + ")'>" + i + "</a></li>";
			} 
			else {
				pageContent += "<li><a href='#' onclick='pageQuery("
						+ i + ")'>" + i + "</a></li>";
			}
		}
		if (pageno < page.totalno) {
			pageContent += "<li><a href='#' onclick='pageQuery("
					+ (pageno + 1) + ")'>下一页</a></li>";
		}
		return pageContent;
    }
   
   
    //列表渲染
    function renderPage(depart,pag){
    	var tableContent="";
		tableContent+="<tr>";
 	  	tableContent+="<td>"+depart.id+"</td>";
 	  	tableContent+="<td><input type='checkbox' name='departid' id='"+depart.id+"'></td>";
 	  	tableContent+="<td>"+depart.departCode+"</td>";
 	 	tableContent+="<td>"+depart.departName+"</td>";
 	 	tableContent+="<td>"+depart.departDesc+"</td>";
 	 	tableContent+="<td>"+depart.departNum+"</td>";
 	 	tableContent+="<td>"+depart.departRemake+"</td>";
 	 	tableContent+="<td>";                       
		tableContent+="<button type='button' onclick='goUpdatePage("+depart.id+",\""+pag+" \")' class='btn btn-primary btn-xs'><i class='glyphicon glyphicon-pencil'></i></button>";
		tableContent+="<button type='button' onclick='godeletePage("+"\""+depart.id+"\""+")' class='btn btn-danger btn-xs'><i class='glyphicon glyphicon-remove'></i></button>";
		tableContent+="	</td>";
 	    tableContent+="</tr>";
 	    return tableContent;
    	
    
    }
   function deleteUsers(){
	   var id_array=new Array();
	   var idstr="";
	   $(":checkbox[name='departid']:checked").each(function(){
		  var ids=$(this).attr("id");
		  id_array.push(ids);
		  idstr=id_array.join(',');
		  
	   });
	   var deletelength=id_array.length;
	   if(deletelength<=0){
		   layer.msg('请选中要删除的数据');
		   return;
	   }
	   //询问框
	   layer.confirm('是否确认删除',{
		   btn:['确定','取消']
	   },function(){
		   $.ajax({
	    		type:"post",
	    		dataType:"json",
	    		url:"/DepartServlet?method=deletetsAjax",
	    	    data:{
	    	    	"idstr":idstr
	    	    },
	    	    beforeSend:function(){
	    	    layereLoadings=layer.msg('删除中',{time:4000});
	    	    },
	    	    success:function(result){
	    	     
	    	    if(result.success){
	    	    	layereLoadings= layer.msg("删除成功",{icon:1,time:4000});
	    	    window.location.href="/DispatcherServlet?method=departPage";
	    	    }else{
	    	    	layereLoadings=layer.msg("删除失败！",{time:3000});
	    	    }
	    	    }
	    	}); 
	   })
   }