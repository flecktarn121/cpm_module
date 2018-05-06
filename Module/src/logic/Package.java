package logic;

public class Package implements Shoppable {
	private String code;
	private String name;
	private String accommodationCode;
	private int days;
	private String parkCode;
	private double adultPrice;
	private double childrenPrice;
	private int numberOfChildren;
	private int numberOfAdults;
	private String initialDate;
	private String shoppingName;

	public Package(String code, String name, String parkCode, String accommodationCode, int days, double adultPrice,
			double childrenPrice) {
		setCode(code);
		setName(name);
		setParkCode(parkCode);
		setAccommodationCode(accommodationCode);
		setDays(days);
		setAdultPrice(adultPrice);
		setChildrenPrice(childrenPrice);
		numberOfAdults = 0;
		numberOfChildren = 0;
		shoppingName = this.getName();
	}

	private void setParkCode(String parkCode) {
		this.parkCode = parkCode;

	}

	public String getParkCode() {
		return parkCode;
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
	 * @return the accommodationCode
	 */
	public String getAccommodationCode() {
		return accommodationCode;
	}

	/**
	 * @param accommodationCode
	 *            the accommodationCode to set
	 */
	private void setAccommodationCode(String accommodationCode) {
		this.accommodationCode = accommodationCode;
	}

	/**
	 * @return the days
	 */
	public int getDays() {
		return days;
	}

	/**
	 * @param days
	 *            the days to set
	 */
	void setDays(int days) {
		this.days = days;
	}

	/**
	 * @return the adultPrice
	 */
	public double getAdultPrice() {
		return adultPrice;
	}

	/**
	 * @param adultPrice
	 *            the adultPrice to set
	 */
	private void setAdultPrice(double adultPrice) {
		this.adultPrice = adultPrice;
	}

	/**
	 * @return the childrenPrice
	 */
	public double getChildrenPrice() {
		return childrenPrice;
	}

	/**
	 * @param childrenPrice
	 *            the childrenPrice to set
	 */
	private void setChildrenPrice(double childrenPrice) {
		this.childrenPrice = childrenPrice;
	}

	public int getNumberOfChildren() {
		return numberOfChildren;
	}

	public void setNumberOfChildren(int numberOfChildren) {
		if (numberOfChildren < 0) {
			throw new IllegalArgumentException();
		}
		this.numberOfChildren = numberOfChildren;
	}

	public int getNumberOfAdults() {
		return numberOfAdults;
	}

	public void setNumberOfAdults(int numberOfAdults) {
		if (numberOfAdults < 0) {
			throw new IllegalArgumentException();
		}
		this.numberOfAdults = numberOfAdults;
	}

	@Override
	public double getPrice() {

		return ((getChildrenPrice() * getNumberOfChildren()) + (getAdultPrice() * getAdultPrice()));
	}

	@Override
	public String getIntialDate() {

		return initialDate;
	}

	public void setIntialDate(String intialDate) {
		this.initialDate = intialDate;
	}

	@Override
	public String getShoppingName() {
		return shoppingName;
	}

	public void setShoppingName(String shoppingName, boolean isRepeated, int index) {
		if (!isRepeated) {
			this.shoppingName = shoppingName;
		} else {
			this.shoppingName = shoppingName + "(" + index + ")";
		}
	}

	@Override
	public String toString() {
		return "Packages [code=" + code + ", name=" + name + ", accommodationCode=" + accommodationCode + ", days="
				+ days + ", parkCode=" + parkCode + ", adultPrice=" + adultPrice + ", childrenPrice=" + childrenPrice
				+ "]";
	}

	@Override
	public String getImage() {

		return "/img/" + this.getParkCode() + ".jpg";
	}

	@Override
	public String displayContents() {
		StringBuffer message = new StringBuffer();
		message.append("Name :" + getName() + "\n");
		message.append("Adults :" + numberOfAdults + "\n");
		message.append("Children :" + numberOfChildren + "\n");
		message.append("Initial date :" + getIntialDate() + "\n");
		message.append("Number of days: " + getDays() + "\n");
		message.append("Final price: " + getFinalPrice() + "\n");
		return message.toString();
		
	}

	@Override
	public double getFinalPrice() {
		
		return ((getChildrenPrice() * getNumberOfChildren()) + (getAdultPrice() * getAdultPrice()));
	}

}
