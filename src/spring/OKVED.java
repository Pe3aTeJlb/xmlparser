package spring;

@Entity
@Table(name = "person_l_okved")
public class OKVED {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Basic
	@Column(name = "person_id")
	private Long person_id;

	@Basic
	@Column(name = "okved_id")
	private Long okved_id;

}