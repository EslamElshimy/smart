package pan.smart.smart.Models;

public class Translations {
    private String de;

    private String it;

    private String fr;

    private String es;

    private String ja;

    public String getDe ()
    {
        return de;
    }

    public void setDe (String de)
    {
        this.de = de;
    }

    public String getIt ()
    {
        return it;
    }

    public void setIt (String it)
    {
        this.it = it;
    }

    public String getFr ()
    {
        return fr;
    }

    public void setFr (String fr)
    {
        this.fr = fr;
    }

    public String getEs ()
    {
        return es;
    }

    public void setEs (String es)
    {
        this.es = es;
    }

    public String getJa ()
    {
        return ja;
    }

    public void setJa (String ja)
    {
        this.ja = ja;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [de = "+de+", it = "+it+", fr = "+fr+", es = "+es+", ja = "+ja+"]";
    }
}

