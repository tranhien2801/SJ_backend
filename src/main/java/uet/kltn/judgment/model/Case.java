package uet.kltn.judgment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "`case`")
@Where(clause = "state != 3")
public class Case extends BaseEntity {
    @Column(name = "`case_name`", columnDefinition = "VARCHAR(555)", nullable = false)
    private String caseName;

    @Column(name = "`case_type`", columnDefinition = "VARCHAR(255)", nullable = false)
    private String caseType;

    @Column(name = "`num_article`", columnDefinition = "VARCHAR(10)", nullable = false)
    private String numArticle;
}
