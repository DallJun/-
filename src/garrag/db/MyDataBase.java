package garrag.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDataBase extends SQLiteOpenHelper{
	
	private static final int VERSION = 2;
	public MyDataBase(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}
	public MyDataBase(Context context,String name){
		this(context,name,VERSION);
	}
	public MyDataBase(Context context,String name,int version){
		this(context, name,null,version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		System.out.println("create a Database");
		//学生表
		db.execSQL("CREATE TABLE `user` (`sid` INT NOT NULL,`sname` VARCHAR(20) NULL,`smac` VARCHAR(20) NULL,PRIMARY KEY (`sid`));");
		//班级课程表
		db.execSQL("CREATE TABLE `course` (`class` VARCHAR(20) NOT NULL,`subject` VARCHAR(20) NOT NULL,`monitor` VARCHAR(20) NULL,`classnum` INT NULL,`dep` VARCHAR(20) NULL,PRIMARY KEY (`class`, `subject`));");
		//考勤表
		db.execSQL("CREATE TABLE `check` (`calss` INT NOT NULL,`subject` VARCHAR(20) NOT NULL,`sid` INT NULL,`sgin` INT NULL DEFAULT 0,`unsgin` INT NULL DEFAULT 0,PRIMARY KEY (`calss`, `subject`));");
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		System.out.println("update a Database");
	}
	
}
