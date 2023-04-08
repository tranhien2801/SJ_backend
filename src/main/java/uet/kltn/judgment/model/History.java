package uet.kltn.judgment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "history")
@Where(clause = "state != 3")
public class History extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_uid")
    private User user;

    @ManyToOne
    @JoinColumn(name = "judgment_uid")
    private Judgment judgment;

}
