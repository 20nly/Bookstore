import bookStore.model.*;

import java.util.ArrayList;

public class Main {

    static ArrayList<Book> books = new ArrayList<>();
    static ArrayList<Customer> customers = new ArrayList<>();
    static ArrayList<Employee> employees = new ArrayList<>();
    static ArrayList<Order> orders = new ArrayList<>();


    public static void main(String[] args) {
        initData();

        // задача 1
        String booksInfo =
                String.format("Общее кол-во проданных книг %d на сумму %f",
                        getCountOfSoldBooks(), getAllPriceOfSoldBooksInOrder());
        System.out.println(booksInfo);

        // задача 2
        for (Employee employee: employees) {
            System.out.println(employee.getName() + " продал(а) " +
                    getProfitByEmployee(employee.getId()).toString());
        }

        for (Order order: orders) {
            for (Book book : books){
                System.out.println(book.getGenre() + " = " + getPriceOfSoldBooksByGenre(order, book.getGenre()));
            }

        }


    }

    /**
     * получить общую сумму книг, в одном заказе по определенному ЖАНРУ
     * @param order заказ
     * @param genre жанр
     * @return общую стоимость
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
     * Получить общее количество и общую стоимость проданного товара для продавца
     * @param employeeId уникальный номер для продавца
     * @return количество и общую стоимость каждого продавца
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

    //получить общую стоимсоть ВСЕХ заказов
    public static double getAllPriceOfSoldBooksInOrder(){
        double price = 0;
        for (Order order : orders){
            price += getPriceOfSoldBooksInOrder(order);
        }
        return price;
    }

    /**
     * получить общую стоимсоть ОДНОГО заказа
     * @param order заказ по которому считается стоимость
     * @return стоимость для всех продагнных книг в заказе
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

    // общее количество всех проданных книг
    public static int getCountOfSoldBooks(){
        int count = 0;
        for (Order order : orders){
            count += order.getBooks().length;
        }
        return count;
    }

    /**
     * Писк книги по ее уникальному номеру
     * @param id уникальный номер книги
     * @return найденная книга
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
        // продавцы
        employees.add(new Employee(1, "Иванов Иван", 32));
        employees.add(new Employee(2, "Петров Петр", 30));
        employees.add(new Employee(3, "Васильева Алиса", 26));

        //покупатели
        customers.add(new Customer(1,"Сидоров Алексей",25));
        customers.add(new Customer(2,"Романов Дмитрий",32));
        customers.add(new Customer(3,"Симонов Кирил",19));
        customers.add(new Customer(4,"Кириенко Данил",45));
        customers.add(new Customer(5,"Воотынцева Элина", 23));

        //книги
        books.add(new Book(1,"Война и Мир","Толстой Лев", 1600,BookGenre.Art));
        books.add(new Book(2,"Преступление и наказание","Достоевский Федор", 600, BookGenre.Art));
        books.add(new Book(3,"Мертвые души","Гоголь Николай", 750, BookGenre.Art));
        books.add(new Book(4,"Руслан и Людмила","Пушкин Александр", 500, BookGenre.Art));

        books.add(new Book(5,"Введение в психоанализ","Фрейд Зигмунд", 1050, BookGenre.Psychology));
        books.add(new Book(6,"Психология влияния. Убеждай. Воздействуй. Защищайся","Чалдини Роберт", 550, BookGenre.Psychology));
        books.add(new Book(7,"Как перестать беспокоиться и начать жить","Карнеги Дейл", 1600, BookGenre.Psychology));

        books.add(new Book(8,"Gang of Four","Гамма Эрих", 900, BookGenre.Programming));
        books.add(new Book(9,"Совершенный код","Макконел Стив", 1200, BookGenre.Programming));
        books.add(new Book(10,"Рефакторинг. Улучшение Существующего кода","Фауллер Мартин", 850, BookGenre.Programming));
        books.add(new Book(11,"Аогоритмы. Построение и анализа","Кормен Томас Х", 450, BookGenre.Programming));

        //заказы
        orders.add(new Order(1,1,1, new long[]{8,9,10,11}));
        orders.add(new Order(2,1,2, new long[]{1}));

        orders.add(new Order(3,2,3, new long[]{5,6,7}));
        orders.add(new Order(4,2,4, new long[]{1,2,3,4}));

        orders.add(new Order(5,3,5, new long[]{2,5,9}));
    }
}
