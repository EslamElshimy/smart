package pan.smart.smart.Login;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import pan.smart.smart.Utills.Constant;
import pan.smart.smart.Utills.Validation;

import static android.content.Context.MODE_PRIVATE;

public class loginPresenter implements loginInterface {

    Context context;
    public SharedPreferences mSharedPreferences;


    public loginPresenter(Context context)
    {
        this.context        = context;
        mSharedPreferences  =   context.getSharedPreferences("tokenDetail",MODE_PRIVATE);

    }


    @Override
    public boolean validation(String userName, String password)
    {
        if (!Validation.validateFields(userName))
        {

            Toast.makeText(context, "please Enter UserName", Toast.LENGTH_LONG).show();
            return false;
        }
        else if (!Validation.validateFields(password)) {

            {
                Toast.makeText(context, "please Enter password", Toast.LENGTH_LONG).show();
                return false;
            }

        }

        else
        {

            if(userName.equals(mSharedPreferences.getString(Constant.Username,""))&&password.equals(mSharedPreferences.getString(Constant.password,"")))
            {
                return true;
            }
            else
            {
                Toast.makeText(context, "User Name or Password incorrect", Toast.LENGTH_LONG).show();

                return false;
            }


        }
    }
}
