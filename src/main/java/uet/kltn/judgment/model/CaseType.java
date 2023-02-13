package uet.kltn.judgment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "`case_type`")
@Where(clause = "state != 3")
public class CaseType extends BaseEntity {
    @Column(name = "`case_type_name`", columnDefinition = "VARCHAR(255)", nullable = false)
    private String caseTypeName;

    @ManyToMany(mappedBy = "caseTypes")
    @JsonIgnore
    private Set<User> users;

    public CaseType(String uid, String caseTypeName) {
        super();
        this.setUid(uid);
        this.setCreated(LocalDateTime.now());
        this.caseTypeName = caseTypeName;
    }
}
