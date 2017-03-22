package ru.blockboxchain.reader.blockchain;


import java.io.*;
import java.security.GeneralSecurityException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Kida on 19.03.2017.
 */
public class Miner extends Thread {


    private List<EventBlock> eventList;
    public volatile List<CarEvent> pendingList;


    private byte[] target;
   // BlockController blockController;

    public Miner() {
       // this.blockController = blockController;
        eventList = new LinkedList<>();
        pendingList = new LinkedList<>();
        target = new byte[32];
        for (int i = 0; i < 32; i++) {
            target[i] = (byte) (i < 3 ? 0 : 0xFF); //TODO param
        }
    }


    /**
     * add Event pendingList - list of pending event, save pending event in file
     * For listener
     * @param newEvent
     * @throws IOException
     */
    public void addEvent(CarEvent newEvent) throws IOException {
        pendingList.add(newEvent);
        savePendingEventsToFile();
    }

    /**
     * Start of Mainer
     * Detect new event in pendingList and generateNonce
     * TODO: Stand lists from files (backup)
     */
    @Override
    public void run() {
        int countOfWaitingEvent = pendingList.size();
        while (true) {
            Lock lock = new ReentrantLock();
            lock.lock();
            try {
                if(pendingList.isEmpty() || countOfWaitingEvent==pendingList.size()) {
                    continue;
                }
            } finally {
                lock.unlock();
            }

            generateNonce(countOfWaitingEvent);
        }
    }

    private void generateNonce(int curPendingEvent) {
        boolean nonceNotFound = true;
        int nonce = 0;
        Hasher hasher = null;

        EventBlock eventBlock = new EventBlock();
        eventBlock.setEvent(pendingList);
        eventBlock.setParentHash(eventList.isEmpty()?"":eventList.get(eventList.size()-1).getEventHash());
        //TODO - fill Block
        try {
            hasher = new Hasher();
            byte[] header = eventBlock.getHeader();

            while (nonce < Integer.MAX_VALUE) {
                if(curPendingEvent!=pendingList.size()) return;
                if (tryNonce(nonce, hasher, header)) break;
                nonce++;
            }

        } catch (GeneralSecurityException e) {
            throw new RuntimeException("hasher error"); //TODO
        }

        if (nonce == Integer.MAX_VALUE) {
            throw new RuntimeException("nonce doesn't exists"); //TODO
        } else {
            eventBlock.setNonce(nonce);
            try {
                saveNewBlock(eventBlock);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Save error: " + e.getMessage());
            }
        }
    }

    /**
     * save block with nonce in list and file, delete events by block from pending list.
     * Save pending list
     * @param eventBlock
     * @throws IOException
     */
    private void saveNewBlock(EventBlock eventBlock) throws IOException {
        eventList.add(eventBlock);
        for (CarEvent event : eventBlock.getEvent()) {
            pendingList.remove(event);
        }
        saveBlockListToFile((eventList.size()/100)*100, eventList.size()-1);
        savePendingEventsToFile();
    }


    private boolean tryNonce(int nonce, Hasher hasher, byte[] header) throws GeneralSecurityException {
        byte[] hash = hasher.hash(header, nonce);
        for (int i = hash.length - 1; i >= 0; i--) {
            if ((hash[i] & 0xff) > (target[i] & 0xff))
                return false;
            if ((hash[i] & 0xff) < (target[i] & 0xff))
                return true;
        }
        return true;
    }

    private void saveBlockListToFile(int startPosition, int endPosition) throws IOException {
        List<EventBlock> curList = new LinkedList<>();
        for (int i = startPosition; i <= endPosition; i++) {
            curList.add(eventList.get(i));
        }

        FileOutputStream fos = new FileOutputStream("history_storage\\history_" + startPosition
                + "_" + endPosition + ".out"); //TODO write file every day
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(curList);
        oos.flush();
        oos.close();

    }

    private void savePendingEventsToFile() throws IOException {
        FileOutputStream fos = new FileOutputStream("tmp\\pending_event.out");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(pendingList);
        oos.flush();
        oos.close();
    }

    private void getListFromFile(int startPosition, int endPosition) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("history_storage\\history_" + startPosition
                + "_" + endPosition + ".out");
        ObjectInputStream oin = new ObjectInputStream(fis);
        List<EventBlock> curList = (LinkedList<EventBlock>) oin.readObject();

    }

}
