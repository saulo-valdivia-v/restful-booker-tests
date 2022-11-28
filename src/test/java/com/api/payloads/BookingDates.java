package com.api.payloads;

/**
 * Object to represent BookingDates and related fields used as Payloads or Results by Request and Response
 */
public class BookingDates {
    private String checkin;
	private String checkout;
	
	
	public BookingDates() {
		super();
	}
	
	public BookingDates(String checkin, String checkout) {
		super();
		this.checkin = checkin;
		this.checkout = checkout;
	}
	public String getCheckin() {
		return checkin;
	}
	public void setCheckin(String checkin) {
		this.checkin = checkin;
	}
	public String getCheckout() {
		return checkout;
	}
	public void setCheckout(String checkout) {
		this.checkout = checkout;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;
		
		BookingDates that = (BookingDates) o;
		return checkin.equals(that.checkin) &&
				checkout.equals(that.checkout);
	}
}
