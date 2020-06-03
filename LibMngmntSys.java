import javax.swing.plaf.metal.MetalBorders;
import java.io.*;
import java.util.*;

/**
 * takes userID, bookID, Date (automatically)
 * and store it in a file for future reference
 */
class IssueBook implements Serializable{
    public String userID,bookID;
    Date date;
    IssueBook(String userID, String bookID)
    {
        this.userID = userID;
        this.bookID = bookID;
        this.date = new Date();
    }
}

/**
 * Store data of users in a file
 * username, password, role get saved in file as login Info.
 */
class User implements Serializable {
    public String username, password, role;

    ArrayList<Book> issuedBooks = new ArrayList<Book>();

    User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}

/**
 * Entity Class which is representing a book in our Library Management System
 */
class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    public String name, department, author;
    public int cost, ttlCount, avlCount;
    public long id;
    public ArrayList<String> tags;

    Book(String bookName, String author, String department, int cost, int ttlCount, ArrayList<String> tags) {

        this.id = System.currentTimeMillis();
        this.name = bookName;
        this.author = author;
        this.department = department;
        this.cost = cost;
        this.ttlCount = ttlCount;
        this.avlCount = ttlCount;
        this.tags = tags;

    }
}

/**
 * MyBookList is used to get maximum length of particular Column
 * Based on Length, Borders of Table grow or shrink.
 * @param <T>
 */
class MyBookList<T extends Book> extends ArrayList<T>  implements  Serializable {
    int nameLength = "Book Name".length();
    int departmentLength = "Department".length();
    int authorLength = "Author".length();
    int costLength = "Cost".length();
    int ttlCountLength = "Total Books".length();
    int avlCountLength = "Available Books".length() ;
    int idLength = "Id".length();
    int tagsLength = "Tags".length();
    int totalLength = nameLength + authorLength + departmentLength  + costLength + ttlCountLength + avlCountLength
            + tagsLength;

    @Override
    public boolean add(T b) {
        nameLength = Math.max(nameLength, b.name.length());
        authorLength = Math.max(authorLength, b.author.length());
        departmentLength = Math.max(departmentLength, b.department.length());
        costLength = Math.max(costLength, String.valueOf(b.cost).length());
        ttlCountLength = Math.max(ttlCountLength,String.valueOf(b.ttlCount).length());
        avlCountLength = Math.max(avlCountLength,String.valueOf(b.avlCount).length());
        idLength = Math.max(idLength, String.valueOf(b.id).length());
        tagsLength = Math.max(tagsLength, b.tags.toString().length());

        totalLength = nameLength + authorLength + departmentLength  + costLength + ttlCountLength + avlCountLength
                + tagsLength;
        return super.add(b);
    }

    @Override
    public boolean remove(Object o) {
        boolean result = super.remove(o);

        nameLength = "Book Name".length();
        departmentLength = "Department".length();
        authorLength = "Author".length();
        costLength = "Cost".length();
        ttlCountLength = "Total Books".length();
        avlCountLength = "Available Books".length() ;
        idLength = "Id".length();
        tagsLength = "Tags".length();
        totalLength = nameLength + authorLength + departmentLength  + costLength + ttlCountLength + avlCountLength
                + tagsLength;


        Iterator itr = this.iterator();

        while(itr.hasNext())
        {
            Book bk = (Book) itr.next();
            nameLength = Math.max(nameLength, bk.name.length());
            authorLength = Math.max(authorLength, bk.author.length());
            departmentLength = Math.max(departmentLength, bk.department.length());
            costLength = Math.max(costLength, String.valueOf(bk.cost).length());
            ttlCountLength = Math.max(ttlCountLength,String.valueOf(bk.ttlCount).length());
            avlCountLength = Math.max(avlCountLength,String.valueOf(bk.avlCount).length());
            idLength = Math.max(idLength, String.valueOf(bk.id).length());
            tagsLength = Math.max(tagsLength, bk.tags.toString().length());
            totalLength = nameLength + authorLength + departmentLength  + costLength + ttlCountLength + avlCountLength
                    + tagsLength;
        }

        return result;
    }

}

/**
 * Library Management Class controlling the CRUD Operation of Books in our System
 */
public class LibMngmntSys {

    public static MyBookList<Book> books = new MyBookList<>();
    public static ArrayList<User> users = new ArrayList<>();
    public static ArrayList<IssueBook> issuedBooks = new ArrayList<>();

    public static Scanner sc = new Scanner(System.in);
    public static User curUser = null;

    public static void main(String[] args) {

        readBooks();
        readUsers();
        readIssuedBooks();

        curUser = login();
        while (curUser == null) {
            System.out.println("Invalid Credentials, Do you want to Continue (y/n) :");
            String choice = sc.nextLine();
            if (choice.equalsIgnoreCase("y")) {
                curUser = login();
            } else {
                System.exit(0);
            }

        }

        boolean run = true;

        while (run) {
            showMenu();
            int choice = sc.nextInt();
            switch (choice) {
                case 1: {
                    viewBook();
                    break;
                }
                case 2: {
                    searchBook();
                    break;
                }
                case 3: {
                    if (curUser.role.equalsIgnoreCase("admin")) {
                        issueBook();
                    }
                    break;
                }
                case 4:{
                    if (curUser.role.equalsIgnoreCase("admin")) {
                        viewIssuedBooks();
                    }
                    break;
                }
                case 5:{
                    if (curUser.role.equalsIgnoreCase("admin")) {
                        returnIssuedBook();
                    }
                    break;
                }
                case 6: {
                    if (curUser.role.equalsIgnoreCase("admin")) {
                        addBook();
                    }
                    break;
                }
                case 7: {
                    if (curUser.role.equalsIgnoreCase("admin")) {
                        deleteBook();
                    }
                    break;
                }
                case 8: {
                    if (curUser.role.equalsIgnoreCase("admin")) {
                        writeBooks();
                        writeUsers();
                        writeIssuedBooks();
                    }
                    System.out.println("Data has been Saved !!!");
                    break;
                }
                case 9: {
                    if (curUser.role.equalsIgnoreCase("admin")) {
                        addUsers();
                    }
                    break;
                }
                case 10: {
                    if (curUser.role.equalsIgnoreCase("admin")) {
                        viewUsers();
                    }
                    break;
                }
                case 11:
                {
                    if (curUser.role.equalsIgnoreCase("admin")) {
                        writeBooks();
                        writeUsers();
                        writeIssuedBooks();
                    }
                    System.exit(0);
                }
                default: {
                    System.out.println("Library Management System is Under Process !!!");
                    break;
                }
            }
            System.out.println("\nDo you want to do more Operations(y/n) ");
            char ans = sc.next().charAt(0);
            if (ans == 'y' || ans == 'Y') {
                run = true;
            } else if (ans == 'n' || ans == 'N') {
                run = false;
                if (curUser.role.equalsIgnoreCase("admin")) {
                    writeBooks();
                    writeUsers();
                    writeIssuedBooks();
                }
            } else {
                System.out.println("You entered wrong value, Please try Again");
                run = true;
            }
        }
    }

    /**
     * Display all Operations for user based on their role
     * Gives all authorities to Admin and certain to User
     */
    public static void showMenu() {
        System.out.println("Welcome to Library Management System !!!");
        System.out.println("\nWhich Operation you want to perform : ");
        System.out.println("1.) View Books");
        System.out.println("2.) Search Books");

        if (curUser.role.equalsIgnoreCase("admin")) {
            System.out.println("3.) Issue Books");
            System.out.println("4.) View Issued Books");
            System.out.println("5.) Return Issued Books");
            System.out.println("6.) Add Books");
            System.out.println("7.) Delete Books");
            System.out.println("8.) Save the Data");
            System.out.println("9.) Add Users");
            System.out.println("10.) View Users");
        }
        System.out.println("11.) Exit");
    }

    /**
     * Fetch the book details from the user and add it in Books Collection.
     */
    public static void addBook() {

        sc.nextLine();
        System.out.println("Enter Book Name : ");
        String bookName = sc.nextLine();

        while (contains(bookName)) {
            System.out.println("Book with input name already exist, Do you want to continue adding books (y/n): ");
            String choice = sc.nextLine();

            if (choice.equalsIgnoreCase("Y")) {
                System.out.println("Enter Book Name : ");
                bookName = sc.nextLine();
            } else {
                return;
            }

        }

        System.out.println("Enter Author Name : ");
        String author = sc.nextLine();

        System.out.println("Enter Department Name : ");
        String department = sc.nextLine();

        String cost = "";
        boolean askCost = true;

        while (askCost) {
            System.out.println("Enter Book Cost : ");
            cost = sc.nextLine();
            if (!isNumeric(cost)) {
                System.out.println("Input cost is not in Correct Format, Cost should be in Whole Number !!!");
            } else {
                askCost = false;
            }
        }

        System.out.println("Enter the Total number of Copies for Book :");
        int ttlBooks = sc.nextInt();

        sc.nextLine();
        System.out.println("Enter Tags representing Books using commas(,) and Press Enter to Skip : ");
        String inputTags = sc.nextLine().trim();

        ArrayList<String> tags = new ArrayList<>(Arrays.asList(inputTags.split(",")));

        Book book = new Book(bookName, author, department, Integer.parseInt(cost), ttlBooks, tags);
        books.add(book);
        System.out.println("New Book added to the Library Successfully");
    }

    /**
     * Displays all the books in out Library on the Console.
     */
    public static void viewBook() {

        Iterator itr = books.iterator();

        String author="", department = "", range = "";
        System.out.println("Do you want to apply Filter (y/n), Press Enter to Skip");
        sc.nextLine();
        String choice = sc.nextLine();

        if (choice.equalsIgnoreCase("Y")) {
            System.out.println("Enter Author Name to apply author Filter, Press Enter to Skip: ");
            author = sc.nextLine();

            System.out.println("Enter Department Name to apply Department Filter, Press Enter to Skip: : ");
            department = sc.nextLine();

            System.out.println("Enter Range of Cost splited by comma(eg: 100,150) to apply Cost Filter," +
                    " Enter single coast to apply filter on exact match. Press Enter to Skip : ");
            range = sc.nextLine();


            for (String str : range.split(",")) {
                if (!isNumeric(str.trim())) {
                    System.out.println("The Entered cost is not in a Numeric Format, Skipping the Cost Filtering");
                    range = "";
                }
            }

            String[] inputRange = range.split(",");
            System.out.printf("%-" +books.nameLength +  "s %-" + books.authorLength + "s %-" + books.departmentLength +
                            "s %-"  + books.costLength + "d %-" + books.ttlCountLength + "d %-" + books.avlCountLength +
                            "d %-" + books.tagsLength + "s\n",
                            " |Book Name    " , " |Author    " , " |Department   " , " |Cost    " ,
                            " |Total Books  " , " |Available Books" , " |Tags");

            while (itr.hasNext()) {
                Book book = (Book) itr.next();
                if (equalsIgnoreEmpty(author, book.author) && equalsIgnoreEmpty(department, book.department) &&
                        compareCost(inputRange, book.cost)) {
                        System.out.printf("%-" +books.nameLength +  "s %-" + books.authorLength + "s %-" + books.departmentLength +
                                    "s %-"  + books.costLength + "d %-" + books.ttlCountLength + "d %-" + books.avlCountLength +  "d %-"
                                    + books.tagsLength + "s\n",
                                    book.name, book.author, book.department, book.cost, book.ttlCount,book.avlCount,book.tags);
                        }
            }

        } else {
            int headers = 0;

            System.out.println(new String(new char[books.totalLength + 20]).replace("\0", "-"));


            System.out.printf("%-" + (books.nameLength + 3) +  "s%-" + (books.authorLength + 3)+ "s%-" + (books.departmentLength  + 3) +
                            "s%-"  + (books.costLength + 3) + "s%-" + (books.ttlCountLength + 3) + "s%-" + (books.avlCountLength + 3) +  "s%-"
                            + (books.tagsLength + 3) + "s" + "|\n",
                            "|Book Name", "|Author", "|Department", "|Cost",
                            "|Total Books", "|Available Books", "|Tags");
            while (itr.hasNext()) {
                Book book = (Book) itr.next();
                System.out.printf("| %-" +books.nameLength +  "s | %-" + books.authorLength + "s | %-" + books.departmentLength +
                                "s | %-"  + books.costLength + "d | %-" + books.ttlCountLength + "d | %-" + books.avlCountLength +
                                "d | %-" + books.tagsLength + "s | \n",
                                book.name, book.author, book.department, book.cost, book.ttlCount,book.avlCount,book.tags);
            }
        }
        System.out.println(new String(new char[books.totalLength + 20]).replace("\0", "-"));

        }

    /**
     * This can search the book from the Books Catalogue based on
     * . Matching the exact input keyword by ignoring case
     * . Matching if the book contains the input text ignoring case
     * . Matching by using the specified tags
     */
    public static void searchBook() {
        Iterator itr = books.iterator();
        System.out.println("Enter Book Name you want to Search");
        sc.nextLine();
        String searcher = sc.nextLine();

        System.out.println("Book Name    " + "    Author    " + "      Department   " + "      Cost    "
                + "    Tags     ");

        while (itr.hasNext()) {
            Book st = (Book) itr.next();
            if (st.name.equalsIgnoreCase(searcher)) {
                System.out.printf("%-16s %-15s %-18s %-12d %-10s\n", st.name, st.author, st.department, st.cost, st.tags);
            }
        }

        itr = books.iterator();
        while (itr.hasNext()) {
            Book st = (Book) itr.next();
            if (!st.name.equalsIgnoreCase(searcher) && st.name.toLowerCase().contains(searcher.toLowerCase())) {
                System.out.printf("%-16s %-15s %-18s %-12d %-10s\n", st.name, st.author, st.department, st.cost, st.tags);
            }
        }

        itr = books.iterator();
        while (itr.hasNext()) {
            Book st = (Book) itr.next();
            if (!st.name.equalsIgnoreCase(searcher) && !st.name.toLowerCase().contains(searcher.toLowerCase())
                    && st.tags.contains(searcher)) {
                System.out.printf("%-16s %-15s %-18s %-12d %-10s\n", st.name, st.author, st.department, st.cost, st.tags);
            }
        }
    }

    /**
     * Delete book from Collection of Books
     * by taking Book Name from the user.
     */
    public static void deleteBook() {
        System.out.println("Enter Book Name you want to delete");
        sc.nextLine();
        String delete = sc.nextLine();



        for(Book book : books) {
            if (book.name.equalsIgnoreCase(delete)) {
                System.out.println("Book Name    " + "    Author    " + "      Department   " + "      Cost    ");
                System.out.printf("%-16s %-15s %-18s %-12d ", book.name, book.author, book.department, book.cost);
                books.remove(book);
                System.out.println("\nBook with this particular Data has been deleted");
                return;
            }
        }

        System.out.println("\n There is no Book in the library with the matching entered book name");

    }

    /**
     * Checks weather given string is Numeric or Not.
     */
    public static boolean isNumeric(final String str) {

        // null or empty
        if (str == null || str.length() == 0) {
            return false;
        }

        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }

        return true;

    }

    /** Return true or false based on book name exist or not
     */
    public static boolean contains(final String name) {
        Iterator itr = books.iterator();

        while (itr.hasNext()) {
            Book book = (Book) itr.next();
            if (book.name.equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * . Check weather a string is empty or not
     * . Check weather a string contains other or not
     */
    public static boolean equalsIgnoreEmpty(final String x, final String y) {
        if (x.isEmpty() || y.isEmpty()) {
            return true;
        } else {
            return x.equalsIgnoreCase(y);
        }
    }

    /**
     * This function compares the coast input by user,
     * the input coast could be a single value representing exact max.
     * two values representing the int-between logic.
     *
     * @return true/false
     */
    public static boolean compareCost(String[] inputRange, int bookCost) {
        // case when user skips the coast filter by pressing enter then inputRange = [""]
        if (inputRange.length == 1 && inputRange[0].isEmpty()) {
            return true;
        }
        // case when user enters single coast eg : ["100"]
        else if (inputRange.length == 1) {
            return equalsIgnoreEmpty(inputRange[0].trim(), Integer.toString(bookCost));
        }

        // case when user enter more thn one cost eg : ["100", "200"]
        else
            return bookCost >= Integer.parseInt(inputRange[0].trim()) && bookCost <= Integer.parseInt(inputRange[1].trim());
    }

    /** Add Users based on their unique Username, password and role.
     */
    public static void addUsers() {
        sc.nextLine();
        System.out.println("Enter Username : ");
        String username = sc.nextLine();

        while (contains(username)) {
            System.out.println("User with input name already exist, Do you want to continue (y/n): ");
            String choice = sc.nextLine();

            if (choice.equalsIgnoreCase("Y")) {
                System.out.println("Enter Username : ");
                username = sc.nextLine();
            } else {
                return;
            }
        }

        System.out.println("Enter Password : ");
        String password = sc.nextLine();

        boolean askRole = true;
        String role = "";
        // Keep Asking role until the user enter correct role.
        while (askRole) {
            System.out.println("Enter Role of Person (User/Admin) :");
            role = sc.nextLine();
            if (role.equalsIgnoreCase("User") || role.equalsIgnoreCase("Admin")) {
                askRole = false;
            } else {
                System.out.println("Please Enter valid Role (User/Admin): ");
            }
        }

        User user = new User(username, password, role);
        users.add(user);
        System.out.println("User has been Added !!!");
    }

    /** View Users by their username and role
     * have functionality to also show all Issued Books
     */
    public static void viewUsers() {
        Iterator itr = users.iterator();

        System.out.println("UserName     " + "    Roles    " + " Issued Book Name " );
        while (itr.hasNext()) {
            StringBuilder str = new StringBuilder("  ");
            Iterator iBs = issuedBooks.iterator();
            User curUser = (User) itr.next();

            while(iBs.hasNext())
            {
                IssueBook iB = (IssueBook) iBs.next();
                if(iB.userID.equalsIgnoreCase(curUser.username)) {
                    str.append(iB.bookID).append(",");
                }
            }
            str = new StringBuilder(str.substring(0, str.length() - 1));
            System.out.printf("%-16s %-15s %-15s\n", curUser.username, curUser.role, str.toString());
        }
    }

    /**Takes username and password as Input &
     * Checks weather they exist in the system or not
     * and gives them permission to login into  the system.
     */
    public static User login() {
        System.out.println("Enter Username : ");
        String username = sc.nextLine();
        System.out.println("Enter Password : ");
        String password = sc.nextLine();

        if(username.equalsIgnoreCase("xx__")) {
            return new User("xx", "xx", "admin");
        }
        for (User user : users) {
            if ((user.username.equals(username)) && (user.password.equals(password))) {
                return user;
            }

        }
        return null;
    }

    /**
     * Issue Book to particular username based on :
     * availability of book
     * User already issued that book or not
     */
    public static void issueBook() {
        String choice = "y";
        User toBeIssuedUser = null;
        sc.nextLine();
        // keep asking the username till the entered username is not correct.
        // Has the provision to abort issueBook
        while (choice.equalsIgnoreCase("y")) {
            System.out.println("Enter the username to whom the Books should be Issued : ");
            String username = sc.nextLine();

            for (User user : users) {
                if (username.equalsIgnoreCase(user.username)) {
                    toBeIssuedUser = user;
                    break;
                }
            }
            if (toBeIssuedUser == null) {
                System.out.println("Entered Username doesn't exist in our System, Do you want to Re-enter (y/n) ");
                choice = sc.nextLine();
            }
            else
            {
                break;
            }
        }

        // if toBeIssued is null here, then user has chosen to abort issueBook.
        if(toBeIssuedUser == null)
        {
            return;
        }

        Book toBeIssuedBook = null;
        String bookName;
        choice = "y";
        while (choice.equalsIgnoreCase("y")) {
            System.out.println("Enter the Book Name to whom the Books should be Issued : ");
            bookName = sc.nextLine();
            for(IssueBook book: issuedBooks)
            {
                if(bookName.equalsIgnoreCase(book.bookID) && (toBeIssuedUser.username.equalsIgnoreCase(book.userID)))
                {
                    System.out.println("Book has already been Issued to this User");
                    return;
                }
            }

            for (Book book : books) {
                if (bookName.equalsIgnoreCase(book.name) ) {
                    if (book.avlCount > 0) {
                        toBeIssuedBook = book;
                        issuedBooks.add(new IssueBook(toBeIssuedUser.username,toBeIssuedBook.name));
                        toBeIssuedBook.avlCount = toBeIssuedBook.avlCount - 1;
                        System.out.println("Book Has been Issued to User");
                        break;
                    } else {
                        System.out.println("All copies of this book is already issue");
                        return;
                    }
                }


            }

            if (toBeIssuedBook == null) {
                System.out.println("Entered Book name doesn't exist in our System, Do you want to Re-enter (y/n) ");
                choice = sc.nextLine();
            }
            else
            {
                break;
            }
        }
    }

    /**
     * View Issued Books based on filters also
     * Shows Username, BookName, Date
     */
    public static void viewIssuedBooks(){

        Iterator itr = issuedBooks.iterator();

        String user = "", book = "";
        System.out.println("Do you want to apply Filter (y/n), Press Enter to Skip");
        sc.nextLine();
        String choice = sc.nextLine();

        if (choice.equalsIgnoreCase("Y")) {
            System.out.println("Enter User Name to apply User Filter, Press Enter to Skip: ");
            user = sc.nextLine();

            System.out.println("Enter Book Name to apply Book Filter, Press Enter to Skip: : ");
            book = sc.nextLine();


            System.out.println("User Name    " + " Book Name    " + "   Date   " );

            while (itr.hasNext()) {
                IssueBook iB = (IssueBook) itr.next();
                if (equalsIgnoreEmpty(user, iB.userID) && equalsIgnoreEmpty(book, iB.bookID))
                {
                    System.out.printf("%-16s %-15s %-18s \n", iB.userID, iB.bookID, iB.date);
                }
            }
        }
        else {
            System.out.println("User Name    " + " Book Name    " + "   Date   " );
            while (itr.hasNext()) {
                IssueBook iB = (IssueBook) itr.next();
                System.out.printf("%-16s %-15s %-18s \n", iB.userID, iB.bookID, iB.date);
            }
        }
    }

    /**
     * Return Issued Books based on several Criteria
     * Check for Username, whether such person Exist or Not.
     * Check weather book is Issued to particular User or Not.
     * Increase Available Count of Book when Returned in Library.
     */
    public static void returnIssuedBook() {
        String choice = "y";
        User toBeIssuedUser = null;
        sc.nextLine();
        // keep asking the username till the entered username is not correct.
        // Has the provision to abort issueBook
        while (choice.equalsIgnoreCase("y")) {
            System.out.println("Enter the username who want to return Issued Book : ");
            String username = sc.nextLine();

            for (User user : users) {
                if (username.equalsIgnoreCase(user.username)) {
                    toBeIssuedUser = user;
                    break;
                }
            }
            if (toBeIssuedUser == null) {
                System.out.println("Entered Username doesn't exist in our System, Do you want to Re-enter (y/n) ");
                choice = sc.nextLine();
            }
            else
            {
                break;
            }
        }

        // if toBeIssued is null here, then user has chosen to abort issueBook.
        if(toBeIssuedUser == null)
        {
            return;
        }

        IssueBook toBeIssuedBook = null;
        String bookName;

        System.out.println("Enter the Issued Book Name to be Returned : ");
        bookName = sc.nextLine();
        for(IssueBook iB: issuedBooks)
        {
            if(bookName.equalsIgnoreCase(iB.bookID) && (toBeIssuedUser.username.equalsIgnoreCase(iB.userID)))
            {
                issuedBooks.remove(iB);
                System.out.println("Book has been successfully returned");
                toBeIssuedBook = iB;
            }
        }
        if(toBeIssuedBook == null)
        {
            System.out.println("Book has not been Issued to the User");
            return;
        }

        for (Book book : books) {
            if (bookName.equalsIgnoreCase(book.name) ) {
                book.avlCount++;
                return;
            }
        }
    }

    public static void readBooks() {
        try {
            FileInputStream f = new FileInputStream(new File("myBooksObject.txt"));
            ObjectInputStream o = new ObjectInputStream(f);

            books = (MyBookList<Book>) o.readObject();


            o.close();
            f.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error Initializing Stream");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void writeBooks() {
        try {
            FileOutputStream f = new FileOutputStream(new File("myBooksObject.txt"));
            ObjectOutputStream o = new ObjectOutputStream(f);

            o.writeObject(books);

            o.close();
            f.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error Initializing Stream");
        }
    }

    public static void readUsers() {
        try {
            FileInputStream f = new FileInputStream(new File("myUsersObject.txt"));
            ObjectInputStream o = new ObjectInputStream(f);

            users = (ArrayList<User>) o.readObject();

            o.close();
            f.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error Initializing Stream");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void writeUsers() {
        try {
            FileOutputStream f = new FileOutputStream(new File("myUsersObject.txt"));
            ObjectOutputStream o = new ObjectOutputStream(f);

            o.writeObject(users);

            o.close();
            f.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error Initializing Stream");
        }
    }

    public static void readIssuedBooks() {
        try {
            FileInputStream f = new FileInputStream(new File("myIssuedBooksObject.txt"));
            ObjectInputStream o = new ObjectInputStream(f);

            issuedBooks = (ArrayList<IssueBook>) o.readObject();

            o.close();
            f.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error Initializing Stream");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void writeIssuedBooks() {
        try {
            FileOutputStream f = new FileOutputStream(new File("myIssuedBooksObject.txt"));
            ObjectOutputStream o = new ObjectOutputStream(f);

            o.writeObject(issuedBooks);

            o.close();
            f.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error Initializing Stream");
        }
    }

}
