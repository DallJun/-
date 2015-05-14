package garrag.view;
import garrag.db.LYDao;
import garrag.shiti.KaoqingDetail;
import garrag.shiti.MClass;
import garrag.shiti.User;

import java.io.Serializable;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 学生详情
 * @author tc
 *
 */
public class StudentInfoActivity extends Activity implements OnClickListener{
	private TextView tv_name;
	private EditText tv_address;
	private EditText tv_email;
	private EditText tv_houseaddress;
	private EditText tv_in;
	private EditText tv_out;
	private TextView tv_nummber;
	private EditText tv_sushe;
	private TextView tv_class;
	private TextView tv_edit;
	/**
	 * 是否是编辑状态
	 */
	private boolean edit = false;
	//要显示的学生信息
	private User user;
	private MClass mc;
	private KaoqingDetail kq;
	LYDao dao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_student_info);
		Intent intent = getIntent();
		user = (User) intent.getSerializableExtra("user");
		mc = (MClass) intent.getSerializableExtra("class");
		dao = new LYDao(getApplicationContext());
		kq = dao.getKaoqingByClassAndStudent(mc, user);
		initView();
		initListener();
		initData();
	}
	/**
	 * 初始化数据
	 */
	private void initData() {
		// TODO Auto-generated method stub，
		tv_name.setText(user.getName());
		tv_address.setText("");
		tv_email.setText("");
		tv_houseaddress.setText("");
		tv_in.setText(kq.getChidao()+"");
		tv_out.setText(kq.getQuedao()+"");
		tv_nummber.setText(user.getId());
		tv_sushe.setText("");
		tv_class.setText(mc.getClassName());
	}
	/**
	 * 初始化监听
	 */
	private void initListener() {
		tv_edit.setOnClickListener(this);
	}
	/**
	 * 初始化View
	 */
	private void initView() {
		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_class = (TextView) findViewById(R.id.tv_class);
		tv_nummber = (TextView) findViewById(R.id.tv_nummber);
		tv_address = (EditText) findViewById(R.id.tv_address);
		tv_email = (EditText) findViewById(R.id.tv_email);
		tv_houseaddress = (EditText) findViewById(R.id.tv_houseaddress);
		tv_in = (EditText) findViewById(R.id.tv_in);
		tv_out = (EditText) findViewById(R.id.tv_out);
		tv_sushe = (EditText) findViewById(R.id.tv_sushe);
		tv_edit = (TextView)findViewById(R.id.tv_edit);
	}
	/**
	 * 点击事件处理
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_edit:
			if(edit){
				edit();
			}else{
				complated();
			}
			break;
		default:
			break;
		}
	}
	/**
	 * 设置编辑状态
	 */
	public void edit(){
		edit = !edit;
		tv_edit.setText("完成");
		tv_address.setFocusable(false);
		tv_email.setFocusable(false);
		tv_houseaddress.setFocusable(false);
		tv_in.setFocusable(false);
		tv_out.setFocusable(false);
		tv_sushe.setFocusable(false);
	}
	/**
	 * 完成状态
	 */
	public void complated(){
		//修改到数据库,
		tv_edit.setText("编辑");
		tv_address.setFocusable(true);
		tv_email.setFocusable(true);
		tv_houseaddress.setFocusable(true);
		tv_in.setFocusable(true);
		tv_out.setFocusable(true);
		tv_sushe.setFocusable(true);
		Toast.makeText(this, "要添加到数据库，请修改"+getClass().getName()+"这个类", 0).show();
	}
}
