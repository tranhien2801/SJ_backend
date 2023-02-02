package uet.kltn.judgment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;
import uet.kltn.judgment.dto.request.auth.SignUpRequestDto;
import uet.kltn.judgment.dto.request.auth.UpdateUserRequestDto;
import uet.kltn.judgment.util.Utils;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user",
        indexes = {
                @Index(name = "index_uid", columnList = "uid"),
                @Index(name = "index_email", columnList = "email"),
                @Index(name = "index_phone_number", columnList = "phone_number")
        })
@Where(clause = "state != 3")
public class User extends BaseEntity{

    @Column(name = "`password`", columnDefinition = "VARCHAR(255)", nullable = false)
    private String password;

    @Column(name = "`name`", columnDefinition = "VARCHAR(255)", nullable = false)
    private String name;

    @Column(name = "`email`", columnDefinition = "VARCHAR(255)", nullable = false)
    private String email;

    @Column(name = "`phone_number`", columnDefinition = "VARCHAR(20)", nullable = true)
    private String phoneNumber;

    @Column(name = "`description`", columnDefinition = "TEXT", nullable = true)
    private String description;

    @Column(name = "`avatar`", columnDefinition = "TEXT", nullable = true)
    private String avatar;

    @Column(name = "`gender`", columnDefinition = "TINYINT(10)", nullable = true)
    private Integer gender;

    @Column(name = "`birthday`", columnDefinition = "DATE", nullable = true)
    private Date birthday;

    @Column(name = "`role`", columnDefinition = "TINYINT(10)", nullable = false)
    private Integer role;

    @Column(name = "`usage_time`", columnDefinition = "TINYINT(10) DEFAULT 0", nullable = true)
    private Integer usageTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_uid")
    private Unit unit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_uid")
    private Work work;

    @Column(name = "`hobby`", columnDefinition = "TEXT", nullable = true)
    private String hobby;

    @Column(name = "`number_employee`", columnDefinition = "INT", nullable = true)
    private Integer numberEmployee;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_case_type",
                joinColumns = @JoinColumn(name = "user_uid", referencedColumnName = "uid"),
                inverseJoinColumns = @JoinColumn(name = "case_type_uid", referencedColumnName = "uid"))
    private Set<CaseType> caseTypes;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_function",
                joinColumns = @JoinColumn(name = "user_uid", referencedColumnName = "uid"),
                inverseJoinColumns = @JoinColumn(name = "function_uid", referencedColumnName = "uid"))
    private Set<Function> functions;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "judgment_liked",
            joinColumns = @JoinColumn(name = "user_uid", referencedColumnName = "uid"),
            inverseJoinColumns = @JoinColumn(name = "judgment_uid", referencedColumnName = "uid"))
    private Set<Judgment> judgments;

    @Column(name = "`power`", columnDefinition = "TINYINT DEFAULT 4", nullable = false)
    private Integer power;

    @Column(name = "`level`", columnDefinition = "TINYINT", nullable = true)
    private Integer level;


    @Column(name = "`last_login`", columnDefinition = "BIGINT")
    private LocalDateTime lastLogin;

    public User(String uid, int state, String password, String name, LocalDateTime created, LocalDateTime
            modified, String email, String phoneNumber, String description, String avatar, Integer gender,
                Date birthday, Integer role, Integer power) {
        super();
        this.setUid(uid);
        this.avatar = avatar;
        this.setState(state);
        this.password = password;
        this.name = name;
        this.setCreated(created);
        this.setModified(modified);
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.description = description;
        this.gender = gender;
        this.birthday = birthday;
        this.role = role;
        this.power = power;
    }


    public void update(UpdateUserRequestDto updateUserRequestDto) {
        this.setModified(LocalDateTime.now());
        this.name = updateUserRequestDto.getName();
        this.email = updateUserRequestDto.getEmail();
        this.phoneNumber = updateUserRequestDto.getPhoneNumber();
        this.description = updateUserRequestDto.getDescription();
    }

}
