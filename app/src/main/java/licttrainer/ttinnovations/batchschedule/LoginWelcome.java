package licttrainer.ttinnovations.batchschedule;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class LoginWelcome extends AppCompatActivity implements View.OnClickListener {
    private EditText editText;
    private Button loginwelcomebutton, logoutButton;
    private Spinner spinner;
    private String url = "http://27.147.210.136:8080/bht/UserLogin?userlogin=";
    private ListView list;

    //http://27.147.210.136:8080/bht/UserLogin?userlogin=tarkeshwar.barua
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_welcome);

        // spinner= (Spinner) findViewById(R.id.idspinner_batch_info);
        // loginwelcomebutton= (Button) findViewById(R.id.id_loginintosession);
        // loginwelcomebutton.setOnClickListener(this);
        logoutButton = (Button) findViewById(R.id.clear_sharedpreference);
        list = (ListView) findViewById(R.id.schedulelist);

        logoutButton.setOnClickListener(this);

        SharedPreferences myshared = getSharedPreferences("myshared", MODE_PRIVATE);
        String getname = myshared.getString("getname", null);
        String getpassword = myshared.getString("getpassword", null);
        try {
            String s = new Connect().execute(url + getname).get();
            System.out.println("response from Server - " + s);
            BatchSchedule[] batchSchedules = new ObjectMapper().readValue(s, BatchSchedule[].class);
            List<String> batchcode = new ArrayList<String>();
            List<Date> date = new ArrayList<Date>();
            List<String> status = new ArrayList<String>();
            List<String> trainer = new ArrayList<String>();
            List<String> university = new ArrayList<String>();
            List<Long> id = new ArrayList<Long>();
            for (int i = 0; i < batchSchedules.length; i++) {
                batchcode.add(batchSchedules[i].getBatchcode());
                date.add(batchSchedules[i].getDate());
               setAlarm(batchSchedules[i].getDate().getTime()-(1000*60*30));
                status.add(batchSchedules[i].getStatus());
                trainer.add(batchSchedules[i].getTrainer());
                university.add(batchSchedules[i].getUniversity());
                id.add(batchSchedules[i].getId());
            }
            Long[] idA = new Long[batchcode.size()];
            String[] batchcodeA = new String[batchcode.size()], dateA = new String[batchcode.size()], statusA = new String[batchcode.size()], trainerA = new String[batchcode.size()], universityA = new String[batchcode.size()];
            for (int j = 0; j < batchcode.size(); j++) {
                batchcodeA[j] = batchcode.get(j);
                dateA[j] = date.get(j).toString();
                statusA[j] = status.get(j);
                trainerA[j] = trainer.get(j);
                universityA[j] = university.get(j);
                idA[j] = id.get(j);
            }
            list.setAdapter(new CustomAdapter(this, idA, universityA, batchcodeA, statusA));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*editText.setText(getname);
        First way to get data from previous Activity !!
        Intent intent = getIntent();
        String mydata = intent.getStringExtra("mydata");
        System.out.println(mydata);
        editText.setText(mydata);*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.clear_sharedpreference:
                SharedPreferences preferences = getSharedPreferences("myshared", 0);
                preferences.edit().remove("getname").commit();
                preferences.edit().remove("getpassword").commit();
                break;



                /*Intent intent = getIntent();
                String mydata = intent.getStringExtra("mydata");
                editText.setText(mydata);
                Toast.makeText(getBaseContext(),"Reload is Clicked !!",Toast.LENGTH_LONG).show();*/
        }
    }
    public void setAlarm(long date){

        Intent intent = new Intent(this, AlarmReceiver.class);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP,date, PendingIntent.getBroadcast(this,1,intent,PendingIntent.FLAG_UPDATE_CURRENT));


    }

}






