package edu.hdu.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hejiecheng on 17/5/8.
 */
public class DbHelp {

    private Connection connection = null ;
    private PreparedStatement preparedStatement = null ;
    private ResultSet resultSet = null ;

    public DbHelp(){
        try{
            Class.forName(DbConfig.driver) ;
        }
        catch (ClassNotFoundException e){
            System.out.println("驱动加载失败!");
            e.printStackTrace();
        }
    }

    // 获取数据库连接
    private Connection getConnection(){
        try{
            connection = DriverManager.getConnection(DbConfig.url , DbConfig.username , DbConfig.password) ;
        }
        catch (SQLException e){
            System.out.println("数据库连接数据有误!");
            e.printStackTrace();
        }
        return connection ;
    }

    public void test(){
        if(getConnection() != null){
            System.out.println("ok");
        }
        else{
            System.out.println("error");
        }
    }

    // 进行单行的修改
    public final boolean updateOneLine(String sqlModel , List<Object> parameter){
        boolean result = false ;
        int lines = -1 ;
        try{
            connection = getConnection() ;
            preparedStatement = connection.prepareStatement(sqlModel) ;
            if(parameter != null && !parameter.isEmpty()){
                for(int index = 1 , i = 0 ; i < parameter.size() ; ++ i , ++ index){
                    preparedStatement.setObject(index , parameter.get(i));
                }
            }
            lines = preparedStatement.executeUpdate() ;
            result = lines > 0 ? true : false ;
        }
        catch (SQLException e){
            System.out.println("updateOneLine Error!");
            e.printStackTrace();
            return result ;
        }
        finally {
            freedConnection();
        }
        return result ;
    }

    // 多行的修改-事务
    public final boolean updateMoreLines(String sqlModel , List<List<Object>> parameter){
        try{
            connection = getConnection() ;
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(sqlModel) ;
            if(parameter != null && !parameter.isEmpty()){
                for(int i = 0  ; i < parameter.size() ; ++ i){
                    List<Object> oneLine = parameter.get(i) ;
                    for(int j = 0 ; j < oneLine.size() ; ++ j){
                        preparedStatement.setObject(j+1 , oneLine.get(j));
                    }
                    preparedStatement.execute() ;
                }
            }
            connection.commit();
        }
        catch (SQLException e){
            System.out.println("批量数据插入出错!");
            e.printStackTrace();
            try {
                connection.rollback();
            }
            catch (SQLException con){
                con.printStackTrace();
            }
            return false ;
        }
        finally {
            freedConnection();
        }
        return true ;
    }
    // 多条语句执行
    public final boolean updateTwoSql(String sqlModel_1 , List<List<Object>> parameter_1 , String sqlModel_2 , List<List<Object>> parameter_2){
        try{
            connection = getConnection() ;
            connection.setAutoCommit(false);
            //进行第一条语句
            preparedStatement = connection.prepareStatement(sqlModel_1) ;
            if(parameter_1 != null && !parameter_1.isEmpty()){
                for(int i = 0 ; i < parameter_1.size() ; ++ i){
                    List<Object> oneLine = parameter_1.get(i) ;
                    for(int j = 0 ; j < oneLine.size() ; ++ j){
                        preparedStatement.setObject(j+1 , oneLine.get(j));
                    }
                    preparedStatement.execute();
                }
            }
            //进行第二天语句
            preparedStatement = connection.prepareStatement(sqlModel_2) ;
            if(parameter_2 != null && !parameter_2.isEmpty()){
                for(int i = 0 ; i < parameter_2.size() ; ++ i){
                    List<Object> oneLine = parameter_2.get(i) ;
                    for(int j = 0 ; j < oneLine.size() ; ++ j){
                        preparedStatement.setObject(j+1 , oneLine.get(j));
                    }
                    preparedStatement.execute();
                }
            }
            connection.commit();
        }
        catch (SQLException e){
            e.printStackTrace();
            try {
                connection.rollback();
            }
            catch (SQLException con){
                con.printStackTrace();
                return false ;
            }
            return false ;
        }
        return true ;
    }

    // 查询单行
    public Map<String , Object> findSimpleResult(String sqlModel , List<Object> parameter){
        Map<String , Object> map = new HashMap<String, Object>() ;
        try{
            connection = getConnection() ;
            preparedStatement = connection.prepareStatement(sqlModel) ;
            if(parameter != null && !parameter.isEmpty()){
                for(int i = 0 ; i < parameter.size() ; ++ i){
                    preparedStatement.setObject(i+1 , parameter.get(i));
                }
            }
            resultSet = preparedStatement.executeQuery() ;
            ResultSetMetaData metaData = resultSet.getMetaData() ;
            int col_len = metaData.getColumnCount() ;
            while(resultSet.next()){
                for(int i = 0 ; i < col_len ; ++ i) {
                    String col_name = metaData.getColumnLabel(i + 1);
                    Object col_val = resultSet.getObject(col_name);
                    if (col_val == null) {
                        col_val = "";
                    }
                    map.put(col_name , col_val) ;
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return null ;
        }
        finally {
            freedConnection();
        }
        return map ;
    }

    // 获取多行
    public List<Map<String , Object>> findMoreLines(String sqlModel , List<Object> parameter){
        List<Map<String , Object>> list = new ArrayList<Map<String, Object>>() ;
        try{
            connection = getConnection() ;
            preparedStatement = connection.prepareStatement(sqlModel) ;
            if(parameter != null && !parameter.isEmpty()){
                for(int i = 0 ; i < parameter.size() ; ++ i){
                    preparedStatement.setObject(i+1 , parameter.get(i));
                }
            }
            resultSet = preparedStatement.executeQuery() ;
            ResultSetMetaData metaData = resultSet.getMetaData() ;
            int col_len = metaData.getColumnCount() ;
            while(resultSet.next()){
                Map<String , Object> map = new HashMap<String , Object>() ;
                for(int i = 0 ; i < col_len ; ++ i){
                    String col_name = metaData.getColumnLabel(i+1) ;
                    Object col_val = resultSet.getObject(col_name) ;
                    if(col_val == null){
                        col_val = "" ;
                    }
                    map.put(col_name , col_val) ;
                }
                list.add(map) ;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            return null ;
        }
        finally {
            freedConnection();
        }
        return list ;
    }

    // 释放资源
    public void freedConnection(){
        if(resultSet != null){
            try {
                resultSet.close();
            }
            catch (Exception e){ e.printStackTrace();}
        }
        if(preparedStatement != null){
            try {
                preparedStatement.close();
            }
            catch (Exception e){e.printStackTrace();}
        }
        if(connection != null){
            try {
                connection.close();
            }
            catch (Exception e){e.printStackTrace();}
        }
    }
}
