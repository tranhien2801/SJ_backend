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
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "unit")
@Where(clause = "state != 3")
public class Unit extends BaseEntity{
        @Column(name = "`unit_name`", columnDefinition = "VARCHAR(255)", nullable = false)
        private String unitName;
}
