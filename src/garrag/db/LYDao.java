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
	
	static public List<User> getStudentsBySubject(MClass mc) {
		List<User> users = new ArrayList<User>();
//		Cursor cursor = db.query("kaoqing", new String[]{"sid", "sname","smac"}, "class=? and subject=?", new String[]{mc.getClassName(), mc.getSubject()}, null, null, null);
		Cursor cursor = db.rawQuery("select * from t_check as c INNER JOIN user as u on u.sid = c.sid where c.class=? and c.subject=?", new String[]{mc.getClassName(), mc.getSubject()});
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
		db.execSQL("replace into course ('class','subject','monitor','classnum','dep') VALUES (?,?,?,?,?)",
				new Object[]{mclass.getClassName(), mclass.getSubject(), mclass.getMonitor(), 0, mclass.getDep()});
	}
	/**
	 * 获取所有的班级信息
	 * @return
	 */
	static public ArrayList<MClass> getMClass(){
		ArrayList<MClass> list = new ArrayList<MClass>();
		Cursor cursor = db.rawQuery("select * from course order by subject", new String[]{});
		while(cursor.moveToNext()){
			MClass c = new MClass();
			c.setClassName(cursor.getString(cursor.getColumnIndex("class")));
			c.setSubject(cursor.getString(cursor.getColumnIndex("subject")));
			c.setMonitor(cursor.getString(cursor.getColumnIndex("monitor")));
			c.setClassnum(cursor.getInt(cursor.getColumnIndex("classnum")));
			c.setDep(cursor.getString(cursor.getColumnIndex("dep")));
			list.add(c);
		}
		return list;
	}
	/**
	 * 添加考勤记录(在添加学生的时候 需要添加对应的考勤记录)
	 * @param u  学生
	 * @param mc  所属班级
	 */
	public void addCheckItem(User u, MClass mc) {
		db.execSQL("replace into t_check ('class','subject','sid') VALUES (?,?,?)",
				new String[]{mc.getClassName(), mc.getSubject(), u.getId()});
	}
	
}
















