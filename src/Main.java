import model.dao.CustomerDaoImpl;
import model.entity.Customer;
import model.entity.Order;
import model.entity.Product;
import model.dao.OrderDaoImpl;
import view.ViewUi;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("\n" +
                "  ███████╗ ██████╗  ██████╗ ██████╗     ██████╗  █████╗ ███╗   ██╗██████╗  █████╗ \n" +
                "  ██╔════╝██╔═══██╗██╔═══██╗██╔══██╗    ██╔══██╗██╔══██╗████╗  ██║██╔══██╗██╔══██╗\n" +
                "  █████╗  ██║   ██║██║   ██║██║  ██║    ██████╔╝███████║██╔██╗ ██║██║  ██║███████║\n" +
                "  ██╔══╝  ██║   ██║██║   ██║██║  ██║    ██╔═══╝ ██╔══██║██║╚██╗██║██║  ██║██╔══██║\n" +
                "  ██║     ╚██████╔╝╚██████╔╝██████╔╝    ██║     ██║  ██║██║ ╚████║██████╔╝██║  ██║\n" +
                "  ╚═╝      ╚═════╝  ╚═════╝ ╚═════╝     ╚═╝     ╚═╝  ╚═╝╚═╝  ╚═══╝╚═════╝ ╚═╝  ╚═╝\n" +
                "                                                                                  \n");
        ViewUi view = new ViewUi();
        view.ui();
    }
}