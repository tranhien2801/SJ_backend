package uet.kltn.judgment.model;

import jnr.ffi.annotations.In;
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
@Table(name = "`case`",
        indexes = {
            @Index(name = "index_case_type", columnList = "case_type")
        })
@Where(clause = "state != 3")
public class Case extends BaseEntity {
    @Column(name = "`case_name`", columnDefinition = "VARCHAR(555)", nullable = false)
    private String caseName;

    @Column(name = "`case_type`", columnDefinition = "VARCHAR(255)", nullable = false)
    private String caseType;

    @Column(name = "`num_article`", columnDefinition = "VARCHAR(10)", nullable = false)
    private String numArticle;
}
