package bee;

import world.BeeHive;
import world.QueensChamber;

/**
 * The male drone bee has a tough life.  His only job is to mate with the queen
 * by entering the queen's chamber and awaiting his royal highness for some
 * sexy time.  Unfortunately his reward from mating with the queen is his
 * endophallus gets ripped off and he perishes soon after mating.
 *
 * @author Sean Strout @ RIT CS
 * @author Isaias Villalobos
 */
public class Drone extends Bee {

    private final int SLEEP_TIME_MS = 1000;
    private boolean mated;

    /**
     * When the drone is created they should retrieve the queen's
     * chamber from the bee hive and initially the drone has not mated.
     *
     * @param beeHive the bee hive
     */
    public Drone(BeeHive beeHive){
        super(Role.DRONE, beeHive);
    }

    /**
     * When the drone runs, they check if the bee hive is active.  If so,
     * display a message:<br>
     * <br>
     * <tt>"*D* {bee} enters queen's chamber</tt><br>
     * <br>
     * they perform their sole task of entering the queen's chamber.
     * If they return from the chamber, it can mean only one of two
     * things.  If they mated with the queen, they sleep for the
     * required mating time, and then perish (the beehive should be
     * notified of this tragic event).  You should display a message:<br>
     * <br>
     * <tt>*D* {bee} has perished!</tt><br>
     * <br>
     * <br>
     * Otherwise if the drone has not mated it means they survived the
     * simulation and they should end their run without any
     * sleeping.
     */

    public void run() {
        if(beeHive.isActive()){
//            System.out.println("*D* " + Role.DRONE + " enters the queen's chamber.");
            QueensChamber x = beeHive.getQueensChamber();
            x.enterChamber(this);
            if(mated){
                try {
                    Thread.sleep(SLEEP_TIME_MS);
                    beeHive.beePerished();
                    System.out.println("*D* " + Role.DRONE +  " has perished!");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    /**
     * The queen will let the drone know when they have mated.
     */
    public void setMated() {
        this.mated = true;
    }
}