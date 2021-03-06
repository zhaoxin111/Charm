package com.science.strangertofriend.adapter;

import java.util.Vector;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.science.strangertofriend.R;
import com.science.strangertofriend.TaskType;
import com.science.strangertofriend.R.color;
import com.science.strangertofriend.adapter.Task_Accept_Complete_Adapter.ViewHolder;
import com.science.strangertofriend.bean.Task;

import de.hdodenhof.circleimageview.CircleImageView;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 完成任务列表的适配器
 * 
 * @author lilin
 * @date 2015年11月11日 .下午2:57:24
 * @blog www.gaosililn.iteye.com
 * @email gaosi0812@gamil.com
 * @school usc
 * 
 */
public class Task_Publish_Complete_Adapter extends BaseAdapter {
	public static Vector<Task> vector = new Vector<>();
	private LayoutInflater layoutInflater = null;
	private static Task_Publish_Complete_Adapter adapter = null;
	

	private Task_Publish_Complete_Adapter() {
	}

	/**
	 * 获取适配器的对象
	 * 
	 * @return 适配器对象
	 */
	public static Task_Publish_Complete_Adapter initAdapter() {
		if (adapter == null) {
			adapter = new Task_Publish_Complete_Adapter();
		}
		return adapter;
	}

	/**
	 * 获取适配器的对象
	 * 
	 * @param activity
	 *            ListView的宿主Activity
	 * @return 适配器的对象
	 */
	public static Task_Publish_Complete_Adapter initAdapter(Activity activity) {
		if (adapter == null) {
			adapter = new Task_Publish_Complete_Adapter();
		}
		adapter.layoutInflater = activity.getLayoutInflater();
		return adapter;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return vector.size();
	}

	@Override
	public Object getItem(int position) {

		return vector.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = layoutInflater.inflate(
					R.layout.task_pubulisg_complete_layout, null, false);
			viewHolder.mCircleImageView = (CircleImageView) convertView
					.findViewById(R.id.avatar);
			viewHolder.publish_type = (TextView) convertView
					.findViewById(R.id.publish_type);
			viewHolder.publish_time = (TextView) convertView
					.findViewById(R.id.publish_time);
			viewHolder.publish_address = (TextView) convertView
					.findViewById(R.id.publish_address);
			viewHolder.publish_gold = (TextView) convertView
					.findViewById(R.id.publish_gold);
			viewHolder.publish_topsy_turvy = (TextView) convertView
					.findViewById(R.id.publish_topsy_turvy);
			viewHolder.publish_hunter = (TextView) convertView
					.findViewById(R.id.publish_hunter);
			convertView.setTag(viewHolder);
			// 设置颜色
//			if (position % 2 == 0) {
//				convertView
//						.setBackgroundColor(color.background_floating_material_dark);
//			}
		} else {// convertView 以缓冲
			viewHolder = (ViewHolder) convertView.getTag();
		}

		initValues(viewHolder, position);// 设置值
		return convertView;
	}

	/**
	 * 向控件中设置值
	 * 
	 * @param viewHolder
	 *            保存所有空进的Holder
	 * @param position
	 *            ListView的id
	 */
	private void initValues(ViewHolder viewHolder, int position) {
		viewHolder.publish_address.setText(vector.get(position).getLocation());
		viewHolder.publish_gold.setText(vector.get(position).getPrice() + "");
		viewHolder.publish_time.setText(vector.get(position).getEndTime());
		viewHolder.publish_topsy_turvy.setText(vector.get(position)
				.getTaskDescription());
		viewHolder.publish_hunter.setText(vector.get(position)
				.getAcceptedName());
		//viewHolder.publish_type.setText(vector.get(position).getType());
		showTaskType(viewHolder, position);
	}

	static class ViewHolder {
		private TextView publish_type, publish_time, publish_address,
				publish_gold, publish_hunter, publish_topsy_turvy;
		private CircleImageView mCircleImageView;
	}

	public static Vector<Task> getVector() {
		return vector;
	}

	public static void setVector(Vector<Task> vector) {
		Task_Publish_Complete_Adapter.vector = vector;
	}

	/**
	 * 添加相应的任务实体
	 * 
	 * @param task
	 *            任务实体
	 */
	public static void addVector(Task task) {
		vector.add(task);
	}

	/**
	 * 添加相应的任务实体进队列当总
	 * 
	 * @param task
	 *            任务实体
	 * @param i
	 *            制定的位置 所指定的位置大于列表的位置则添加到队列的末尾
	 */
	public static void addVector(Task task, int i) {
		if (i < vector.size()) {
			vector.add(i, task);
		} else {
			vector.add(task);
		}
	}

	/**
	 * 把相应的任务从任务队列当中移除
	 * 
	 * @param task
	 *            需要移除的任务实体
	 */
	public static void removeVector(Task task) {
		vector.remove(task);
	}

	/**
	 * 相应的任务从对列当中移除
	 * 
	 * @param acation
	 *            移除的下标
	 */
	public static void removeVector(int acation) {
		vector.remove(acation);
	}

	/**
	 * 移除全部的数据
	 */
	public static void removeAll() {
		vector.removeAllElements();
	}
	/**
	 * 设置任务类型
	 */
	public void showTaskType(ViewHolder viewHolder,int position){
		String type=vector.get(position).getType();
		switch (type) {
		case TaskType.SERVICE_CATERING:
			viewHolder.publish_type.setText("餐饮服务");
			break;
		case TaskType.SERVICE_EDUCATION:
			viewHolder.publish_type.setText("教育服务");
			break;
		case TaskType.SERVICE_EXPRESS:
			viewHolder.publish_type.setText("物流服务");
			break;
		case TaskType.SERVICE_FIX:
			viewHolder.publish_type.setText("维修服务");
			break;
		case TaskType.SERVICE_HOUSEWORK:
			viewHolder.publish_type.setText("家政服务");
			break;
		case TaskType.SERVICE_INTERNET:
			viewHolder.publish_type.setText("网络服务");
			break;
		case TaskType.SERVICE_OTHERS:
			viewHolder.publish_type.setText("其他服务");
			break;
		default:
			break;
		}
	}
}
