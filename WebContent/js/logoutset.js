var t;
$(document).ready(function(){
	var url = document.URL;
	var s = url.indexOf(contentPath);
	var pathURI = url.substring(s + contentPath.length, url.indexOf("?"));
	var array;
	var arrayData;
	var path = "/"+window.location.pathname.split("/")[1];

	if(url.split("?").length > 1){
		array = url.split("?");
		arrayData = array[1].split("&");
	$("#dialog-logout").dialog({
		autoOpen: false,
        modal: true,
        dialogClass: "no-close",
        closeOnEscape: false,
		buttons:{
			'確定':function(){
				$.ajax({
					url: path + "/login",
					type:"POST",
					data: "URIPage=" + pathURI +"&pageData="+ arrayData,
					success: function(){
						loginWay();
						$("#dialog-logout").dialog("close");
//						window.location.href= path +"/01_login/login.jsp";
//						$("#dailog-lougoutMsg").html($("#dailog-lougoutMsg").load(path +"/01_login/login2.jsp"));
					},
				});
			},
			'繼續瀏覽':function(){
				logout();
				$("#dialog-logout").dialog("close");
				location.reload();
			}
		}
	});
	}else{
		pathURI = url.substring(s+ contentPath.length, url.length);
		$("#dialog-logout").dialog({
			autoOpen: false,
	        modal: true,
	        dialogClass: "no-close",
	        closeOnEscape: false,
			buttons:{
				'確定':function(){
					$.ajax({
						url: path + "/login",
						type:"POST",
						data: "URIPage=" + pathURI,
					});
//					loginWay();
					window.location.href = path+"/01_login/login.jsp";
//					$("#dialog-logout").dialog("close");
				}
			}
		});
	}
});
function inactivityTime(){
	window.onload = resetTimer;
	document.onmousemove = resetTimer;
	document.onkeypress = resetTimer;
};

function resetTimer() {
	clearTimeout(t);
	t = setTimeout(logout, 30*60*1000);
};

function logout() {
	var path = "/"+window.location.pathname.split("/")[1];
	$.ajax({
		url: path + "/login",
		type:"GET",
	});
	
	window.onload = clearTimeout(t);
	document.onmousemove = clearTimeout(t);
	document.onkeypress = clearTimeout(t);
	
	$("#dialog-logout").dialog("open");
	$("#dailog-lougoutMsg").text("因閒置時間過長您已被登出, 按確定重新登入");
}