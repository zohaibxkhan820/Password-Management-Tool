package com.password.tool.dto;


import com.password.tool.config.Constants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class AccountDto {

    private int accountId;

    @NotNull
    @Size(min = 5 , max = 20 , message = "Account name Should have min 5 chars and max 20 chars")
    @Pattern(regexp = Constants.ACCOUNT_GROUP_NAME_REGEX, message = "Account Name should be lower case alphabets and can have underscore(_)")
    private String accountName;

    @NotNull
    @Size(min = 5 , max = 30 , message = "User Name should be of min 5 chars and max 30 chars")
    private String userName;

    @NotNull
    @Pattern(regexp = Constants.PASSWORD_REGEX, message = "Password Should Contain Lower case , Upper case , Numbers and Special chars with min of 8 chars")
    private String password;

    @NotNull
    @Pattern(regexp = Constants.URL_REGEX , message = "Url should have proper pattern")
    private String url;

    private Date createdAt;

    private Date lastModifiedAt;

    private UserDto user;

    private GroupDto group;

    public AccountDto(String accountName, String userName, String password, String url) {
        this.accountName = accountName;
        this.userName = userName;
        this.password = password;
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountDto that = (AccountDto) o;
        return accountId == that.accountId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId);
    }
}
