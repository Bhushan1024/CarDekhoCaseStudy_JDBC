//Bhushan Chavan
package com.jspiders.cardekho_case_study.operation;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

import com.jspiders.cardekho_case_study.entity.Car;

public class CarOperation {
	
	private static Connection connection;
	private static Statement statement;
	private static PreparedStatement preparedStatement;
	private static ResultSet resultSet;
	private static int result;
	private static FileInputStream file;
	private static Properties properties=new Properties();
	private final static String filePath="D:\\WEJA2\\jdbc\\resources\\db_info.properties";
	private static String query;
	

	public  void addCars(){
		try {
			openConnection();
			Scanner scanner =new Scanner(System.in);
			int choice=scanner.nextInt();
			query="insert into car_details values(?,?,?,?,?,?)";
			preparedStatement=connection.prepareStatement(query);
			

			for(int i=1;i<=choice;i++)
			{
				
				System.out.println("Enter the details for car "+i);
				Car car=new Car();
				
				try {
					System.out.println("\nEnter Car id: ");
					car.setCar_id(scanner.nextInt());
					
					System.out.println("\nEnter Car Name: ");
					car.setName(scanner.next());
					
					System.out.println("\nEnter Car Model: ");
					car.setModel(scanner.next());
					
					System.out.println("\nEnter Car Brand: ");
					car.setBrand(scanner.next());
					
					System.out.println("\nEnter Car Fuel Type: ");
					car.setFuel_type(scanner.next());
					
					System.out.println("\nEnter Car Price: ");
					car.setPrice(scanner.nextDouble());
					
					preparedStatement.setInt(1, car.getCar_id());
					preparedStatement.setString(2, car.getName());
					preparedStatement.setString(3, car.getModel());
					preparedStatement.setString(4, car.getBrand());
					preparedStatement.setString(5, car.getFuel_type());
					preparedStatement.setDouble(6, car.getPrice());
					
					try {
						result=preparedStatement.executeUpdate();
						
					} catch (Exception e) {
						System.out.println("Entered ID already exists! "
								+ "Record not added!");
					}
					
				} catch (Exception e) {
					System.out.println("Invalid input! "
							+ "Data not inserted!");
					break;
				}
				
				if (result!=0) 
				{
					System.out.println("Car no. "+i+" added.");
				}	
				
			}		
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			closeConnection();
		}	
	}
	
	public static void updateCar(){
		try {
			getAllCarDetails();
			openConnection();
			System.out.print("Select Car id to update : ");
			Scanner scanner=new Scanner(System.in);
			
			Car car=new Car();
			car.setCar_id(scanner.nextInt());
			System.out.print("Select option to update : \n"
					+ "1. Name\n"
					+ "2. Model\n"
					+ "3. Brand\n"
					+ "4. Fuel Type\n"
					+ "5. Price");
			int choice = scanner.nextInt();

			switch(choice){
				case 1:
					query = "update car_details "
							+ "set name = ? "
							+ "where car_id = " + car.getCar_id();
					preparedStatement = connection.prepareStatement(query);
					System.out.println("Enter new car name : ");
					car.setName(scanner.next());
					preparedStatement.setString(1, car.getName());
					result = preparedStatement.executeUpdate();
					if (result != 0) {
						System.out.println("Car name updated. ");
					}
					break;
					
					
				case 2:
					query = "update car_details "
							+ "set brand = ? "
							+ "where car_id = " + car.getCar_id();
					preparedStatement = connection.prepareStatement(query);
					System.out.println("Enter new car model : ");
					car.setBrand(scanner.next());
					preparedStatement.setString(1, car.getModel());
					result = preparedStatement.executeUpdate();
					if (result != 0) {
						System.out.println("Car brand updated. ");
					}
					break;
				
				case 3:
					query = "update car_details "
							+ "set brand = ? "
							+ "where car_id = " + car.getCar_id();
					preparedStatement = connection.prepareStatement(query);
					System.out.println("Enter new car brand : ");
					car.setBrand(scanner.next());
					preparedStatement.setString(1, car.getBrand());
					result = preparedStatement.executeUpdate();
					if (result != 0) {
						System.out.println("Car brand updated. ");
					}
					break;
					
				case 4:
					query = "update car_details "
							+ "set fuel_type = ? "
							+ "where car_id = " + car.getCar_id();
					preparedStatement = connection.prepareStatement(query);
					System.out.println("Enter new car fuel type : ");
					car.setFuel_type(scanner.next());
					preparedStatement.setString(1, car.getFuel_type());
					result = preparedStatement.executeUpdate();
					if (result != 0) {
						System.out.println("Car fuel type updated. ");
					}
					break;
					
				case 5:
					query = "update car_details "
							+ "set price = ? "
							+ "where car_id = " + car.getCar_id();
					preparedStatement = connection.prepareStatement(query);
					System.out.println("Enter new car price : ");
					car.setPrice(scanner.nextDouble());
					preparedStatement.setDouble(1, car.getPrice());
					result = preparedStatement.executeUpdate();
					if (result != 0) {
						System.out.println("Car price updated. ");
					}
					break;
		
				default:
					System.out.println("Invalid choice. ");
					break;
				}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			closeConnection();
		}
	}
	
	public static void deleteCar(){
		try {
			openConnection();
			Scanner scanner=new Scanner(System.in);
			System.out.println("\nEnter Car Id You want to Delete: ");
			int car_name=scanner.nextInt();
			Car car=new Car();
			resultSet=getAllCarDetails();
			ArrayList<Integer> cars=new ArrayList<>();
			while (resultSet.next()) {
				cars.add((resultSet.getInt(1)));
				if(cars.contains(car_name)){
				query="delete from car_details where car_id=?";
				System.out.println("Enter Car  you want to delete: ");
				preparedStatement=connection.prepareStatement(query);
				preparedStatement.setInt(1, scanner.nextInt());
				result=preparedStatement.executeUpdate();
				}
			}		
			System.out.println("Car is Deleted!!");
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			closeConnection();
		}
	}
	
	public static void SearchByname(){
		try {
			openConnection();
			Scanner scanner=new Scanner(System.in);
			query="select * from car_details where name=?";
			System.out.println("Enter Car Name you Want to Search:");
			preparedStatement=connection.prepareStatement(query);
			preparedStatement.setString(1, scanner.next());
			resultSet=preparedStatement.executeQuery();
			
			while(resultSet.next()){
				System.out.println(resultSet.getInt(1)+ " | "+ resultSet.getString(2)+" | " + resultSet.getString(3)+" | "+ resultSet.getString(4)+" | "+ resultSet.getString(5)+" | "+resultSet.getFloat(6));
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			closeConnection();
		}
	}
	
	public static void SearchByModel(){
		try {
			openConnection();
			Scanner scanner=new Scanner(System.in);
			query="select * from car_details where model=?";
			System.out.println("Enter Car Model you Want to Search:");
			preparedStatement=connection.prepareStatement(query);
			preparedStatement.setString(1, scanner.next());
			resultSet=preparedStatement.executeQuery();
			
			while(resultSet.next()){
				System.out.println(resultSet.getInt(1)+ " | "+ resultSet.getString(2)+" | " + resultSet.getString(3)+" | "+ resultSet.getString(4)+" | "+ resultSet.getString(5)+" | "+resultSet.getFloat(6));
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			closeConnection();
		}
	}
	
	public static void SearchByBrand(){
		try {
			openConnection();
			Scanner scanner=new Scanner(System.in);
			query="select * from car_details where brand=?";
			System.out.println("Enter Car Brand you Want to Search:");
			preparedStatement=connection.prepareStatement(query);
			preparedStatement.setString(1, scanner.next());
			resultSet=preparedStatement.executeQuery();
			
			while(resultSet.next()){
				System.out.println(resultSet.getInt(1)+ " | "+ resultSet.getString(2)+" | " + resultSet.getString(3)+" | "+ resultSet.getString(4)+" | "+ resultSet.getString(5)+" | "+resultSet.getFloat(6));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			closeConnection();
		}
	}
	
	public static void SearchByFuelType(){
		try {
			openConnection();
			Scanner scanner=new Scanner(System.in);
			query="select * from car_details where fuel_type=?";
			System.out.println("Enter Car Fuel Type you Want to Search:");
			preparedStatement=connection.prepareStatement(query);
			preparedStatement.setString(1, scanner.next());
			resultSet=preparedStatement.executeQuery();
			
			while(resultSet.next()){
				System.out.println(resultSet.getInt(1)+ " | "+ resultSet.getString(2)+" | " + resultSet.getString(3)+" | "+ resultSet.getString(4)+" | "+ resultSet.getString(5)+" | "+resultSet.getFloat(6));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			closeConnection();
		}
	}
	
	public static  ResultSet getAllCarDetails(){
		try {
			openConnection();
			query="select * from car_details";
			
			resultSet=statement.executeQuery(query);
			
			while(resultSet.next()){
				System.out.println(resultSet.getInt(1)+ " | "+ resultSet.getString(2)+" | " + resultSet.getString(3)+" | "+ resultSet.getString(4)+" | "+ resultSet.getString(5)+" | "+resultSet.getFloat(6));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			closeConnection();
		}
		return resultSet;
	}
	private static void openConnection()
	{
		try {
			file=new FileInputStream(filePath);
			properties.load(file);
			
			Class.forName(properties.getProperty("driverPath"));
//			System.out.println("Success1..");
			
			connection=DriverManager.getConnection(properties.getProperty("dburl"), properties);
//			System.out.println("Success2");
			statement=connection.createStatement();
//			System.out.println("Success3");
//			preparedStatement=connection.prepareStatement(query);
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	private static void closeConnection(){
		try {
			if(connection!=null){
				connection.close();
			}
			if(statement!=null){
				statement.close();
			}
			if(preparedStatement!=null){
				preparedStatement.close();
			}
			if(resultSet!=null){
				resultSet.close();
			}
			if(file!=null){
				file.close();
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

