package cn.jxzhang.server.campushelper.pojo;

/**
 * Created on 2017-03-16 11:29
 * <p>Title:       User</p>
 * <p>Description: [Description]</p>
 *
 * @author <a href="mailto:zhangjiaxing@ultrapower.com.cn">zhangjiaxing</a>
 * @version 1.0
 */
public class User {

    private String userId;
    private String identityType;
    private String identifier;
    private String credential;
    private String isVerified;
    private String isThirdPartyAccount;

    private String accountName;
    private String email;
    private String phone;

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", identityType='" + identityType + '\'' +
                ", identifier='" + identifier + '\'' +
                ", credential='" + credential + '\'' +
                ", isVerified='" + isVerified + '\'' +
                ", isThirdPartyAccount='" + isThirdPartyAccount + '\'' +
                ", accountName='" + accountName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    public String getIsThirdPartyAccount() {
        return isThirdPartyAccount;
    }

    public void setIsThirdPartyAccount(String isThirdPartyAccount) {
        this.isThirdPartyAccount = isThirdPartyAccount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIdentityType() {
        return identityType;
    }

    public void setIdentityType(String identityType) {
        this.identityType = identityType;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getCredential() {
        return credential;
    }

    public void setCredential(String credential) {
        this.credential = credential;
    }

    public String getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(String isVerified) {
        this.isVerified = isVerified;
    }
}
