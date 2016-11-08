package innodevs.dentistulmeu;

/**
 * Created by vlad.butnaru on 9/19/2016.
 */
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.support.v4.app.ActivityCompat.startActivity;

public class CustomAdapter extends BaseAdapter{
    String [] result;
    Context context;
    ArrayList<Bitmap> imageId;
    String [] ratings;
    private static LayoutInflater inflater=null;

    public CustomAdapter(MainActivity mainActivity, String[] prgmNameList, String[] ratings , ArrayList<Bitmap> images) {
        // TODO Auto-generated constructor stub
        result=prgmNameList;
        context=mainActivity;
        imageId=images;
        this.ratings = ratings;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView tv;
        TextView tvSmall;
        ImageView img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.program_list, null);
        holder.tv=(TextView) rowView.findViewById(R.id.textView1);
        holder.tvSmall=(TextView) rowView.findViewById(R.id.textView2);
        holder.img=(ImageView) rowView.findViewById(R.id.imageView1);
        holder.tv.setText(result[position]);
        holder.tvSmall.setText(ratings[position]);
        holder.img.setImageBitmap(imageId.get(position));
        rowView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "You Clicked "+result[position], Toast.LENGTH_LONG).show();
                Intent i = new Intent(context,CabinetViewActivity.class);
                i.putExtra("cabinetName",result[position]);
              context.startActivity(i);
            }
        });
        return rowView;
    }

}