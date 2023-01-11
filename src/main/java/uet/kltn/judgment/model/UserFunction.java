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
//@Table(name = "user_function",
//        uniqueConstraints = {
//                @UniqueConstraint(name = "unique_user_function", columnNames = {"function_uid", "user_uid"})
//        })
//@Where(clause = "state != 3")
//public class UserFunction extends BaseEntity{
//        @ManyToOne(fetch = FetchType.LAZY)
//        @JoinColumn(name = "user_id")
//        private User user;
//
//        @ManyToOne(fetch = FetchType.LAZY)
//        @JoinColumn(name = "function_uid")
//        private Function function;
//}
