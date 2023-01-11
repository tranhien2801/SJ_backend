package uet.kltn.judgment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import uet.kltn.judgment.constant.State;
import uet.kltn.judgment.util.Utils;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@MappedSuperclass
public class BaseEntity {
    @Id
    @Column(columnDefinition = "VARCHAR(32)")
    private String uid;

    @Column(name="`state`", columnDefinition = "TINYINT(4) DEFAULT 1", nullable = false)
    private int state;

    @Column(name="`created`", columnDefinition = "DATETIME", nullable = false)
    private LocalDateTime created;

    @Column(name="`modified`", columnDefinition = "DATETIME DEFAULT NULL", nullable = true)
    private LocalDateTime modified;

    public BaseEntity() {
        this.setCreated(LocalDateTime.now());
        this.setState(State.ACTIVE.getId());
    }

    @PrePersist
    public void initializeUUID() {
        if (uid == null) {
            uid = Utils.uuid();
        } else {
            uid = uid.replace("-", "");
        }
    }
}
