package fp.dam.pmdm.contador;

public class zzDataStorage {

    private String datos_username = "username";
    private String datos_password = "password";
    private String datos_num = "datos_num";
    private String datos_mult = "datos_mult";
    private String datos_inc = "datos_inc";
    private String datos_cClick = "datos_cClick";
    private String datos_cAutoC = "datos_cAutoC";
    private String datos_score = "datos_score";

    public zzDataStorage(String datos_username,
                         String datos_password,
                         String datos_num,
                         String datos_mult,
                         String datos_inc,
                         String datos_cClick,
                         String datos_cAutoC,
                         String datos_score) {
        this.datos_username = datos_username;
        this.datos_password = datos_password;
        this.datos_num = datos_num;
        this.datos_mult = datos_mult;
        this.datos_inc = datos_inc;
        this.datos_cClick = datos_cClick;
        this.datos_cAutoC = datos_cAutoC;
        this.datos_score = datos_score;
    }

    // GETTERS Y SETTERS
    // ////////////////////////////////////////////////////////////////////////////////////////////

    public String getDatos_username() {
        return datos_username;
    }

    public void setDatos_username(String datos_username) {
        this.datos_username = datos_username;
    }

    public String getDatos_password() {
        return datos_password;
    }

    public void setDatos_password(String datos_password) {
        this.datos_password = datos_password;
    }

    public String getDatos_num() {
        return datos_num;
    }

    public void setDatos_num(String datos_num) {
        this.datos_num = datos_num;
    }

    public String getDatos_mult() {
        return datos_mult;
    }

    public void setDatos_mult(String datos_mult) {
        this.datos_mult = datos_mult;
    }

    public String getDatos_inc() {
        return datos_inc;
    }

    public void setDatos_inc(String datos_inc) {
        this.datos_inc = datos_inc;
    }

    public String getDatos_cClick() {
        return datos_cClick;
    }

    public void setDatos_cClick(String datos_cClick) {
        this.datos_cClick = datos_cClick;
    }

    public String getDatos_cAutoC() {
        return datos_cAutoC;
    }

    public void setDatos_cAutoC(String datos_cAutoC) {
        this.datos_cAutoC = datos_cAutoC;
    }

    public String getDatos_score() {
        return datos_score;
    }

    public void setDatos_score(String datos_score) {
        this.datos_score = datos_score;
    }
}
