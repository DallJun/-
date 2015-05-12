package garrag.view;
import garrag.shiti.KaoqingDetail;
import garrag.utirl.MyAdapter;

import java.util.List;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
public class KaoqingDetailActivity extends Activity {
	private ListView listView;
	private MyAdapter adapter = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kaoqing);
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
				holder.quediao = (TextView) arg1.findViewById(R.id.queding);
				holder.chidao = (TextView) arg1.findViewById(R.id.chidao);
				arg1.setTag(holder);
			}else{
				holder = (ViewHolder) arg1.getTag();
			}
			KaoqingDetail detail = details.get(arg0);
			holder.name.setText(detail.user.getName());
			holder.name.setText(detail.quedao);
			holder.name.setText(detail.chidao);
			return arg1;
		}
	}
	
	class ViewHolder{
		private TextView name;
		private TextView quediao;
		private TextView chidao;
	}
}
