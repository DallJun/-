package garrag.view;
/**
 * 暂时无用
 */
import java.util.List;
import garrag.db.LYDao;
import garrag.shiti.User;
import garrag.utirl.MyAdapter;
import garrag.utirl.ViewHolder;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
public class DeleteStudentActivity extends SherlockActivity {
	private List<User> users;
	private ListView listView;
	private Button fanxuan;
	private Button deleteAll;
	private Button selectall;
	private MyAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_delete);
		initView();
		initData();
		initListener();
	}
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
		getSherlock().setTitle("删除学生");
        return true;
    }
	private void initListener() {
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				//得到viewHolder
                ViewHolder viewHolder = (ViewHolder) arg1.getTag();  
                //改变cb的状态
                viewHolder.cb.toggle(); 
                MyAdapter.getIsSelected().set(arg2,  viewHolder.cb.isChecked());
			}
		});
		selectall.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				List<Boolean> list = adapter.getIsSelected();
				for(int i = 0;list!=null&&i<list.size();i++){
					Boolean flag = list.get(i);
					flag = true;
				}
				adapter.notifyDataSetChanged();
			}
		});
		fanxuan.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				List<Boolean> list = adapter.getIsSelected();
				for(int i = 0;list!=null&&i<list.size();i++){
					Boolean flag = list.get(i);
					flag = !flag;
				}
				adapter.notifyDataSetChanged();
			}
		});
		deleteAll.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(hasClickedDelete){
					return;
				}
				hasClickedDelete = true;
				new AsyncTask<Void, Integer, Boolean>() {
					private ProgressDialog dialog;
					@Override
					protected void onPreExecute() {
						dialog = new ProgressDialog(DeleteStudentActivity.this);
						dialog.setMessage("正在删除，请稍后");
						dialog.setCancelable(false);
						dialog.show();
					}
					@Override
					protected Boolean doInBackground(Void... params) {
						//删除该学生
						return true;
					}
					
					@Override
					protected void onPostExecute(Boolean result) {
						dialog.dismiss();
						Toast.makeText(DeleteStudentActivity.this, "删除成功", 0).show();
					}
				};
			}
		});
	}
	private boolean hasClickedDelete = false;
	private void initData() {
		// TODO Auto-generated method stub
		LYDao dao = new LYDao(this);
		users = LYDao.getStudents();
		adapter = new MyAdapter(this, users, true);
		listView.setAdapter(adapter);
	}

	private void initView() {
		listView = (ListView) findViewById(R.id.listView1);
		fanxuan = (Button) findViewById(R.id.button1);
		deleteAll = (Button) findViewById(R.id.button2);
		selectall = (Button) findViewById(R.id.bt_all);
	}
	
}
