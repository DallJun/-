package garrag.db;

import garrag.shiti.KaoqingDetail;
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
	/**
	 * 添加学生
	 * @param u
	 */
	public void addStudent(User u) {
		db.execSQL("replace into user ('sid','sname','smac') VALUES (?,?,?)",
				new String[]{u.getId(), u.getName(), u.getMac()});
	}

	/**
	 * 根据ID删除对应的学生考勤记录
	 * @param sid
	 */
	public void delStudentCheck(String sid) {
		db.execSQL("delete from t_check where sid = ?",new Object[]{sid});
	}

	/**
	 * 获取对应班级课程的所有学生考勤
	 * @param mc
	 * @return
	 */
	public List<KaoqingDetail> getKaoqingByClass(MClass mc) {
		ArrayList<KaoqingDetail> list = new ArrayList<KaoqingDetail>();
		Cursor cursor = db.rawQuery("select * from t_check as c INNER JOIN user as u on u.sid = c.sid where c.class=? and c.subject=?", new String[]{mc.getClassName(), mc.getSubject()});
		while(cursor.moveToNext()){
			KaoqingDetail kq = new KaoqingDetail();
			kq.setChidao(cursor.getInt(cursor.getColumnIndex("sgin")));
			kq.setQuedao(cursor.getInt(cursor.getColumnIndex("unsgin")));
			User u = new User();
			u.setId(cursor.getString(cursor.getColumnIndex("sid")));
			u.setMac(cursor.getString(cursor.getColumnIndex("smac")));
			u.setName(cursor.getString(cursor.getColumnIndex("sname")));
			kq.setUser(u);
			list.add(kq);
		}
		return list;
	}

	/**
	 * 更新已到次数
	 * @param mc 
	 * @param u 
	 * @param i
	 */
	public void updateSgin(User u, MClass mc, int i) { 
		Cursor cursor  = db.rawQuery("select sgin from t_check where class=? and subject=? and sid=?", new String[]{mc.getClassName(), mc.getSubject(), u.getId()});
		if(cursor.moveToNext()){
			int num =  cursor.getInt(cursor.getColumnIndex("sgin"));
			num+=i;
			db.execSQL("update t_check set sgin=? where class=? and subject=? and sid=?", new Object[]{num, mc.getClassName(), mc.getSubject(), u.getId()});
		}
	}

	/**
	 * 更新未到次数
	 * @param mc 
	 * @param u    
	 * @param i
	 */
	public void updateUnsgin(User u, MClass mc, int i) {
		Cursor cursor  = db.rawQuery("select unsgin from t_check where class=? and subject=? and sid=?", new String[]{mc.getClassName(), mc.getSubject(), u.getId()});
		while(cursor.moveToNext()){
			int num =  cursor.getInt(cursor.getColumnIndex("unsgin"));
			num+=i;
			db.execSQL("update t_check set unsgin=? where class=? and subject=? and sid=?", new Object[]{num, mc.getClassName(), mc.getSubject(), u.getId()});
		}
	}

	public KaoqingDetail getKaoqingByClassAndStudent(MClass mc, User user) {
		KaoqingDetail kq = new KaoqingDetail();
		Cursor cursor  = db.rawQuery("select * from t_check where sid=? and class=? and subject=?", new String[]{user.getId(), mc.getClassName(), mc.getSubject()});
		while(cursor.moveToNext()){
			kq.setChidao(cursor.getInt(cursor.getColumnIndex("sgin")));
			kq.setQuedao(cursor.getInt(cursor.getColumnIndex("unsgin")));
			kq.setUser(user);
		}
		return kq;
	}
	
	
}
















