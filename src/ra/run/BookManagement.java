package ra.run;


import ra.bussiness.Book;

import java.util.Scanner;
import java.util.regex.Pattern;

public class BookManagement {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BookManagement store = new BookManagement();

        byte choice;
        while (true) {
            System.out.println("****************JAVA-HACKATHON-05-BASIC-MENU***************");
            System.out.println("1. Nhập số lượng sách thêm mới và nhập thông tin cho từng cuốn sách");
            System.out.println("2. Hiển thị thông tin tất cả sách trong thư viện");
            System.out.println("3. Tìm ra sản phẩm có giá lớn thứ hai");
            System.out.println("4. Xóa sách theo mã sách");
            System.out.println("5. Tìm kiếm tương đối sách theo tên sách hoặc mô tả");
            System.out.println("6. Thay đổi thông tin sách theo mã sách");
            System.out.println("7. Thoát");
            System.out.println("Lựa chọn của bạn là:");
            choice = Byte.parseByte(sc.nextLine());

            switch (choice) {
                case 1:
                    store.addBook(sc);
                    break;
                case 2:
                    store.displayBook();
                    break;
                case 3:
                    store.findBookSecondMax();
                    break;
                case 4:
                    store.deleteBook(sc);
                    break;
                case 5:
                    System.out.println("Nhập từ khóa tìm kiếm:");
                    String keyword = sc.nextLine();
                    store.searchBooksByKeyword(keyword);
                    break;
                case 6:
                    store.editBook(sc);
                    break;
                case 7:
                    System.out.println("Cam on ban da su dung chuong trinh!");
                    return;
                default:
                    System.out.println("Lua chon khong hop le. Vui long chon lai.");
            }
        }
    }

    private Book[] books = new Book[0];

    public BookManagement() {
    }

    public void addBook(Scanner sc) {
        System.out.println("Nhap so sách ban muon them moi");
        int totalBook = Integer.parseInt(sc.nextLine());

        Book[] oldBooks = this.books;
        this.books = new Book[oldBooks.length + totalBook];

        for (int i = 0; i < oldBooks.length; i++) {
            this.books[i] = oldBooks[i];
        }

        for (int i = oldBooks.length; i < books.length; i++) {

            System.out.println("Nhâp tên sách");
            String bookName = sc.nextLine();
            System.out.println("Nhập tên tác giả");
            String author = sc.nextLine();
            System.out.println("Nhập mô tả sách");
            String descriptions = sc.nextLine();
            System.out.println("Giá nhập");
            double importPrice = Double.parseDouble(sc.nextLine());
            System.out.println("Giá xuất");
            double exportPrice = Double.parseDouble(sc.nextLine());
            System.out.println("Trạng thái sách");
            boolean bookStatus = Boolean.parseBoolean(sc.nextLine());
            float interest = (float) (exportPrice - importPrice);
            Book newBook = new Book(bookName, author, descriptions, importPrice, exportPrice, interest, bookStatus);
            this.books[i] = newBook;
        }
        System.out.println("Thêm mới thành công, mời bạn chọn số 2 để xem chi tiết");
    }

    public void displayBook() {
        System.out.println("DANH SÁCH SÁCH TRONG KHO");
        System.out.println("...................");
        byte lengthArr = (byte) books.length;
        if (lengthArr == 0) {
            System.out.println("chua co thong tin sách");
        } else {
            for (int i = 0; i < lengthArr; i++) {
                System.out.printf("Day la thong tin sách thu %d: \n", i + 1);
                System.out.println(books[i].toString());
            }
        }
        System.out.println("...................");
    }

    public void deleteBook(Scanner sc) {

        byte choiseIndex = getIndexFromUser(sc);

        byte bookArrLength = (byte) books.length;

        if (books.length == 0) {
            System.out.println("Kho trống");
            return;
        }

        Book[] newBooks = new Book[bookArrLength - 1];

        int currentIndex = 0;
        for (int i = 0; i < bookArrLength; i++) {
            if (i == choiseIndex) {
                continue;
            }
            newBooks[currentIndex++] = books[i];
        }

        books = newBooks;
        System.out.println("Da xoa sách thanh cong!");
        displayBook();
    }

    public void editBook(Scanner sc) {
        if (books.length == 0) {
            System.out.println("Danh sách sách rỗng. Không thể chỉnh sửa.");
            return;
        }

        System.out.println("Danh sách sách:");
        displayBook();

        byte choiseIndex = getIndexFromUser(sc);
        Book bookToEdit = books[choiseIndex];

        System.out.println("Thông tin sách cần chỉnh sửa:");
        System.out.println(bookToEdit);

        System.out.println("Nhập tên sách mới:");
        String newName = sc.nextLine();
        System.out.println("Nhập tác giả mới:");
        String newAuthor = sc.nextLine();
        System.out.println("Nhập mô ta ve sách mới:");
        String newDescriptions = sc.nextLine();
        System.out.println("Nhập giá nhập mới:");
        double newImportPrice = Double.parseDouble(sc.nextLine());
        System.out.println("Nhập gia xuất mới:");
        double newExportPrice = Double.parseDouble(sc.nextLine());
        System.out.println("Cập nhật trạng thái");
        boolean newBookStatus = Boolean.parseBoolean(sc.nextLine());

        bookToEdit.setBookName(newName);
        bookToEdit.setAuthor(newAuthor);
        bookToEdit.setDescriptions(newDescriptions);
        bookToEdit.setImportPrice(newImportPrice);
        bookToEdit.setExportPrice(newExportPrice);
        bookToEdit.setBookStatus(newBookStatus);

        System.out.println("Đã cập nhật thông tin sách thành công:");
        System.out.println(bookToEdit);
    }

    public void findBookSecondMax() {
        if (books.length < 2) {
            System.out.println("Không có đủ sách để tìm sách có giá xuất lớn thứ hai.");
            return;
        }

        for (int i = 0; i < books.length - 1; i++) {
            for (int j = 0; j < books.length - i - 1; j++) {
                if (books[j].getExportPrice() > books[j + 1].getExportPrice()) {
                    Book temp = books[j];
                    books[j] = books[j + 1];
                    books[j + 1] = temp;
                }
            }
        }

        System.out.println("Sách có giá xuất lớn thứ hai:");
        System.out.println(books[books.length - 2].toString());
    }

    public void searchBooksByKeyword(String keyword) {
        boolean found = false;
        String lowercaseKeyword = keyword.toLowerCase();
        System.out.println("Kết quả tìm kiếm sách phù hợp với từ khóa '" + keyword + "':");
        for (Book book : books) {
            String lowercaseBookName = book.getBookName().toLowerCase();
            String lowercaseDescription = book.getDescriptions().toLowerCase();
            if (lowercaseBookName.contains(lowercaseKeyword) || lowercaseDescription.contains(lowercaseKeyword)) {
                System.out.println(book.toString());
                found = true;
            }
        }
        if (!found) {
            System.out.println("Không tìm thấy sách nào phù hợp với từ khóa '" + keyword + "'.");
        }
    }

    public byte getIndexFromUser(Scanner scanner) {
        byte choiseIndex = 0;
        boolean isvalid = true;
        while (isvalid) {
            System.out.println("Vui long chon vi tri mong muon");
            byte inputIndex = (byte) (Byte.parseByte(scanner.nextLine()) - 1);
            if (inputIndex < books.length && inputIndex >= 0) {
                choiseIndex = inputIndex;
                isvalid = false;

            } else if (books.length == 0) {
                System.out.println("Kho trống");
                isvalid = false;
            } else {
                System.out.println("So qua lon hoac nho, vui long nhap lai");
            }
        }
        return choiseIndex;
    }

    private static String getInputFromUser(Scanner scanner, String regex){
        while (true){
            String inputData = scanner.nextLine();
            boolean isValid = Pattern.compile(regex).matcher(inputData).matches();
            if(isValid){
                return inputData;
            } else {
                System.out.println("Nhap sai hay nhap lai");
            }
        }
    }
}
