package spring;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "persons")
public class Person {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@NotNull
	@Column(name = "c_id", unique = true)
	private Long cId;

	@NotNull
	@Column(name = "state_reg_prime_num")
	private String stateRegPrimeNum;

	@OneToMany(mappedBy = "person")
	private OKVED okved;

	@Column(name = "state_reg_date")
	private LocalDate stateRegDate;

	@OneToOne(mappedBy = "person")
	private ContactInfo contactInfo;

	@Column(name = "created_date")
	private LocalDateTime createdDate = LocalDateTime.now();

}