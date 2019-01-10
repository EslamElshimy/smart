package pan.smart.smart.Register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import pan.smart.smart.Login.loginActivity;
import pan.smart.smart.R;
import pan.smart.smart.Main.main;

public class RegisterActivity extends AppCompatActivity implements RegisterInterface{


    EditText name;
    EditText userName;
    EditText phone;
    EditText email;
    EditText password;
    RadioButton male;
    RadioButton female;
    Button signUP;
    TextView login_txt;
    RegisterPresenter registerPresenter;
    int gender = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name          =  findViewById(R.id.name_txt);
        userName      =  findViewById(R.id.userName_txt);
        phone         =  findViewById(R.id.phone);
        email         =  findViewById(R.id.email_txt);
        password      =  findViewById(R.id.Pass_txt);
        male          =  findViewById(R.id.radioM);
        female        =  findViewById(R.id.radioF);
        signUP        =  findViewById(R.id.create);
        login_txt     =  findViewById(R.id.login);

        registerPresenter  = new RegisterPresenter(RegisterActivity.this);

        signUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name         = name.getText().toString();
                String user_name    = userName.getText().toString();
                String phoneNumber  = phone.getText().toString();
                String Email        = email.getText().toString();
                String pass         = password.getText().toString();

                if(registerPresenter.validation(Name,user_name,phoneNumber,Email,pass))
                {
                    Intent intent = new Intent(RegisterActivity.this, main.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                    finish();
                }

            }
        });

        login_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, loginActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean validation(String Name, String userName, String phone, String email, String password) {
        return true;
    }
}
