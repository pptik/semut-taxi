package pptik.id.semuttaxi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Profile {

    @SerializedName("ID")
    @Expose
    private Integer iD;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("CountryCode")
    @Expose
    private Integer countryCode;
    @SerializedName("PhoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("Gender")
    @Expose
    private Integer gender;
    @SerializedName("Birthday")
    @Expose
    private String birthday;
    @SerializedName("Joindate")
    @Expose
    private String joindate;
    @SerializedName("Poin")
    @Expose
    private Integer poin;
    @SerializedName("PoinLevel")
    @Expose
    private Integer poinLevel;
    @SerializedName("AvatarID")
    @Expose
    private Integer avatarID;
    @SerializedName("Verified")
    @Expose
    private Integer verified;
    @SerializedName("Visibility")
    @Expose
    private Integer visibility;
    @SerializedName("Reputation")
    @Expose
    private Integer reputation;
    @SerializedName("deposit")
    @Expose
    private Integer deposit;
    @SerializedName("ID_role")
    @Expose
    private Integer iDRole;
    @SerializedName("username")
    @Expose
    private String username;

    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(Integer countryCode) {
        this.countryCode = countryCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getJoindate() {
        return joindate;
    }

    public void setJoindate(String joindate) {
        this.joindate = joindate;
    }

    public Integer getPoin() {
        return poin;
    }

    public void setPoin(Integer poin) {
        this.poin = poin;
    }

    public Integer getPoinLevel() {
        return poinLevel;
    }

    public void setPoinLevel(Integer poinLevel) {
        this.poinLevel = poinLevel;
    }

    public Integer getAvatarID() {
        return avatarID;
    }

    public void setAvatarID(Integer avatarID) {
        this.avatarID = avatarID;
    }

    public Integer getVerified() {
        return verified;
    }

    public void setVerified(Integer verified) {
        this.verified = verified;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }

    public Integer getReputation() {
        return reputation;
    }

    public void setReputation(Integer reputation) {
        this.reputation = reputation;
    }

    public Integer getDeposit() {
        return deposit;
    }

    public void setDeposit(Integer deposit) {
        this.deposit = deposit;
    }

    public Integer getIDRole() {
        return iDRole;
    }

    public void setIDRole(Integer iDRole) {
        this.iDRole = iDRole;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}