package garrag.view;
import garrag.db.LYDao;
import garrag.db.MyDataBase;
import garrag.shiti.MClass;
import garrag.shiti.User;
import garrag.utirl.MyAdapter;
import garrag.utirl.ViewHolder;
import java.io.Serializable;
import java.util.List;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
public class ClassStudentListActivity extends SherlockActivity {
	Button tianjia;  //添加按钮
	Button chakan;   //查看按钮
	MyAdapter adapter;  //适配器
	boolean visflag = false;  //是否显示勾选框
	List<User> users;   //用户列表
	LYDao dao;
	MClass mc;
	private Button bt_delete;
	private Button bt_kaoqing;
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
		getSherlock().setTitle(mc.getClassName());
        menu.add("扫描添加")
            .setIcon(R.drawable.add_class)
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return true;
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		//TODO 添加班级课程
		if (item.getTitleCondensed().equals("扫描添加")) {
			Intent intent = new Intent(this, SearchAddActivity.class);
			intent.putExtra("class", mc);
			startActivity(intent);
		}
		
		return true;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Intent intent = getIntent();
		mc  = (MClass) intent.getSerializableExtra("class");
		dao = new LYDao(this);//初始化数据库
		tianjia = (Button) findViewById(R.id.button1);
		chakan = (Button) findViewById(R.id.button2);
		bt_delete = (Button) findViewById(R.id.bt_delete);
		bt_kaoqing = (Button) findViewById(R.id.bt_kaoqing);
		
		MyDataBase myDataBase = new MyDataBase(this, "blue_user");
		SQLiteDatabase db = myDataBase.getReadableDatabase();
		
		final Context self = this; 
		tianjia.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//跳转至添加页面 收集数据
				Intent intents = new Intent();
				intents.setClass(ClassStudentListActivity.this, SearchAddActivity.class);
				intents.putExtra("class", mc);
				startActivity(intents);
			}
		});
		chakan.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// 跳转到点名界面
				Intent intent = new Intent();
				intent.putExtra("user", (Serializable)users);
				intent.setClass(ClassStudentListActivity.this, DianmingActivity.class);
				startActivity(intent);
			}
		});
		bt_delete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ClassStudentListActivity.this, DeleteStudentActivity.class);
				startActivity(intent);
			}
		});
		bt_kaoqing.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ClassStudentListActivity.this, KaoqingDetailActivity.class);
				startActivity(intent);
			}
		});
		db.close();
		myDataBase.close();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		ListView listView = (ListView) findViewById(R.id.listView1);
		
		users = dao.getStudentsBySubject(mc);//拿到数据源
		adapter = new MyAdapter(this, users, visflag);
		listView.setAdapter(adapter);
		
		//选择复选框
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(visflag)
                {  
					//得到viewHolder
                    ViewHolder viewHolder = (ViewHolder) view.getTag();  
                    //改变cb的状态
                    viewHolder.cb.toggle(); 
                    MyAdapter.getIsSelected().set(position,  viewHolder.cb.isChecked());
                }
				else{
					//跳转到学生详情
					Intent intent = new Intent(ClassStudentListActivity.this,StudentInfoActivity.class);
					intent.putExtra("user", users.get(position));
					startActivity(intent);
				}
			}    
        });
		//长按弹出复选框
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
					visflag = true;
					adapter.setVisflag(true);
					adapter.notifyDataSetChanged();  
				return false;
			}
		});
		
	}
//	//添加菜单内容	
//	public boolean onCreateOptionsMenu(Menu menu)    
//    {    
//        menu.add(0, 0, 0, "操作");    
//        menu.add(0, 1, 0, "确定删除");    
//        return super.onCreateOptionsMenu(menu);    
//    }		
//	//菜单内容监听
//	public boolean onOptionsItemSelected(MenuItem item)    
//    {    
//        switch(item.getItemId())    
//        {    
//            case 0:  // 批量处理    
//                {    
//                    if(visflag)    
//                    {    
//                        visflag = false;    
//                        for(int i=0; i<users.size();i++)  
//                        {  
//                            //boolList.set(i, false);  
//                            MyAdapter.getIsSelected().set(i, false);
//                        }  
//                    }    
//                    else    
//                    {    
//                        visflag = true;
//                        adapter.setVisflag(true);
//                    }    
//                    this.adapter.notifyDataSetInvalidated();    
//                    break;    
//                }    
//            case 1: //确定删除    
//                {    
//                    if(users.size()>0)    
//                    {    
//                        if(visflag)  
//                        {  
//                           for(int location=0; location<MyAdapter.getIsSelected().size(); )  
//                            {  
//                                if(MyAdapter.getIsSelected().get(location))  
//                                {  
//                                	MyAdapter.getIsSelected().remove(location);
//                                    deleteData(location);//删除数据根据id
//                                    users.remove(location);
//                                    continue;  
//                                }
//                                location++;  
//                            }  
//                        }  
//                            
//                    }  
//                    visflag = false;
//                    adapter.setVisflag(false);
//                    this.adapter.notifyDataSetChanged();  
//                    break;    
//                }    
//        }    
//        return super.onOptionsItemSelected(item);    
//    }	
	
	//根据ID删除数据库里面的数据
	public void deleteData(int location) {
		MyDataBase myDataBase = new MyDataBase(this, "blue_user");
 		SQLiteDatabase db = myDataBase.getWritableDatabase();
 		db.delete("user", "sid=?", new String[]{users.get(location).getId()});
 		db.close();
 		myDataBase.close();
	}
	//接受收集过来的数据
	/*protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(data!=null) {
			String name = data.getStringExtra("name");
			String id = data.getStringExtra("id");
			ContentValues contentValues = new ContentValues();
			contentValues.put("sid", Integer.parseInt(id));
			contentValues.put("sname", name);
			contentValues.put("smac", "ab:cd:ef:gh");
			MyDataBase myDataBase = new MyDataBase(this, "blue_user");
			SQLiteDatabase db = myDataBase.getWritableDatabase();
			db.insert("user", null, contentValues);
			db.close();
			myDataBase.close();
		}
	}*/
	
	
	
	
	
	
	
}
