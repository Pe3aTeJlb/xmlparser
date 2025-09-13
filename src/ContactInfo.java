import java.util.List;

public class ContactInfo {
	private String fio;
	private List<PhoneNumber> phoneNumbers;
	private List<PostalAddress> postalAddresses;

	public void setFio(String fio) {
		this.fio = fio;
	}

	public void setPhoneNumbers(List<PhoneNumber> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}

	public void setPostalAddresses(List<PostalAddress> postalAddresses) {
		this.postalAddresses = postalAddresses;
	}
}