/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import Database.Database;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lasith
 */
public class ToDo {
    
    private int todo_id;
    private String title;
    private String description;
    private String date;
    private String time;
    private String state;    
    private String category;
    
    private static final String FIND_ALL = "SELECT * FROM todos";
    private static final String SEARCH_TODOS = "SELECT * FROM todos WHERE title LIKE ?";        
    private static final String UPDATE_TODO = "UPDATE todos SET title=? , description= ? , date = ? , time = ? , state= ? , category = ? WHERE todo_id = ?";
    private static final String INSERT_TODO = "INSERT INTO `todos` VALUES (?,?,?,?,?,?,?)";
    private static final String DELETE_TODO = "DELETE FROM `todos` WHERE todo_id = ?";
    
    
    public int getTodo_id() {
        return todo_id;
    }

    public void setTodo_id(int todo_id) {
        this.todo_id = todo_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
   
    public static String find() {
        try (Connection con = Database.getConnection()) {
            List<ToDo> todos = new ArrayList();
            PreparedStatement findAll = con.prepareStatement(FIND_ALL);
            ResultSet resultSet = findAll.executeQuery();
            
            while(resultSet.next()){
                ToDo todo = new ToDo();
                todo.setTodo_id(resultSet.getInt("todo_id"));
                todo.setTitle(resultSet.getString("title"));
                todo.setDescription(resultSet.getString("description"));
                todo.setDate(resultSet.getString("date"));
                todo.setTime(resultSet.getString("time"));
                todo.setState(resultSet.getString("state"));
                todo.setTitle(resultSet.getString("title"));
                todo.setCategory(resultSet.getString("category"));
                todos.add(todo);
            }
            return new Gson().toJson(todos);
        } 
        catch (Exception e) {
            e.printStackTrace();
            String response = "{'message':'An error occured!'}";
            JsonElement jsonElement = JsonParser.parseString(response);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            return new Gson().toJson(jsonObject);
        }
    }
    
    public static String find(String title) {
        try (Connection con = Database.getConnection()) {
            List<ToDo> todos = new ArrayList();
            PreparedStatement findAll = con.prepareStatement(SEARCH_TODOS);
            findAll.setString(1,"%"+title.replace("\"", "")+"%");
            System.out.println(findAll);
            ResultSet resultSet = findAll.executeQuery();
            ToDo todo = new ToDo();
            while(resultSet.next()){
                todo.setTitle(resultSet.getString("title"));
                todo.setDescription(resultSet.getString("description"));
                todo.setDate(resultSet.getString("date"));
                todo.setTime(resultSet.getString("time"));
                todo.setState(resultSet.getString("state"));
                todo.setCategory(resultSet.getString("category"));
                todos.add(todo);
            }
            return new Gson().toJson(todos);
        } 
        catch (Exception e) {
            e.printStackTrace();
            String response = "{'message':'An error occured!'}";
            JsonElement jsonElement = JsonParser.parseString(response);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            return new Gson().toJson(jsonObject);
        }
        
    }
     
    public static String save(ToDo todo) {
        try (Connection con = Database.getConnection()) {
            PreparedStatement newToDo = con.prepareStatement(INSERT_TODO);
            newToDo.setString(1, null);            
            newToDo.setString(2, todo.getTitle());            
            newToDo.setString(3, todo.getDescription());            
            newToDo.setString(4, todo.getDate());            
            newToDo.setString(5, todo.getTime());            
            newToDo.setString(6, todo.getState());
            newToDo.setString(7, todo.getCategory());
            
            if(newToDo.executeUpdate() > 0){
                String response = "{'message':'Todo created!'}";
                JsonElement jsonElement = JsonParser.parseString(response);
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                return new Gson().toJson(jsonObject);
            }else{
                String response = "{'message':'An error occured!'}";
                JsonElement jsonElement = JsonParser.parseString(response);
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                return new Gson().toJson(jsonObject);
            }
            
        }
        catch (Exception e) {
            e.printStackTrace();
            String response = "{'message':'An error occured!'}";
            JsonElement jsonElement = JsonParser.parseString(response);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            return new Gson().toJson(jsonObject);
        }
    }
    
    public static String update(ToDo todo, int id) {
        try (Connection con = Database.getConnection()) {
            PreparedStatement updateToDo = con.prepareStatement(UPDATE_TODO);
            updateToDo.setString(1, todo.getTitle());            
            updateToDo.setString(2, todo.getDescription());            
            updateToDo.setString(3, todo.getDate());            
            updateToDo.setString(4, todo.getTime());            
            updateToDo.setString(5, todo.getState());
            updateToDo.setString(6, todo.getCategory());
            updateToDo.setInt(7, id);
            updateToDo.executeUpdate();
            String response = "{'message':'Todo updated!'}";
            JsonElement jsonElement = JsonParser.parseString(response);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            return new Gson().toJson(jsonObject);
        }
        catch (Exception e) {
            e.printStackTrace();
            String response = "{'message':'An error occured!'}";
            JsonElement jsonElement = JsonParser.parseString(response);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            return new Gson().toJson(jsonObject);

        }
    }

    public static String remove(int todo_id) {
        try (Connection con = Database.getConnection()) {
            PreparedStatement removeToDo = con.prepareStatement(DELETE_TODO);
            removeToDo.setInt(1, todo_id); 
            removeToDo.executeUpdate();
            String response = "{'message':'Todo removed!'}";
            JsonElement jsonElement = JsonParser.parseString(response);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            return new Gson().toJson(jsonObject);
        }
        catch (Exception e) {
            e.printStackTrace();
            String response = "{'message':'An error occured!'}";
            JsonElement jsonElement = JsonParser.parseString(response);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            return new Gson().toJson(jsonObject);
        }
    }
    
}
