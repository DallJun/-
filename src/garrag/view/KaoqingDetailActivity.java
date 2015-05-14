package garrag.view;
/**
 * 所有学生考勤情况
 */
import garrag.db.LYDao;
import garrag.shiti.KaoqingDetail;
import garrag.shiti.MClass;

import java.util.List;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
public class KaoqingDetailActivity extends SherlockActivity {
	private ListView listView;
	private MyAdapter adapter = null;
	LYDao dao;
	MClass mc;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kaoqing);
		dao = new LYDao(this);
		mc = (MClass)getIntent().getSerializableExtra("class");
		initView();
		initListener();
		initData();
	}

	private void initData() {
		new AsyncTask<Void, Integer, List<KaoqingDetail>>(){

			@Override
			protected List<KaoqingDetail> doInBackground(Void... params) {
				List<KaoqingDetail> list= null;
				//查询数据库,xie dao
				list =  dao.getKaoqingByClass(mc);
				return list;
			}
			@Override
			protected void onPostExecute(List<KaoqingDetail> result) {
				adapter = new MyAdapter(result);
				listView.setAdapter(adapter);
			}
		}.execute(new Void[]{});
	}

	private void initListener() {
		
	}

	private void initView() {
		listView = (ListView) findViewById(R.id.lv_kaoqing);
	}
	
	private class MyAdapter extends BaseAdapter{
		private List<KaoqingDetail> details;
		public MyAdapter(List<KaoqingDetail> kaoqingDetails){
			this.details = kaoqingDetails;
		}
		@Override
		public int getCount() {
			return details == null?0:details.size();
		}

		@Override
		public Object getItem(int arg0) {
			return details.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			ViewHolder holder = null;
			if(arg1 == null){
				holder = new ViewHolder();
				arg1 = View.inflate(KaoqingDetailActivity.this, R.layout.item_kaoqing, null);
				holder.name = (TextView) arg1.findViewById(R.id.name);
				holder.quediao = (TextView) arg1.findViewById(R.id.quedao);
				holder.chidao = (TextView) arg1.findViewById(R.id.chidao);
				arg1.setTag(holder);
			}else{
				holder = (ViewHolder) arg1.getTag();
			}
			KaoqingDetail detail = details.get(arg0);
			Toast.makeText(getApplicationContext(), detail.getUser().getName(), 1).show();
			holder.name.setText(detail.getUser().getName());
			holder.quediao.setText(detail.getQuedao()+"");
			holder.chidao.setText(detail.getChidao()+"");
			return arg1;
		}
	}
	
	class ViewHolder{
		private TextView name;
		private TextView quediao;
		private TextView chidao;
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSherlock().setTitle("考勤");
		menu.add("返回").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getTitleCondensed().equals("返回")) {
			this.finish();
		}
		return true;
	}
}
