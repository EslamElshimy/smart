package pan.smart.smart.adapters;


/**
 * Created by anupamchugh on 09/02/16.
 */
public class searchModelRecycler {


    private String CountryName;
    private String Capital;
    private int data;

    public searchModelRecycler(String countryName, String captial, int data) {
        this.CountryName = countryName;
        this.Capital     = captial;
    }


    public String getCountryName() {
        return CountryName;
    }

    public void setCountryName(String countryName) {
        CountryName = countryName;
    }

    public String getCapital() {
        return Capital;
    }

    public void setCapital(String capital) {
        Capital = capital;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }







}
