package licttrainer.ttinnovations.batchschedule;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener
{
    private String loginURL="http://27.147.210.136:8080/bht/UserLogin";
    private Button button1,button2;
    private EditText logineditTextname,logineditTextpassword;
    //private DataAccess dataAccess;
    private String name,password,tarunshareddata;
    private  SharedPreferences sharedPreferences;
    private Long[] id;
    private Date[] date;
    private String[] university;
    private String[] Status,trainer,batchcode;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        logineditTextname= (EditText) findViewById(R.id.id_name);
        logineditTextpassword= (EditText) findViewById(R.id.id_password);

        sharedPreferences = getSharedPreferences("myshared",MODE_PRIVATE);
        name = sharedPreferences.getString("getname", null);
        password=sharedPreferences.getString("getpassword",null);

        if(name!=null || password!=null)
        {
            logineditTextname.setText(name);
            logineditTextpassword.setText(password);
            GetSetTrainer trainer=new GetSetTrainer();
            trainer.setUsername(name);
            trainer.setPassword(password);
            ObjectMapper mapper=new ObjectMapper();

            try {
                String s1 = mapper.writeValueAsString(trainer);
                String s = new Connect().execute(loginURL+s1).get();
                System.out.println("Response from Server - "+s);
                mapper.readValue(s, BatchSchedule[].class);
                if(s.equalsIgnoreCase("Done")){
                    Toast.makeText(getApplicationContext(),"Login Successfully",Toast.LENGTH_LONG).show();
                }
                System.out.println("Response from Server "+s);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (JsonGenerationException e) {
                e.printStackTrace();
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Intent intent=new Intent(getApplicationContext(),LoginWelcome.class);
            //intent.putExtra("mydata",name);
            startActivity(intent);
            Toast.makeText(getBaseContext(),"Going to Next Activity!!",Toast.LENGTH_SHORT).show();
            finish();

        }

        name = logineditTextname.getText().toString();
        password=logineditTextname.getText().toString();

        button1= (Button) findViewById(R.id.id_login);
        button1.setOnClickListener(this);
        /*button2= (Button) findViewById(R.id.id_signup);
        button2.setOnClickListener(this);*/
    }

    @Override
    public void onClick(View v)
    {

            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putString("getname",logineditTextname.getText().toString());
            edit.putString("getpassword",logineditTextpassword.getText().toString());
            edit.commit();

            name = logineditTextname.getText().toString();

            if(v.getId()==R.id.id_login)
            {
            //dataAccess=new DataAccess();
            //dataAccess.setName(logineditTextname.getText().toString());
            //dataAccess.setPassword(logineditTextname.getText().toString());

                Intent intent=new Intent(getApplicationContext(),LoginWelcome.class);
                //intent.putExtra("mydata",name);
                startActivity(intent);
                Toast.makeText(getBaseContext(),"Going to Next Activity!!",Toast.LENGTH_SHORT).show();
                finish();

            }

            /*if(v.getId()==R.id.id_signup)
            {
            Intent intent=new Intent(getApplicationContext(),SiguUpActivity.class);
            startActivity(intent);
            Toast.makeText(LoginActivity.this,"You'r Going to SignUP !!",Toast.LENGTH_LONG).show();
                finish();
            }*/
        }
}


