package garrag.db;

import garrag.shiti.MClass;
import garrag.shiti.User;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class LYDao {
	static SQLiteDatabase db;
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
	
	static public void addClass(MClass mclass) {
		db.execSQL("insert into course ('class','subject','monitor','classnum','dep') VALUES (?,?,?,?,?)",new Object[]{"网络工程4班", "数学", "周", 33, "计算机系"});
	}
	
	static public String getMClass(){
		String calssname = "";
		String subject = "";
		String monitor = "";
		Cursor cursor = db.query("course", new String[]{"class","subject","monitor","classnum","dep"}, null, null, null, null, null);
		System.out.println("获取班级");
		while(cursor.moveToNext()){
			calssname = cursor.getString(cursor.getColumnIndex("class"));
			subject = cursor.getString(cursor.getColumnIndex("subject"));
			monitor = cursor.getString(cursor.getColumnIndex("monitor"));
		}
		return "--------" + calssname + " : " + subject + " : " + monitor + "------";
	}
	
}
