package pan.smart.smart.Utills;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.google.gson.Gson;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import pan.smart.smart.R;


public class Constant {

    public static String tabSelected = "";

    public static final String BASE_URL_HTTP ="https://restcountries.eu/rest/v1/";


    public static final String counteryID = "CountryID";
    public static final String counteryName = "1";
    public static final String counterylogo = "counterylogo";
    public static final String cityID = "CityID";
    public static final String cityName = "City_name";
    public static final String citylogo = "citylogo";
    public static final String Name = "Name";
    public static final String MerchantID = "MerchantID";
    public static final String BranchAR = "BranchAR";
    public static final String lat = "lat";
    public static final String lng = "lng";
    public static final String BranchEN = "BranchEN";
    public static final String Username = "Username";
    public static final String Useremail = "Usersemail";
    public static final String mobile = "mobile";
    public static final String password = "password";
    public static final String Activationcode = "Activationcode";
    public static final String Activationstate = "Activationstate";
    public static final String expiredate = "expiredate";
    public static final String gender = "gender";
    public static final String birthdate = "birthdate";
    public static final String nationalityid = "nationalityid";
    public static final String imageUri = "imageUri";
    public static final String status = "status";
    public static final String nationalitynameen = "nationalitynameen";
    public static final String nationalitynamear = "nationalitynamear";
    public static final String language = "Language";
    public static String parseXML(String response) {
        String json="";
        DocumentBuilder builder = null;
        try {
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource src = new InputSource();
            src.setCharacterStream(new StringReader(response));

            Document doc = builder.parse(src);

            json = doc.getElementsByTagName("string").item(0).getTextContent();
            Log.i("1", json);
            Gson gson = new Gson();
            // Toast.makeText(getApplicationContext(),likeModel.getResultMsg(),Toast.LENGTH_SHORT).show();


        } catch (ParserConfigurationException e) {


            e.printStackTrace();
        } catch (SAXException e) {


            e.printStackTrace();
        } catch (IOException e) {


            e.printStackTrace();
        }
        return json;
    }


    public static boolean isRTL(Locale locale) {
        final int directionality = Character.getDirectionality(locale.getDisplayName().charAt(0));
        return directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT ||
                directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC;
    }
    public static boolean isLTR(Locale locale) {
        final int directionality = Character.getDirectionality(locale.getDisplayName().charAt(0));
        return directionality == Character.DIRECTIONALITY_LEFT_TO_RIGHT||
                directionality == Character.DIRECTIONALITY_LEFT_TO_RIGHT_EMBEDDING;
    }


    public static AlertDialog.Builder buildDialog(final Activity c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet connection.");
        builder.setMessage("You have no internet connection");
        builder.setMessage("please check internet connection");
        builder.setIcon(R.drawable.wifi_ic);
        builder.setCancelable(false);

        builder.setPositiveButton("Reload", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                //Toast.makeText(c, "please check internet connection", Toast.LENGTH_LONG).show();
                //builder.show();
                c.finish();
                c.startActivities(new Intent[]{c.getIntent()});
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                c.finish();
            }
        });
        return builder;
    }




}
