//package uet.kltn.judgment.model;
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.hibernate.annotations.Where;
//
//import javax.persistence.*;
//import java.util.List;
//
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@Table(name = "function")
//@Where(clause = "state != 3")
//public class Function extends BaseEntity{
//    @Column(name = "`function_name`", columnDefinition = "VARCHAR(255)", nullable = false)
//    private String functionName;
//
//    @OneToMany(mappedBy = "function")
//    private List<UserFunction> userFunctions;
//}
