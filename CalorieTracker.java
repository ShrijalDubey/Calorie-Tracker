import java.util.Scanner;

/* ================= BASE CLASS ================= */
class FoodItem {
    String name;
    int calories;
    int protein;
    int carbs;
    int fat;

    FoodItem(String name, int calories, int protein, int carbs, int fat) {
        this.name = name;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
    }
}

/* ================= CHILD CLASS (INHERITANCE) ================= */
class FoodLog extends FoodItem {
    String mealType;

    FoodLog(String name, int calories, int protein, int carbs, int fat, String mealType) {
        super(name, calories, protein, carbs, fat);
        this.mealType = mealType;
    }
}

class CalorieTracker {

    static final int MAX = 100;

    // ================= USER LOG ARRAYS =================
    static FoodLog[] logs = new FoodLog[MAX];
    static int count = 0;

    static Scanner sc = new Scanner(System.in);

    // ================= USER GOALS =================
    static int calorieGoal, proteinGoal, carbGoal, fatGoal;
    static double bmi;
    static String userGoal;

    // ================= FOOD DATABASE =================
    static FoodItem[] foodDB = {
            new FoodItem("roti",100,3,20,1),
            new FoodItem("chapati",100,3,20,1),
            new FoodItem("rice",200,4,45,1),
            new FoodItem("dal",150,9,25,4),
            new FoodItem("paneer",250,18,8,20),
            new FoodItem("samosa",300,6,30,15),
            new FoodItem("idli",60,2,12,1),
            new FoodItem("dosa",180,6,35,6),
            new FoodItem("poha",120,3,25,3),
            new FoodItem("upma",170,4,30,5),
            new FoodItem("biryani",350,12,55,8),
            new FoodItem("curd",80,4,6,3),
            new FoodItem("butter chicken",400,26,5,30),
            new FoodItem("rajma",240,15,30,6),
            new FoodItem("chole",260,14,35,5),
            new FoodItem("pav bhaji",380,10,40,18),
            new FoodItem("vada pav",290,7,35,12),
            new FoodItem("paratha",220,6,30,8),
            new FoodItem("khichdi",180,8,35,5),
            new FoodItem("pulao",230,7,40,6),
            new FoodItem("aloo sabzi",150,3,20,4),
            new FoodItem("palak paneer",260,18,10,18),
            new FoodItem("veg curry",200,6,30,8),
            new FoodItem("naan",260,5,45,5),
            new FoodItem("fried rice",250,7,50,6),
            new FoodItem("jeera rice",230,4,45,4),
            new FoodItem("lemon rice",240,4,48,4),
            new FoodItem("coconut rice",260,4,50,5),
            new FoodItem("masala dosa",280,6,40,6),
            new FoodItem("uttapam",250,5,38,5)
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
            System.out.println("║ 3  =>  Edit Food Quantity            ║");
            System.out.println("║ 4  =>  View Food Log                 ║");
            System.out.println("║ 5  =>  Daily Totals                  ║");
            System.out.println("║ 6  =>  Remaining Goals               ║");
            System.out.println("║ 7  =>  Exit                          ║");
            System.out.println("╚══════════════════════════════════════╝");
            System.out.print("Select option: ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> addEntry();
                case 2 -> removeEntry();
                case 3 -> editEntry();
                case 4 -> viewEntries();
                case 5 -> totalMacros();
                case 6 -> remainingCalories();
                case 7 -> System.out.println("\nThank you for using Calorie Tracker.");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 7);
    }
    //=======================================================
    // Collects user data and calculates calorie & macro goals
    static void setupUserHealth() {

        System.out.println("\n═════════════ USER DETAILS ═════════════");
        System.out.print("Age (years): ");
        int age = sc.nextInt();

        System.out.print("Weight (kg): ");
        double weight = sc.nextDouble();

        System.out.print("Height (cm): ");
        double height = sc.nextDouble();

        char gender;
        while (true) {
            System.out.print("Gender (M/F): ");
            gender = sc.next().toUpperCase().charAt(0);
            if (gender == 'M' || gender == 'F') break;
            System.out.println("Invalid input. Please enter M or F.");
        }

        double bmr = (gender == 'M' || gender == 'm')
                ? 10 * weight + 6.25 * height - 5 * age + 5
                : 10 * weight + 6.25 * height - 5 * age - 161;
        String status;
        bmi = weight / Math.pow(height / 100, 2);

        if (bmi < 18.5) status = "Underweight";
        else if (bmi <= 24.9) status = "Normal Weight";
        else if (bmi <= 29.9) status = "Overweight";
        else status = "Obese";

        System.out.println("BMI Category: " + status);
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
    //===================================================
    // Adds food entry and loops until user exits
    static void addEntry() {
        
        System.out.println("\nSelect Meal Type:");
        System.out.println("1 Breakfast");
        System.out.println("2 Lunch");
        System.out.println("3 Dinner");
        System.out.println("4 Snacks");
        System.out.print("Choice: ");

        int m = sc.nextInt();
        sc.nextLine();

        if (m < 1 || m > 4) {
            System.out.println("Invalid meal type.");
            return;
        }

        System.out.println("\nAvailable Foods:");
        for (int i = 0; i < foodDBSize; i++) {
            System.out.printf("%-15s", foodDB[i].name);
            if ((i + 1) % 5 == 0) System.out.println();
        }
        System.out.println();
        if (count >= MAX) {
            System.out.println("Food log full.");
            return;
        }

        String mealType =
                (m == 1) ? "Breakfast" :
                        (m == 2) ? "Lunch" :
                                (m == 3) ? "Dinner" : "Snacks";

        char again = 'Y';

        // 🔹 Loop for adding multiple foods to SAME meal
        do {

            if (count >= MAX) {
                System.out.println("Food log full.");
                return;
            }

            System.out.print("\nFood name: ");
            String userFood = sc.nextLine();

            FoodItem item = null;
            for (int i = 0; i < foodDBSize; i++) {
                if (foodDB[i].name.equalsIgnoreCase(userFood)) {
                    item = foodDB[i];
                    break;
                }
            }

            if (item == null) {
                System.out.println("Food not found in database.");
            } else {

                System.out.print("Quantity (servings): ");
                int qty = sc.nextInt();
                sc.nextLine();

                logs[count++] = new FoodLog(
                        item.name,
                        item.calories * qty,
                        item.protein * qty,
                        item.carbs * qty,
                        item.fat * qty,
                        mealType
                );

                FoodLog f = logs[count - 1];

                System.out.println("\n╔══════════ FOOD ADDED ══════════╗");
                System.out.println("Food     : " + f.name);
                System.out.println("Meal     : " + f.mealType);
                System.out.println("Calories : " + f.calories + " kcal");
                System.out.println("Protein  : " + f.protein + " g");
                System.out.println("Carbs    : " + f.carbs + " g");
                System.out.println("Fat      : " + f.fat + " g");
                System.out.println("╚════════════════════════════════╝");
            }

            System.out.println("\n════════════════════");
            System.out.print("Add more food to " + mealType + "? (Y/N): ");
            again = sc.next().charAt(0);
            sc.nextLine();

        } while (again == 'Y' || again == 'y');
    }
    //===================================================
    // Removes food entry by index 
    static void removeEntry() {
        if (count == 0) return;

        viewEntries();
        System.out.print("Entry number to remove: ");
        int idx = sc.nextInt() - 1;

        if (idx < 0 || idx >= count) return;

        for (int i = idx; i < count - 1; i++) {
            logs[i] = logs[i + 1];
        }
        count--;

        System.out.println("Entry removed.");
    }
    //===================================================
    // Edits food quantity and updates macros accordingly
    static void editEntry() {

        if (count == 0) {
            System.out.println("No food entries to edit.");
            return;
        }

        viewEntries();

        System.out.print("\nEnter entry number to edit: ");
        int idx = sc.nextInt() - 1;
        sc.nextLine();

        if (idx < 0 || idx >= count) {
            System.out.println("Invalid entry number.");
            return;
        }

        FoodLog log = logs[idx];

        System.out.print("Enter new quantity (servings): ");
        int newQty = sc.nextInt();
        sc.nextLine();

        if (newQty == 0) {
            for (int i = idx; i < count - 1; i++) {
                logs[i] = logs[i + 1];
            }
            count--;
            System.out.println("\nQuantity set to 0. Entry removed.");
            return;
        }

        if (newQty < 0) {
            System.out.println("Invalid quantity. Cannot be negative.");
            return;
        }

        FoodItem base = null;
        for (int i = 0; i < foodDBSize; i++) {
            if (foodDB[i].name.equalsIgnoreCase(log.name)) {
                base = foodDB[i];
                break;
            }
        }

        if (base == null) {
            System.out.println("Error: Food original data not found.");
            return;
        }

        log.calories = base.calories * newQty;
        log.protein  = base.protein  * newQty;
        log.carbs    = base.carbs    * newQty;
        log.fat      = base.fat      * newQty;

        System.out.println("\nQuantity updated successfully!");
    }
    //===================================================
    // Displays all food entries
    static void viewEntries() {
        if (count == 0) {
            System.out.println("No food logged yet.");
            return;
        }

        System.out.println("\n════════ FOOD LOG ════════");
        for (int i = 0; i < count; i++) {
            FoodLog f = logs[i];
            System.out.println(
                    (i + 1) + ". " + f.mealType +
                            " | " + f.name +
                            " | " + f.calories + " kcal" +
                            " | P:" + f.protein +
                            " C:" + f.carbs +
                            " F:" + f.fat
            );
        }
    }
    //===================================================
    // Calculates and displays total macros&calories consumed
    static void totalMacros() {
        int c = 0, p = 0, cb = 0, f = 0;
        for (int i = 0; i < count; i++) {
            c += logs[i].calories;
            p += logs[i].protein;
            cb += logs[i].carbs;
            f += logs[i].fat;
        }

        System.out.println("\n╔════════ DAILY TOTAL ════════╗");
        System.out.println("Calories : " + c + " / " + calorieGoal);
        System.out.println("Protein  : " + p + " / " + proteinGoal);
        System.out.println("Carbs    : " + cb + " / " + carbGoal);
        System.out.println("Fat      : " + f + " / " + fatGoal);
        System.out.println("╚═════════════════════════════╝");
    }
    //===================================================
    // Calculates and displays total macros&calories remaining
    static void remainingCalories() {
        int c = 0, p = 0, cb = 0, f = 0;
        for (int i = 0; i < count; i++) {
            c += logs[i].calories;
            p += logs[i].protein;
            cb += logs[i].carbs;
            f += logs[i].fat;
        }

        System.out.println("\n╔══════ REMAINING ══════╗");
        System.out.println("Calories : " + (calorieGoal - c));
        System.out.println("Protein  : " + (proteinGoal - p));
        System.out.println("Carbs    : " + (carbGoal - cb));
        System.out.println("Fat      : " + (fatGoal - f));
        System.out.println("╚═══════════════════════╝");
    }
}