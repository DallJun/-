package garrag.db;

import garrag.shiti.User;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class LYDao {
	static SQLiteDatabase db = null;
	public LYDao(Context context) {
		MyDataBase myDataBase = new MyDataBase(context, "blue_user");
		db = myDataBase.getReadableDatabase();
	}
	
	static public List<User> getStudents() {
		List<User> users = new ArrayList<User>();
		Cursor cursor = db.query("user", new String[]{"sid", "sname","smac"}, null, null, null, null, null);
		while(cursor.moveToNext()) {
			String name = cursor.getString(cursor.getColumnIndex("sname"));
			String id = cursor.getString(cursor.getColumnIndex("sid"));
			String mac = cursor.getString(cursor.getColumnIndex("smac"));
			User u = new User();
			u.setName(name);
			u.setId(id);
			u.setMac(mac);
			users.add(u);
		}
		return users;
	}
	
}
