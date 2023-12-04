package azterketa;

public class Diner extends Thread {
    private Restaurant restaurant;
    private int dinerId;

    public Diner(Restaurant restaurant, int dinerId) {
        this.restaurant = restaurant;
        this.dinerId = dinerId;
    }

    @Override
    public void run() {
        try {
            Chef chef;
            int option;

            while (true) {
                //diners choose the type of food they will it
                option = (int) (Math.random() * 3);
                //go to chef that coocks that meal
                chef = restaurant.getChefs()[option];
                
                System.out.println("Diner " + dinerId + " entered the restaurant.");

                // Try to sit at the table.
                chef.getSem().acquire();

                //if table is empty
                if (chef.getDinerId() == 0){
                    chef.setDinerId(dinerId); // ocupy the table by seting dinerID in chef.
                    System.out.println("Diner " + dinerId + " sat down to eat " + foodOption(option));
                    chef.simulateCook(chef.getChefId(), foodOption(option)); // simulate chef is cooking
                    Thread.sleep((int) (Math.random() * 5000) + 1000); // Simulate diner is eating
                    System.out.println("Diner " + dinerId + " finished eating " + foodOption(option) + " and is leaving the restaurant.");
                    
                    //release the table and set dinerId to 0
                    chef.getSem().release();
                    chef.setDinerId(0);
                    
                    // Wait 10 seconds before re-entering the restaurant
                    Thread.sleep(10000); 
                
                // if table is ocupied.
                }else{
                    System.out.println("Diner " + dinerId + " is waiting to eat " + foodOption(option));
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Method to simulate diner is eating
    public void simulateEat() throws InterruptedException {
        System.out.println("Diner " + dinerId + " is eating.");
        Thread.sleep((int) (Math.random() * 5000)+1000);
    }

    // Method to get food name based on option chosen by diner
    public String foodOption(int option) {
        switch (option) {
            case 0:
                return "sushi";
            case 1:
                return "patsa";
            default:
                return "marmitako";
        }
    }
}
