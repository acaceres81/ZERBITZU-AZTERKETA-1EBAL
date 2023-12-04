package azterketa;

import java.util.concurrent.Semaphore;

public class Chef {
    private Semaphore sem;
    private int chefId;
    private int dinerId;
    
    
    public Chef(int chefId) {
        this.sem = new Semaphore(1);
        this.chefId = chefId;
        this.dinerId = 0;
    }


    public Semaphore getSem() {
        return sem;
    }


    public void setSem(Semaphore sem) {
        this.sem = sem;
    }


    public int getChefId() {
        return chefId;
    }


    public void setChefId(int chefId) {
        this.chefId = chefId;
    }


    public int getDinerId() {
        return dinerId;
    }


    public void setDinerId(int dinerId) {
        this.dinerId = dinerId;
    }
    
    public void simulateCook(int chef, String foodOption) throws InterruptedException {
        System.out.println("Chef " + chef + " is cooking " + foodOption);
        Thread.sleep((int) (Math.random() * 2000)+2000);
        System.out.println("Chef " + chef + " served the " + foodOption);
    }
}
