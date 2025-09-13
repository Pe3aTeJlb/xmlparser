public class PostalAddress {
	private Integer regionId;
	private String addrType;
	private String country;
	private String region;
	private String city;
	private String kladrCode;


	public void setAddrType(String addrType) {
		this.addrType = addrType;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setKladrCode(String kladrCode) {
		this.kladrCode = kladrCode;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}
}