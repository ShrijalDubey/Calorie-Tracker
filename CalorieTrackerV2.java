import java.util.Scanner;

class CalorieTrackerV2 {

    static final int MAX = 100;

    // ================= USER LOG ARRAYS =================
    static String[] foodName = new String[MAX];
    static int[] calories = new int[MAX];
    static int[] protein = new int[MAX];
    static int[] carbs = new int[MAX];
    static int[] fat = new int[MAX];
    static String[] mealType = new String[MAX];
    static int count = 0;

    static Scanner sc = new Scanner(System.in);

    // ================= USER GOALS =================
    static int calorieGoal, proteinGoal, carbGoal, fatGoal;
    static double bmi;
    static String userGoal;

    // ================= FOOD DATABASE =================
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

    // ================= MAIN =================
    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║           CALORIE TRACKER            ║");
        System.out.println("╚══════════════════════════════════════╝");

        setupUserHealth();

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
                case 6 -> System.out.println("\nThank you for using Calorie Tracker.");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 6);
    }

    // Collects user data and calculates calorie & macro goals
    static void setupUserHealth() {

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

    // Adds food entry and loops until user exits
    static void addEntry() {

        char again;
        do {

            if (count >= MAX) {
                System.out.println("Food log full.");
                return;
            }

            System.out.print("\nFood name: ");
            String userFood = sc.nextLine();

            int index = -1;
            for (int i = 0; i < foodDBSize; i++) {
                if (foodDB[i].equalsIgnoreCase(userFood)) {
                    index = i;
                    break;
                }
            }

            if (index == -1) {
                System.out.println("Food not found in database.");
            } else {

                System.out.print("Quantity (servings): ");
                int qty = sc.nextInt();

                System.out.println("Meal Type: 1 Breakfast | 2 Lunch | 3 Dinner | 4 Snacks");
                int m = sc.nextInt();
                sc.nextLine();

                mealType[count] = (m == 1) ? "Breakfast" :
                                  (m == 2) ? "Lunch" :
                                  (m == 3) ? "Dinner" : "Snacks";

                calories[count] = foodDBCalories[index] * qty;
                protein[count]  = foodDBProtein[index] * qty;
                carbs[count]    = foodDBCarbs[index] * qty;
                fat[count]      = foodDBFat[index] * qty;
                foodName[count] = foodDB[index];

                count++;

                System.out.println("\n╔══════════ FOOD ADDED ══════════╗");
                System.out.println("Food     : " + foodName[count - 1]);
                System.out.println("Meal     : " + mealType[count - 1]);
                System.out.println("Calories : " + calories[count - 1] + " kcal");
                System.out.println("Protein  : " + protein[count - 1] + " g");
                System.out.println("Carbs    : " + carbs[count - 1] + " g");
                System.out.println("Fat      : " + fat[count - 1] + " g");
                System.out.println("╚════════════════════════════════╝");
            }

            System.out.println("\n──────────────────────────────");
            System.out.print("Add more food? (Y/N): ");
            again = sc.next().charAt(0);
            sc.nextLine();

        } while (again == 'Y' || again == 'y');
    }

    // Removes food entry
    static void removeEntry() {
        if (count == 0) return;

        viewEntries();
        System.out.print("Entry number to remove: ");
        int idx = sc.nextInt() - 1;

        if (idx < 0 || idx >= count) return;

        for (int i = idx; i < count - 1; i++) {
            foodName[i] = foodName[i + 1];
            calories[i] = calories[i + 1];
            protein[i] = protein[i + 1];
            carbs[i] = carbs[i + 1];
            fat[i] = fat[i + 1];
            mealType[i] = mealType[i + 1];
        }
        count--;

        System.out.println("Entry removed.");
    }

    // Displays food log
    static void viewEntries() {
        if (count == 0) {
            System.out.println("No food logged yet.");
            return;
        }

        System.out.println("\n════════ FOOD LOG ════════");
        for (int i = 0; i < count; i++) {
            System.out.println(
                (i + 1) + ". " + mealType[i] +
                " | " + foodName[i] +
                " | " + calories[i] + " kcal" +
                " | P:" + protein[i] +
                " C:" + carbs[i] +
                " F:" + fat[i]
            );
        }
    }

    // Shows total intake
    static void totalMacros() {
        int c = 0, p = 0, cb = 0, f = 0;
        for (int i = 0; i < count; i++) {
            c += calories[i];
            p += protein[i];
            cb += carbs[i];
            f += fat[i];
        }

        System.out.println("\n╔════════ DAILY TOTAL ════════╗");
        System.out.println("Calories : " + c + " / " + calorieGoal);
        System.out.println("Protein  : " + p + " / " + proteinGoal);
        System.out.println("Carbs    : " + cb + " / " + carbGoal);
        System.out.println("Fat      : " + f + " / " + fatGoal);
        System.out.println("╚═════════════════════════════╝");
    }

    // Shows remaining goals
    static void remainingCalories() {
        int c = 0, p = 0, cb = 0, f = 0;
        for (int i = 0; i < count; i++) {
            c += calories[i];
            p += protein[i];
            cb += carbs[i];
            f += fat[i];
        }

        System.out.println("\n╔══════ REMAINING ══════╗");
        System.out.println("Calories : " + (calorieGoal - c));
        System.out.println("Protein  : " + (proteinGoal - p));
        System.out.println("Carbs    : " + (carbGoal - cb));
        System.out.println("Fat      : " + (fatGoal - f));
        System.out.println("╚═══════════════════════╝");
    }
}
