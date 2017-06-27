package com.hung.alarmclock.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import com.hung.alarmclock.R;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class AlarmAdapter extends ArrayAdapter {
    private Context context;
    private int resource;
    private List<Long> arrayAlarm = new ArrayList<>();

    public AlarmAdapter(Context context, int resource, List<Long> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.arrayAlarm = objects;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.alarm_row, parent, false);
            viewHolder.alarmView = (TextView) convertView.findViewById(R.id.alarm_view);
            viewHolder.alarmPosition = (TextView) convertView.findViewById(R.id.alarm_position);
            viewHolder.btn_delete =(Button) convertView.findViewById(R.id.delete);
            convertView.setTag(viewHolder);


        } else {
            viewHolder = (ViewHolder) convertView.getTag();

        }

        Long t = arrayAlarm.get(position);
        Date d = new Date(t);
        SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        String s = formater.format(d);
        viewHolder.alarmPosition.setText(String.valueOf((position + 1)));
        viewHolder.alarmView.setText("Alarm Date-Time "+"\n"+ s);

        viewHolder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayAlarm.remove(position);
                notifyDataSetChanged();
            }
        });


        return convertView;
    }

    public class ViewHolder {
        TextView alarmView;
        Button btn_delete;
        TextView alarmPosition;

    }
}
