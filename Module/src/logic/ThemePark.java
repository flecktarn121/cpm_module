package logic;

public class ThemePark implements Cloneable{

	private final static double DISCOUNT = 20;

	private String code;
	private String name;
	private String country;
	private String city;
	private String description;
	private boolean discount;
	private String image;

	public ThemePark(String code, String name, String country, String city, String description) {
		setCode(code);
		setName(name);
		setCountry(country);
		setCity(city);
		setDescription(description);
		setDiscount(false);
		image = "/img/" + getCode() + ".jpg";
	}

	void setDiscount(boolean discount) {
		this.discount = discount;

	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	private void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	private void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country
	 *            the country to set
	 */
	private void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	private void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	private void setDescription(String description) {
		this.description = description;
	}

	public double getDiscount() {
		double discount = 0;
		if (hasDiscount()) {
			discount = DISCOUNT;
		}
		return discount;
	}

	public String getImage() {
		return image;
	}

	public boolean hasDiscount() {

		return discount;
	}

	@Override
	public String toString() {
		return this.code + "/" + this.name + "/" + this.city + "/" + this.country + this.description;
	}
	
	@Override
	public ThemePark clone() {
		ThemePark park = new ThemePark(getCode(), getName(), getCountry(), getCity(), getCode());
		return park;
	}

}
