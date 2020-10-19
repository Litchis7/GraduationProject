//定义全局变量
var FrunWeb_IP,FrunWeb_PORT; //IP地址和起始端口存储变量
var websocket_Connected; //网络连接标志位，0--断开，1--连接
var websocket;           //连接的句柄（存放websocket连接对象）
//-----------------------------------------------------------------
function SocketConnect(nSocket){   //websocket 连接函数，参数nSocket 1--连接端口1，2--连接端口2
	var Uri;                               //链接地址存储变量
	var nPort;                             //连接的目标端口存储变量
	GetIP();							  //获取HTML5-NET服务器的IP地址和起始端口号
	nPort = parseInt(FrunWeb_PORT)+nSocket; //在起始端口（FrunWeb_PORT）的基础上获取目标端口

	Uri="ws://"+FrunWeb_IP+":"+nPort.toString();//创建链接地址

	if (!("WebSocket" in window)){//判断浏览器是否支持websocket协议，不支持则通过对话框输出提示
		alert("提示:该浏览器不支持HTML5 Websocket，建议选择Google，FireFox浏览器！");
		return;
	}   
	try{
		websocket_Connected=0;          //初始化网络连接状态
		websocket = new WebSocket(Uri);//创建网络连接
		websocket.onopen    = function (evt) { websocket_Open(evt);   };//监听网络连接成功事件
		websocket.onclose   = function (evt) { websocket_Close(evt);  };//监听断开网络连接的事件
		websocket.onmessage = function (evt) { websocket_Message(evt);};//监听网络数据接受成功事件
		websocket.onerror   = function (evt) { websocket_Error(evt);  };//异常监听
	} 
	catch (err){
		window.alert("提示：连接错误，请重新连接！");
	}       
}
//---------------------------------------------------------------
function CloseWebSocket(){//手动断开网络连接
	websocket.close();//调用websocket对象中的关闭网络连接的方法
}
//----------------------------------------------------------------
function websocket_Open(evt)//网络连接成功时执行
{
   websocket_Connected=1;
   onConnect();  
}
//---------------------------------------------------------------
function websocket_Close(evt)//网络连接断开时执行
{
   websocket_Connected=0;
   Disconnect(1);
}
//---------------------------------------------------------------
function websocket_Error(evt)//网络连接异常时执行，例如找不到连接地址所指向的服务器
{
   Disconnect(2);
}
//---------------------------------------------------------------
function websocket_Message(evt)//网页接收到服务器返回的数据时执行
{  
	var str=evt.data;//获取缓存区的原始数据
   	onReceiveHex(str);
	//onReceiveStr(str);
}
//---------------------------------------------------------------
function WebSocket_Send(str){//网页向服务器发送数据
	try{
		if(websocket.readyState==1){
			websocket.send(str);//调用websocket对象的send方法向服务器发送数据
		}
	}
	catch (err){window.alert("提示：数据发送错误，请重新发送！");} 
}
//---------------------------------------------------------------
function Send_Data_Hex(str){//网页向服务器发送十六进制数据
	var buff,i,tstr;
	str=str.replace(/\s+/g,""); //去掉所有的空格
	buff=new Uint8Array(str.length/2);
	for(i=0;i<buff.length;i++){
		tstr=str.substr(2*i,2);
	    buff[i]=parseInt(tstr,16);//将字符串转为16进制字节
	}
	if(websocket_Connected==1){//判断网络是否处在连接状态
		WebSocket_Send(buff);
	}
	else{
		Disconnect(1);
	}
}
//---------------------------------------------------------------
function Send_Data_Str(str){//网页给服务器发送字符串数据
	if(websocket_Connected==1){//判断网络是否处在连接状态
		WebSocket_Send(str);
	}
	else{
		Disconnect(1);
	}
}
//---------------------------------------------------------------
function onReceiveHex(blob){//十六进制数据读取函数（当网页接收到来自服务器的数据时执行该函数，在函数websocket_Message中调用）
	var str,bytebuf;
	var reader = new FileReader();
	reader.readAsArrayBuffer(blob); 
	reader.onload = function(evt){
		bytebuf= new Uint8Array(reader.result);
		str=HexToStr(bytebuf);//在这里，十六进制数对外显示为对应的十进制数，因此需要转换成字符串才能直观地查看
		Data_Dispose(str,bytebuf,ASCIIToStr(bytebuf));
    };
}
//---------------------------------------------------------------
function onReceiveStr(blob){//字符串数据读取函数（当网页接收到来自服务器的数据时执行该函数，在函数websocket_Message中调用）
	var str;
    var reader = new FileReader();
	reader.readAsText(blob,'utf-8'); 
	reader.onload = function(evt){
		if(T_PLC_Mes!=null){
			clearInterval(T_PLC_Mes);
			T_PLC_Mes=null;
		}
		str=reader.result;
		//此处根据实际需要编写相应的数据处理代码
		Data_Dispose(str);
	};
}
//---------------------------------------------------------------
function HexToStr(buf){//将十六进制数据转换成字符串
	var str,i;
	str="";
	for(i=0;i<buf.length;i++){
		if(buf[i]<16){
			str=str+"0"+buf[i].toString(16)+" ";  
	  	}
	 	else{
			str=str+buf[i].toString(16)+" ";  
	  	}
   	}
   	str=str.toUpperCase()+"\r\n"  //将全部小写字母转成大写字母，并增加回车换行符
	return str;
}
function ASCIIToStr(buf){
	var str="";
	for(var i=0;i<buf.length;i++){
		str+=String.fromCharCode(buf[i]);
	}
	return str;
}
//---------------------------------------------------------------
function GetIP(){
	/************/
	//获取服务器本地IP及端口号（网页上传至服务器应用阶段）
   	var str,ip;
	str=window.location.href;//获取网页的链接地址
	str=str.split("/",10);
	ip=str[2].split(":",2);
   	FrunWeb_IP=ip[0];
   	FrunWeb_PORT=ip[1];
	/************/
	//使用固定的IP及端口号（网页开发调试阶段）
    /*FrunWeb_IP="192.168.1.243";
   	FrunWeb_PORT="5000";*/
   
}
//----------------------------------------------------------------


var rebas = 1;

function btn_net(){//监听网页加载完成的事件
	//当网页加载完成之后执行匿名函数，实现网页的自动联网功能
	/************************************************/
	//络连接
	if(rebas_1 % 2 == 1){
		SocketConnect(1);//调用网络连接函数连接模块的UDP服务
		Initialize();//文本框初始化函数
	}else{
		CloseWebSocket();
	}
	rebas++;
	/************************************************/
}