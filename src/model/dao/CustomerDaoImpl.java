package model.dao;

import model.entity.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CustomerDaoImpl implements CustomerDao{
    @Override
    public List<Customer> queryAllCustomers() {
        String sql = """
                SELECT * FROM "customer"
                """;
        try(
                Connection connection = DatabaseConnection.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
        ){
            List<Customer> customers = new ArrayList<>();
        while (resultSet.next()){
            customers.add(
                    new Customer(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("email"),
                            resultSet.getString("password"),
                            resultSet.getBoolean("is_deleted"),
                            resultSet.getDate("created_date")
                    )
            );
        }
        return customers;
        }catch (SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
        return new ArrayList<>();
    }

    @Override
    public int updateCustomerById(Integer id) {
        String sql = """
                UPDATE "customer" SET name = ?, email = ?  WHERE id = ?;
                """;
        try(
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ){
            Customer customer = searchCustomerById(id);
            if(customer != null){
                System.out.print("[+] Insert name: ");
                preparedStatement.setString(1, new Scanner(System.in).next());
                System.out.print("[+] Insert email: ");
                preparedStatement.setString(2, new Scanner(System.in).next());
                preparedStatement.setInt(3, id);
                int rows = preparedStatement.executeUpdate();
                String message = rows>0 ? "Update Successfully" : "Update Faild";
                System.out.println(message);
            }else {
                System.out.println("Customer not found");
            }
        }catch (SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
        return 0;
    }

    @Override
    public int deleteCustomerById(Integer id) {
        String sql = """
                DELETE FROM "customer" WHERE id = ?
                """;
        try(
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ){
            Customer customer = searchCustomerById(id);
            if(customer == null) {
                System.out.print("Cannot Delete");
            }else{
                preparedStatement.setInt(1, id);
                int rows = preparedStatement.executeUpdate();
                if(rows > 0) {
                    System.out.print("Deleted Successfully");
                }
            }
        }catch (SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
        return 0;
    }

    @Override
    public int insertCustomer(Customer customer) {
        String sql = """
                INSERT INTO "customer" (name, email, password, is_deleted, created_date) VALUES (?, ?, ?, ?, ?)
                """;
        try(
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ){
                preparedStatement.setString(1, customer.getName());
                preparedStatement.setString(2, customer.getEmail());
                preparedStatement.setString(3, customer.getPassword());
                preparedStatement.setBoolean(4, customer.getIsDeleted());
                preparedStatement.setDate(5, customer.getCreationDate());
                int rows = preparedStatement.executeUpdate();
                if(rows > 0) {
                    System.out.println("Insert Successfully");
                }

        }catch (SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
        return 0;
    }

    @Override
    public Customer searchCustomerById(Integer id) {
        String sql = """
                SELECT * FROM "customer" WHERE id = ?
                """;
        try(
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ){
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Customer customer = null;
            while(resultSet.next()) {
                customer = Customer.builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .email(resultSet.getString("email"))
                        .password(resultSet.getString("password"))
                        .isDeleted(resultSet.getBoolean("is_deleted"))
                        .creationDate(resultSet.getDate("created_date"))
                        .build();
            }
            return customer;

        }catch (SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
        return null;
    }
}
