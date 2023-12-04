package azterketa;

import java.util.concurrent.Semaphore;

public class Restaurant {
    private Chef[] chefs;

    public Restaurant() {
        this.chefs = new Chef[3];
        for (int i = 0; i < chefs.length; i++) {
            chefs[i] = new Chef(i + 1);
        }
    }

    public Chef[] getChefs() {
        return chefs;
    }

    public void setChefs(Chef[] chefs) {
        this.chefs = chefs;
    }
}