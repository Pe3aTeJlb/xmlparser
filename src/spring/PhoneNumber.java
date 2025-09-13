package spring;

@Entity
@Table(name = "phone_numbers")
public class PhoneNumber {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "contact_card_id")
	private ContactInfo contactInfo;

	@Column(name = "is_primary")
	private Boolean primary = false;

	@NotNull
	@Column(name = "phone_number", length = 20)
	private String phone;


}