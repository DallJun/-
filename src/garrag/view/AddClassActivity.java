package garrag.view;

import garrag.db.LYDao;
import garrag.shiti.MClass;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class AddClassActivity extends SherlockActivity {
	
	EditText name;   //班级名称
	EditText monitor; //班长 
	EditText subject; // 课程
	EditText dep;   // 部门
	EditText num;    //班级人数
	LYDao dao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_class);
		name = (EditText) findViewById(R.id.add_class_name);
		monitor = (EditText) findViewById(R.id.add_class_monitor);
		subject = (EditText) findViewById(R.id.add_class_subject);
		dep = (EditText) findViewById(R.id.add_class_dep);
		num = (EditText) findViewById(R.id.add_class_num);
		dao = new LYDao(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSherlock().setTitle("添加班级");
		menu.add("提交").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getTitleCondensed().equals("提交")) {
			if(name.getText().toString().equals("")||monitor.getText().toString().equals("")||subject.getText().toString().equals("")||
					dep.getText().toString().equals("")||num.getText().toString().equals("")){
				Toast.makeText(this, "填写信息不完整", 1).show();
				return true;
			}
			// TODO 提交数据
			MClass c = new MClass();
			c.setClassName(name.getText().toString());
			c.setMonitor(monitor.getText().toString());
			c.setSubject(subject.getText().toString());
			c.setDep(dep.getText().toString());
			c.setClassnum(50);
			//提交数据
			LYDao.addClass(c);
			Toast.makeText(this, c.toString(), 1).show();
		}
		return true;
	}
}
