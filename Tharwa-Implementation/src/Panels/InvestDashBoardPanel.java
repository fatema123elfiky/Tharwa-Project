package Panels;
import LogicBusiness.Asset;
import LogicBusiness.User;
import Funcs.HelperFunc;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Vector;
/**
 * The InvestDashBoardPanel class provides the functionality for managing assets.
 * It allows users to add, remove, view, and edit their assets.
 * This class is part of a menu-based system where users can interact with their assets.
 */
public class InvestDashBoardPanel implements Panel{
    private final User user ;

    /**
     * Displays the asset management menu and allows the user to choose between
     * adding, removing, viewing, or editing assets. This method runs continuously
     * until the user chooses to go back.
     */
    @Override
    public void ViewMenu() {
        while (true) {
            System.out.println("# === Asset Panel === #");

            String Menu = "1.add Asset\n2.Remove Asset\n3.ViewAssets\n4.EditAsset\n5.Go back\nEnter your choice :";
            ArrayList<String> choices = new ArrayList<>(Arrays.asList("1", "2", "3", "4", "5"));
            String choice = HelperFunc.check_menu(Menu, choices);

            if (choice.equals("1"))
                addAsset();
            else if (choice.equals("2"))
                removeAsset();
            else if(choice.equals("3"))
                ViewAssets();
            else if(choice.equals("4"))
                EditAsset();
            else
                break;

        }


    }

    /**
     * Constructor for the InvestDashBoardPanel.
     * Initializes the panel with the provided user.
     *
     * @param user the User object associated with this panel.
     */
    public InvestDashBoardPanel(User user){
        this.user=user;
    }

    /**
     * Allows the user to add a new asset by providing its name, type, and purchase date.
     * The asset's type is validated from a list of predefined options (Gold, Real state, Crypto, Stocks).
     * The purchase date is validated to ensure that the day and month are within valid ranges.
     */
    public void addAsset (){
        Scanner in = new Scanner(System.in);

        System.out.print("Enter the name of the asset : ");
        String name="" ;
        name =HelperFunc.getNonEmptyInput(name);

        // validation for type
        String menu = "Choose a type for the new asset" +
                     " :\n1.Gold\n2.Real state\n3.Crypto\n4.Stocks\n\nEnter your choice : ";
        ArrayList<String>choices= new ArrayList<>(Arrays.asList("1","2","3","4","5"));
        String choice = HelperFunc.check_menu(menu,choices);
        String Type ;
        if(choice.equals("1"))
            Type="Gold";
        else if(choice.equals("2"))
            Type="Real state";
        else if( choice.equals("3"))
            Type="Crypto";
        else
            Type="Stocks";

        //validation for date
        System.out.print("Enter the purchase date !! ");

        System.out.print("Enter the day :");
        String day = in.next();
        while(!HelperFunc.isNum(day )|| (Integer.parseInt(day)>31||Integer.parseInt(day)<1)){
            System.out.println("Please enter a valid day !!");
            System.out.print("Enter the day :");
            day=in.next();
        }

        System.out.print("Enter the month :");
        String month = in.next();
        while(!HelperFunc.isNum(month )|| (Integer.parseInt(month)>12||Integer.parseInt(month)<1)){
            System.out.println("Please enter a valid month !!");
            System.out.print("Enter the month :");
            month=in.next();
        }

        System.out.print("Enter the year :");
        String year = in.next();
        while(!HelperFunc.isNum(year )|| (Integer.parseInt(year)>2025||Integer.parseInt(year)<1800)){
            System.out.println("Please enter a valid year !!");
            System.out.print("Enter the year :");
            year=in.next();
        }
        String purchaseTime = day+'-'+month+'-'+year;




        //validation for numbers
        System.out.print("Enter the quantity : ");
        String quantity = in.next();
        while(!HelperFunc.isNum(quantity )){
            System.out.println("Please enter a valid number !!");
            System.out.print("Enter the quantity : ");
            quantity=in.next();
        }

        System.out.print("Enter the purchase price : ");
        String price = in.next();
        while(!HelperFunc.isFloat(price )){
            System.out.println("Please enter a valid number !!");
            System.out.print("Enter the purchase price : ");
            price=in.next();
        }


        //validation for repeating
        Asset asset =new Asset(name,Type ,purchaseTime, Integer.parseInt(quantity),Integer.parseInt(price));
        boolean isFound =user.SearchAsset(asset);
        if(isFound)
            System.out.println("That Asset already exists !!\n\n");
        else {
            System.out.println("The Asset is added successfully !!\n\n");
            user.setAsset(asset);
        }
    }

    /**
     * Removes an asset from the user's portfolio.
     * The specific removal logic can be implemented here.
     */
    public void removeAsset(){
        Vector<Asset> assets = user.getAssets();
        if (assets.isEmpty()) {
            System.out.println("No assets available to remove!\n\n");
            return;
        }

        // Display all assets with numbers
        System.out.println("Available assets:");
        for (int i = 0; i < assets.size(); i++) {
            System.out.print((i + 1) + ". ");
            Asset asset = assets.get(i);
            System.out.print("Name: " + asset.getName());
            System.out.print(", Type: " + asset.getType());
            System.out.print(", Purchase Price: " + asset.getPurchasePrice());
            System.out.print(", Date: " + asset.getPurchaseTime());
            System.out.println(", Qty: " + asset.getQuantity());
        }

        // Get user input for which asset to remove
        Scanner in = new Scanner(System.in);
        System.out.print("\nEnter the number of asset to remove (or 0 to cancel): ");
        String input = in.next();

        while (!HelperFunc.isNum(input) || Integer.parseInt(input) < 0 || Integer.parseInt(input) > assets.size()) {
            System.out.println("Invalid input! Please enter a number between 1 and " + assets.size() + " (or 0 to cancel)");
            System.out.print("Enter the number of asset to remove: ");
            input = in.next();
        }

        int choice = Integer.parseInt(input);
        if (choice == 0) {
            System.out.println("Operation cancelled.\n\n");
            return;
        }

        // Remove the selected asset
        Asset removedAsset = assets.remove(choice - 1);
        System.out.println("\nAsset '" + removedAsset.getName() + "' removed successfully!");
        System.out.println("Details: Type=" + removedAsset.getType() +
                ", Purchase Price=" + removedAsset.getPurchasePrice() +
                ", Date=" + removedAsset.getPurchaseTime() +
                ", Qty=" + removedAsset.getQuantity() + "\n");
    }

    /**
     * Displays the user's assets.
     * This method will allow the user to view all of their current assets.
     */
    public void ViewAssets (){
        Vector<Asset> assets = user.getAssets();
        for (int idx = 0; idx < assets.size(); idx++) {
            System.out.print("Asset "+(idx+1)+" --> ");
            assets.elementAt(idx).ViewAsset();
        }
    }

    /**
     * Edits an existing asset in the user's portfolio.
     * This method will allow the user to modify the details of an asset.
     */
    public void EditAsset() {
        Vector<Asset> assets = user.getAssets();
        if (assets.isEmpty()) {
            System.out.println("No assets available to edit!\n\n");
            return;
        }

        // Display all assets with numbers
        System.out.println("Available assets:");
        for (int i = 0; i < assets.size(); i++) {
            System.out.print((i + 1) + ". ");
            Asset asset = assets.get(i);
            System.out.print("Name: " + asset.getName());
            System.out.print(", Type: " + asset.getType());
            System.out.print(", Purchase Price: " + asset.getPurchasePrice());
            System.out.print(", Date: " + asset.getPurchaseTime());
            System.out.println(", Qty: " + asset.getQuantity());
        }

        // Get user input for which asset to edit
        Scanner in = new Scanner(System.in);
        System.out.print("\nEnter the number of asset to edit (or 0 to cancel): ");
        String input = in.next();

        while (!HelperFunc.isNum(input) || Integer.parseInt(input) < 0 || Integer.parseInt(input) > assets.size()) {
            System.out.println("Invalid input! Please enter a number between 1 and " + assets.size() + " (or 0 to cancel)");
            System.out.print("Enter the number of asset to edit: ");
            input = in.next();
        }

        int choice = Integer.parseInt(input);
        if (choice == 0) {
            System.out.println("Operation cancelled.\n\n");
            return;
        }

        Asset assetToEdit = assets.get(choice - 1);

        // Display edit menu
        String menu = "\nWhat would you like to edit?\n" +
                "1. Name\n" +
                "2. Type\n" +
                "3. Purchase Price\n" +
                "4. Purchase Date\n" +
                "5. Quantity\n" +
                "6. Cancel\n" +
                "Enter your choice: ";

        ArrayList<String> choices = new ArrayList<>(Arrays.asList("1", "2", "3", "4", "5", "6"));
        String editChoice = HelperFunc.check_menu(menu, choices);

        switch (editChoice) {
            case "1": // Edit Name
                in.nextLine(); // Consume newline
                System.out.print("\nCurrent name: " + assetToEdit.getName());
                System.out.print("\nEnter new name: ");
                String newName = in.nextLine();
                assetToEdit.setName(newName);
                System.out.println("\nName updated successfully!\n");
                break;

            case "2": // Edit Type
                System.out.print("\nCurrent type: " + assetToEdit.getType());
                String typeMenu = "\nChoose new type:\n" +
                        "1. Gold\n" +
                        "2. Real state\n" +
                        "3. Crypto\n" +
                        "4. Stocks\n" +
                        "Enter your choice: ";
                ArrayList<String> typeChoices = new ArrayList<>(Arrays.asList("1", "2", "3", "4"));
                String typeChoice = HelperFunc.check_menu(typeMenu, typeChoices);

                String newType;
                if (typeChoice.equals("1")) newType = "Gold";
                else if (typeChoice.equals("2")) newType = "Real state";
                else if (typeChoice.equals("3")) newType = "Crypto";
                else newType = "Stocks";

                assetToEdit.setType(newType);
                System.out.println("\nType updated to " + newType + "!\n");
                break;

            case "3": // Edit Purchase Price
                System.out.print("\nCurrent price: " + assetToEdit.getPurchasePrice());
                System.out.print("\nEnter new purchase price: ");
                String priceInput = in.next();
                while (!HelperFunc.isNum(priceInput) || Integer.parseInt(priceInput) <= 0) {
                    System.out.println("Please enter a valid positive number!");
                    System.out.print("Enter new purchase price: ");
                    priceInput = in.next();
                }
                assetToEdit.setPurchasePrice(Integer.parseInt(priceInput));
                System.out.println("\nPurchase price updated to " + priceInput + "!\n");
                break;

            case "4": // Edit Purchase Date
                System.out.print("\nCurrent date: " + assetToEdit.getPurchaseTime());
                System.out.println("\nEnter new purchase date:");

                System.out.print("Day (1-31): ");
                String day = in.next();
                while (!HelperFunc.isNum(day) || Integer.parseInt(day) > 31 || Integer.parseInt(day) < 1) {
                    System.out.println("Please enter a valid day (1-31)!");
                    System.out.print("Day: ");
                    day = in.next();
                }

                System.out.print("Month (1-12): ");
                String month = in.next();
                while (!HelperFunc.isNum(month) || Integer.parseInt(month) > 12 || Integer.parseInt(month) < 1) {
                    System.out.println("Please enter a valid month (1-12)!");
                    System.out.print("Month: ");
                    month = in.next();
                }

                System.out.print("Year (1800-2025): ");
                String year = in.next();
                while (!HelperFunc.isNum(year) || Integer.parseInt(year) > 2025 || Integer.parseInt(year) < 1800) {
                    System.out.println("Please enter a valid year (1800-2025)!");
                    System.out.print("Year: ");
                    year = in.next();
                }

                String newDate = day + "/" + month + "/" + year;
                assetToEdit.setPurchaseTime(newDate);
                System.out.println("\nPurchase date updated to " + newDate + "!\n");
                break;

            case "5": // Edit Quantity
                System.out.print("\nCurrent quantity: " + assetToEdit.getQuantity());
                System.out.print("\nEnter new quantity: ");
                String quantity = in.next();
                while (!HelperFunc.isNum(quantity) || Integer.parseInt(quantity) < 0) {
                    System.out.println("Please enter a valid non-negative number!");
                    System.out.print("Enter new quantity: ");
                    quantity = in.next();
                }
                assetToEdit.setQuantity(Integer.parseInt(quantity));
                System.out.println("\nQuantity updated to " + quantity + "!\n");
                break;

            case "6": // Cancel
                System.out.println("\nEdit cancelled.\n");
                break;
        }
    }

    /**
     * Evaluates the performance of the user's assets.
     * This method calculates the growth or loss of assets based on their initial price
     * and current market value (which might be retrieved from an external source).
     */
    public void Evaluate(){
        // Not implemented yet
    }
}
