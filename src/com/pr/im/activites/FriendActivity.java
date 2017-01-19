/**
 * 
 */
package com.pr.im.activites;

import org.jivesoftware.smackx.packet.VCard;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pr.im.R;
import com.pr.im.constant.Constants;
import com.pr.im.constant.MyApplication;
import com.pr.im.d3View.D3View;
import com.pr.im.dao.MsgDbHelper;
import com.pr.im.dao.NewMsgDbHelper;
import com.pr.im.model.Friend;
import com.pr.im.model.User;
import com.pr.im.util.CircularImage;
import com.pr.im.util.Tool;
import com.pr.im.util.XmppLoadThread;
import com.pr.im.xmpp.XmppConnection;

/**
 * @author MZH
 *
 */
public class FriendActivity extends BaseActivity {
	@D3View(click="onClick") Button operBtn;
	@D3View TextView nameView,sexView,signView,nickNameView,phoneView,emailView;
	@D3View CircularImage headView;
	private String username;
	private User friend;
	private FriendReceiver reciver;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.acti_friend);
		initTitle();
		username = getIntent().getStringExtra("username");
		nameView.setText(username);
		// ���յ�����Ϣ���¼�����
		reciver = new FriendReceiver();
		registerReceiver(reciver,new IntentFilter("friendChange"));
		
		if (username.equals(Constants.USER_NAME)) {
			operBtn.setVisibility(View.GONE);
		}
		isFriend();
		initData();
	}
	
	public void isFriend(){
		if (XmppConnection.getInstance().getFriendBothList().contains(new Friend(username))) {
			operBtn.setText("�Ƴ�ͨѶ¼");
		}
		else {
			operBtn.setText("���ӵ�ͨѶ¼");
		}
	}
	
	
	public void	initData() {
		new XmppLoadThread(this) {
			
			@Override
			protected void result(Object object) {
				VCard vCard = (VCard)object;
				friend = new User(vCard);
				if (friend!=null) {
					if (friend.sex != null) {
						sexView.setText(friend.sex);
					}
					if (friend.intro != null) {
						signView.setText(friend.intro);
					}
					if (friend.nickname != null) {
						nickNameView.setText(friend.nickname);
					}
					if (friend.email != null) {
						emailView.setText(friend.email);
					}
					if (friend.mobile != null) {
						phoneView.setText(friend.mobile);
					}
					friend.showHead(headView);
				}
			}
			
			@Override
			protected Object load() {
				return XmppConnection.getInstance().getUserInfo(username);
			}
		};
	}
	
	public void onClick(View v){
		switch (v.getId()) {
		case R.id.operBtn:
			if (operBtn.getText().equals("���ӵ�ͨѶ¼")) {
				XmppConnection.getInstance().addUser(username);
				Tool.initToast(getApplicationContext(), "���ӳɹ����ȴ�ͨ����֤");
				MyApplication.getInstance().sendBroadcast(new Intent("friendChange"));
				isFriend();
			}
			else if (operBtn.getText().equals("�Ƴ�ͨѶ¼")){
				XmppConnection.getInstance().removeUser(username);
				Tool.initToast(getApplicationContext(), "�Ƴ��ɹ�");
				MyApplication.getInstance().sendBroadcast(new Intent("friendChange"));
				operBtn.setText("���ӵ�ͨѶ¼");
				NewMsgDbHelper.getInstance(getApplicationContext()).delNewMsg(username);
				MyApplication.getInstance().sendBroadcast(new Intent("ChatNewMsg"));
				MsgDbHelper.getInstance(getApplicationContext()).delChatMsg(username);
			}
			break;

		default:
			break;
		}
		
	}
	
	private class FriendReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			isFriend();
		}
	}
}