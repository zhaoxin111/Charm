package com.science.strangertofriend;

import java.util.List;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.PushService;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;
import com.avoscloud.leanchatlib.controller.ChatManager;
import com.avoscloud.leanchatlib.controller.ChatManagerAdapter;
import com.avoscloud.leanchatlib.model.UserInfo;
import com.iflytek.cloud.SpeechUtility;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.science.strangertofriend.ui.ChatRoomActivity;
import com.science.strangertofriend.ui.DetailedTaskActivity;
import com.science.strangertofriend.ui.ReceiveMessageHandler;
import com.science.strangertofriend.ui.ShowNearMenMapActivity;

/**
 * @description 全局应用程序类：用于保存和调用全局应用配置及访问网络数据
 * 
 */
public class AppContext extends Application {

	public static boolean isThisLocation = true;

	@Override
	public void onCreate() {
		super.onCreate();
		//讯飞语音
		SpeechUtility.createUtility(AppContext.this, "appid=5835a709");
		
		// ImageLoader图片缓存
		initImageLoader(getApplicationContext());

		// U need your AVOS key and so on to run the code.
		AVOSCloud.initialize(getApplicationContext(),
				"u30ryjydwuqfa8edh8oo6af2kf78cgh3uatpa3bj5s837rla",
				"u44at27q33q07n14i0xdp7clqgq0d5x9a8o4trssw3t7gija");
		// 启用崩溃错误统计
		AVAnalytics.enableCrashReport(this.getApplicationContext(), true);
		AVOSCloud.setDebugLogEnabled(true);
		ChatManager.setDebugEnabled(true);// tag leanchatlib

		AVIMMessageManager.registerMessageHandler(AVIMTypedMessage.class,
				new ReceiveMessageHandler(this));
		final ChatManager chatManager = ChatManager.getInstance();
		chatManager.init(this);
		chatManager.setChatManagerAdapter(new ChatManagerAdapter() {
			@Override
			public UserInfo getUserInfoById(String userId) {
				UserInfo userInfo = new UserInfo();
				userInfo.setUsername(userId);
				userInfo.setAvatarUrl("http://ac-x3o016bx.clouddn.com/86O7RAPx2BtTW5zgZTPGNwH9RZD5vNDtPm1YbIcu");
				return userInfo;
			}

			@Override
			public void cacheUserInfoByIdsInBackground(List<String> userIds)
					throws Exception {
			}

			// 关于这个方法请见 leanchat 应用中的 ChatManagerAdapterImpl.java
			@Override
			public void shouldShowNotification(Context context, String selfId,
					AVIMConversation conversation, AVIMTypedMessage message) {
				Toast.makeText(context, "您收到一条消息，请查收！", Toast.LENGTH_LONG)
						.show();
			}
		});

		/**
		 * 消息透传
		 */
		// 设置默认打开的 Activity
		PushService.setDefaultPushCallback(getApplicationContext(),
				ShowNearMenMapActivity.class);
		// 订阅频道，当该频道消息到来的时候，打开对应的 Activity
		PushService.subscribe(getApplicationContext(), "public",
				MainActivity.class);
		PushService.subscribe(getApplicationContext(), "private",
				ChatRoomActivity.class);

	}

	/**
	 * 检测网络是否可用
	 * 
	 * @return
	 */
	public boolean isNetworkConnected() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return ni != null && ni.isConnectedOrConnecting();
	}

	/** 初始化图片加载类配置信息 **/
	@SuppressWarnings("deprecation")
	public static void initImageLoader(Context context) {

		// This configuration tuning is custom. You can tune every option, you
		// may tune some of them,
		// or you can create default configuration by
		// ImageLoaderConfiguration.createDefault(this);
		// method.
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)// 加载图片的线程数
				.denyCacheImageMultipleSizesInMemory() // 解码图像的大尺寸将在内存中缓存先前解码图像的小尺寸。
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())// 设置磁盘缓存文件名称
				.tasksProcessingOrder(QueueProcessingType.LIFO)// 设置加载显示图片队列进程
				.writeDebugLogs() // Remove for release app
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}

}
