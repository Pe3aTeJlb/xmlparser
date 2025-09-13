package spring;

@Entity
@Table(name = "postal_addresses")
public class PostalAddress {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "contact_card_id")
	private ContactInfo contactInfo;

	@Column(name = "region_id")
	private Integer regionId;

	@NotNull
	@Column(name = "addr_type", length = 50)
	private String addrType;

	@NotNull
	@Column(length = 100)
	private String country;

	@Column(length = 100)
	private String region;

	@NotNull
	@Column(length = 100)
	private String city;

	@Column(name = "kladr_code", length = 20)
	private String kladrCode;

}