package model.dao;

import model.entity.Customer;
import model.entity.Order;
import model.entity.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OrderDaoImpl implements OrderDao{
    @Override
    public int addNewOrder(Order order) {
        String sql = """
                INSERT INTO "order" (order_name, order_description, cus_id, ordered_at) VALUES (?, ?, ?, ?)
                """;
        String sql1 = """
                INSERT INTO "product_order" VALUES (?, ?)
                """;
        try(
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
        ){
            preparedStatement.setString(1, order.getOrderName());
            preparedStatement.setString(2, order.getOrderDescription());
            preparedStatement.setInt(3, order.getCustomer().getId());
            preparedStatement.setDate(4, order.getOrderedAt());
            int rows = preparedStatement.executeUpdate();
            if(rows > 0) {
                System.out.println("Insert Successfully");
            }

//            Product Order
            for (Product product : order.getProductList()){
                preparedStatement1.setInt(1, product.getId());
                preparedStatement1.setInt(2, order.getId());
            }
            int rowaffect = preparedStatement1.executeUpdate();
            if( rowaffect > 0){
                System.out.println("Product has been ordered");
            }else{
                System.out.println("Product Out of stock");
            }

        }catch (SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
        return 0;
    }

    @Override
    public int deleteOrderById(Integer id) {
        String sql = """
                DELETE FROM "order" WHERE id = ?
                """;
        String sql1 = """
                DELETE FROM "product_order" WHERE order_id = ?
                """;
        try(
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
                ){
             Order order = searchOrderById(id);
             if (order != null){
                 preparedStatement.setInt(1, id);
                 preparedStatement1.setInt(1, id);
                 preparedStatement1.executeUpdate();
                 int rowaffected = preparedStatement.executeUpdate();
                 if(rowaffected > 0) {
                     System.out.println("Delete Successfully");
                     return rowaffected;
                 }else {
                     System.out.println("Delete Failed");
                 }
             }else {
                 System.out.println("Not Found Order");
             }

        }catch (SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
        return 0;
    }

    @Override
    public int updateOrderById(Integer id) {
        String sql = """
                UPDATE "order" SET order_name = ?, order_description = ? WHERE id = ?
                """;
        try(
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ){
            Order order = searchOrderById(id);
                if (order != null){
                    System.out.println("[+] Insert Order Name: ");
                    preparedStatement.setString(1, new Scanner(System.in).nextLine());
                    System.out.println("[+] Insert Order Description: ");
                    preparedStatement.setString(2, new Scanner(System.in).nextLine());
                    preparedStatement.setInt(3, id);
                    int rowaffected = preparedStatement.executeUpdate();
                    if(rowaffected > 0) {
                        System.out.print("Update Successfully");
                        return rowaffected;
                    }else {
                        System.out.print("Update Failed");
                    }
                }else {
                    System.out.println("Not Found Order");
                }
        }catch (SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
        return 0;
    }

    @Override
    public Order searchOrderById(Integer id) {
        String sql = """
                SELECT * FROM "order" WHERE id = ?
                """;
        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ){
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Order order = null;
            if(resultSet.next()) {
                order = Order.builder()
                        .id(resultSet.getInt("id"))
                        .orderName(resultSet.getString("order_name"))
                        .orderDescription(resultSet.getString("order_description"))
                        .customer(Customer.builder()
                                .id(resultSet.getInt("cus_id"))
                                .build())
                        .orderedAt(resultSet.getDate("ordered_at"))
                        .build();
            }
            return order;
        }catch (SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
        return null;
    }

    @Override
    public List<Order> queryAllOrder() {
        String sql = """ 
                SELECT * FROM "order"
                INNER JOIN customer c ON "order".cus_id = c.id
                """;
        try(
                Connection connection = DatabaseConnection.getConnection();
                Statement statement = connection.createStatement();
        ){
            ResultSet resultSet = statement.executeQuery(sql);
            List<Order> orderList = new ArrayList<>();
            while (resultSet.next()){
                orderList.add(
                        Order.builder()
                                .id(resultSet.getInt("id"))
                                .orderName(resultSet.getString("order_name"))
                                .orderDescription(resultSet.getString("order_description"))
                                .orderedAt(resultSet.getDate("ordered_at"))
                                .customer(Customer.builder()
                                        .id(resultSet.getInt("cus_id"))
                                        .name(resultSet.getString("name"))
                                        .email(resultSet.getString("email"))
                                        .password(resultSet.getString("password"))
                                        .isDeleted(resultSet.getBoolean("is_deleted"))
                                        .creationDate(resultSet.getDate("created_date"))
                                        .build())
                                .build()
                );
            }
            return orderList;
        }catch (SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
        return new ArrayList<>();
    }
}
