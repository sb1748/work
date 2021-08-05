package com.jxszj.quartz;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.jxszj.pojo.Userinfo;
import com.jxszj.service.IUserInfoService;
import com.jxszj.utils.DateUtils;

//每天生成一个新密码
@Service
public class UserInfoQuartz {

	@Autowired
	private IUserInfoService userInfoService;

	public void updatePwd() {
		try {
			int flag = new Random().nextInt(999999);
			if (flag < 100000) {
				flag += 100000;
			}
			Userinfo userinfo=new Userinfo();
			userinfo.setId(1);
			userinfo.setName("root");
			userinfo.setPwd(flag);
			int i=userInfoService.updateUser(userinfo);
			if(i>0){
				DingTalkClient client1 = new DefaultDingTalkClient("https://oapi.dingtalk.com/robot/send?access_token=1b8e15f8bf15b55a58408070210f32f29baf85fc0877d9ae92a6e0690073a0d5");
				OapiRobotSendRequest request = new OapiRobotSendRequest();
				request.setMsgtype("text");
				OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
				text.setContent(DateUtils.getNowDateToString(DateUtils.FORMAT_2_STRING)+"随机密码:"+flag);
				request.setText(text);
				client1.execute(request);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
