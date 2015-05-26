package auspost.com.au.hackday.persistence;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Set attributes order
 * name,
 * email,
 * phone,
 * dob,
 * driver license,
 * passport number,
 * address1:address2:address3...etc
 */
public class DatabaseManager {

    public static final String NAME = "name";
    public static final String EMAIL = "email";
    public static final String PHONE = "phone";
    public static final String DOB = "dob";
    public static final String DL = "dl";
    public static final String PN = "pn";
    public static final String ADS = "ads";
    public static final String PWD = "pwd";
    public static final String VER = "ver";
    private final SharedPreferences database;

    public DatabaseManager(Context context) {
        database = context.getSharedPreferences("APDM", Context.MODE_PRIVATE);
    }

    public void save(String username, Map<String, String> values) {
        safePutValue(username, NAME, values.get(NAME));
        safePutValue(username, EMAIL, values.get(EMAIL));
        safePutValue(username, PHONE, values.get(PHONE));
        safePutValue(username, DOB, values.get(DOB));
        safePutValue(username, DL, values.get(DL));
        safePutValue(username, PN, values.get(PN));
        safePutValue(username, ADS, values.get(ADS));
        safePutValue(username, PWD, values.get(PWD));
        safePutValue(username, VER, values.get(VER));
    }

    public String getName(String username) {
        return database.getString(username + "_" + NAME, null);
    }

    public String getEmail(String username) {
        return database.getString(username + "_" + EMAIL, null);
    }

    public String getPhone(String username) {
        return database.getString(username + "_" + PHONE, null);
    }

    public Date getDateOfBirth(String username) {
        Date date = null;
        String dob = database.getString(username + "_" + DOB, null);
        if (dob != null && !dob.trim().equals("")) {
            date = new Date(new Long(dob));
        }
        return date;
    }

    public String getDriverLicenseNumber(String username) {
        return database.getString(username + "_" + DL, null);
    }

    public String getVerificationPercentage(String username) {
        return database.getString(username + "_" + VER, null);
    }

    public String getPassportNumber(String username) {
        return database.getString(username + "_" + PN, null);
    }

    public String getPassword(String username) {
        return database.getString(username + "_" + PWD, null);
    }

    public List<String> getAddresses(String username) {
        List<String> addresses = null;
        String addressesStr = database.getString(username + "_" + ADS, null);
        if (addressesStr != null && !addressesStr.trim().equals("")) {
            addresses = Arrays.asList(addressesStr.split(":"));
        }
        return addresses;
    }

    public Map<String, Object> getUser(String username) {
        Map<String, Object> user = new HashMap<>();
        user.put(NAME, getName(username));
        user.put(EMAIL, getEmail(username));
        user.put(PHONE, getPhone(username));
        user.put(DOB, getDateOfBirth(username));
        user.put(DL, getDriverLicenseNumber(username));
        user.put(PN, getPassportNumber(username));
        user.put(ADS, getAddresses(username));
        user.put(VER, getVerificationPercentage(username));
        return user;
    }

    private void safePutValue(String key, String attr, String value) {
        if (value != null && !value.trim().equals("")) {
            database.edit().putString(key + "_" + attr, value).apply();
        }
    }
}
