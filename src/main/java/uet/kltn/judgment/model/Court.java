package uet.kltn.judgment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "`court`",
        indexes = {
            @Index(name = "index_court_level", columnList = "court_level")
        })
@Where(clause = "state != 3")
public class Court extends BaseEntity {
    @Column(name = "`court_name`", columnDefinition = "VARCHAR(255)", nullable = false)
    private String courtName;

    @Column(name = "`court_level`", columnDefinition = "VARCHAR(255)", nullable = false)
    private String courtLevel;

    @Column(name = "`address`", columnDefinition = "VARCHAR(255)", nullable = true)
    private String address;
}
