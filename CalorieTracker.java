import java.util.Scanner;

/* ================= USER BASE CLASS ================= */
class User {
    static int calorieGoal, proteinGoal, carbGoal, fatGoal;
    static double bmi;
    static String userGoal;

    void setupUserHealth(Scanner sc) {

        System.out.println("\n──────── USER DETAILS ────────");
        System.out.print("Age (years): ");
        int age = sc.nextInt();

        System.out.print("Weight (kg): ");
        double weight = sc.nextDouble();

        System.out.print("Height (cm): ");
        double height = sc.nextDouble();

        System.out.print("Gender (M/F): ");
        char gender = sc.next().charAt(0);

        double bmr = (gender == 'M' || gender == 'm')
                ? 10 * weight + 6.25 * height - 5 * age + 5
                : 10 * weight + 6.25 * height - 5 * age - 161;

        int maintenance = (int)(bmr * 1.2);

        System.out.println("\nChoose Goal:");
        System.out.println("1 => Lose Weight");
        System.out.println("2 => Maintain Weight");
        System.out.println("3 => Gain Weight");
        System.out.print("Choice: ");
        int g = sc.nextInt();

        calorieGoal = (g == 1) ? maintenance - 500 :
                      (g == 3) ? maintenance + 500 :
                      maintenance;

        userGoal = (g == 1) ? "Lose" : (g == 3) ? "Gain" : "Maintain";

        proteinGoal = (int)((calorieGoal * 0.20) / 4);
        carbGoal    = (int)((calorieGoal * 0.50) / 4);
        fatGoal     = (int)((calorieGoal * 0.30) / 9);

        bmi = weight / Math.pow(height / 100, 2);

        System.out.println("\n╔══════════ DAILY GOALS ══════════╗");
        System.out.println("Goal      : " + userGoal);
        System.out.println("Calories  : " + calorieGoal + " kcal");
        System.out.println("Protein   : " + proteinGoal + " g");
        System.out.println("Carbs     : " + carbGoal + " g");
        System.out.println("Fat       : " + fatGoal + " g");
        System.out.println("BMI       : " + String.format("%.2f", bmi));
        System.out.println("╚═════════════════════════════════╝");
    }
}

/* ================= FOOD LOG CLASS ================= */
class FoodLog {
    static final int MAX = 100;

    String[] foodName = new String[MAX];
    int[] calories = new int[MAX];
    int[] protein = new int[MAX];
    int[] carbs = new int[MAX];
    int[] fat = new int[MAX];
    String[] mealType = new String[MAX];
    int count = 0;
}

/* ================= MAIN TRACKER ================= */
public class CalorieTracker extends User {

    static Scanner sc = new Scanner(System.in);
    static FoodLog log = new FoodLog();

    // ===== FOOD DATABASE =====
    static String[] foodDB = {
        "roti","chapati","rice","dal","paneer","samosa","idli","dosa","poha","upma",
        "biryani","curd","butter chicken","rajma","chole","pav bhaji","vada pav","paratha",
        "khichdi","pulao","aloo sabzi","palak paneer","veg curry","naan","fried rice",
        "jeera rice","lemon rice","coconut rice","masala dosa","uttapam"
    };

    static int[] foodDBCalories = {
        100,100,200,150,250,300,60,180,120,170,
        350,80,400,240,260,380,290,220,
        180,230,150,260,200,260,250,
        230,240,260,280,250
    };

    static int[] foodDBProtein = {
        3,3,4,9,18,6,2,6,3,4,
        12,4,26,15,14,10,7,6,
        8,7,3,18,6,5,7,
        4,4,4,6,5
    };

    static int[] foodDBCarbs = {
        20,20,45,25,8,30,12,35,25,30,
        55,6,5,30,35,40,35,30,
        35,40,20,10,30,45,50,
        45,48,50,40,38
    };

    static int[] foodDBFat = {
        1,1,1,4,20,15,1,6,3,5,
        8,3,30,6,5,18,12,8,
        5,6,4,18,8,5,6,
        4,4,5,6,5
    };

    static int foodDBSize = foodDB.length;

    public static void main(String[] args) {

        CalorieTrackerV2 tracker = new CalorieTrackerV2();

        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║           CALORIE TRACKER            ║");
        System.out.println("╚══════════════════════════════════════╝");

        tracker.setupUserHealth();

        int choice;
        do {
            System.out.println("\n╔═════════════ MAIN MENU ══════════════╗");
            System.out.println("║ 1  =>  Add Food Entry                ║");
            System.out.println("║ 2  =>  Remove Food Entry             ║");
            System.out.println("║ 3  =>  View Food Log                 ║");
            System.out.println("║ 4  =>  Daily Totals                  ║");
            System.out.println("║ 5  =>  Remaining Goals               ║");
            System.out.println("║ 6  =>  Exit                          ║");
            System.out.println("╚══════════════════════════════════════╝");
            System.out.print("Select option: ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> addEntry();
                case 2 -> removeEntry();
                case 3 -> viewEntries();
                case 4 -> totalMacros();
                case 5 -> remainingCalories();
            }
        } while (choice != 6);

        System.out.println("\nThank you for using Calorie Tracker.");
    }

    // ===== METHODS (UNCHANGED) =====
    static void addEntry() {
        if (log.count >= FoodLog.MAX) return;

        System.out.print("\nFood name: ");
        String userFood = sc.nextLine();

        int index = -1;
        for (int i = 0; i < foodDBSize; i++) {
            if (foodDB[i].equalsIgnoreCase(userFood)) {
                index = i;
                break;
            }
        }
        if (index == -1) return;

        System.out.print("Quantity: ");
        int qty = sc.nextInt();

        System.out.println("Meal Type: 1 Breakfast | 2 Lunch | 3 Dinner | 4 Snacks");
        int m = sc.nextInt();
        sc.nextLine();

        log.mealType[log.count] =
                (m == 1) ? "Breakfast" :
                (m == 2) ? "Lunch" :
                (m == 3) ? "Dinner" : "Snacks";

        log.foodName[log.count] = foodDB[index];
        log.calories[log.count] = foodDBCalories[index] * qty;
        log.protein[log.count]  = foodDBProtein[index] * qty;
        log.carbs[log.count]    = foodDBCarbs[index] * qty;
        log.fat[log.count]      = foodDBFat[index] * qty;

        System.out.println("\n╔══════════ FOOD ADDED ══════════╗");
        System.out.println("Food     : " + log.foodName[log.count]);
        System.out.println("Calories : " + log.calories[log.count]);
        System.out.println("Protein  : " + log.protein[log.count]);
        System.out.println("Carbs    : " + log.carbs[log.count]);
        System.out.println("Fat      : " + log.fat[log.count]);
        System.out.println("╚════════════════════════════════╝");

        log.count++;
    }

    static void removeEntry() {
        if (log.count == 0) return;
        viewEntries();
        int idx = sc.nextInt() - 1;
        if (idx < 0 || idx >= log.count) return;

        for (int i = idx; i < log.count - 1; i++) {
            log.foodName[i] = log.foodName[i + 1];
            log.calories[i] = log.calories[i + 1];
            log.protein[i] = log.protein[i + 1];
            log.carbs[i] = log.carbs[i + 1];
            log.fat[i] = log.fat[i + 1];
            log.mealType[i] = log.mealType[i + 1];
        }
        log.count--;
    }

    static void viewEntries() {
        for (int i = 0; i < log.count; i++) {
            System.out.println((i + 1) + ". " + log.mealType[i] + " | " +
                    log.foodName[i] + " | " + log.calories[i] + " kcal");
        }
    }

    static void totalMacros() {
        int c=0,p=0,cb=0,f=0;
        for (int i=0;i<log.count;i++) {
            c+=log.calories[i];
            p+=log.protein[i];
            cb+=log.carbs[i];
            f+=log.fat[i];
        }
        System.out.println("Calories : " + c + " / " + calorieGoal);
        System.out.println("Protein  : " + p + " / " + proteinGoal);
        System.out.println("Carbs    : " + cb + " / " + carbGoal);
        System.out.println("Fat      : " + f + " / " + fatGoal);
    }

    static void remainingCalories() {
        int c=0,p=0,cb=0,f=0;
        for (int i=0;i<log.count;i++) {
            c+=log.calories[i];
            p+=log.protein[i];
            cb+=log.carbs[i];
            f+=log.fat[i];
        }
        System.out.println("Calories : " + (calorieGoal - c));
        System.out.println("Protein  : " + (proteinGoal - p));
        System.out.println("Carbs    : " + (carbGoal - cb));
        System.out.println("Fat      : " + (fatGoal - f));
    }
}
