package auspost.com.au.hackday;

/**
 * Created by reidj2 on 26/05/2015.
 */
public class Service {

    public String name;
    public String status;
    public Boolean active;

    public Service(String name, String status, boolean active) {
        this.name = name;
        this.status = status;
        this.active = active;
    }
}
