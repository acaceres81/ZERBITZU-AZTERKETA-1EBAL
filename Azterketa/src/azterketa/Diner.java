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
                // diner enters the restaurant
                System.out.println("Diner " + dinerId + " entered the restaurant and is looking at the options.");
                Thread.sleep(1000);   
                
                //diners choose the type of food they will it
                option = (int) (Math.random() * 3);
                System.out.println("Diner " + dinerId + " would like to eat " + foodOption(option));
                Thread.sleep(1000);
                
                // Try to sit at the table. If table is taken he will wait.
                chef = restaurant.getChefs()[option];
                chef.getSem().acquire();

                //if table is empty
                if (chef.getDinerId() == 0){
                    chef.setDinerId(dinerId); // ocupy the table by seting dinerID in chef.
                    System.out.println("Diner " + dinerId + " sat down to eat " + foodOption(option));
                    chef.simulateCook(chef.getChefId(), foodOption(option)); // simulate chef is cooking
                    simulateEat(foodOption(option)); // Simulate diner is eating
                    
                    
                    //release the table and set dinerId to 0
                    chef.getSem().release();
                    chef.setDinerId(0);
                    
                    // Wait 10 seconds before re-entering the restaurant
                    Thread.sleep(10000); 
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Method to simulate diner is eating
    public void simulateEat(String food) throws InterruptedException {
        System.out.println("Diner " + dinerId + " is eating " + food + ".");
        Thread.sleep((int) (Math.random() * 5000)+1000);
        System.out.println("Diner " + dinerId + " finished eating " + food + " and is leaving the restaurant.");
    }

    // Method to get food name based on option chosen by diner
    public String foodOption(int option) {
        switch (option) {
            case 0:
                return "sushi";
            case 1:
                return "pasta";
            default:
                return "marmitako";
        }
    }
}
