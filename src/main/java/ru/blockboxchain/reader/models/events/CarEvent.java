package ru.blockboxchain.reader.models.events;

/**
 * Created by toktar.
 */
public abstract class CarEvent extends ICarEvent  implements Comparable {

    private long timestamp;
    private String eventId;
    private long mileage;

    @Override
    public int compareTo(Object obj) {
        CarEvent carEvent = (CarEvent)obj;
        return carEvent.getEventId().equals(this.getEventId())?0:-1;

    }

    public long getMileage() {
        return mileage;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getEventId() {
        return eventId;
    }

}
