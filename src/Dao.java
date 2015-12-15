import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Dao {
  static Connection con = ConnectionProvider.getCon();
	static PreparedStatement statement;

	public static List<Bean> getAllProduct(int pageIndex, int perPage) {
        List<Bean> myBean = new ArrayList<Bean>();

        String query = "SELECT * FROM MYPRODUCT  limit "+pageIndex+","+perPage;
        try {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                        Bean bean = new Bean();
                        bean.setProductId(rs.getInt("productId"));
                        bean.setName(rs.getString("NAME"));
                        bean.setPrice(rs.getFloat("price"));
                        bean.setExpiryYear(rs.getString("expiryYear"));
                        myBean.add(bean);
                }
        } catch (SQLException e) {
                System.err.println(e.getMessage());
        }
        return myBean;
}
	public static void add(Bean bean) {
		System.out.println("Inside add method in DAO");
		Random r = new Random();
		int id = r.nextInt(10000);
		try{
		statement=con.prepareStatement("insert into myProduct values(?,?,?,?)");
		statement.setInt(1, id);
		statement.setString(2, bean.getName());
		statement.setFloat(3, bean.getPrice());
		statement.setString(4,bean.getExpiryYear());
		
		int status= statement.executeUpdate();		
        } catch (SQLException e) {
                System.err.println(e.getMessage());
        }
  }
	
	public static void deleteStudent(int userId) {
		System.out.println("Inside Dao Drop");
		String deleteQuery = "DELETE FROM myproduct WHERE productId = ?";
		try {
			statement = con.prepareStatement(deleteQuery);
			statement.setInt(1, userId);
			statement.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	public static void update(Bean bean)  {
		System.out.println("Inside update dao");
		String updateQuery = "UPDATE myproduct SET NAME = ?, " +
				"Price = ?, expiryYear = ? WHERE productId = ?";
		try {
			statement = con.prepareStatement(updateQuery);		
			statement.setString(1, bean.getName());
			statement.setFloat(2, bean.getPrice());
			statement.setString(3, bean.getExpiryYear());
			statement.setInt(4, bean.getProductId());
			statement.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}	
	public static int getProductCount(){
		 int count=0;
		 try {
		      Statement   statement =  con.createStatement();
		  ResultSet rs =  statement.executeQuery("select count(*) as count from myProduct");
		  while (rs.next()) {
		   count=rs.getInt("count");
		  }
		 } catch (SQLException e) {
		  e.printStackTrace();
		 }
		 System.out.println("Total record in the product table "+count);
		 return count;
		}	
}