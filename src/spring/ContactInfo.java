package spring;

import java.util.List;

@Entity
@Table(name = "contact_cards")
public class ContactInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@OneToOne
	@JoinColumn(name = "person_id")
	private Person person;

	@NotNull
	@Column(length = 255)
	private String fio;

	@OneToMany(mappedBy = "contactInfo")
	private List<PhoneNumber> phoneNumbers;

	@OneToMany(mappedBy = "contactInfo")
	private List<PostalAddress> postalAddresses;

}
