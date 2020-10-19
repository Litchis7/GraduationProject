
var info = window.localStorage.info;
var data7 = JSON.parse(info);
function ac(){
	var val=document.getElementById("AC_Output0").value;
		$.ajax({
		type:'POST',
		url:'http://127.0.0.1:8080/AQ0/insert',
		data:{
			'current':$("#AC_Output0").val(),
			'sid':data7.sid
			
		},
		dataType:"json",
		success:function(msg){
			alert(msg.success);
		}
		});		
}

// function ac(num){
// 	if(num == 1){
// 		alert("1")
// 	}
// 	else{
// 		alert("2")
// 	}
// 
// }