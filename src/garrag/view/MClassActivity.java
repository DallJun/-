package garrag.view;

import java.util.ArrayList;

import garrag.db.LYDao;
import garrag.shiti.MClass;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class MClassActivity extends SherlockActivity {
	
	LYDao dao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mclass);
		dao = new LYDao(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		ArrayList<MClass> list = dao.getMClass();
		String str = "";
		for(int i=0; i<list.size(); i++){
			MClass c = list.get(i);
			str += c.toString() + "\n";
		}
		Toast.makeText(this, str, 1).show();
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
}
