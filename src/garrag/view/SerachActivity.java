package garrag.view;

import garrag.shiti.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import android.os.Bundle;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class SerachActivity extends Activity {

	private ListView listView; 
	private BluetoothAdapter bluetoothAdapter = null;
	private BluetoothReceiver bluetoothReceiver = null;
	private List<User> users = new ArrayList<User>();
	private SimpleAdapter sa;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_serach);
		
		listView = (ListView) findViewById(R.id.listView_serach);
		
		users = (ArrayList<User>)getIntent().getSerializableExtra("user");
		//System.out.println("+++++++++++++++++" + users.get(0).getName());
		for(int i=0; i<users.size(); i++) {
			User u = users.get(i);
			u.setZhuangtai("1");
		}
		
		sa = listToadpater(users);
		listView.setAdapter(sa);
		
		IntentFilter intentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		bluetoothReceiver = new BluetoothReceiver();
		registerReceiver(bluetoothReceiver, intentFilter);
		bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		//开始扫描蓝牙设备
		bluetoothAdapter.startDiscovery();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.serach, menu);
		return true;
	}

	
	//蓝牙接收器
	private class BluetoothReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if(BluetoothDevice.ACTION_FOUND.equals(action)) {
				BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				System.out.println("扫描到了------" + device.getName());
				for(int i=0;i<users.size();i++) {
					User u = users.get(i);
					u.setZhuangtai("0");
					sa = listToadpater(users);
					listView.setAdapter(sa);
					SerachActivity.this.sa.notifyDataSetChanged();
				}
			}
		}
	}
	
	//把List转化成SimpleAdapter
		public SimpleAdapter listToadpater(List<User> users) {
			List<HashMap<String , Object>> data = new ArrayList<HashMap<String,Object>>();
			for (Iterator iterator = users.iterator(); iterator.hasNext();) {
				User u = (User) iterator.next();
				HashMap<String, Object> hm = new HashMap<String, Object>();
				hm.put("user_name", u.getName());
				hm.put("user_address", u.getId());
				if(u.getZhuangtai().equals("1")) {
					hm.put("user_zhuangtai", android.R.drawable.presence_busy);
				} else {
					hm.put("user_zhuangtai", android.R.drawable.presence_online);
				}
				data.add(hm);
			}
			sa = new SimpleAdapter(this, data, R.layout.dianming_item, new String[]{"user_name","user_address","user_zhuangtai"}, new int[]{R.id.dian_name, R.id.dian_xuehao, R.id.imageView1});
			return sa;
		}
		@Override
		protected void onDestroy() {
			// TODO Auto-generated method stub
			unregisterReceiver(bluetoothReceiver);
			super.onDestroy();
		}
	
}
