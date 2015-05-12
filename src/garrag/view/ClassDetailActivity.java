package garrag.view;

import java.util.ArrayList;

import garrag.db.LYDao;
import garrag.shiti.MClass;
import garrag.shiti.User;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class ClassDetailActivity extends SherlockActivity {
	
	EditText name;   //班级名称
	EditText monitor; //班长 
	EditText subject; // 课程
	EditText dep;   // 部门
	EditText num;    //班级人数
	LYDao dao;
	MClass mc;
	Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_class_detail);
		//接受数据
		context = this;
		mc = (MClass)getIntent().getSerializableExtra("class");
		name = (EditText) findViewById(R.id.add_class_name);
		monitor = (EditText) findViewById(R.id.add_class_monitor);
		subject = (EditText) findViewById(R.id.add_class_subject);
		dep = (EditText) findViewById(R.id.add_class_dep);
		num = (EditText) findViewById(R.id.add_class_num);
		dao = new LYDao(this);
		
		name.setText(mc.getClassName());
		monitor.setText(mc.getMonitor());
		subject.setText(mc.getSubject());
		dep.setText(mc.getDep());
		num.setText(mc.getClassnum()+"");
		//冻结班级名称与课程名称
		name.setEnabled(false);
		subject.setEnabled(false);
		
		ImageButton but = (ImageButton) findViewById(R.id.class_detail_student);
		but.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				//进入班级学生列表
//				Toast.makeText(context, mc.getClassName() ,1).show();
				Intent it = new Intent(ClassDetailActivity.this,MainActivity.class);
				startActivity(it);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSherlock().setTitle("修改班级信息");
		menu.add("完成").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getTitleCondensed().equals("完成")) {
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
