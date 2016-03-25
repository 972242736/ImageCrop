package mmf.imagecrop.activity;

import java.util.ArrayList;
import java.util.List;

import com.mmf.imagecrop.R;

import mmf.imagecrop.adapter.FeedBackAdapter;
import mmf.imagecrop.adapter.FeedBackAdapter.ClickListenerDel;
import mmf.imagecrop.utils.CameraUtil;
import mmf.imagecrop.widget.SettingDialog;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * @author MMF
 */

public class FeedBackActivity  extends Activity implements OnClickListener{
	

	// 拍照
	private final int REQ_CODE_CAMERA = 21;
	// 相册
	private final int REQ_CODE_PICTURE = 22;
	// 裁图
	private final int REQ_CODE_CUT = 23;
	
	private ImageView imgFbackAdd;
	private GridView gvFbackImg;
	private FeedBackAdapter adapter;
	private SettingDialog setDialog;
	
	private List<String> imgList;
	private Uri imgUrl;
	
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_feedback);
	initView();// 初始化view
}
	private void initView() {
		imgFbackAdd = (ImageView) findViewById(R.id.img_fback_add);
		gvFbackImg = (GridView) findViewById(R.id.gv_fback_img);
		setDialog = new SettingDialog(this, R.style.setting_dialog_style);
		setDialog.bt1.setOnClickListener(this);
		setDialog.bt2.setOnClickListener(this);
		imgFbackAdd.setOnClickListener(this);
		imgList = new ArrayList<String>();
		gvFbackImg.setEnabled(false);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.img_fback_add:
			if(imgList != null &&imgList.size()<3){
				setDialog.setButtonContent("通过摄像头拍摄", "从手机相册选择");
				setDialog.show();
			}else{
				Toast.makeText(this, "做多只能添加三张照片", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.bt1:
			imgUrl = CameraUtil.getTempUri();
			startActivityForResult(CameraUtil.takePicture(imgUrl), REQ_CODE_CAMERA);
			setDialog.dismiss();
			break;

		case R.id.bt2:
			CameraUtil.selectPhoto();
			startActivityForResult(Intent.createChooser(CameraUtil.selectPhoto(),"选择照片"),REQ_CODE_PICTURE);
			setDialog.dismiss();
			break;
		}
		
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode == RESULT_OK) {

			switch (requestCode) {
			case REQ_CODE_CAMERA:
				if(data != null){
					Uri uri = data.getData();
					startActivityForResult(CameraUtil.cropPhoto(uri,imgUrl,150,150), REQ_CODE_CUT);
				}else{
					startActivityForResult(CameraUtil.cropPhoto(imgUrl,imgUrl,150,150), REQ_CODE_CUT);
				}
				break;

			case REQ_CODE_PICTURE:
				if (data != null) {
					imgUrl = CameraUtil.getTempUri();
					startActivityForResult(CameraUtil.cropPhoto(data.getData(),imgUrl,150,150), REQ_CODE_CUT);
				}
				break;

			case REQ_CODE_CUT:
					imgList.add(CameraUtil.getPathFromUri(this,imgUrl));
					setListViewAdapter();
//				imgFbackAdd.setImageBitmap(CameraUtil.getBitmapByUri(this, imgUrl));
			default:
				break;
			}

		}

		super.onActivityResult(requestCode, resultCode, data);

	}
	
	private void setListViewAdapter(){
		
		if(adapter == null){
			if(imgList != null){
				adapter = new FeedBackAdapter(imgList, this);
				gvFbackImg.setAdapter(adapter);
				adapter.setListener(new ClickListenerDel() {
					
					@Override
					public void onClick(int position) {
						// TODO Auto-generated method stub
						imgList.remove(position);
						adapter.notifyDataSetChanged();
					}
				});
			}
		}else{
			adapter.notifyDataSetChanged();
		}
	}

}
