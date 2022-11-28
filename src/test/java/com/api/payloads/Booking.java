package com.api.payloads;

/**
 * Object to represent Booking and related fields used as Payloads or Results by Request and Response
 */
public class Booking {
    private String firstname;
	private String lastname;
	private long totalprice;
	private boolean depositpaid;	
    private BookingDates bookingdates;
	private String additionalneeds;

    public Booking() {
		super();
	}
	
	public Booking(String firstname, String lastname, long totalprice, boolean depositpaid, BookingDates bookingdates, String additionalneeds) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.totalprice = totalprice;
		this.depositpaid = depositpaid;		
        this.bookingdates = bookingdates;
		this.additionalneeds = additionalneeds;
	}

    public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public long getTotalprice() {
		return totalprice;
	}

	public boolean getDepositpaid() {
		return depositpaid;
	}

    public BookingDates getBookingdates() {
		return bookingdates;
	}

	public String getAdditionalneeds() {
		return additionalneeds;
	}

    public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

    public void setTotalprice(long totalprice) {
		this.totalprice = totalprice;
	}

    public void setDepositpaid(boolean depositpaid) {
        this.depositpaid = depositpaid;
    }

    public void setBookingdates(BookingDates bookingdates) {
        this.bookingdates = bookingdates;
    }

    public void setAdditionalneeds(String additionalneeds) {
		this.additionalneeds = additionalneeds;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;
		
		Booking that = (Booking) o;
		return firstname.equals(that.firstname) &&
				lastname.equals(that.lastname) &&
				totalprice == that.totalprice &&
				depositpaid == that.depositpaid &&
				additionalneeds.equals(that.additionalneeds) &&
				bookingdates.equals(that.bookingdates);
	}
}
