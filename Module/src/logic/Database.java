package logic;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JComboBox;

public class Database {
	public final static String NO_FILTER_KEYWORD = "All";
	List<Package> packages;
	List<Accommodation> accommodations;
	List<ThemePark> themeParks;
	List<Ticket> tickets;
	List<String> countries;
	List<String> cities;
	FileUtility fileUtility = new FileUtility();

	public Database() {
		packages = new ArrayList<Package>();
		accommodations = new ArrayList<Accommodation>();
		themeParks = new ArrayList<ThemePark>();
		tickets = new ArrayList<Ticket>();
		countries = new ArrayList<String>();
		cities = new ArrayList<String>();
	}

	public List<ThemePark> getParks() {
		return themeParks;
	}

	public List<Package> getPackages() {
		return packages;
	}

	public List<Accommodation> getAccommodations() {
		return accommodations;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public List<String> getCountries() {
		return countries;
	}

	public void save(String filename, String text) {
		List<String> lines = new ArrayList<String>();
		lines.add(text);
		fileUtility.saveToFile(filename,lines );
	}

	public void loadDatabase(String packages, String accomodations, String themeParks, String tickets)
			throws FileNotFoundException {
		List<String> listParks = fileUtility.loadFile(themeParks);
		loadParks(listParks);
		loadAccommodations(fileUtility.loadFile(accomodations));
		loadPackages(fileUtility.loadFile(packages));
		loadTickets(fileUtility.loadFile(tickets));
	}

	private void loadTickets(List<String> listTIckets) {
		List<Ticket> tickets = new ArrayList<Ticket>(listTIckets.size());
		String data[];
		for (String line : listTIckets) {
			data = line.split("@");
			tickets.add(new Ticket(data[0], data[1], Double.parseDouble(data[2]), Double.parseDouble(data[3])));
		}

		this.tickets.addAll(tickets);

	}

	private void loadPackages(List<String> listPacks) {
		List<Package> packs = new ArrayList<Package>(listPacks.size());
		String[] data;
		for (String line : listPacks) {
			data = line.split("@");
			String code = data[0];
			String name = data[1];
			String parkCode = data[2];
			String accommodationCode = data[3];
			int adultPrice = Integer.parseInt(data[4]);
			int childrenPrice = Integer.parseInt(data[5]);
			int stars = Integer.parseInt(data[6]);
			Package pack = new Package(code, name, parkCode, accommodationCode, adultPrice, childrenPrice, stars);
			packs.add(pack);
		}

		packages.addAll(packs);

	}

	private void loadParks(List<String> listParks) {
		List<ThemePark> parks = new ArrayList<ThemePark>(listParks.size());
		String[] data;
		for (String line : listParks) {
			data = line.split("@");
			parks.add(new ThemePark(data[0], data[1], data[2], data[3], data[4]));
			if (!countries.contains(data[2])) {
				countries.add(data[2]);
			}
			if (!cities.contains(data[3])) {
				cities.add(data[3]);
			}
		}
		Random chooser = new Random(System.currentTimeMillis());
		parks.get(chooser.nextInt(parks.size())).setDiscount(true);
		themeParks.addAll(parks);

	}

	private void loadAccommodations(List<String> listAccommodations) {
		List<Accommodation> accommodations = new ArrayList<Accommodation>(listAccommodations.size());

		String[] data;
		TypeOfAccomodation type = null;
		double category;
		int capacity;
		double price;
		for (String line : listAccommodations) {
			data = line.split("@");
			switch (data[1]) {
			case "AP":
				type = TypeOfAccomodation.APARTMENT;
				break;
			case "HO":
				type = TypeOfAccomodation.HOTEL;
				break;
			case "AH":
				type = TypeOfAccomodation.APARTHOTEL;
				break;

			}
			category = Double.parseDouble(data[2]);
			capacity = Integer.parseInt(data[5]);
			price = Double.parseDouble(data[6]);

			accommodations.add(new Accommodation(data[0], type, category, data[3], data[4], capacity, price));
		}
		this.accommodations.addAll(accommodations);
	}

	public ThemePark getParkByCode(String code) {
		ThemePark thePark = null;
		for (ThemePark park : themeParks) {
			if (park.getCode().equals(code)) {
				thePark = park;
				break;
			}
		}

		return thePark;
	}

	public Accommodation getAccommodationByCode(String code) {
		Accommodation acc = null;
		for (Accommodation place : accommodations) {
			if (place.getCode().equals(code)) {
				acc = place;
				break;
			}
		}

		return acc;
	}

	public List<String> getCities() {

		return cities;
	}

	public Accommodation getAccommodationByName(String name) {
		Accommodation acc = null;
		for (Accommodation place : accommodations) {
			if (place.getName().equals(name)) {
				acc = place;
				break;
			}
		}

		return acc;
	}

	public List<Accommodation> filterAccByCountry(List<Accommodation> newList, String country) {
		List<Accommodation> listToBeReturned = new ArrayList<Accommodation>();
		if (!country.equals(NO_FILTER_KEYWORD)) {
			for (Accommodation acc : newList) {
				if (getParkByCode(acc.getThemeParkCode()).getCountry().equals(country)) {
					listToBeReturned.add(acc);
				}
			}
		} else {
			listToBeReturned = newList;
		}
		return listToBeReturned;
	}

	public List<Accommodation> filterAccByThemePark(List<Accommodation> newList, String park) {
		List<Accommodation> listToBeReturned = new ArrayList<Accommodation>();
		if (!park.equals(NO_FILTER_KEYWORD)) {
			for (Accommodation acc : newList) {
				if (getParkByCode(acc.getThemeParkCode()).getName().equals(park)) {
					listToBeReturned.add(acc);
				}
			}
		} else {
			listToBeReturned = newList;
		}
		return listToBeReturned;
	}

	public List<Accommodation> filterAccByType(List<Accommodation> newList, boolean apHotel, boolean hotel,
			boolean motel) {
		List<Accommodation> listToBeReturned = new ArrayList<Accommodation>();
		for (Accommodation acc : newList) {
			boolean isAp = (apHotel && !(acc.getType().equals(TypeOfAccomodation.APARTHOTEL)));
			boolean ishotel = (hotel && !(acc.getType().equals(TypeOfAccomodation.HOTEL)));
			boolean isMotel = (motel && !(acc.getType().equals(TypeOfAccomodation.APARTMENT)));
			if (!(isAp || ishotel || isMotel)) {
				listToBeReturned.add(acc);
			}
		}
		return listToBeReturned;
	}

	public List<Accommodation> filterAccByPrice(List<Accommodation> newList, Integer price) {
		List<Accommodation> listToBeReturned = new ArrayList<Accommodation>();
		for (Accommodation acc : newList) {
			if ((acc.getPrice() <= price.intValue())) {
				listToBeReturned.add(acc);
			}
		}
		return listToBeReturned;
	}

	public Ticket getTicketByCode(String themeParkCode) {
		Ticket ticketToReturn = null;
		for (Ticket ticket : tickets) {
			if (themeParkCode.equals(ticket.getParkCode())) {
				ticketToReturn = ticket;
				break;
			}
		}
		return ticketToReturn;
	}

	public List<ThemePark> filterParkByCountry(List<ThemePark> newList, String country) {
		List<ThemePark> listToBeReturned = new ArrayList<ThemePark>();
		if (!country.equals(NO_FILTER_KEYWORD)) {
			for (ThemePark park : newList) {
				if (park.getCountry().equals(country)) {
					listToBeReturned.add(park);
				}
			}
		} else {
			listToBeReturned = newList;
		}
		return listToBeReturned;
	}

	public List<ThemePark> filterParkCity(List<ThemePark> newList, String city) {
		List<ThemePark> listToBeReturned = new ArrayList<ThemePark>(newList.size());
		if (!city.equals(NO_FILTER_KEYWORD)) {
			for (ThemePark park : newList) {
				if (park.getCity().equals(city)) {
					listToBeReturned.add(park);
				}
			}
		} else {
			listToBeReturned = newList;
		}
		return listToBeReturned;
	}

	public List<ThemePark> filterParkByPrice(List<ThemePark> newList, Integer maxPrice) {
		List<ThemePark> listToBeReturned = new ArrayList<ThemePark>(newList.size());
		for (ThemePark park : newList) {
			if (this.getTicketByCode(park.getCode()).getAdultPrice() <= maxPrice) {
				listToBeReturned.add(park);
			}
		}
		return listToBeReturned;
	}

	public List<Package> filterPackByCountry(List<Package> newList, String country) {
		List<Package> listToBeReturned = new ArrayList<Package>(newList.size());
		if (!country.equals(NO_FILTER_KEYWORD)) {
			for (Package pack : newList) {
				if (getParkByCode(pack.getParkCode()).getCountry().equals(country)) {
					listToBeReturned.add(pack);
				}
			}
		} else {
			listToBeReturned = newList;
		}

		return listToBeReturned;
	}

	public List<Package> filterPackByPark(List<Package> newList, String park) {
		List<Package> listToBeReturned = new ArrayList<Package>(newList.size());
		if (!park.equals(NO_FILTER_KEYWORD)) {
			for (Package pack : newList) {
				if (getParkByCode(pack.getParkCode()).getName().equals(park)) {
					listToBeReturned.add(pack);
				}
			}
		} else {
			listToBeReturned = newList;
		}

		return listToBeReturned;
	}

	public List<Package> filterPackByTypeOfAcc(List<Package> newList, String type) {
		List<Package> listToBeReturned = new ArrayList<Package>(newList.size());
		if (!type.equals(NO_FILTER_KEYWORD)) {
			for (Package pack : newList) {
				if (getAccommodationByCode(pack.getAccommodationCode()).getType().toString().toLowerCase()
						.equals(type)) {
					listToBeReturned.add(pack);
				}
			}
		} else {
			listToBeReturned = newList;
		}
		return listToBeReturned;
	}

	public List<Package> filterPacksByBreakfast(List<Package> newList) {
		List<Package> listToBeReturned = new ArrayList<Package>(newList.size());
		for (Package pack : newList) {
			if (getAccommodationByCode(pack.getAccommodationCode()).getType().equals(TypeOfAccomodation.HOTEL)) {
				listToBeReturned.add(pack);
			}
		}
		return listToBeReturned;

	}

	public ThemePark getParkByName(String name) {
		ThemePark finalPark = null;
		for (ThemePark park : themeParks) {
			if (park.getName().equals(name)) {
				finalPark = park;
				break;
			}
		}
		return finalPark;
	}

	public Package getPackByName(String selectedName) {
		Package pack = null;
		for (Package thePack : packages) {
			if (thePack.getName().equals(selectedName)) {
				pack = thePack;
				break;
			}
		}

		return pack;
	}

	public List<Accommodation> searchAcc(String text) {
		List<Accommodation> results = new ArrayList<Accommodation>();
		for (Accommodation place : accommodations) {
			if (place.getName().toLowerCase().equals(text.toLowerCase())) {
				results.add(place);
			}
		}
		return results;
	}

	public List<ThemePark> searchPark(String text) {
		List<ThemePark> results = new ArrayList<ThemePark>();
		for (ThemePark park : themeParks) {
			if (park.getName().toLowerCase().equals(text.toLowerCase())) {
				results.add(park);
			}
		}
		return results;
	}

	public List<Package> searchPack(String text) {
		List<Package> results = new ArrayList<Package>();
		for (Package pack : packages) {
			if (pack.getName().toLowerCase().equals(text.toLowerCase())) {
				results.add(pack);
			}
		}
		return results;
	}

}
