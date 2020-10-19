//---------------------------------------------------------------
function onConnect(){//网络连接成功将自动调用该函数，用户只需编写执行代码即可;该函数用来表现网络连接成功的状态
	/***************	****************/
	console.log("网络连接成功！");//向控制台输出内容
	/*******************************/
	if(T_HMI_Net!=null){//判断定时器存储变量是否为空
		clearInterval(T_HMI_Net);//不是删除变量里存储的定时器对象
		T_HMI_Net=null;//清空变量
	}
	else {
		T_HMI_Net=setInterval(HMI_Net_Statu,300);//创建定时器，每300ms调用一次函数HMI_Net_Statu
	}
	$("#RUN_STOP").css({"background-color":BgColor[1]});//切换运行LED的颜色
	$("#NETLINK").css({"background-color":BgColor[1]});//切换PLC网络连接LED的颜色
	//注意：这两个LED元素的状态不代表PLC的实际状态
}
//---------------------------------------------------------------
function Disconnect(num){//网络连接断开将自动调用该函数，用户只需编写执行代码即可；该函数用来表现网络连接断开的状态
	if(num==1){//网络连接断开
		if(T_HMI_Net!=null){//判断定时器存储变量是否为空
			clearInterval(T_HMI_Net);//不是删除变量里存储的定时器对象
			T_HMI_Net=null;//清空变量
		}
		$("#NetOn1").css({"display":Display[0]});//将网页联网成功的图标不显示
		$("#NetOn2").css({"display":Display[0]});//将网页联网成功的图标不显示
		//SocketConnect(1);//从新连接网络
	}
	else if(num==2){//网络连接异常
		alert("网络连接异常！");//网页弹出对话框显示文本"网络连接异常！"
	}
}
//---------------------------------------------------------------
function Data_Dispose(str,Hex,HexStr){//数据处理函数
	//控制台输出数据；控制台打开放式：用浏览器打开网页（.html文件），按键盘按键F12即可进入浏览器的调试台，选择“控制台”或“Console”选项；
	var Num=$("#PLC_Num").val();//获取当前的PLC编号
	if(Num==HexStr.substr(1,4)&&HexStr.substr(0,1)=="@"){//判断数据是否来自当前PLC编号所对节点
		var Buff=Hex.slice(6);//将数组的第七位元素开始的数组分割出来赋给变量Buff
		if(CRCFun(Buff).Result==0){//进行CRC校验，校验错误则退出函数
			return;
		}
		//获取I0和I1的状态字节数据
		var I0=Hex[HexStr.indexOf("I")+1],I1=Hex[HexStr.indexOf("I")+2];
		//获取Q0和Q1的状态字节数据
		var Q0=Hex[HexStr.indexOf("Q")+1],Q1=Hex[HexStr.indexOf("Q")+2];
		//获取模拟量采集口通道0的数据
		var AI0=Hex[HexStr.indexOf("A")+1]*0x100+Hex[HexStr.indexOf("A")+2];
		//获取模拟量采集口通道1的数据
		var AI1=Hex[HexStr.indexOf("A")+3]*0x100+Hex[HexStr.indexOf("A")+4];
		var IB0=I0.toString(2),IB1=I1.toString(2);//将十六进制数据转换成对应的二进制数的字符串
		var QB0=Q0.toString(2),QB1=Q1.toString(2);
		while(IB0.length<8){IB0="0"+IB0;}//长度不满足要求在高位补零
		while(IB1.length<6){IB1="0"+IB1;}
		while(QB0.length<8){QB0="0"+QB0;}
		while(QB1.length<2){QB1="0"+QB1;}
		GetBitStatu(IB1+IB0,0);
		GetBitStatu(QB1+QB0,1);
		GetACData(AI0,AI1);
	}
}
function CRCFun(Buff){//CRC校验函数
	var CRC_={CRC:0,Result:0}
	for(var i=0;i<Buff.length-1;i++){
		CRC_.CRC+=Buff[i];
		if(CRC_.CRC>255){
			CRC_.CRC-=256;
		}
	}
	if(CRC_.CRC==Buff[i]) CRC_.Result=1;
	else CRC_.Result=0;
	return CRC_;
}
function GetBitStatu(str,num){//状态设置函数
	var BIT=0;
	if(num==0){//设置输入端的状态
		for(var i=0;i<14;i++){
			BIT=parseInt(str.substr(13-i,1));//parseInt函数将字符串转换成数字
			if(InputStatu[i]!=BIT){
				InputStatu[i]=BIT;
				$(InputName[i]).css({"background-color":BgColor[InputStatu[i]]});
			}
			
		}
	}
	else if(num==1){//设置输入端的状态
		for(var i=0;i<10;i++){
			BIT=parseInt(str.substr(9-i,1));
			if(OutputStatu[i]!=BIT){
				OutputStatu[i]=BIT;
				$(OutputName[i]).css({"background-color":BgColor[OutputStatu[i]]});
			}
		}
	}
}
function GetACData(VALUE0,VALUE1){//使用模拟量采集的原始数据按照公式进行标准化和标定，得到电压值。
	var NORM_X_MIN=-27648,SCALE_X_MIN=-10;
	var NORM_X_MAX=27648,SCALE_X_MAX=10;
	var AI0=((VALUE0-NORM_X_MIN)/(NORM_X_MAX-NORM_X_MIN)*(SCALE_X_MAX-SCALE_X_MIN)+SCALE_X_MIN)+"";
	var AI1=((VALUE1-NORM_X_MIN)/(NORM_X_MAX-NORM_X_MIN)*(SCALE_X_MAX-SCALE_X_MIN)+SCALE_X_MIN)+"";
	$("#AC_Input0").val(AI0.substr(0,AI0.indexOf(".")+3));//提取数据，保留小数点后两位
	$("#AC_Input1").val(AI1.substr(0,AI1.indexOf(".")+3));//提取数据，保留小数点后两位
}
//---------------------------------------------------------------
function Control_DQ(num){//输出口输出状态控制程序
	var Data="40";//定义数据标识位
	var Data_Hex=new Array();
	var Bit="";
	for(var i=0;i<4;i++){//将编号转换成ASCII码对应的十六进制数
		Bit=PLC_Number.substr(i,1);
		Data=Data+" "+Bit.charCodeAt().toString(16);
	}
	Data+=" 3A ";//添加字符“:”的ASCII码对应的十六进制数
	Data_Hex[0]=81;//字符Q的ASCII码
	Data_Hex[1]=num;//输出口编号
	Data_Hex[2]=1-OutputStatu[num];//输出口要切换的状态
	Data_Hex[3]=0;//CRC位初始化
	CRC=CRCFun(Data_Hex).CRC;//获取CRC校验值
	Data_Hex[3]=CRC;
	Data+=HexToStr(Data_Hex);//获得最终的控制指令（HexToStr函数将十六进制数转换成对应的字符串）
	Send_Data_Hex(Data);//发送数据
}
//---------------------------------------------------------------
function HMI_Net_Statu(){//网页与服务器的网络连接状态显示动画
	NetPicStatu=1-NetPicStatu;
	$("#NetOn1").css({"display":Display[NetPicStatu]});
	$("#NetOn2").css({"display":Display[1-NetPicStatu]});
}
//---------------------------------------------------------------
function SetNum(){//设置当前页面监控的PLC编号
	PLC_Number=$("#UDP_Num").val();//获取单行文本框的值
	$("#PLC_Num").val(PLC_Number);//给单行文本框赋值
}
//---------------------------------------------------------------
function Initialize(){//初始化部分单行文本框的值
	$("#PLC_Num").val("0001");
	$("#UDP_Num").val("");
	$("#AC_Input").val("");
	$("#AC_Output").val("");
}
//---------------------------------------------------------------
/* function ACOutFun(num){//获取设置PLC模拟量输出口输出值的指令
	var Data="40";
	var Data_Hex=new Array();
	var Bit="";
	var ID="#AC_Output"+num;
	var VALUE=parseInt($(ID).val());
	

	
	for(var i=0;i<4;i++){
		Bit=PLC_Number.substr(i,1);
		Data=Data+" "+Bit.charCodeAt().toString(16);
	}
	Data+=" 3A ";
	Data_Hex[0]=65;//字符"A"的ASCII码的十六进制数
	Data_Hex[1]=num;//模拟量输出口编号
	Data_Hex[2]=VALUE;//模拟量输出值（整数）
	Data_Hex[3]=0;
	CRC=CRCFun(Data_Hex).CRC;
	Data_Hex[3]=CRC;
	Data+=HexToStr(Data_Hex);
	Send_Data_Hex(Data);
} */
function AQ0(){//获取设置PLC模拟量输出口输出值的指令
	var Data="40";
	var Data_Hex=new Array();
	var Bit="";
	var ID="#AC_Output0";
	var VALUE=parseInt($(ID).val());
	
	$.ajax({
		type:'POST',
		url:'http://'+nip+':8080/AQ0/insert',
		data:{
			'current':VALUE,
			'sid':data7.sid
		},
		dataType:"json",
		success:function(msg){
			alert(msg.success);
		}
	});
	
	for(var i=0;i<4;i++){
		Bit=PLC_Number.substr(i,1);
		Data=Data+" "+Bit.charCodeAt().toString(16);
	}
	Data+=" 3A ";
	Data_Hex[0]=65;//字符"A"的ASCII码的十六进制数
	Data_Hex[1]=0;//模拟量输出口编号
	Data_Hex[2]=VALUE;//模拟量输出值（整数）
	Data_Hex[3]=0;
	CRC=CRCFun(Data_Hex).CRC;
	Data_Hex[3]=CRC;
	Data+=HexToStr(Data_Hex);
	Send_Data_Hex(Data);
}
function AQ1(){//获取设置PLC模拟量输出口输出值的指令
	var Data="40";
	var Data_Hex=new Array();
	var Bit="";
	var ID="#AC_Output1";
	var VALUE=parseInt($(ID).val());
	
	$.ajax({
					type:'POST',
					url:'http://'+nip+':8080/AQ1/insert',
					data:{
						'current':VALUE,
						'sid':data7.sid
					},
					dataType:"json",
					success:function(msg){
						alert(msg.success);
					}
				});

	for(var i=0;i<4;i++){
		Bit=PLC_Number.substr(i,1);
		Data=Data+" "+Bit.charCodeAt().toString(16);
	}
	Data+=" 3A ";
	Data_Hex[0]=65;//字符"A"的ASCII码的十六进制数
	Data_Hex[1]=1;//模拟量输出口编号
	Data_Hex[2]=VALUE;//模拟量输出值（整数）
	Data_Hex[3]=0;
	CRC=CRCFun(Data_Hex).CRC;
	Data_Hex[3]=CRC;
	Data+=HexToStr(Data_Hex);
	Send_Data_Hex(Data);
}

function AI0(){
		var test = function(){
		$.ajax({
			type:'POST',
			url:'http://'+nip+':8080/AI0/insert',
			data:{
				'voltage':$("#AC_Input0").val(),
				'sid':data7.sid
			},
			dataType:"json"
		});
	}
	setInterval(test,5000);
	alert("开始采集AI0！")
}

function AI1(){
		var test = function(){
		$.ajax({
			type:'POST',
			url:'http://'+nip+':8080/AI1/insert',
			data:{
				'voltage':$("#AC_Input1").val(),
				'sid':data7.sid
			},
			dataType:"json"
		});
	}
	setInterval(test,5000);
	alert("开始采集AI1！")
}
