package garrag.view;
/**
 * 扫描添加
 */
import garrag.db.LYDao;
import garrag.shiti.MClass;
import garrag.shiti.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class SearchAddActivity extends SherlockActivity {

	private ListView listView;
	private BluetoothAdapter bluetoothAdapter = null;
	private BluetoothReceiver bluetoothReceiver = null;
	private List<User> users = new ArrayList<User>();
	private SimpleAdapter sa ;
	private EditText eName,eId;
	MClass mc;
	LYDao dao;
	private Button bt_add;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_add);
		dao = new LYDao(this);
		Intent intent = getIntent();
		mc  = (MClass) intent.getSerializableExtra("class");
		listView = (ListView) findViewById(R.id.shaomiao_result);
		bt_add = (Button) findViewById(R.id.bt_add);
		//一键添加 便利data
		bt_add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(null!=data&&data.size()>0){
					for(int i= 0 ; i<data.size() ; i++){
						User u = new User();
						u.setName(data.get(i).get("user_name"));
						u.setId(data.get(i).get("user_id"));
						u.setMac(data.get(i).get("user_address"));
						addUser(u);
					}
				}else{
					Toast toast=new Toast(SearchAddActivity.this);
					toast.makeText(SearchAddActivity.this, "暂无设备", 0).show();
				}
			}
		});
		
		//注册蓝牙信息
		IntentFilter intentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		//注册蓝牙接收者
		bluetoothReceiver = new BluetoothReceiver();
		registerReceiver(bluetoothReceiver, intentFilter);
		//获得蓝牙适配器
		bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int arg2, long arg3) {
				TextView textView = (TextView) view.findViewById(R.id.user_adress);
				final String mac = textView.getText().toString(); 
				// 弹出窗口收集 学生的姓名和学号
				AlertDialog.Builder builder = new Builder(SearchAddActivity.this);
					builder.setTitle("添加...");
					View v = SearchAddActivity.this.getLayoutInflater().inflate(R.layout.activity_add_isadd, null);
					
					eName = (EditText) v.findViewById(R.id.editText1);
					eId = (EditText) v.findViewById(R.id.editText2);
					
					eName.setText(data.get(arg2).get("user_name"));
					eId.setText(data.get(arg2).get("user_id"));
					
					builder.setView(v); 
					
					// 对输入的数据做处理
					builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int whichButton) {
							AlertDialog d = (AlertDialog) dialog;
							eName = (EditText) d.findViewById(R.id.editText1);
							eId = (EditText) d.findViewById(R.id.editText2);
							String name = eName.getText().toString();
							String id = eId.getText().toString();
							User u = new User();
							u.setName(name);
							u.setId(id);
							u.setMac(mac);
							addUser(u);
						}
					});  
					
					// 做取消输入的事情
					builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int whichButton) {
						      
						}
					});
				AlertDialog dialog = builder.create();
				dialog.show();
				
			}
		});
		
		
	}
	

	/*public void queding(View view) {
		String strname = name.getText().toString().trim();
		String strid = id.getText().toString().trim();
		
		Intent intent = new Intent();
		intent.putExtra("name", strname);
		intent.putExtra("id", strid);
		
		this.setResult(1, intent);
		this.finish();
	}*/
	/**
	 * 蓝牙接收器
	 * @author asus
	 *
	 */
	private class BluetoothReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			//获取蓝牙动作
			String action = intent.getAction();
			//判断蓝牙动作是否是  找到对方蓝牙驱动的动作
			if(BluetoothDevice.ACTION_FOUND.equals(action)) {
				//获取搜索到的蓝牙驱动信息
				BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//System.out.println("扫描到了------" + device.getName());
				User u = getUserInfo(device.getName(), device.getAddress());
				users.add(u);		
				Toast.makeText(context, u.getName() + ":" + u.getId(), 1).show();
				sa = listToadpater(users);
				listView.setAdapter(sa);
				SearchAddActivity.this.sa.notifyDataSetChanged();
			}
		}
	}
	
	//把List转化成SimpleAdapter
	public SimpleAdapter listToadpater(List<User> users) {
		data = new ArrayList<HashMap<String,String>>();
		for (Iterator iterator = users.iterator(); iterator.hasNext();) {
			User u = (User) iterator.next();
			HashMap<String, String> hm = new HashMap<String, String>();
			hm.put("user_name", u.getName());
			hm.put("user_address", u.getMac());
			hm.put("user_id", u.getId());
			data.add(hm);
		}
		sa = new SimpleAdapter(this, data, R.layout.activity_shaomiao, new String[]{"user_name","user_address"}, new int[]{R.id.user_name,R.id.user_adress});
		return sa;
	}
	
	private ProgressDialog dialog = null;
	private List<HashMap<String , String>> data;
	//定义响应扫描按钮
	public void shaomiao(View view) {
		
		//开启蓝牙扫描
		bluetoothAdapter.startDiscovery();
System.out.println("开始搜索");
		if(users.size() > 0) {
			users.clear();
			sa = listToadpater(users);
			listView.setAdapter(sa);
System.out.println("user have count-----------:" + users.size());
		}
		ProgressDialog dialog = new ProgressDialog(this);
		dialog.setTitle("正在扫描,请稍后...");
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);
		dialog.show();
	}
	//添加用户
	public void addUser(User u) {
		dao.addStudent(u);
		dao.addCheckItem(u ,mc);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		unregisterReceiver(bluetoothReceiver);
		super.onDestroy();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSherlock().setTitle("扫描添加");
		menu.add("完成").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getTitleCondensed().equals("完成")) {
			Toast.makeText(this, "111111", 0).show();
		}
		return true;
	}
	/**
	 * 获取蓝牙信息分装到javabean
	 * @param lanya
	 * @param mac
	 * @return
	 */
	public User getUserInfo(String lanya,String mac){
		User user = null;
		try{
			String sid = lanya.split("/")[0];
			String name = lanya.split("/")[1];
			user = new User(name, sid, mac);
		}catch (Exception e) {
			Toast.makeText(this, "蓝牙名称为"+lanya+"的命名规则不对", 0).show();
			user = new User();
			user.setName(lanya);
			user.setMac(mac);
			user.setId("0");
		}
		return user;
	}
}













