package async.crash.com.venuesuite;

import android.media.Image;
import android.widget.ImageView;

/**
 * Created by mitchthornton on 7/3/18.
 * Holds the data for the contact list
 */

public class DataModel {

    private String name;
    private String phoneNumber;
    private String jobRole;
    private Image profile_Image;

    public DataModel(String name, String phoneNumber, String jobRole, Image profile_Image) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.jobRole = jobRole;
        this.profile_Image = profile_Image;
    }

    public DataModel(String name, String phoneNumber, String jobRole) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.jobRole = jobRole;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getJobRole() {
        return jobRole;
    }

    public void setJobRole(String jobRole) {
        this.jobRole = jobRole;
    }

    public Image getProfile_Image() {
        return profile_Image;
    }

    public void setProfile_Image(Image profile_Image) {
        this.profile_Image = profile_Image;
    }



}
