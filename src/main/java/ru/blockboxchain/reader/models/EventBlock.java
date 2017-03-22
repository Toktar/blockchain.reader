package ru.blockboxchain.reader.models;

import ru.blockboxchain.reader.models.events.CarEvent;

import java.util.List;

/**
 * Created by toktar.
 */
public class EventBlock {

    private String parentHash;
    private List<CarEvent> event;
    private String eventHash;
    private long timestamp;
    private String signature;
    private long nonce =0;

    public String getEventHash() {
        return eventHash;
    }

    public void setEventHash(String eventHash) {
        this.eventHash = eventHash;
    }



    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }



    public String getParentHash() {
        return parentHash;
    }

    public void setParentHash(String parentHash) {
        this.parentHash = parentHash;
    }

    public List<CarEvent> getEvent() {
        return event;
    }

    public void setEvent(List<CarEvent> event) {
        this.event = event;
    }

    public long getNonce() {
        return nonce;
    }

    public void setNonce(long nonce) {
        this.nonce = nonce;
    }

    public byte[] getHeader() {
        //TODO
        return new byte[0];
    }
}
