package uet.kltn.judgment.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Date;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "contract")
@Where(clause = "state != 3")
public class Contract extends BaseEntity{
    @Column(name = "`enterprise_name`", columnDefinition = "VARCHAR(255)", nullable = false)
    private String enterpriseName;

    @Column(name = "`enterprise_owner`", columnDefinition = "VARCHAR(255)", nullable = false)
    private String enterpriseOwner;

    @Column(name = "`address`", columnDefinition = "VARCHAR(255)", nullable = false)
    private String address;

    @Column(name = "`phone`", columnDefinition = "VARCHAR(20)", nullable = false)
    private String phone;

    @Column(name = "`email`", columnDefinition = "VARCHAR(50)", nullable = false)
    private String email;

    @Column(name = "`signed_time`", columnDefinition = "DATETIME", nullable = false)
    private LocalDateTime signedTime;

    @Column(name = "`expiried_time`", columnDefinition = "DATETIME", nullable = false)
    private LocalDateTime expiriedTime;

    @Column(name = "`amount_payment`", columnDefinition = "DOUBLE", nullable = false)
    private Double amountPayment;

    @Column(name = "`account_number`", columnDefinition = "VARCHAR(50)", nullable = false)
    private String accountNumber;

    @Column(name = "`amount_paid`", columnDefinition = "DOUBLE", nullable = true)
    private Double amountPaid;

    @Column(name = "`amount_remained`", columnDefinition = "DOUBLE", nullable = true)
    private String amountRemained;

}
