package auspost.com.au.hackday.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User implements Parcelable {
    public String name;
    public String phone;
    public String email;
    public Date dateOfBirth;
    public String driverLicense;
    public String passportNumber;
    public String verificationPercentage;
    public String changeAddress;
    public List<String> addresses = new ArrayList<>();

    public User() {

    }

    public User(String name, String phone, String email, Date dateOfBirth, String driverLicense, String passportNumber, List<String> addresses, String verificationPercentage, String changeAddress) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
        this.driverLicense = driverLicense;
        this.passportNumber = passportNumber;
        this.addresses = addresses;
        this.verificationPercentage = verificationPercentage;
        this.changeAddress = changeAddress;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(phone);
        dest.writeString(email);
        long dob = dateOfBirth == null ? -1 : dateOfBirth.getTime();
        dest.writeLong(dob);
        dest.writeString(driverLicense);
        dest.writeString(passportNumber);
        dest.writeString(verificationPercentage);
        dest.writeString(changeAddress);
        dest.writeList(addresses);
    }

    public static final Parcelable.Creator<User> CREATOR
            = new Parcelable.Creator<User>() {

        @Override
        public User createFromParcel(Parcel source) {
            String name = source.readString();
            String phone = source.readString();
            String email = source.readString();
            long dobLong = source.readLong();
            Date dob = dobLong != -1 ? new Date(dobLong) : null;
            String dl = source.readString();
            String pn = source.readString();
            String ver = source.readString();
            String chadd = source.readString();
            ArrayList<String> addresses = new ArrayList<>();
            source.readList(addresses, null);
            User user = new User(name, email, phone, dob, dl, pn, addresses, ver, chadd);
            return user;
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
