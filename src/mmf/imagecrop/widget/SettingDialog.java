package mmf.imagecrop.widget;

import com.mmf.imagecrop.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SettingDialog extends Dialog implements
		android.view.View.OnClickListener {

	public Button bt1, bt2, btcancle;
	

	public SettingDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		// TODO Auto-generated constructor stub
	}

	public SettingDialog(Context context, int theme) {
		super(context, theme);
		this.setContentView(R.layout.dialog_setting);
		init();
		// TODO Auto-generated constructor stub
	}

	public SettingDialog(Context context) {
		super(context);
		this.setContentView(R.layout.dialog_setting);
		init();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	private void init() {
		// TODO Auto-generated method stub

		// private Button bt1,bt2,btcancle;
		bt1 = (Button) this.findViewById(R.id.bt1);
		bt2 = (Button) this.findViewById(R.id.bt2);
		btcancle = (Button) this.findViewById(R.id.btcancle);
		btcancle.setOnClickListener(this);

	}

	/**
	 * 设置按钮文字
	 * @param a bt1
	 * @param b bt2
	 */
	public void setButtonContent(String a, String b) {

		bt1.setText(a);
		bt2.setText(b);

	}



	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		this.cancel();

	}

}
