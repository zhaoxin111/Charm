package com.science.strangertofriend.utils;

import java.util.List;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.science.strangertofriend.adapter.Task_Accept_Complete_Adapter;
import com.science.strangertofriend.adapter.Task_Accept_UnComplete_Adapter;
import com.science.strangertofriend.adapter.Task_Publish_Complete_Adapter;
import com.science.strangertofriend.adapter.Task_Publish_UnComplete_Adapter;
import com.science.strangertofriend.bean.Task;

/**
 * ��ȡ����������ʷ�嵥�Ĺ�����
 * 
 * @author lilin
 * @date 2015��11��12�� .����10:58:34
 * @blog www.gaosililn.iteye.com
 * @email gaosi0812@gamil.com
 * @school usc
 *
 */
public class GetUserTaskLists {
	private HandlerThread handlerThread;
	/**
	 * ��ǰ�û���
	 */
	public static String myUserName = "";

	public GetUserTaskLists() {
		// ��ȡ��ǰ�û�
		AVUser avUser = AVUser.getCurrentUser();
		// ����HandlerThread �߳�
		handlerThread = new HandlerThread("getUserTaskList");
		// �����߳�
		handlerThread.start();
		// ���ͱ�־������Ӧ�Ĳ���
		handler.sendEmptyMessage(1);
	}

	Handler handler = new Handler(handlerThread.getLooper()) {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:// �տ�ʼ��ȡ����
				getValues();
				break;
			case 2:// ʵʱ�������

				break;
			default:

				break;
			}

		}
	};

	/**
	 * ��ȡ����
	 */
	private void getValues() {
		while (true) {
			try {
				// �Ƴ�����
				Task_Accept_Complete_Adapter.removeAll();
				Task_Accept_UnComplete_Adapter.removeAll();
				Task_Publish_UnComplete_Adapter.removeAll();
				Task_Publish_Complete_Adapter.removeAll();
				// ��ȡ����
				getValuesAtBinOfPub();
				getValuesAtBinOfAcce();
				// 10���Ӹ���һ��
				Thread.sleep(600000);

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * һ��ʼ��ȡ��������ȫ������
	 */
	public void getValuesAtBinOfPub() {
		AVQuery<AVObject> query = new AVQuery<>("Task");
		query.whereEqualTo("publisherName", myUserName);
		Task taskBean = null;
		try {
			List<AVObject> tasks = query.find();
			int len = tasks.size();
			for (int i = 0; i < len; i++) {
				AVObject task = tasks.get(i);
				taskBean = new Task();
				taskBean.setPublisherName(task.getString("publisherName"));
				taskBean.setAccepted(false);
				boolean isAccomplished = task.getBoolean("isAccomplished");
				taskBean.setAccomplished(isAccomplished);
				taskBean.setEndTime(task.getString("endTime"));
				taskBean.setPrice(task.getString("price"));
				taskBean.setTheme(task.getString("theme"));
				taskBean.setTaskDescription(task.getString("TaskDescription"));
				taskBean.setType(task.getString("service_task"));
				taskBean.setLatitude(task.getAVGeoPoint("geoPoint")
						.getLatitude());
				taskBean.setLongitude(task.getAVGeoPoint("geoPoint")
						.getLongitude());
				taskBean.setLocation(task.getString("location"));
				taskBean.setType(task.getString("service_type"));
				// �ӵ���Ӧ�Ķ��е���
				if (isAccomplished) {// �Ѿ���ɵ�
					// ���������ӵ�����Ķ���
					Task_Publish_Complete_Adapter.addVector(taskBean);
				} else {// ��δ��ɵ�
					// ���������ӵ������������е���
					Task_Publish_UnComplete_Adapter.addVector(taskBean);
				}

			}

		} catch (AVException e) {
			e.printStackTrace();
		}
	}

	/**
	 * һ��ʼ��ȡ�����յ����������
	 */
	public void getValuesAtBinOfAcce() {
		AVQuery<AVObject> query = new AVQuery<>("Task");
		query.whereEqualTo("acceptedName", myUserName);
		Task taskBean = null;
		try {
			List<AVObject> tasks = query.find();
			int len = tasks.size();
			for (int i = 0; i < len; i++) {
				AVObject task = tasks.get(i);
				taskBean = new Task();
				taskBean.setPublisherName(task.getString("publisherName"));
				taskBean.setAccepted(false);
				boolean isAccomplished = task.getBoolean("isAccomplished");
				taskBean.setAccomplished(isAccomplished);
				taskBean.setEndTime(task.getString("endTime"));
				taskBean.setPrice(task.getString("price"));
				taskBean.setTheme(task.getString("theme"));
				taskBean.setTaskDescription(task.getString("TaskDescription"));
				taskBean.setType(task.getString("service_task"));
				taskBean.setLatitude(task.getAVGeoPoint("geoPoint")
						.getLatitude());
				taskBean.setLongitude(task.getAVGeoPoint("geoPoint")
						.getLongitude());
				taskBean.setLocation(task.getString("location"));
				taskBean.setType(task.getString("service_type"));
				// �ӵ���Ӧ�Ķ��е���
				if (isAccomplished) {// �Ѿ���ɵ�
					// ���������ӵ�����Ķ���
					Task_Accept_Complete_Adapter.addVector(taskBean);
				} else {// ��δ��ɵ�
					// ���������ӵ������������е���
					Task_Accept_UnComplete_Adapter.addVector(taskBean);
				}

			}

		} catch (AVException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��ȡ�����Ƿ��ѷ����ı�
	 */
	public void getChangeValuesInPub() {

	}

}