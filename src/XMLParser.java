import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class XMLParser {

	public Person parseXml(String xmlContent) throws Exception {
		try (InputStream inputStream = new ByteArrayInputStream(
				xmlContent.getBytes(StandardCharsets.UTF_8))) {

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(inputStream);
			document.getDocumentElement().normalize();

			Element root = document.getDocumentElement();

			// Парсинг основной информации
			Person person = parsePersonInfo(root);

			// Парсинг контактной информации
			Element contactInfoElement = (Element) root.getElementsByTagName("ContactInfo").item(0);
			if (contactInfoElement != null) {
				person.setContactInfo(parseContactInfo(contactInfoElement));
			}

			return person;
		}
	}

	private Person parsePersonInfo(Element root) {
		Long cId = getLongValue(root, "C_ID");
		String stateRegPrimeNum = getTextContent(root, "StateRegPrimeNum");
		/*
		* Вот тут не совсем ясно, т.к. по идее оквэдов может быть несколько и в общем формируется manyTOmany отношение
		* Детальнее в спринге
		* */
		String okved = getTextContent(root, "OKVED");
		String okvedNum = getTextContent(root, "OKVED.NUM");
		LocalDate stateRegDate = parseStateRegDate(root);

		if (cId == null) {
			throw new IllegalArgumentException("C_ID is required");
		}
		if (stateRegPrimeNum == null || stateRegPrimeNum.trim().isEmpty()) {
			throw new IllegalArgumentException("StateRegPrimeNum is required");
		}

		return new Person(cId, stateRegPrimeNum, okved, okvedNum, stateRegDate);
	}

	private ContactInfo parseContactInfo(Element contactInfoElement) {
		ContactInfo contactInfo = new ContactInfo();

		Element cardElement = (Element) contactInfoElement.getElementsByTagName("Card").item(0);
		if (cardElement != null) {
			contactInfo.setFio(getTextContent(cardElement, "FIO"));
		}

		Element phoneNumsElement = (Element) contactInfoElement.getElementsByTagName("PhoneNums").item(0);
		if (phoneNumsElement != null) {
			contactInfo.setPhoneNumbers(parsePhoneNumbers(phoneNumsElement));
		}

		Element postAddrsElement = (Element) contactInfoElement.getElementsByTagName("PostAddrs").item(0);
		if (postAddrsElement != null) {
			contactInfo.setPostalAddresses(parsePostalAddresses(postAddrsElement));
		}

		return contactInfo;
	}

	private List<PhoneNumber> parsePhoneNumbers(Element phoneNumsElement) {
		List<PhoneNumber> phoneNumbers = new ArrayList<>();
		NodeList phoneNumList = phoneNumsElement.getElementsByTagName("PhoneNum");

		for (int i = 0; i < phoneNumList.getLength(); i++) {
			Element phoneNumElement = (Element) phoneNumList.item(i);

			Boolean isPrimary = getBooleanValue(phoneNumElement, "Primary");
			String phone = getTextContent(phoneNumElement, "Phone");

			if (phone != null) {

				if (!isValidPhoneNumber(phone)) {
					throw new IllegalArgumentException("Invalid phone number: " + phone);
				}

				PhoneNumber phoneNumber = new PhoneNumber();
				phoneNumber.setPrimary(Boolean.TRUE.equals(isPrimary));
				phoneNumber.setPhone(phone);
				phoneNumbers.add(phoneNumber);
			}
		}

		return phoneNumbers;
	}

	private List<PostalAddress> parsePostalAddresses(Element postAddrsElement) {
		List<PostalAddress> postalAddresses = new ArrayList<>();
		NodeList postAddrList = postAddrsElement.getElementsByTagName("PostAddr");

		for (int i = 0; i < postAddrList.getLength(); i++) {
			Element postAddrElement = (Element) postAddrList.item(i);

			Integer regionId = getIntegerValue(postAddrElement, "RegionID");
			String addrType = getTextContent(postAddrElement, "AddrType");
			String country = getTextContent(postAddrElement, "Country");
			String region = getTextContent(postAddrElement, "Region");
			String city = getTextContent(postAddrElement, "City");
			String kladrCode = getTextContent(postAddrElement, "KladrCode");

			// Валидация обязательных полей адреса
			if (addrType == null || country == null || city == null) {
				throw new IllegalArgumentException("AddrType, Country and City are required for postal address");
			}

			PostalAddress postalAddress = new PostalAddress();
			postalAddress.setRegionId(regionId);
			postalAddress.setAddrType(addrType);
			postalAddress.setCountry(country);
			postalAddress.setRegion(region);
			postalAddress.setCity(city);
			postalAddress.setKladrCode(kladrCode);

			postalAddresses.add(postalAddress);
		}

		return postalAddresses;
	}

	private LocalDate parseStateRegDate(Element root) {
		Element stateRegDtElement = (Element) root.getElementsByTagName("StateRegDt").item(0);
		if (stateRegDtElement != null) {
			Integer year = getIntegerValue(stateRegDtElement, "Year");
			Integer month = getIntegerValue(stateRegDtElement, "Month");
			Integer day = getIntegerValue(stateRegDtElement, "Day");

			if (year != null && month != null && day != null) {
				try {
					return LocalDate.of(year, month, day);
				} catch (Exception e) {
					throw new IllegalArgumentException("Invalid date format in StateRegDt");
				}
			}
		}
		return null;
	}


	private String getTextContent(Element parent, String tagName) {
		NodeList nodes = parent.getElementsByTagName(tagName);
		if (nodes.getLength() > 0) {
			return nodes.item(0).getTextContent().trim();
		}
		return null;
	}

	private Long getLongValue(Element parent, String tagName) {
		String value = getTextContent(parent, tagName);
		return value != null ? Long.parseLong(value) : null;
	}

	private Integer getIntegerValue(Element parent, String tagName) {
		String value = getTextContent(parent, tagName);
		return value != null ? Integer.parseInt(value) : null;
	}

	private Boolean getBooleanValue(Element parent, String tagName) {
		String value = getTextContent(parent, tagName);
		return value != null ? Boolean.parseBoolean(value) : null;
	}

	private boolean isValidPhoneNumber(String phone) {
		// какая-то регулярка для телефона. Без доп информации сложно сказать какая нужна.
		return true;
	}
}