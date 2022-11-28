package com.api.payloads;

/**
 * Object to represent BookingResponse and related fields returned as Response by some endpoints
 */
public class BookingResponse {
    private int bookingid;
	private Booking booking;
	private String payload;

    public BookingResponse() {
		super();
	}
	
	public BookingResponse(int bookingid, Booking booking, String payload) {
		super();
		this.bookingid = bookingid;
		this.booking = booking;		
		this.payload = payload;
	}

    public int getBookingid() {
		return bookingid;
	}

    public void setBookingid(int bookingid) {
		this.bookingid = bookingid;
	}

    public Booking getBooking() {
		return booking;
	}

    public void setBooking(Booking booking) {
		this.booking = booking;
	}
	
	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}
}
