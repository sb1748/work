package com.jxszj.utils;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.taobao.api.ApiException;

/**
 * 
 * <pre>
 * <b>.</b>
 * <b>Description:</b> 
 *    当经销商之家的定时任务出现异常时，通过钉钉机器人播报到群里
 * <b>Author:</b> yanwei
 * <b>Date:</b> 2019年7月23日下午3:42:05
 * </pre>
 */
public class ExceptionRobot {

	public static void robotMessage(String quarteName,String message){
		try {
			DingTalkClient client1 = new DefaultDingTalkClient("https://oapi.dingtalk.com/robot/send?access_token=46525f10f7247bb8af570cd5cddcb3e3af61ae7c8e7f3b07c82c4fd7cea07c3d");
			OapiRobotSendRequest request = new OapiRobotSendRequest();
			request.setMsgtype("text");
			OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
			text.setContent("异常来源：经销商之家"+"\n任务名称："+quarteName+"\n异常可能情况："+message);
			request.setText(text);
			client1.execute(request);
		} catch (ApiException e) {
			e.printStackTrace();
		}
	}
	
	public static void robotSapMessage(String quarteName,String message){
		try {
			DingTalkClient client1 = new DefaultDingTalkClient("https://oapi.dingtalk.com/robot/send?access_token=46525f10f7247bb8af570cd5cddcb3e3af61ae7c8e7f3b07c82c4fd7cea07c3d");
			OapiRobotSendRequest request = new OapiRobotSendRequest();
			request.setMsgtype("text");
			OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
			text.setContent("任务名称："+quarteName+"\n失败情况："+message);
			request.setText(text);
			client1.execute(request);
		} catch (ApiException e) {
			e.printStackTrace();
		}
	}
}
