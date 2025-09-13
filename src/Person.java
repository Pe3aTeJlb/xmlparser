import java.time.LocalDate;

public class Person {
	private Long id;
	private Long cId;
	private String stateRegPrimeNum;
	private String okved;
	private String okvedNum;
	private LocalDate stateRegDate;
	private ContactInfo contactInfo;

	// Конструкторы, геттеры и сеттеры
	public Person() {}

	public Person(Long cId, String stateRegPrimeNum, String okved,
				  String okvedNum, LocalDate stateRegDate) {
		this.cId = cId;
		this.stateRegPrimeNum = stateRegPrimeNum;
		this.okved = okved;
		this.okvedNum = okvedNum;
		this.stateRegDate = stateRegDate;
	}

	public void setContactInfo(ContactInfo contactInfo) {
		this.contactInfo = contactInfo;
	}
}