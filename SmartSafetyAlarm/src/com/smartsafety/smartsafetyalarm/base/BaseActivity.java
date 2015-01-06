package com.smartsafety.smartsafetyalarm.base;

import com.smartsafety.smartsafetyalarm.SmartSafetyApplication;
import com.smartsafety.smartsafetyalarm.util.HttpClient;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.view.LayoutInflater;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public abstract class BaseActivity extends Activity {
	
	public abstract void init();
	public abstract void setData();
	
	public static Activity mActivity = null;
	
	public LayoutInflater inflater = null;
	public SmartSafetyApplication app = null;
	private static ProgressDialog plg = null;
	public LinearLayout mapViewLayout = null;
	public InputMethodManager imm;
	public HttpClient api;
	public String lang = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		app = (SmartSafetyApplication)getApplication();

		api = HttpClient.getInstance();
		
		inflater = LayoutInflater.from(this);
		imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		progressInit();

	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	public void setCancelable(boolean value)
	{
		plg.setCancelable(value);
	}
	
	/**
	 * 프로그레스 초기화
	 */
	public void progressInit() {
		plg = new ProgressDialog(this);
		plg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		plg.setMessage("Loading...");
		plg.setCancelable(false);
		plg.setOnCancelListener(new DialogInterface.OnCancelListener()
        {
            @Override
            public void onCancel(DialogInterface dialog)
            {
            	pHide();
            }
        });
	}

	
	
	public static Activity getmActivity() {
		return mActivity;
	}
	public static void setmActivity(Activity mActivity) {
		BaseActivity.mActivity = mActivity;
	}
	/**
	 * 프로그레스 보이기
	 */
	public void pShow() {
		if (plg != null) progressInit();		
		pHide();		
		if (!plg.isShowing()) {
			//TODO: 간헐적 bug
			if(plg!=null)
				plg.show();
		}
	}
	
	/**
	 * 프로그레스가 돌고 있는지
	 * @return
	 */
	public boolean pIsShow(){
		return plg.isShowing();
	}
	
	/**
	 * 프로그레스 숨기기
	 */
	public void pHide() {
		if (plg != null && plg.isShowing()) {
			plg.dismiss();
		}
	}
	
	/**
	 * 토스트 띄우기
	 * @param message
	 */
	static Toast mToast = null;

	public synchronized void showToast(String message) {
		if (mToast == null) {
			mToast = Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG);
		} else {
			mToast.setText(message);
		}
		mToast.show();
	}

	/**
	 * 간단한 AlertDialog 
	 * @param message
	 */
	public void showSimpleAlertDialog(String message) {
		new AlertDialog.Builder(this)
				.setTitle("알림")
				.setMessage(message)
				.setIcon(
						getResources().getDrawable(
								android.R.drawable.ic_dialog_alert))
				.setPositiveButton("확인", null).show();
	}

	/**
	 * Thread 
	 * @param run
	 */
	public void startThread(Runnable run) {
		new Thread(run).start();
	}
	
	/**
	 * 키보드 숨기기
	 * @param et
	 */
	public void setHideKeyBoard(EditText et)
	{
		imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
	}
	
	/**
	 * 키보드 보이기
	 * @param et
	 */
	public void setShowKeyboard(EditText et)
	{
		imm.showSoftInputFromInputMethod(et.getWindowToken(), InputMethodManager.SHOW_FORCED);
		imm.showSoftInputFromInputMethod (et.getApplicationWindowToken(),InputMethodManager.SHOW_FORCED);
		imm.toggleSoftInputFromWindow(et.getApplicationWindowToken(),  InputMethodManager.SHOW_FORCED, 0);
	}
	
	/**
	 *  키보드 보일때 액션도 같이 넣을 경우
	 * @param et
	 * @param receiver
	 */
	public void setShowKeyboardaCatchAction(EditText et, ResultReceiver receiver)
	{
		imm.showSoftInput(et, InputMethodManager.SHOW_FORCED, receiver);
	}

}
