package com.password.tool.dto;


import com.password.tool.config.Constants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class GroupDto {

    private int groupId;
    @NotNull
    @Size(min = 5 , max = 20 , message = "Group name Should have min 5 chars and max 20 chars")
    @Pattern(regexp = Constants.ACCOUNT_GROUP_NAME_REGEX, message = "Group Name should be lower case alphabets and can have underscore(_)")
    private String groupName;
    private Date createdAt;
    private Date lastModifiedAt;
    private List<AccountDto> accounts;
    private UserDto user;

    public GroupDto(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupDto groupDto = (GroupDto) o;
        return groupId == groupDto.groupId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId);
    }
}
