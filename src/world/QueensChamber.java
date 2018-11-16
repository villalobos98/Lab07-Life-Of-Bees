package world;

import bee.Drone;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;


/**
 * The queen's chamber is where the mating ritual between the queen and her
 * drones is conducted.  The drones will enter the chamber in order.
 * If the queen is ready and a drone is in here, the first drone will
 * be summoned and mate with the queen.  Otherwise the drone has to wait.
 * After a drone mates they perish, which is why there is no routine
 * for exiting (like with the worker bees and the flower field).
 *
 * @author Sean Strout @ RIT CS
 * @author Isaias Villalobos
 */
public class QueensChamber {
    private final int SLEEP_TIME_MS = 1000;
    Queue<Drone> queueDrones;
    boolean queenReady;

    /**
     * constructor, creates new Queue structure and sets Queen mate status to false
     */
    public QueensChamber() {
        queueDrones = new ConcurrentLinkedQueue<>();
        queenReady = false;
    }

    /**
     * The bees should be stored in some queue like collection.
     * If the queen is ready and this drone is at the front of the collection, they are allowed to mate. Otherwise they must wait.
     * @param drone bee
     */
    public synchronized void enterChamber(Drone drone) {

        System.out.println("*QC* " + drone + " enters chamber");
        queueDrones.add(drone);
        while(!queueDrones.element().equals(drone) || !queenReady) //could write if this hasDrone
        {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        queueDrones.remove();
        queenReady = false;
        System.out.println("*QC* " + drone + " leaves chamber");


    }

    /**
     * When the queen is ready, they will summon the next drone from the collection (if at least one is there).
     * The queen will mate with the first drone and display a message:
     */
    public synchronized void summonDrone() {
        if(!queueDrones.isEmpty()) {
            Drone d = queueDrones.peek();
            this.queenReady = true;
            d.setMated();
            System.out.println("*QC* Queen mates with " + d);
            notifyAll();
        }
        else{
            System.out.println("prints");
        }
    }

    /**
     * At the end of the simulation the queen uses this routine repeatedly
     * to dismiss all the drones that were waiting to mate.
     */
    public synchronized void dismissDrone() {
        queenReady = true;
        notifyAll();
    }

    /**
     * Are there any waiting drones? The queen uses this to check if she can mate,
     * and also in conjunction with dismissDrone().
     * @return boolean
     */
    public boolean hasDrone() {
        return queueDrones.size() > 0 ;
    }
}

