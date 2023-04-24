import bookStore.model.*;

import java.util.ArrayList;

public class Main {

    static ArrayList<Book> books = new ArrayList<>();
    static ArrayList<Customer> customers = new ArrayList<>();
    static ArrayList<Employee> employees = new ArrayList<>();
    static ArrayList<Order> orders = new ArrayList<>();


    public static void main(String[] args) {
        initData();

        // ������ 1
        String booksInfo =
                String.format("����� ���-�� ��������� ���� %d �� ����� %f",
                        getCountOfSoldBooks(), getAllPriceOfSoldBooksInOrder());
        System.out.println(booksInfo);

        // ������ 2
        for (Employee employee: employees) {
            System.out.println(employee.getName() + " ������(�) " +
                    getProfitByEmployee(employee.getId()).toString());
        }

        for (Order order: orders) {
            for (Book book : books){
                System.out.println(book.getGenre() + " = " + getPriceOfSoldBooksByGenre(order, book.getGenre()));
            }

        }


    }

    /**
     * �������� ����� ����� ����, � ����� ������ �� ������������� �����
     * @param order �����
     * @param genre ����
     * @return ����� ���������
     */
    public static double getPriceOfSoldBooksByGenre(Order order, BookGenre genre){
        double price = 0;

        for (long bookId : order.getBooks()){
            Book book = getBookById(bookId);
            if (book != null && book.getGenre() == genre){
                price += book.getPrice();
            }
        }
        return price;
    }


    /***
     * �������� ����� ���������� � ����� ��������� ���������� ������ ��� ��������
     * @param employeeId ���������� ����� ��� ��������
     * @return ���������� � ����� ��������� ������� ��������
     */
    public static Profit getProfitByEmployee(long employeeId){
        int count = 0;
        double price = 0;
        for (Order order : orders){
            if (order.getEmployeeId() == employeeId){
                price += getPriceOfSoldBooksInOrder(order);
                count += order.getBooks().length;
            }
        }
        return new Profit(count, price);
    }

    //�������� ����� ��������� ���� �������
    public static double getAllPriceOfSoldBooksInOrder(){
        double price = 0;
        for (Order order : orders){
            price += getPriceOfSoldBooksInOrder(order);
        }
        return price;
    }

    /**
     * �������� ����� ��������� ������ ������
     * @param order ����� �� �������� ��������� ���������
     * @return ��������� ��� ���� ���������� ���� � ������
     */
    public static double getPriceOfSoldBooksInOrder(Order order){
        double price = 0;

        for (long bookId : order.getBooks()){
            Book book = getBookById(bookId);
            if (book != null){
                price += book.getPrice();
            }
        }
        return price;
    }

    // ����� ���������� ���� ��������� ����
    public static int getCountOfSoldBooks(){
        int count = 0;
        for (Order order : orders){
            count += order.getBooks().length;
        }
        return count;
    }

    /**
     * ���� ����� �� �� ����������� ������
     * @param id ���������� ����� �����
     * @return ��������� �����
     */
    public static Book getBookById(long id){
        Book current = null;

        for (Book book : books){
            if (book.getId() == id){
                current = book;
                break;
            }
        }
        return current;
    }
    public static void initData(){
        // ��������
        employees.add(new Employee(1, "������ ����", 32));
        employees.add(new Employee(2, "������ ����", 30));
        employees.add(new Employee(3, "��������� �����", 26));

        //����������
        customers.add(new Customer(1,"������� �������",25));
        customers.add(new Customer(2,"������� �������",32));
        customers.add(new Customer(3,"������� �����",19));
        customers.add(new Customer(4,"�������� �����",45));
        customers.add(new Customer(5,"���������� �����", 23));

        //�����
        books.add(new Book(1,"����� � ���","������� ���", 1600,BookGenre.Art));
        books.add(new Book(2,"������������ � ���������","����������� �����", 600, BookGenre.Art));
        books.add(new Book(3,"������� ����","������ �������", 750, BookGenre.Art));
        books.add(new Book(4,"������ � �������","������ ���������", 500, BookGenre.Art));

        books.add(new Book(5,"�������� � �����������","����� �������", 1050, BookGenre.Psychology));
        books.add(new Book(6,"���������� �������. �������. �����������. ���������","������� ������", 550, BookGenre.Psychology));
        books.add(new Book(7,"��� ��������� ������������ � ������ ����","������� ����", 1600, BookGenre.Psychology));

        books.add(new Book(8,"Gang of Four","����� ����", 900, BookGenre.Programming));
        books.add(new Book(9,"����������� ���","�������� ����", 1200, BookGenre.Programming));
        books.add(new Book(10,"�����������. ��������� ������������� ����","������� ������", 850, BookGenre.Programming));
        books.add(new Book(11,"���������. ���������� � �������","������ ����� �", 450, BookGenre.Programming));

        //������
        orders.add(new Order(1,1,1, new long[]{8,9,10,11}));
        orders.add(new Order(2,1,2, new long[]{1}));

        orders.add(new Order(3,2,3, new long[]{5,6,7}));
        orders.add(new Order(4,2,4, new long[]{1,2,3,4}));

        orders.add(new Order(5,3,5, new long[]{2,5,9}));
    }
}
