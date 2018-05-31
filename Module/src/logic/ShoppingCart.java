package logic;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class ShoppingCart {

	private ArrayList<Shoppable> cart;
	private String customresName;
	private String customersNIF;
	private double totalPrice;
	private double accommodationPrice;
	private double ticketPrice;
	private double themeParkPrice;
	private double appliedDiscount;
	private Database db;

	public ShoppingCart(Database db) {
		cart = new ArrayList<Shoppable>();
		totalPrice = 0;
		accommodationPrice = 0;
		ticketPrice = 0;
		themeParkPrice = 0;
		appliedDiscount = 0;
		this.db = db;
	}

	public void restart() {
		cart = new ArrayList<Shoppable>();
		totalPrice = 0;
		accommodationPrice = 0;
		ticketPrice = 0;
		themeParkPrice = 0;
		appliedDiscount = 0;
	}

	public void addAccommodation(Accommodation accomodation, int numberOfAdults, int numberOfChildren,
			String initialDate, int nights) throws WrongInputException {
		if (numberOfAdults == 0 ) {
			throw new WrongInputException("There must be at least one adult.");
		}
		if (numberOfAdults < 0 || numberOfChildren < 0) {
			throw new WrongInputException("Cannot be a negative number of people.");
		}
		if (!isValidDate(initialDate)) {
			throw new WrongInputException("The date is wrong.");
		}
		if (nights <= 0) {
			throw new WrongInputException("The client must stay at least one night.");
		}
		if(accomodation.getType().equals(TypeOfAccomodation.APARTHOTEL) || accomodation.getType().equals(TypeOfAccomodation.APARTMENT)) {
			if((numberOfAdults+numberOfChildren) > accomodation.getCapacity()) {
				throw new WrongInputException("The number of people selected should not exceed the capacity of the accommodation.");
			}
		}
		if (cartContainsShoppingName(accomodation.getName())) {
			accomodation.setNumberOfAdults(numberOfAdults);
			accomodation.setNumberOfChildren(numberOfChildren);
			accomodation.setInitialDate(initialDate);
			accomodation.setNights(nights);
			accomodation.setNumberOfPeople(numberOfAdults + numberOfChildren);
			accomodation.setShoppingName(accomodation.getName(), false, 0);
			cart.add(accomodation);
			accommodationPrice += accomodation.getFinalPrice();
			totalPrice += accomodation.getFinalPrice();
		} else {
			int index = 0;

			do {
				index++;

			} while (cartContainsShoppingName(accomodation.getName() + "(" + index + ")"));
			accomodation.setNumberOfAdults(numberOfAdults);
			accomodation.setNumberOfChildren(numberOfChildren);
			accomodation.setInitialDate(initialDate);
			accomodation.setNights(nights);
			accomodation.setNumberOfPeople(numberOfAdults + numberOfChildren);
			accomodation.setShoppingName(accomodation.getName(), true, index);
			cart.add(accomodation);
			accommodationPrice += accomodation.getFinalPrice();
			totalPrice += accomodation.getFinalPrice();

		}

	}

	private boolean cartContainsShoppingName(String shoppingName) {
		boolean value = false;
		for (Shoppable item : cart) {
			if (item.getShoppingName().equals(shoppingName)) {
				value = true;
				break;
			}
		}
		return value;
	}

	private boolean isValidDate(String initialDate) {
		String[] date = initialDate.split("-");
		initialDate = date[2] + "-" + date[1] + "-" + date[0];
		return getDate().compareTo(initialDate) < 0;
	}

	public void addTicket(Ticket ticket, int children, int adults, String initialDate, int days)
			throws WrongInputException {
		ThemePark park = db.getParkByCode(ticket.getParkCode());
		if (adults == 0) {
			throw new WrongInputException("There must be at least one adult per ticket.");
		}
		if (children < 0 || adults < 0) {
			throw new WrongInputException("The given number of people is invalid.");
		}
		if (!isValidDate(initialDate)) {
			throw new WrongInputException("The given date is not valid.");
		}
		if (days <= 0) {
			throw new WrongInputException("There must be at least one day selcted.");
		}
		if (!cartContainsShoppingName(ticket.getShoppingName())) {
			ticket.setChildren(children);
			ticket.setAdults(adults);
			ticket.setInitialDate(initialDate);
			ticket.setDays(days);
			ticket.setShoppingName(park.getName(), false, 0);
			cart.add(ticket);
			ticketPrice += ticket.getFinalPrice();
			themeParkPrice += ticket.getFinalPrice();
			totalPrice += ticket.getFinalPrice();
		} else {
			int index = 0;

			do {
				index++;

			} while (cartContainsShoppingName(park.getName() + "(" + index + ")"));
			ticket.setChildren(children);
			ticket.setAdults(adults);
			ticket.setInitialDate(initialDate);
			ticket.setDays(days);
			ticket.setShoppingName(park.getName(), true, index);
			cart.add(ticket);
			ticketPrice += ticket.getFinalPrice();
			themeParkPrice += ticket.getFinalPrice();
			totalPrice += ticket.getFinalPrice();
		}

	}

	public void addPackage(Package pack, int numberOfChildren, int numberOfAdults, String initialDate,
			int numberOfDays) throws WrongInputException {
		if (numberOfAdults == 0) {
			throw new WrongInputException("There must be at least one adult.");
		}
		if (numberOfAdults < 0 || numberOfChildren < 0) {
			throw new WrongInputException("Cannot be a negative number of people.");
		}
		if (!isValidDate(initialDate)) {
			throw new WrongInputException("The given date is not valid.");
		}
		if (!cartContainsShoppingName(pack.getShoppingName())) {
			pack.setNumberOfAdults(numberOfAdults);
			pack.setNumberOfChildren(numberOfChildren);
			pack.setIntialDate(initialDate);
			pack.setDays(numberOfDays);
			cart.add(pack);
			themeParkPrice += pack.getFinalPrice();
			ThemePark park = db.getParkByCode(pack.getParkCode());
			if (park.hasDiscount()) {
				this.appliedDiscount = pack.getFinalPrice() - (pack.getFinalPrice() * park.getDiscount());
			}
			totalPrice += pack.getFinalPrice();
		} else {
			int index = 0;

			do {
				index++;

			} while (cartContainsShoppingName(pack.getName() + "(" + index + ")"));
			pack.setNumberOfAdults(numberOfAdults);
			pack.setNumberOfChildren(numberOfChildren);
			pack.setIntialDate(initialDate);
			pack.setDays(numberOfDays);
			pack.setShoppingName(pack.getName(), true, index);
			cart.add(pack);
			themeParkPrice += pack.getFinalPrice();
			ThemePark park = db.getParkByCode(pack.getParkCode());
			if (park.hasDiscount()) {
				this.appliedDiscount = pack.getFinalPrice() - (pack.getFinalPrice() * park.getDiscount());
			}
			totalPrice += pack.getFinalPrice();
		}
	}

	public ArrayList<Shoppable> getCart() {
		return cart;
	}

	public String getCustomresName() {
		return customresName;
	}

	public void setCustomresName(String customresName) {
		this.customresName = customresName;
	}

	public String getCustomersNIF() {
		return customersNIF;
	}

	public void setCustomersNIF(String customersNIF) {
		this.customersNIF = customersNIF;
	}

	public void addAccommodation(Shoppable accomodation, int numberOfAdults, int numberOfChildren, String initialDate)
			throws WrongInputException {
		if (numberOfAdults == 0 && numberOfChildren == 0) {
			throw new WrongInputException("There must be at least one adult or child.");
		}
		if (numberOfChildren < 0 || numberOfAdults < 0) {
			throw new WrongInputException("The given number of people is invalid.");
		}
		if (!isValidDate(initialDate)) {
			throw new WrongInputException("The given date is not valid.");
		}
		((Accommodation) accomodation).setNumberOfAdults(numberOfAdults);
		((Accommodation) accomodation).setNumberOfChildren(numberOfChildren);
		((Accommodation) accomodation).setInitialDate(initialDate);
		cart.add(accomodation);
	}

	public String printBill() {
		StringBuffer message = new StringBuffer();

		createHeader(message);

		List<Package> packs = new ArrayList<Package>(cart.size());
		List<ThemePark> parks = new ArrayList<ThemePark>(cart.size());
		List<Accommodation> accommodations = new ArrayList<Accommodation>(cart.size());
		List<Ticket> tickets = new ArrayList<Ticket>(cart.size());

		for (Shoppable item : cart) {
			if (item instanceof Package) {
				packs.add((Package) item);
			} else if (item instanceof ThemePark) {
				parks.add((ThemePark) item);
			} else if (item instanceof Accommodation) {
				accommodations.add((Accommodation) item);
			} else if (item instanceof Ticket) {
				tickets.add((Ticket) item);
			}
		}

		displayThemeparks(packs, message);
		displayAccommodations(accommodations, message);
		displayTickets(tickets, message);
		displayPayment(message);
		return message.toString();
	}

	private void displayPayment(StringBuffer message) {
		message.append("**** PAYMENT ****\n");
		message.append("Theme parks: " + this.themeParkPrice + "\n");
		message.append("Accommodations: " + this.accommodationPrice + "\n");
		message.append("Tickets: " + this.ticketPrice + "\n");
		message.append("Discounts: " + this.appliedDiscount + "\n" + "\n" + "\n" + "\n");
		message.append("Final price: " + this.totalPrice);

	}

	private void displayTickets(List<Ticket> tickets, StringBuffer message) {
		message.append("** TICKETS ** \n");
		ThemePark park;
		for (Ticket ticket : tickets) {
			park = db.getParkByCode(ticket.getParkCode());
			message.append("Ticket: " + ticket.getCode() + " / " + park.getName() + "\n");
			message.append("Initial Date: " + ticket.getIntialDate() + " / Days: " + ticket.getDays() + "\n");
			message.append("N. Adults: " + ticket.getAdults() + " / N. Children: " + ticket.getChildren() + "\n");
		}
		message.append("\n" + "\n");

	}

	private void displayAccommodations(List<Accommodation> accommodations, StringBuffer message) {
		message.append("** ACCOMMODATION/s **\n");
		for (Accommodation place : accommodations) {
			message.append("Accomodation: " + place.getCode() + " /  " + place.getType() + " /  " + place.getCategory()
					+ " /  " + "\n");
			message.append("Initial date: " + place.getIntialDate() + " / Nights: " + place.getNights() + "\n");
			message.append("N. People: " + place.getNumberOfPeople() + "\n");
		}
		message.append("\n" + "\n");
	}

	private void displayThemeparks(List<Package> packs, StringBuffer message) {
		message.append("\n** THEME PARKS **\n");
		ThemePark park;
		for (Package pack : packs) {
			park = db.getParkByCode(pack.getParkCode());
			if (park != null) {
				message.append("Package: " + pack.getCode() + " / " + park.getName() + " / " + pack.getDays() + "\n");
				message.append("Initial date: " + getDate() + "\n");
				message.append("N. Adults: " + pack.getNumberOfAdults() + " / N. Children: "
						+ pack.getNumberOfChildren() + "\n");
			}
		}
		
		message.append("\n" + "\n");
	}

	private void createHeader(StringBuffer message) {
		message.append("WORLD TRAVELS.INC " + "\n");
		message.append("BOOKING CONFIRMATION - " + this.getDate() + "\n");
		message.append("---------------------------------------------------------------------------\n");
		message.append("( " + getCustomersNIF() + " - " + getCustomresName());
		message.append("**** BOOKING DATA ****" + "\n" + "\n");

	}

	private String getDate() {

		return new Date(System.currentTimeMillis()).toString();
	}

	public Shoppable getShoppableByShoopingName(String shoppingName) {
		Shoppable product = null;
		for (Shoppable item : cart) {
			if (item.getShoppingName().equals(shoppingName)) {
				product = item;
				break;
			}
		}
		return product;
	}

	public void editAccommodation(int numberOfAdults, int numberOfChildren, String date, int numberOfDays,
			String ShoppingName) throws WrongInputException {
		if (numberOfAdults == 0 && numberOfChildren == 0) {
			throw new WrongInputException("There must be at least one child or one adult.");
		}
		if (numberOfAdults < 0 || numberOfChildren < 0) {
			throw new WrongInputException("Cannot be a negative number of people.");
		}
		if (!isValidDate(date)) {
			throw new WrongInputException("The date is wrong.");
		}
		if (numberOfDays <= 0) {
			throw new WrongInputException("The client must stay at least one night.");
		}

		for (Shoppable accomodation : cart) {
			if (accomodation.getShoppingName().equals(ShoppingName)) {
				totalPrice -= accomodation.getFinalPrice();
				accommodationPrice -= accomodation.getFinalPrice();
				((Accommodation) accomodation).setNumberOfAdults(numberOfAdults);
				((Accommodation) accomodation).setNumberOfChildren(numberOfChildren);
				((Accommodation) accomodation).setInitialDate(date);
				((Accommodation) accomodation).setNights(numberOfDays);
				((Accommodation) accomodation).setNumberOfPeople(numberOfAdults + numberOfChildren);
				accommodationPrice += accomodation.getFinalPrice();
				totalPrice += accomodation.getFinalPrice();
				break;
			}
		}
	}

	public void editPack(int numberOfAdults, int numberOfChildren, String date, int numberOfDays, String shoppingName)
			throws WrongInputException {
		if (numberOfAdults == 0 && numberOfChildren == 0) {
			throw new WrongInputException("There must be at least one child or one adult.");
		}
		if (numberOfAdults < 0 || numberOfChildren < 0) {
			throw new WrongInputException("Cannot be a negative number of people.");
		}
		if (!isValidDate(date)) {
			throw new WrongInputException("The given date is not valid.");
		}
		for (Shoppable pack : cart) {
			if (pack.getShoppingName().equals(shoppingName)) {
				totalPrice -= pack.getFinalPrice();
				themeParkPrice -= pack.getFinalPrice();
				((Package) pack).setNumberOfAdults(numberOfAdults);
				((Package) pack).setNumberOfChildren(numberOfChildren);
				((Package) pack).setIntialDate(date);
				((Package) pack).setDays(numberOfDays);
				themeParkPrice += pack.getPrice();
				ThemePark park = db.getParkByCode(((Package) pack).getParkCode());
				if (park.hasDiscount()) {
					this.appliedDiscount = pack.getPrice() - (pack.getPrice() * park.getDiscount());
				}
				totalPrice += pack.getFinalPrice();
			}
		}
	}

	public void editPark(int numberOfAdults, int numberOfChildren, String date, int numberOfDays, String shoppingName)
			throws WrongInputException {
		if (numberOfChildren == 0 && numberOfAdults == 0) {
			throw new WrongInputException("There must be at least one person per ticket.");
		}
		if (numberOfChildren < 0 || numberOfAdults < 0) {
			throw new WrongInputException("The given number of people is invalid.");
		}
		if (!isValidDate(date)) {
			throw new WrongInputException("The given date is not valid.");
		}
		if (numberOfDays <= 0) {
			throw new WrongInputException("There must be at least one day selcted.");
		}

		for (Shoppable ticket : cart) {
			if (ticket.getShoppingName().equals(shoppingName)) {
				ThemePark park = db.getParkByCode(((Ticket) ticket).getParkCode());
				ticketPrice -= ticket.getFinalPrice();
				themeParkPrice -= ticket.getFinalPrice();
				totalPrice -= ticket.getFinalPrice();
				((Ticket) ticket).setChildren(numberOfChildren);
				((Ticket) ticket).setAdults(numberOfAdults);
				((Ticket) ticket).setInitialDate(date);
				((Ticket) ticket).setDays(numberOfDays);
				((Ticket) ticket).setShoppingName(park.getName(), false, 0);
				ticketPrice += ticket.getFinalPrice();
				themeParkPrice += ticket.getFinalPrice();
				totalPrice += ticket.getFinalPrice();
			}
		}
	}

	public void remove(Shoppable item) {
		int index = 0;
		for (int i = 0; i < cart.size(); i++) {
			if (cart.get(i).getShoppingName().equals(item.getShoppingName())) {
				index = i;
				break;
			}
		}

		totalPrice -= cart.get(index).getFinalPrice();
		if (cart.get(index) instanceof Accommodation) {
			accommodationPrice -= cart.get(index).getFinalPrice();
		} else if (cart.get(index) instanceof Accommodation) {
			ticketPrice -= cart.get(index).getFinalPrice();
		} else {
			themeParkPrice -= cart.get(index).getFinalPrice();
		}

	}

}
