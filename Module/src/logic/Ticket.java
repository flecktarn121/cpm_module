package logic;

public class Ticket implements Shoppable, Cloneable {

	private String code;
	private String parkCode;
	private double adultPrice;
	private double childrenPrice;
	private int children;
	private int adults;
	private String initialDate;
	private int days;
	private String shoppingName;

	public Ticket(String code, String parkCode, double adultPrice, double childrenPrice) {
		setCode(code);
		setParkCode(parkCode);
		setAdultPrice(adultPrice);
		setChildrenPrice(childrenPrice);
		setAdults(0);
		setChildren(0);
		setShoppingName(getCode(), false, 0);
	}

	void setInitialDate(String initialDate) {
		this.initialDate = initialDate;
	}

	String getInitialDate() {
		return initialDate;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getParkCode() {
		return parkCode;
	}

	public void setParkCode(String parkCode) {
		this.parkCode = parkCode;
	}

	public double getAdultPrice() {
		return adultPrice;
	}

	public void setAdultPrice(double adultPrice) {
		this.adultPrice = adultPrice;
	}

	public double getChildrenPrice() {
		return childrenPrice;
	}

	public void setChildrenPrice(double childrenPrice) {
		this.childrenPrice = childrenPrice;
	}

	public int getChildren() {
		return children;
	}

	public void setChildren(int children) {
		this.children = children;
	}

	public int getAdults() {
		return adults;
	}

	public void setAdults(int adults) {
		this.adults = adults;
	}

	@Override
	public double getPrice() {

		return (getAdultPrice() * getAdults()) + (getChildrenPrice() * getChildren());
	}

	@Override
	public String getIntialDate() {

		return initialDate;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		if (days < 1) {
			throw new IllegalArgumentException("There must be at least one day.");
		}
		this.days = days;
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
	public String getImage() {

		return "/src/img/" + this.getParkCode() + ".jpg";
	}

	@Override
	public String displayContents() {
		StringBuffer message = new StringBuffer();
		message.append("Adults :" + adults + "\n");
		message.append("Children :" + children + "\n");
		message.append("Initial date :" + getIntialDate() + "\n");
		message.append("Number of days: " + getDays() + "\n");
		message.append("Final price: " + getFinalPrice() + "\n");
		return message.toString();

	}

	@Override
	public double getFinalPrice() {

		return (adults * getAdultPrice()) + (getChildren() * getChildrenPrice());
	}
	
	@Override
	public Ticket clone() {
		Ticket ticket = new Ticket(getCode(), getParkCode(), getAdultPrice(), getChildrenPrice());
		return ticket;
		
	}

}
