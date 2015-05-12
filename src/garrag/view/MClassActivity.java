package garrag.view;

import java.io.Serializable;
import java.util.ArrayList;

import garrag.db.LYDao;
import garrag.shiti.MClass;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class MClassActivity extends SherlockActivity {
	
	LYDao dao;
	Context mComtext;
	ListView listView;
	ArrayList<MClass> list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mclass);
		dao = new LYDao(this);
		mComtext = this;
		
	}

	@Override
	protected void onResume() {
		super.onResume();
		list = dao.getMClass();
		ClassListAdpater adp = new ClassListAdpater(list);
		listView = (ListView) findViewById(R.id.class_listview);
		listView.setAdapter(adp);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				MClass c = list.get(arg2);
				Intent intent = new Intent();
				intent.putExtra("class", (Serializable)c);
				intent.setClass(MClassActivity.this, ClassDetailActivity.class);
				startActivity(intent);
			}
			
		});
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSherlock().setTitle("班级列表");
		menu.add("add").setIcon(R.drawable.add_class)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// 添加班级课程
		Intent intents = new Intent();
		intents.setClass(MClassActivity.this, AddClassActivity.class);
		startActivity(intents);
		return true;
	}
	
	class ClassListAdpater extends BaseAdapter{
		ArrayList<MClass> list;
		public ClassListAdpater(ArrayList<MClass> list) {
			this.list = list;
		}

		@Override
		public int getCount() {
			return this.list.size();
		}

		@Override
		public Object getItem(int arg0) {
			return this.list.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view  = LayoutInflater.from(mComtext).inflate(R.layout.class_item, null);
			MClass c = this.list.get(position);
			((TextView)view.findViewById(R.id.class_item_monitor)).setText(c.getMonitor());
			((TextView)view.findViewById(R.id.class_item_name)).setText(c.getClassName());
			((TextView)view.findViewById(R.id.class_item_subject)).setText(c.getSubject());
			return view;
		}
		
	}
	
}
