package ru.blockboxchain.reader.models.events;

/**
 * Created by toktar.
 */
public class CarStartEvent extends CarEvent {

    private String driverId;
    private String tripId;

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }
}
