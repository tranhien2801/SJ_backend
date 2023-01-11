//package uet.kltn.judgment.model;
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.hibernate.annotations.Where;
//
//import javax.persistence.*;
//
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@Entity
//@Table(name = "user_case_type",
//        uniqueConstraints = {
//                @UniqueConstraint(name = "unique_user_case_type", columnNames = {"case_uid", "user_uid"})
//        })
//@Where(clause = "state != 3")
//public class UserCaseType extends BaseEntity {
//        @ManyToOne(fetch = FetchType.LAZY)
//        @JoinColumn(name = "user_uid")
//        private User user;
//
//        @ManyToOne(fetch = FetchType.LAZY)
//        @JoinColumn(name = "case_type_uid")
//        private CaseType caseType;
//}
