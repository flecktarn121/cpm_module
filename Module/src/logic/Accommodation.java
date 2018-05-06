package logic;

public class Accommodation implements Shoppable {

	private String code;
	private TypeOfAccomodation type;
	private double category;
	private String name;
	private String themeParkCode;
	private int capacity;
	private double price;
	private boolean breakfast;
	private int numberOfAdults;
	private int numberOfChildren;
	private String initialDate;
	private int nights;
	private int numberOfPeople;
	private String shoppingName;
	private String image;

	public Accommodation(String code, TypeOfAccomodation type, double category, String name, String themeParkCode,
			int capacity, double price) {
		setCode(code);
		setType(type);
		setCategory(category);
		setName(name);
		setThemeParkCode(themeParkCode);
		setCapacity(capacity);
		setPrice(price);
		breakfast = false;
		numberOfAdults = 0;
		numberOfChildren = 0;
		initialDate = "1/1/1970";
		image = "/img/" + getCode() + ".jpg";
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
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the type
	 */
	public TypeOfAccomodation getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(TypeOfAccomodation type) {
		this.type = type;
	}

	/**
	 * @return the category
	 */
	public double getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(double category) {
		this.category = category;
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
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the themeParkCode
	 */
	public String getThemeParkCode() {
		return themeParkCode;
	}

	/**
	 * @param themeParkCode
	 *            the themeParkCode to set
	 */
	public void setThemeParkCode(String themeParkCode) {
		this.themeParkCode = themeParkCode;
	}

	/**
	 * @return the capacity
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * @param capacity
	 *            the capacity to set
	 */
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	@Override
	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	public boolean isBreakfast() {
		return breakfast;
	}

	public void setBreakfast(boolean breakfast) {
		if (this.getType().equals(TypeOfAccomodation.HOTEL)) {
			this.breakfast = breakfast;
		}
	}

	public void setNumberOfChildren(int numberOfChildren) {
		if (numberOfChildren < 0) {
			throw new IllegalArgumentException("Th number of children cannot be negative.");
		} else {
			this.numberOfChildren = numberOfChildren;
		}
	}

	public void setNumberOfAdults(int numberOfAdults) {
		if (numberOfAdults < 0) {
			throw new IllegalArgumentException("The number of adults cannot be negative.");
		} else {
			this.numberOfAdults = numberOfAdults;
		}
	}

	public void setInitialDate(String initialDate) {
		this.initialDate = initialDate;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Accommodation other = (Accommodation) obj;
		if (code == null) {
			if (other.code != null) {
				return false;
			}
		} else if (!code.equals(other.code)) {
			return false;
		}
		return true;
	}

	@Override
	public String getIntialDate() {

		return initialDate;
	}

	public int getNights() {
		return nights;
	}

	public void setNights(int nights) {
		if (nights < 1) {
			throw new IllegalArgumentException("Wrong number of nights.");
		}
		this.nights = nights;
	}

	public int getNumberOfPeople() {
		return numberOfPeople;
	}

	public void setNumberOfPeople(int numberOfPeople) {
		if (numberOfPeople < 1) {
			throw new IllegalArgumentException("There has to be at least one person.");
		}
		this.numberOfPeople = numberOfPeople;
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

		return image;
	}

	@Override
	public String displayContents() {
		StringBuffer message = new StringBuffer();
		message.append("Name :" + getName() + "\n");
		message.append("Type :" + getType().toString().toLowerCase() + "\n");
		message.append("Adults :" + numberOfAdults + "\n");
		message.append("Children :" + numberOfChildren + "\n");
		message.append("Initial date :" + getIntialDate() + "\n");
		message.append("Number of days: " + getNights() + "\n");
		message.append("Final price: " + getFinalPrice() + "\n");
		return message.toString();
	}

	@Override
	public double getFinalPrice() {
		double finalPrice = 0;
		if (this.getType().equals(TypeOfAccomodation.HOTEL)) {
			finalPrice += getNumberOfPeople() * getNights() * getPrice();
			if (breakfast) {
				finalPrice += (0.1 * finalPrice);
			}
		} else {
			finalPrice = getNights() * getPrice();
		}
		return finalPrice;
	}

}