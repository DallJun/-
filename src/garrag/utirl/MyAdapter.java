package garrag.utirl;

import garrag.shiti.User;
import garrag.view.R;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter{

    private Context context;  
    private LayoutInflater inflater = null; 
    private List<User> users;
    private static List<Boolean> boolList;
    private boolean visflag;
    
    
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
            
        if(visflag)    
        {    
            holder.cb.setVisibility(View.VISIBLE);    
        }    
        else    
        {    
            holder.cb.setVisibility(View.INVISIBLE);    
        }    
        
        return convertView;    
    }    
	
	
}

	
