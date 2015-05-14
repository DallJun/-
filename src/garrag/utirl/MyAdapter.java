package garrag.utirl;
/**
 * 自定义的适配器 主要长按弹出勾选框 便于删除
 */
import garrag.shiti.User;
import garrag.view.R;

import java.util.ArrayList;
import java.util.List;

import android.R.bool;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter{

    private Context context;   //上下文
    private LayoutInflater inflater = null;  //打气筒 用于加载xml布局文件
    private List<User> users;  //数据源（所有的学生）
    private static List<Boolean> boolList;  //与学生对应的学生的勾选状态
    private boolean visflag;   //是否显示勾选款
    private boolean isDelete = false;   
    
    public void setIsDelete(boolean flag){
    	isDelete = flag;
    }
	public boolean isVisflag() {
		return visflag;
	}

	public void setVisflag(boolean visflag) {
		this.visflag = visflag;
	}

	public MyAdapter(Context context, List<User> users) {
		super();
		this.context = context;
		this.users = users;
		inflater = LayoutInflater.from(context);
		boolList = new ArrayList<Boolean>();
		initDate();
	}
	
	public MyAdapter(Context context, List<User> users, boolean visflag) {
		super();
		this.context = context;
		this.users = users;
		this.visflag = visflag;
		inflater = LayoutInflater.from(context);
		boolList = new ArrayList<Boolean>();
		initDate();
	}

	private void initDate(){  
        for(int i=0; i<users.size();i++) {  
            getIsSelected().add(false);
        }
    }  
	
	public static List<Boolean> getIsSelected() {  
        return boolList;  
    }  
  
    public static void setIsSelected(List<Boolean> boolList) {  
        MyAdapter.boolList = boolList;  
    }
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return users.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return users.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null ;    
        if(convertView == null)    
        {    
              
            holder = new ViewHolder();  
            convertView  = inflater.inflate(R.layout.lanya_item, null);  
            holder.name = (TextView)convertView.findViewById(R.id.name);
            holder.id = (TextView)convertView.findViewById(R.id.id); 
            holder.mac = (TextView)convertView.findViewById(R.id.mac); 
            holder.cb = (CheckBox)convertView.findViewById(R.id.checkBox1);  
            convertView.setTag(holder);  
              
        }else{  
            holder = (ViewHolder) convertView.getTag();  
        }
        User u = users.get(position);
        holder.name.setText(u.getName());
        holder.id.setText(u.getId());
        holder.mac.setText(u.getMac());
        holder.cb.setChecked(boolList.get(position));    
        if(isDelete){
        	 holder.cb.setVisibility(View.VISIBLE);    
        }
        else{
	        if(visflag)    
	        {    
	            holder.cb.setVisibility(View.VISIBLE);    
	        }    
	        else    
	        {    
	            holder.cb.setVisibility(View.INVISIBLE);    
	        } 
        }
        
        return convertView;    
    }    
	
	
}

	
