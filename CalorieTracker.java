import java.util.Scanner;

class CalorieTracker {

    static final int MAX = 100;

    static String[] foodName = new String[MAX];
    static int[] calories = new int[MAX];
    static String[] mealType = new String[MAX];
    static int count = 0;

    static Scanner sc = new Scanner(System.in);

    static int calorieGoal;
    static double bmi;

    // ================= FOOD DATABASE =================
    static String[] foodDB = {
        "roti","chapati","rice","dal","paneer","samosa","idli","dosa","poha","upma","biryani","curd","butter chicken",
        "rajma","chole","pav bhaji","vada pav","paratha","khichdi","pulao","aloo sabzi","palak paneer","veg curry",
        "naan","fried rice","jeera rice","lemon rice","coconut rice","masala dosa","uttapam","pongal","rasam",
        "sambar","avial","kootu","thepla","dhokla","handvo","undhiyu","misal pav","pani puri","bhel puri","sev puri",
        "ragda pattice","veg sandwich","cheese sandwich","grilled sandwich","veg burger","veg pizza","cheese pizza",
        "french fries","veg momos","fried momos","steam momos","egg curry","egg bhurji","omelette","boiled egg",
        "chicken curry","chicken biryani","chicken fried rice","chicken tikka","chicken roll","chicken sandwich",
        "fish curry","fish fry","prawn curry","prawn fry","mutton curry","mutton biryani","keema","tea","coffee","milk","lassi","buttermilk",
        "banana","apple","orange","mango","papaya","watermelon","bread","brown bread","butter","jam","peanut butter",
        "biscuits","cake","pastry","chocolate","ice cream","kulfi","gulab jamun","rasgulla","jalebi","kheer","halwa",
        "laddu","barfi","mysore pak","bhindi sabzi","aloo paratha","gobi paratha","paneer paratha","veg pulao",
        "veg biryani","curd rice","tomato rice","tamarind rice","veg fried noodles","hakka noodles","manchurian",
        "veg manchow soup","sweet corn soup","dal makhani","dal tadka","mix veg","lauki sabzi","tinda sabzi",
        "karela sabzi","matar paneer","shahi paneer","paneer bhurji","soyabean sabzi","chana masala","sprouts salad",
        "fruit salad","veg cutlet","veg pakora","onion pakora","bread pakora","paneer pakora","aloo tikki","dahi puri",
        "sev tameta","pattice","samosa chaat","kachori","kachori chaat","poori","bhature","chole bhature",
        "puri bhaji","veg frankie","paneer frankie","egg roll","chicken roll double","shawarma","falafel wrap",
        "veg spring roll","chicken soup","tomato soup","veg clear soup","cornflakes","oats","upma oats",
        "vegetable omelette","egg sandwich","paneer sandwich","chicken sandwich grilled","veg puff","egg puff",
        "paneer puff","banana shake","mango shake","chocolate shake","cold coffee","badam milk","sugarcane juice",
        "coconut water","lemon soda","soft drink","energy drink","protein bar","granola bar","dry fruits mix",
        "roasted chana","makhana roasted","peanuts roasted","boiled corn"
    };

    static int[] foodDBCalories = {
        100,100,200,150,250,300,60,180,120,170,350,80,400,240,260,380,290,220,180,230,150,260,200,260,250,230,240,260,
        280,250,220,40,180,200,180,120,150,260,350,320,150,200,220,280,180,220,240,300,350,400,320,200,240,180,
        180,200,120,80,280,350,300,260,300,280,220,240,260,280,350,420,300,30,40,60,120,40,90,80,60,150,60,40,
        70,60,100,40,90,120,250,300,200,200,220,150,180,160,200,220,180,190,210,120,320,300,350,240,280,220,230,210,
        300,320,330,150,180,320,240,200,100,90,110,280,360,300,220,260,180,120,200,280,300,350,360,220,180,200,
        250,300,180,220,180,420,450,380,280,320,280,350,400,330,260,150,90,80,110,160,180,200,280,260,300,180,220,240,
        250,300,350,180,220,120,40,100,150,140,250,180,120,90,170,140
    };

    static int foodDBSize = foodDB.length;

    // ================= MAIN =================
    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("      CALORIE TRACKER SYSTEM      ");
        System.out.println("=================================");

        setupUserHealth();

        int choice;
        do {
            System.out.println("\n----------- MAIN MENU -----------");
            System.out.println("1. Add Food Entry");
            System.out.println("2. Remove Food Entry");
            System.out.println("3. View All Entries");
            System.out.println("4. Total Calories Consumed");
            System.out.println("5. Remaining Calories");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> addEntry();
                case 2 -> removeEntry();
                case 3 -> viewEntries();
                case 4 -> totalCalories();
                case 5 -> remainingCalories();
                case 6 -> System.out.println("\nThank you for using Calorie Tracker.");
                default -> System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 6);
    }

    // ================= HEALTH SETUP =================
    static void setupUserHealth() {

        System.out.println("\n------- USER HEALTH DETAILS -------");

        System.out.print("Age: ");
        int age = sc.nextInt();

        System.out.print("Weight (kg): ");
        double weight = sc.nextDouble();

        System.out.print("Height (cm): ");
        double height = sc.nextDouble();

        System.out.print("Gender (M/F): ");
        char gender = sc.next().charAt(0);

        // BMR Calculation
        double bmr = (gender == 'M' || gender == 'm')
                ? 10 * weight + 6.25 * height - 5 * age + 5
                : 10 * weight + 6.25 * height - 5 * age - 161;

        calorieGoal = (int) (bmr * 1.2);

        // BMI Calculation
        double heightM = height / 100;
        bmi = weight / (heightM * heightM); 

        System.out.println("\n----- HEALTH SUMMARY -----");
        System.out.println("Daily Calorie Goal : " + calorieGoal + " kcal");
        System.out.println("BMI                : " + String.format("%.2f", bmi));
    }

    // ================= ADD ENTRY =================
    static void addEntry() {

        if (count >= MAX) {
            System.out.println("Food log is full.");
            return;
        }

        System.out.print("\nEnter food name: ");
        String userFood = sc.nextLine().toLowerCase();

        int index = -1;
        for (int i = 0; i < foodDBSize; i++) {
            if (foodDB[i].equalsIgnoreCase(userFood)) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            System.out.println("Food not found in database.");
            return;
        }

        System.out.print("Enter quantity: ");
        int qty = sc.nextInt();

        System.out.println("Meal Type:");
        System.out.println("1. Breakfast  2. Lunch  3. Dinner  4. Snacks");
        System.out.print("Select meal type: ");
        int meal = sc.nextInt();
        sc.nextLine();

        mealType[count] = switch (meal) {
            case 1 -> "Breakfast";
            case 2 -> "Lunch";
            case 3 -> "Dinner";
            default -> "Snacks";
        };

        int loggedCalories = foodDBCalories[index] * qty;

        foodName[count] = foodDB[index];
        calories[count] = loggedCalories;
        count++;

        System.out.println("\n✔ Food logged successfully!");
        System.out.println("➤ Food      : " + userFood);
        System.out.println("➤ Calories  : " + loggedCalories + " kcal");
    }

    // ================= REMOVE =================
    static void removeEntry() {
        if (count == 0) {
            System.out.println("No entries to remove.");
            return;
        }

        viewEntries();
        System.out.print("Enter entry number to remove: ");
        int idx = sc.nextInt() - 1;

        if (idx < 0 || idx >= count) {
            System.out.println("Invalid entry number.");
            return;
        }

        for (int i = idx; i < count - 1; i++) {
            foodName[i] = foodName[i + 1];
            calories[i] = calories[i + 1];
            mealType[i] = mealType[i + 1];
        }
        count--;

        System.out.println("Entry removed successfully.");
    }

    // ================= VIEW =================
    static void viewEntries() {
        if (count == 0) {
            System.out.println("No food entries yet.");
            return;
        }

        System.out.println("\n------- FOOD LOG -------");
        for (int i = 0; i < count; i++) {
            System.out.println((i + 1) + ". " + mealType[i] +
                    " | " + foodName[i] +
                    " | " + calories[i] + " kcal");
        }
    }

    // ================= TOTAL =================
    static void totalCalories() {
        int sum = 0;
        for (int i = 0; i < count; i++) sum += calories[i];
        System.out.println("Total Calories Consumed: " + sum + " kcal");
    }

    // ================= REMAINING =================
    static void remainingCalories() {
        int sum = 0;
        for (int i = 0; i < count; i++) sum += calories[i];
        System.out.println("Remaining Calories: " + (calorieGoal - sum) + " kcal");
    }
}