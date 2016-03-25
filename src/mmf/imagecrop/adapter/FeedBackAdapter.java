package mmf.imagecrop.adapter;

import java.util.List;

import com.mmf.imagecrop.R;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.ImageDownloader.Scheme;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

public class FeedBackAdapter extends BaseAdapter {
	private List<String> imgList;
	private LayoutInflater mInflater;
	private DisplayImageOptions options;
	private ImageLoader imageLoader;
	ClickListenerDel listener;
	Context context;

	public FeedBackAdapter(List<String> imgList, Context context) {

		this.imgList = imgList;
		this.context = context;
		mInflater = LayoutInflater.from(context);
		initImageLoader();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return imgList == null ? 0 : imgList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return imgList == null ? null : imgList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		viewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.feed_back_img_item, null);
			holder = new viewHolder();
			holder.imgFback = (ImageView) convertView
					.findViewById(R.id.img_fback);
			holder.imgFbackDel = (ImageView) convertView
					.findViewById(R.id.img_fback_del);
			convertView.setTag(holder);
		} else {
			holder = (viewHolder) convertView.getTag();
		}
		imageLoader.displayImage(Scheme.FILE.wrap(imgList.get(position)),
				holder.imgFback, options);
		holder.imgFbackDel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				listener.onClick(position);
			}
		});
		return convertView;
	}
	public interface ClickListenerDel {
		public void onClick(int position);
	}
	public void setListener(
			ClickListenerDel listener) {
		    this.listener = listener;
		  }

	private void initImageLoader() {
		imageLoader = ImageLoader.getInstance();
		if (!imageLoader.isInited()) {
			// imageLoader.init(ImageLoaderConfiguration.createDefault(this));

			options = new DisplayImageOptions.Builder().cacheInMemory(true)
					.cacheOnDisk(true).considerExifParams(true)
					.bitmapConfig(Bitmap.Config.RGB_565)
					.imageScaleType(ImageScaleType.IN_SAMPLE_INT).build();

			ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
					context)
					.threadPoolSize(3)
					// default
					.threadPriority(Thread.NORM_PRIORITY - 2)
					.denyCacheImageMultipleSizesInMemory()
					.diskCacheFileNameGenerator(new Md5FileNameGenerator())
					.tasksProcessingOrder(QueueProcessingType.LIFO)
					.denyCacheImageMultipleSizesInMemory()
					// .memoryCache(new LruMemoryCache((int) (6 * 1024 * 1024)))
					.memoryCache(new WeakMemoryCache())
					.memoryCacheSize((int) (2 * 1024 * 1024))
					.memoryCacheSizePercentage(13)
					// default
					// .diskCache(new UnlimitedDiscCache(new
					// File(FileUtils.SDPATH))
					// default
					.diskCacheSize(50 * 1024 * 1024)
					.diskCacheFileCount(100)
					.diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
					.defaultDisplayImageOptions(options).writeDebugLogs() // Remove
					.build();
			// Initialize ImageLoader with configuration.
			ImageLoader.getInstance().init(config);
		}
	}

	class viewHolder {
		ImageView imgFback;
		ImageView imgFbackDel;
	}
}
