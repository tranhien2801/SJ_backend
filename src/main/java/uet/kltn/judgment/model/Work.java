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
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "`work`")
@Where(clause = "state != 3")
public class Work extends BaseEntity{
        @Column(name = "`work_name`", columnDefinition = "VARCHAR(255)", nullable = false)
        private String workName;

        public Work(String uid, String name) {
                super();
                this.setUid(uid);
                this.workName = name;
                this.setCreated(LocalDateTime.now());
        }
}
