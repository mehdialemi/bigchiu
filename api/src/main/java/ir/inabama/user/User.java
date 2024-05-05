package ir.inabama.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Strings;
import ir.inabama.auth.DateAudit;
import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
            "username"
        }),
        @UniqueConstraint(columnNames = {
            "email"
        })
})
public class User extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 100)
    private String name;

    @NaturalId
    @NotBlank
    @Size(max = 100)
    private String username;

    @Size(max = 100)
    @Email
    private String email;

    @Size(max = 100)
    @JsonIgnore
    private String password;

    private String address;

    @Size(max = 20)
    private String phone;

    @Size(max = 20)
    private String mobile;

    @Column(name = "account_type")
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Column(name = "user_type")
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Column(name = "national_code")
    @Size(max = 10)
    private String nationalCode;

    @Column(name = "register_image_url")
    @Size(max = 20971520)
    private String registerImageUrl;

    @Column(name = "register_number")
    @Size(max = 10)
    private String registerNumber;

    @URL
    private String website;

    @Column(name = "business_code")
    @Size(max = 20)
    private String businessCode;

    @Column(name = "company_name")
    @Size(max = 100)
    private String companyName;

    @Column(name = "free_credit")
    private Long freeCredit = 0L;

    @Column(name = "mobile_confirmed")
    private Boolean mobileConfirmed = false;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User() {

    }

    public User(String username) {
        this.username = username;
    }

    public void update(UserProfile userProfile) {
        this.userType = userProfile.getUserType();
        this.accountType = userProfile.getAccountType();

        if (!Strings.isNullOrEmpty(userProfile.getName())) {
            this.name = userProfile.getName();
        }

        if (!Strings.isNullOrEmpty(userProfile.getEmail())) {
            this.email = userProfile.getEmail();
        }

        if (!Strings.isNullOrEmpty(userProfile.getAddress())) {
            this.address = userProfile.getAddress();
        }

        if (!Strings.isNullOrEmpty(userProfile.getPhone())) {
            this.phone = userProfile.getPhone();
        }

        if (!Strings.isNullOrEmpty(userProfile.getMobile())) {
            this.mobile = userProfile.getMobile();
        }

        if (!Strings.isNullOrEmpty(userProfile.getNationalCode())) {
            this.nationalCode = userProfile.getNationalCode();
        }

        if (!Strings.isNullOrEmpty(userProfile.getRegisterImageUrl())) {
            this.registerImageUrl = userProfile.getRegisterImageUrl();
        }

        if (userProfile.getUserType() == UserType.COMPANY && !Strings.isNullOrEmpty(userProfile.getRegisterNumber())) {
            this.registerNumber = userProfile.getRegisterNumber();
        }

        if (!Strings.isNullOrEmpty(userProfile.getWebsite())) {
            this.website = userProfile.getWebsite();
        }

        if (userProfile.getUserType() == UserType.COMPANY && !Strings.isNullOrEmpty(userProfile.getBusinessCode())) {
            this.businessCode = userProfile.getBusinessCode();
        }

        if (!Strings.isNullOrEmpty(userProfile.getCompanyName())) {
            this.businessCode = userProfile.getBusinessCode();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRegisterNumber() {
        return registerNumber;
    }

    public void setRegisterNumber(String registerNumber) {
        this.registerNumber = registerNumber;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Long getFreeCredit() {
        return freeCredit;
    }

    public void setFreeCredit(long freeCredit) {
        this.freeCredit = freeCredit;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public String getRegisterImageUrl() {
        return registerImageUrl;
    }

    public void setNationalCodeImage(String registerImageUrl) {
        this.registerImageUrl = registerImageUrl;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Boolean getMobileConfirmed() {
        return mobileConfirmed;
    }

    public void setMobileConfirmed(Boolean mobileConfirmed) {
        this.mobileConfirmed = mobileConfirmed;
    }
}