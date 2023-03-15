package com.password.tool.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "account_group")
@Getter
@Setter
@NoArgsConstructor
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "group_id")
    private int groupId;

    @Column(name = "group_name")
    private String groupName;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "last_modified_at")
    private Date lastModifiedAt;

    @OneToMany(mappedBy = "group" , fetch = FetchType.LAZY)
    private List<Account> accounts;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    public Group(String groupName) {
        this.groupName = groupName;
    }
}
