import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class TicketBookingSystem {
    private int availableSeats = 5;
    private final Lock lock = new ReentrantLock();

    public void bookTicket(String name) {
        lock.lock();
        try {
            if (availableSeats > 0) {
                System.out.println(name + " successfully booked a seat. Remaining seats: " + (--availableSeats));
            } else {
                System.out.println(name + " failed to book a seat. No seats available.");
            }
        } finally {
            lock.unlock();
        }
    }
}

class BookingThread extends Thread {
    private final TicketBookingSystem system;
    private final String userName;

    public BookingThread(TicketBookingSystem system, String userName, int priority) {
        this.system = system;
        this.userName = userName;
        setPriority(priority);
    }

    @Override
    public void run() {
        system.bookTicket(userName);
    }
}

public class TicketBooking{
    public static void main(String[] args) {
        TicketBookingSystem system = new TicketBookingSystem();

        BookingThread vip1 = new BookingThread(system, "VIP-1", Thread.MAX_PRIORITY);
        BookingThread vip2 = new BookingThread(system, "VIP-2", Thread.MAX_PRIORITY);
        BookingThread user1 = new BookingThread(system, "User-1", Thread.NORM_PRIORITY);
        BookingThread user2 = new BookingThread(system, "User-2", Thread.NORM_PRIORITY);
        BookingThread user3 = new BookingThread(system, "User-3", Thread.MIN_PRIORITY);
        BookingThread user4 = new BookingThread(system, "User-4", Thread.MIN_PRIORITY);

        vip1.start();
        vip2.start();
        user1.start();
        user2.start();
        user3.start();
        user4.start();
    }
}
