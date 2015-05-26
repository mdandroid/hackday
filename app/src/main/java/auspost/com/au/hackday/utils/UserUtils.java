package auspost.com.au.hackday.utils;

import auspost.com.au.hackday.model.User;
import auspost.com.au.hackday.persistence.DatabaseManager;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class UserUtils {

    public static User toUser(Map<String, Object> userMap) {
        User user = new User();
        user.name = (String) userMap.get(DatabaseManager.NAME);
        user.email = (String) userMap.get(DatabaseManager.EMAIL);
        user.phone = (String) userMap.get(DatabaseManager.PHONE);
        user.dateOfBirth = (Date) userMap.get(DatabaseManager.DOB);
        user.driverLicense = (String) userMap.get(DatabaseManager.DL);
        user.passportNumber = (String) userMap.get(DatabaseManager.PN);
        user.addresses = (List<String>) userMap.get(DatabaseManager.ADS);
        return user;
    }
}
