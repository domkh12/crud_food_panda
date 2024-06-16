package controller;

import model.dto.CustomerDto;
import model.entity.Customer;
import model.service.CustomerService;
import model.service.CustomerServiceImpl;
import org.intellij.lang.annotations.JdkConstants;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class CustomerController {
    private final CustomerService customerService = new CustomerServiceImpl();
    public void getAllCustomers() {
        List<CustomerDto> customerDtosList = customerService.getCustomers();

        Table table = new Table(3, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.ALL);
        table.setColumnWidth(0, 10, 20);
        table.setColumnWidth(1, 20, 40);
        table.setColumnWidth(2, 30, 50);

        table.addCell("ID", new CellStyle(CellStyle.HorizontalAlign.CENTER));
        table.addCell("Name", new CellStyle(CellStyle.HorizontalAlign.CENTER));
        table.addCell("Email", new CellStyle(CellStyle.HorizontalAlign.CENTER));

        for (CustomerDto customerDto : customerDtosList) {
            table.addCell(String.valueOf(customerDto.id()), new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell(customerDto.name(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell(customerDto.email(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
        }

        System.out.println(table.render());

    }
    public void insertCustomer() {
        System.out.print("👤 Insert Name: ");
        String name = new Scanner(System.in).next();
        System.out.print("📧 Insert Email: ");
        String email = new Scanner(System.in).next();
        System.out.print("🔐 Insert Password: ");
        String password = new Scanner(System.in).next();
        customerService.addCustomer(new Customer(
                null,name,email,password,false, Date.valueOf(LocalDate.now())
        ));
    }

    public void deleteCustomer() {
        System.out.print("🗑️ Delete Customer By ID: ");
        int id = new Scanner(System.in).nextInt();
        if(customerService.getCustomers().isEmpty()){
            System.out.print("Are you sure you want to delete this customer? (Y/N): ");
            String answer = new Scanner(System.in).next();
            if (answer.equals("Y")) {
                customerService.deleteCustomer(id);
            }
        }
    }

    public void updateCustomer() {
        System.out.print("🪪 Update Customer By ID: ");
        int id = new Scanner(System.in).nextInt();
        customerService.updateCustomer(id);
    }
}
