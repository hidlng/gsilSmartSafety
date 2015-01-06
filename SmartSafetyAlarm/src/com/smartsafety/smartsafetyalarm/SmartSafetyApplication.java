package com.smartsafety.smartsafetyalarm;

import android.app.Activity;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;



public class SmartSafetyApplication extends Application {

	/**
	 * SharedPreferences DB 설정
	 */
	public SharedPreferences pref = null;
	public SharedPreferences.Editor editer = null;
	//로그인 유무
	private boolean isLogin = false;
	private String useridx;
	private String siteidx;
	
	
//	public HttpClinetWork httpClinetwork = null;
	
	@Override
	public void onCreate() {
		super.onCreate();
		pref = getBaseContext().getSharedPreferences("smartsafetyalarm", Activity.MODE_PRIVATE);
		editer = pref.edit();
	}
	/**
	 * 로그인 유무
	 * @return
	 */
	public boolean isLogin() {
		
		if(isLogin==false){	
			return isAutoLogin();
		}
		return isLogin;
	}

	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}

	/**
	 * 자동 로그인
	 * @return
	 */
	public boolean isAutoLogin(){
		return pref.getBoolean("autologin", false);
	}
	
	public void setAutoLogin(boolean value){
		editer.putBoolean("autologin", value);
		editer.commit();
	}
	
	

	public String getUseridx() {
		return pref.getString("useridx", "");
	}
	public void setUseridx(String useridx) {
		editer.putString("useridx", useridx);
		editer.commit();
	}
	
	public String getSiteidx() {
		return pref.getString("siteidx", "");
	}
	public void setSiteidx(String siteidx) {
		editer.putString("siteidx", siteidx);
		editer.commit();
	}
	
	
	@SuppressWarnings("deprecation")
	public static void showNotification(Context ctx, int nIDIcon, int id, String strTicker, String strTitle, String strText, Intent intent, boolean fSound, int number) {
		// TODO Auto-generated method stub
		
		final NotificationManager nm = (NotificationManager)ctx.getSystemService(NOTIFICATION_SERVICE);
		String version;
		double ver = 0.0;
		version = android.os.Build.VERSION.RELEASE;
		version = version.split("\\.")[0]+"."+version.split("\\.")[1].replace("\\.", "");
		ver = Double.parseDouble(version);
		PendingIntent intentPending = PendingIntent.getActivity(ctx, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		
		if( ver < 3.3){
			final Notification notification = new Notification(nIDIcon, strTicker, System.currentTimeMillis());
			notification.setLatestEventInfo(ctx, strTitle, strText, intentPending);
			notification.defaults |= Notification.DEFAULT_ALL;
			notification.number = number;
			notification.flags = Notification.FLAG_AUTO_CANCEL;
			nm.notify(id, notification);
		}else{
			NotificationCompat.Builder builder = new NotificationCompat.Builder(ctx)
			.setAutoCancel(true)
			.setContentIntent(intentPending)
			.setWhen(System.currentTimeMillis())
			.setContentTitle(strTitle)
			.setContentText(strText)
			.setDefaults(Notification.DEFAULT_ALL)
			.setSmallIcon(nIDIcon)
			.setNumber(number)
			;
  		
			Notification noti =builder.build();
			noti.flags = Notification.FLAG_AUTO_CANCEL;
			nm.notify(id, noti);
		}
	}
	

	
}
