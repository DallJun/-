package garrag.view;
/**
 * 点名界面
 */
import garrag.db.LYDao;
import garrag.shiti.MClass;
import garrag.shiti.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import android.os.Bundle;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class DianmingActivity extends SherlockActivity {
	private ListView listView;
	private BluetoothAdapter bluetoothAdapter = null;
	private BluetoothReceiver bluetoothReceiver = null;
	private List<User> users = new ArrayList<User>();
	private SimpleAdapter sa;
	LYDao dao;
	MClass mc;
	private TextView tv_sd;
	private TextView tv_yd;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_serach);
		
		dao = new LYDao(this);
		listView = (ListView) findViewById(R.id.listView_serach);
		tv_yd = (TextView) findViewById(R.id.tv_yd);
		tv_sd = (TextView) findViewById(R.id.tv_sd);
		
		// 接受到传送过来的班级学生列表
		users = (ArrayList<User>) getIntent().getSerializableExtra("user");
		mc = (MClass) getIntent().getSerializableExtra("class");
		
		//初始化所有学生的状态 为1
		for (int i = 0; i < users.size(); i++) {
			User u = users.get(i);
			u.setZhuangtai("1");  //设置出事状态,都是1,表示未到
		}
		
		sa = listToadpater(users);
		
		listView.setAdapter(sa);
		IntentFilter intentFilter = new IntentFilter(
				BluetoothDevice.ACTION_FOUND);
		bluetoothReceiver = new BluetoothReceiver();
		registerReceiver(bluetoothReceiver, intentFilter);
		bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		// 开始扫描蓝牙设备
		bluetoothAdapter.startDiscovery();
	}

	// 蓝牙接收器
	private class BluetoothReceiver extends BroadcastReceiver {
		private int count=0;

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (BluetoothDevice.ACTION_FOUND.equals(action)) {
				BluetoothDevice device = intent
						.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
System.out.println("扫描到了------" + device.getName());
				//遍历所有的学生 
					tv_yd.setText("应到："+users.size());
					count=0;
				for (int i = 0; i < users.size(); i++) {
					User u = users.get(i);
					//判断扫描到的蓝牙 和 所有学生中的蓝牙有没有相同的mac 
					if(device.getAddress().equals(u.getMac())){
						Toast.makeText(context, "点名到了:" + u.getName(), 1).show();
						u.setZhuangtai("0");  //被扫描到的学生 设置状态为已到0
						sa = listToadpater(users);
						listView.setAdapter(sa);
						DianmingActivity.this.sa.notifyDataSetChanged();
						count++;
					}
				}
				tv_sd.setText("实到: "+count);
			}
		}
	}

	// 把List转化成SimpleAdapter
	public SimpleAdapter listToadpater(List<User> users) {
		List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		int count=0;
		for (Iterator iterator = users.iterator(); iterator.hasNext();) {
			if(count==0){
				count++;
				continue;
			}
			
			User u = (User) iterator.next();
			HashMap<String, Object> hm = new HashMap<String, Object>();
			hm.put("user_name", u.getName());
			hm.put("user_address", u.getId());
			
			if (("1").equals(u.getZhuangtai())) {  //根据状态显示 1=未到   0=已到
				hm.put("user_zhuangtai", android.R.drawable.presence_busy);
			} else {
				hm.put("user_zhuangtai", android.R.drawable.presence_online);
			}
			data.add(hm);
		}
		
		sa = new SimpleAdapter(this, data, R.layout.dianming_item,
				new String[] { "user_name", "user_address", "user_zhuangtai" },
				new int[] { R.id.dian_name, R.id.dian_xuehao, R.id.imageView1 });
		return sa;
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(bluetoothReceiver);
		super.onDestroy();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSherlock().setTitle("点名");
		menu.add("保存").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getTitleCondensed().equals("保存")) {
			for(int i=0; i<users.size(); i++){
				User u = users.get(i);
				if(u.getZhuangtai().equals("0")){//已到
					dao.updateSgin(u, mc, 1);
				}else { //未到
					dao.updateUnsgin(u, mc, 1);
				}
			}
		}
		this.finish();
		return true;
	}

}
