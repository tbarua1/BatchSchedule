package licttrainer.ttinnovations.batchschedule;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static android.graphics.Color.BLUE;
import static android.graphics.Color.GREEN;
import static android.graphics.Color.MAGENTA;
import static android.graphics.Color.RED;


/**
 * Created by Abhishek.Sehgal on 15-03-2017.
 */

public class CustomAdapter extends BaseAdapter implements View.OnClickListener {
    private String url = "http://27.147.210.136:8080/bht/UpdateStatus?data=";
    String[] result, universities, batchcode, status;
    Context context;
    int[] imageId;
    private static LayoutInflater inflater = null;
    private Long[] ida;

    public CustomAdapter(Context mainActivity, Long[] idA, String[] university, String[] batchcode, String[] status) {
        //result = date;
        this.ida = idA;
        this.universities = university;
        this.batchcode = batchcode;
        this.status = status;
        context = mainActivity;
        //  imageId=prgmImages;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public int getCount() {
        return batchcode.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.activity_main, null);
        // holder.tv = (TextView) rowView.findViewById(R.id.date);
        holder.university = (TextView) rowView.findViewById(R.id.university);
        holder.tv = (TextView) rowView.findViewById(R.id.batch_code);
        holder.img = (Button) rowView.findViewById(R.id.status);
        holder.cancleBatch = (Button) rowView.findViewById(R.id.cancle);
        holder.resiontoCancle=(EditText) rowView.findViewById(R.id.whycancle);
      //  holder.resiontoCancle.setVisibility(View.INVISIBLE);
        holder.cancleBatch.setBackgroundColor(RED);
        holder.tv.setText(universities[position]);
        holder.university.setText(batchcode[position]);
        holder.img.setText(status[position]);
        if (status[position].equalsIgnoreCase("SCHEDULED")) {
            holder.img.setBackgroundColor(Color.YELLOW);
        }
        holder.cancleBatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BatchSchedule batchSchedule = new BatchSchedule();
                batchSchedule.setId(ida[position]);
                batchSchedule.setStatus("Cancled");

               batchSchedule.setBatchcode(holder.resiontoCancle.getText().toString());
                try {
                    String s = new ObjectMapper().writeValueAsString(batchSchedule);
                    System.out.println("data to write - "+s);
                    String s1 = new Connect().execute(url + s).get();
                    System.out.println("Response from Server - " + s1);
                    holder.cancleBatch.setBackgroundColor(BLUE);
                    holder.cancleBatch.setText("Cancled");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context,"Welcome",Toast.LENGTH_LONG).show();
                if (status[position].equalsIgnoreCase("Scheduled")) {
                    BatchSchedule batchSchedule = new BatchSchedule();
                    batchSchedule.setId(ida[position]);
                    batchSchedule.setStatus("Started");
                    try {
                        String s = new ObjectMapper().writeValueAsString(batchSchedule);
                        String s1 = new Connect().execute(url + s).get();
                        System.out.println("Response from Server - " + s1);
                        holder.img.setBackgroundColor(GREEN);
                        holder.img.setText("Started");

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
                if (status[position].equalsIgnoreCase("Started")) {
                    BatchSchedule batchSchedule = new BatchSchedule();
                    batchSchedule.setId(ida[position]);
                    batchSchedule.setStatus("Stopped");
                    try {
                        String s = new ObjectMapper().writeValueAsString(batchSchedule);
                        String s1 = new Connect().execute(url + s).get();
                        System.out.println("Response from Server - " + s1);
                        holder.img.setBackgroundColor(MAGENTA);
                        holder.img.setText("Stopped");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
               /* Intent intent = getIntent();
                finish();
                startActivity(intent);*/
            }
        });
        /*rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "You Clicked " + result[position], Toast.LENGTH_LONG).show();
            }
        });*/
        return rowView;
    }

    public class Holder {
        TextView tv, university, batchcode;
        Button img,cancleBatch;
        EditText resiontoCancle;
    }
}
