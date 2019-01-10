package pan.smart.smart.Register;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import pan.smart.smart.Login.loginInterface;
import pan.smart.smart.Utills.Constant;
import pan.smart.smart.Utills.Validation;

import static android.content.Context.MODE_PRIVATE;

public class RegisterPresenter implements RegisterInterface {

    Context context;
    public SharedPreferences mSharedPreferences;

    public RegisterPresenter(Context context)
    {
        this.context = context;

        mSharedPreferences  =   context.getSharedPreferences("tokenDetail",MODE_PRIVATE);

    }




    @Override
    public boolean validation(String Name, String userName, String phone, String email, String password) {
        if (!Validation.validateFields(Name))
        {

            Toast.makeText(context, "please Enter Name", Toast.LENGTH_LONG).show();
            return false;
        }
        else if (!Validation.validateFields(userName))
            {
                Toast.makeText(context, "please Enter userName", Toast.LENGTH_LONG).show();
                return false;
            }
        else if (!Validation.validateFields(phone))
        {
            Toast.makeText(context, "please Enter phone Number", Toast.LENGTH_LONG).show();
            return false;
        }

        else if (!Validation.validateFields(email))
        {
            Toast.makeText(context, "please Enter Email", Toast.LENGTH_LONG).show();
            return false;
        }
        else if (!Validation.validateFields(password))
            {
                Toast.makeText(context, "please Enter password", Toast.LENGTH_LONG).show();
                return false;
            }

        else
        {

            SharedPreferences.Editor editor_Name = mSharedPreferences.edit();
            editor_Name.putString(Constant.Name, Name);
            editor_Name.apply();


            SharedPreferences.Editor editor_UserName = mSharedPreferences.edit();
            editor_UserName.putString(Constant.Username, userName);
            editor_UserName.apply();

            SharedPreferences.Editor editor_mobile = mSharedPreferences.edit();
            editor_mobile.putString(Constant.mobile, phone);
            editor_mobile.apply();

            SharedPreferences.Editor editor_User_Email = mSharedPreferences.edit();
            editor_User_Email.putString(Constant.Useremail, email);
            editor_User_Email.apply();

            SharedPreferences.Editor editor_password= mSharedPreferences.edit();
            editor_password.putString(Constant.password,password);
            editor_password.apply();

            return true;
        }

    }
}
