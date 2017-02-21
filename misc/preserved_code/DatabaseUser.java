package com.hp.ccue.identity.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hp.ccue.identity.domain.persistence.PersistedObject;
import com.hp.ccue.identity.utilities.PasswordBuilder;

import org.apache.commons.codec.binary.Base64;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.memory.UserAttribute;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = DatabaseUser.TableName)
public class DatabaseUser extends PersistedObject implements UserDetails, PasswordEncoder {


    static final String HASH_ALGORITHM = "SHA-256";
    static final String TableName = "DATABASE_USER";

    public static final String NameAttribute="name";
    public static final String PasswordAttribute="password";
    public static final String SaltAttribute="salt";
    public static final String EnabledAttribute="enabled";
    public static final String AuthoritiesStrAttribute="authoritiesStr";
    public static final String AttributeStr = "attributeStr";
    public static final String OrganizationAttribute="organization";
    public static final String passwordRenewDateAttribute="password_renew_date";
    public static final String passwordExpiredDateAttribute="password_expired_date";
    public static final String failedLoginCounterCol="failed_login_counter";
    public static final String lockedDateCol="locked_date";
    public static final String autoUnlockedCounterCol="auto_unlocked_counter";


    private String name;
    private String password;
    private String salt;
    private String authoritiesStr;
    private boolean enabled = true;
    private String organization;
    private Date passwordRenewDate;
    private Date passwordExpiredDate;
    private Date lockedDate;
    private int failedLoginCounter;
    private int autoUnlockedCounter;

    /**
     * MetaData String Key-Values
     */
    private Set<DatabaseUserMetadata> metadata = new LinkedHashSet<>();

    private DatabaseUserType type;

    public DatabaseUser() {
        setType(DatabaseUserType.SEEDED_USER);
    }

    public DatabaseUser(String organization, String name, String plainPassword) {
        this(organization, name, plainPassword, DatabaseUserType.SEEDED_USER);
    }

    public DatabaseUser(String organization, String name, String plainPassword, DatabaseUserType type) {
        setOrganization(organization);
        setName(name);
        setSalt(this.generateSaltString());
        setPassword(this.encode(plainPassword));
        setType(type);
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    public DatabaseUserType getType() {
        return type;
    }

    public void setType(DatabaseUserType type) {
        this.type = type;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * get Metadata for the user
     *
     * @return {@link Map} metadata for the user
     */
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "metadataKeyValue.user", orphanRemoval=true)
    @Cascade(CascadeType.ALL)
    @JsonIgnore
    public Set<DatabaseUserMetadata> getMetadata() {
        return metadata;
    }

    /**
     * Set DatabaseUser metadata
     *
     * @param metadata Set of new metadatas
     */
    @JsonIgnore
    public void setMetadata(Set<DatabaseUserMetadata> metadatas) {
        for (DatabaseUserMetadata metadata : metadatas) {
            metadata.setUser(this);
        }
        this.metadata = metadatas;
    }

    @Column(name = passwordExpiredDateAttribute)
    public Date getPasswordExpiredDate() {
        return passwordExpiredDate;
    }

    public void setPasswordExpiredDate(Date passwordExpiredDate) {
        this.passwordExpiredDate = passwordExpiredDate;
    }

    /**
     * Returns the authorities granted to the user. Cannot return <code>null</code>.
     *
     * @return the authorities, sorted by natural key (never <code>null</code>)
     */
    @Override
    @Transient
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (getAuthoritiesStr()==null || getAuthoritiesStr().trim().length()==0) return null;
        List<String> authoritiesAsStrings = Arrays.asList(getAuthoritiesStr().split(","));
        UserAttribute userAttrib = new UserAttribute();
        userAttrib.setAuthoritiesAsString(authoritiesAsStrings);
        return userAttrib.getAuthorities();
    }

    public String getPassword() {
        return password;
    }

    /**
     * Returns the username used to authenticate the user. Cannot return <code>null</code>
     * .
     *
     * @return the username (never <code>null</code>)
     */
    @Override
    @Transient
    public String getUsername() {
        return getName();
    }

    /**
     * Indicates whether the user's account has expired. An expired account cannot be
     * authenticated.
     *
     * @return <code>true</code> if the user's account is valid (ie non-expired),
     * <code>false</code> if no longer valid (ie expired)
     */
    @Override
    @Transient
    public boolean isAccountNonExpired() {
        // TODO not used yet
        return true;
    }

    /**
     * Indicates whether the user is locked or unlocked. A locked user cannot be
     * authenticated.
     *
     * @return <code>true</code> if the user is not locked, <code>false</code> otherwise
     */
    @Override
    @Transient
    public boolean isAccountNonLocked() {
        return !isLocked() && (null == getLockedDate());
    }

    /**
     * Indicates whether the user's credentials (password) has expired. Expired
     * credentials prevent authentication.
     *
     * @return <code>true</code> if the user's credentials are valid (ie non-expired),
     * <code>false</code> if no longer valid (ie expired)
     */
    @Override
    @Transient
    public boolean isCredentialsNonExpired() {
        // TODO not checked yet
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuthoritiesStr() {
        return authoritiesStr;
    }

    public void setAuthoritiesStr(String authoritiesStr) {
        this.authoritiesStr = authoritiesStr;
    }

    @JsonIgnore
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    @Column(name = passwordRenewDateAttribute)
    public Date getPasswordRenewDate() {
        return passwordRenewDate;
    }

    public void setPasswordRenewDate(Date passwordRenewDate) {
        this.passwordRenewDate = passwordRenewDate;
    }

    @JsonIgnore
    public String getSalt() {
        return salt;
    }

    @JsonIgnore
    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * Encode the raw password. Generally, a good encoding algorithm applies a SHA-1 or
     * greater hash combined with an 8-byte or greater randomly generated salt.
     *
     * @param rawPassword
     */
    @Override
    public String encode(CharSequence rawPassword) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance(HASH_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        md.update(Base64.decodeBase64(this.getSalt()));
        byte[] messageBytes = rawPassword.toString().getBytes();
        md.update(messageBytes);
        byte[] disgestedPassword = md.digest();

        return Base64.encodeBase64String(disgestedPassword);
    }

    /**
     * Verify the encoded password obtained from storage matches the submitted raw
     * password after it too is encoded. Returns true if the passwords match, false if
     * they do not. The stored password itself is never decoded.
     *
     * @param rawPassword     the raw password to encode and match
     * @param encodedPassword the encoded password from storage to compare with
     * @return true if the raw password, after encoding, matches the encoded password from
     * storage
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encode(rawPassword).equals(encodedPassword);
    }

    public String generateSaltString(){
        return PasswordBuilder.getDefaultBuilder().generateSaltString();
    }

    public void setAuthorities(Set<String> authorities) {
        if (authorities == null || authorities.isEmpty()) {
            return;
        }

        authoritiesStr = "";
        for (String authority : authorities) {
            if (authoritiesStr.length() == 0) {
                authoritiesStr += authority;
            } else {
                authoritiesStr += "," + authority;
            }
        }
    }

    public void updateMetadata(Set<DatabaseUserMetadata> metadatas) {
        metadatas.forEach(this::updateMetadata);
    }
    
    /**
     * Adds {@link DatabaseUserMetadata} to this database user
     * @param databaseUserMetadata metadata to be added to the user
     */
    public void updateMetadata(DatabaseUserMetadata databaseUserMetadata) {
        if (databaseUserMetadata == null) {
            return;
        }
        if (metadata.stream().anyMatch(
                metadata1 -> (metadata1.getKey().equals(databaseUserMetadata.getKey()) && (metadata1.getValue().equals(databaseUserMetadata.getValue()))))) {
            return;
        }
        databaseUserMetadata.setUser(this);
        metadata.add(databaseUserMetadata);
    }

    /**
     * Replaces {@link DatabaseUserMetadata} of the user
     * Creates new metadata entries for all elements of databaseUserMetadataSet and replaces existing metadata
     *
     * @param databaseUserMetadataSet Set of metadata to be added to the user.
     */
    public void replaceMetadata(Set<DatabaseUserMetadata> databaseUserMetadataSet) {
        metadata.clear();
        if (databaseUserMetadataSet != null) {
            for (DatabaseUserMetadata userMetadata : databaseUserMetadataSet) {
                userMetadata.setUser(this);
                metadata.add(userMetadata);
            }
        }
    }

    /**
     * Gets the DatabaseUserMetadata as a set of AbstractUserMetadata
     * 
     * @return
     */
    @JsonIgnore
    @Transient
    public Set<AbstractUserMetadata> getAbstractUserMetadata() {
        Set<AbstractUserMetadata> result = new HashSet<AbstractUserMetadata>();
        for(DatabaseUserMetadata dbUserMetadata : metadata){
            result.add(new AbstractUserMetadata(dbUserMetadata.getKey(), dbUserMetadata.getValue()));
        }
        return result;
    }

    @JsonIgnore
    @Column(name = failedLoginCounterCol)
    public int getFailedLoginCounter() {
        return failedLoginCounter;
    }

    public void setFailedLoginCounter(int failedLoginCounter) {
        this.failedLoginCounter = failedLoginCounter;
    }

    @JsonIgnore
    @Column(name = lockedDateCol)
    public Date getLockedDate() {
        return lockedDate;
    }

    public void setLockedDate(Date lockedDate) {
        this.lockedDate = lockedDate;
    }

    @JsonIgnore
    @Column(name = autoUnlockedCounterCol)
    public int getAutoUnlockedCounter() {
        return autoUnlockedCounter;
    }

    public void setAutoUnlockedCounter(int autoUnlockedCounter) {
        this.autoUnlockedCounter = autoUnlockedCounter;
    }
}
