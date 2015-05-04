package garrag.view;

import garrag.db.LYDao;
import garrag.shiti.MClass;
import android.os.Bundle;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class MClassActivity extends SherlockActivity{
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_mclass);
			
		}
		@Override
	    public boolean onCreateOptionsMenu(Menu menu) {
			getSherlock().setTitle("班级列表");
	        menu.add("add")
	            .setIcon(R.drawable.add_class)
	            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
	        return true;
	    }
		
		@Override
		public boolean onOptionsItemSelected(MenuItem item){
			//TODO 添加班级课程
			Toast.makeText(this, "添加班级", Toast.LENGTH_SHORT).show();
			return true;
		}
}
