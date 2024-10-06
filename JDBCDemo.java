import java.sql.*;
 
public class JDBCDemo {
 
	public static void main(String[] args) throws Exception {
	 
		batchdemo();
 }
	public static void readRecords() throws Exception {

	 String url="jdbc:mysql://localhost:3306/jdbcdemo";
	 String username="abstract-programmer";
	 String password="example-password";
	 String query="select * from employee";
	 
//	 Before java versions they will use the below line to load the jdbc driver
//	 Class.forName("com.mysql.cj.jdbc.Driver");
	 
//	 we are using DRIVE MANAGER because connection is an interface so that we cannot access it directly
	 Connection con=DriverManager.getConnection(url, username, password);
	 
//	 with this STATEMENT only we can execute sql queries
	 Statement st =con.createStatement();
	 
//	 st.executeQuery(query); // this actually returns a RESULTSET OBJECT
//   so we are creating RESULTSET OBJECT to receive the data
//   *********** ExecuteQuery can only be given if data will not be affected
	 ResultSet rs =st.executeQuery(query);
	 
//	 we are using RS.NEXT() to move the pointer to the next next columns
//	 rs.next();
//	 System.out.println("Id is "+rs.getInt(1)); // we are specifying column value in getInt() i.e 1st column
//	 System.out.println("Name is "+rs.getString(2));
//	 System.out.println("Salary is "+rs.getInt(3));
	 
//	 ***************23:01 min**********
//   to traverse multiple rows you can use while loop ,inside the condition is rs.next it means until there is no next information to feed it will run
	 
	 while(rs.next()) {
		 System.out.println("Id is "+rs.getInt(1));
		 System.out.println("Name is "+rs.getString(2));
		 System.out.println("Salary is "+rs.getInt(3));
	 }
//  we are closing the connection	 
	 con.close();
 }
	
//	************************ INSERT *************************
	
	public static void insertRecords() throws Exception{
		
		 String url="jdbc:mysql://localhost:3306/jdbcdemo";
		 String username="abstract-programmer";
		 String password="example-password";
		 
// hard coding of values
		 String query="insert into employee values(4,'priya',250000)";
		
		 Connection con=DriverManager.getConnection(url, username, password);
		 Statement st =con.createStatement();
// EXECUTE UPDATE will return integer value of how many rows it affected
		 int rows=st.executeUpdate(query);
		 
		 System.out.println("Number of rows affected:"+rows);
	}
	
//	public static void insertVar() throws Exception{
//		
//		 String url="jdbc:mysql://localhost:3306/jdbcdemo";
//		 String username="abstract-programmer";
//		 String password="example-password";
//// 1 METHOD		 
////		 int id=5;
////		 String name="Varun";
////		 int salary=300000;
//// always use variables for dynamically reading inputs from users
////		 String query="insert into employee values(" + id +",'"+name+"',"+salary+")";
//		
//		 Connection con=DriverManager.getConnection(url, username, password);
//		 
//// this statement is not needed only for values if it is known
////		 Statement st =con.createStatement();
////EXECUTE UPDATE will return integer value of how many rows it affected
//		 
////		 int rows=st.executeUpdate(query);
//		 
////		 System.out.println("Number of rows affected:"+rows);
//         con.close();	
//}
	public static void insertUsingPst() throws Exception{
		String url="jdbc:mysql://localhost:3306/jdbcdemo";
		 String username="abstract-programmer";
		 String password="example-password";
		 int id=6;
	 String name="Nila";
	 int salary=400000;
// We are going to use prepared statement so put ?
		 String query="insert into employee values(?,?,?)";
		 
		 Connection con=DriverManager.getConnection(url, username, password);
// preparedStatement is an object that is precompiled sql statements		 
		 PreparedStatement pst= con.prepareStatement(query);
//it means 1st ? takes id as its value 		 
		 pst.setInt(1,id);// (?'s number i.e 1st ? ,id is the variable)
		 pst.setString(2,name);
		 pst.setInt(3,salary);
// here we are not passing the query , executeUpdate returns integer i.e no.of affected rows		 
		 int rows = pst.executeUpdate();
		 System.out.println("Number of rows affected:"+rows);
		 con.close();
		 
	}
	
//	************************ DELETE*************************
	
	public static void delete() throws Exception{
		
		 String url="jdbc:mysql://localhost:3306/jdbcdemo";
		 String username="abstract-programmer";
		 String password="example-password";
		 int id=5;
		 String query="delete from employee where emp_id="+id;
		 Connection con=DriverManager.getConnection(url,username,password);
		 Statement st=con.createStatement();
		 int rows=st.executeUpdate(query);
		 System.out.println("Number of rows affected:"+rows);
		 con.close();
}
//	************************ UPDATE *************************
	
	public static void update() throws Exception{
		
		 String url="jdbc:mysql://localhost:3306/jdbcdemo";
		 String username="abstract-programmer";
		 String password="example-password";
		 int id=1;
		 int salary=10000;
		 String query="update employee set salary ="+salary+"where emp_id="+id;
//		 String query="update employee set salary=1000 where emp_id=1";
		 Connection con=DriverManager.getConnection(url,username,password);
		 Statement st=con.createStatement();
		 int rows=st.executeUpdate(query);
		 System.out.println("Number of rows affected:"+rows);
		 con.close();
}
//	************************ 41:45 min *************
//  ******************STORED PROCEDURE *****************************	
	public static void sp() throws Exception{
		String url="jdbc:mysql://localhost:3306/jdbcdemo";
		 String username="abstract-programmer";
		 String password="example-password";
		 Connection con=DriverManager.getConnection(url,username,password);
//     prepareCall gives the callable statement object so we are creating the callable statement object		 
		 CallableStatement cst=  con.prepareCall("{call GetEmp()}");
		 ResultSet rs=cst.executeQuery();
		 
		 while(rs.next()) {
			 System.out.println("Id is "+rs.getInt(1));
			 System.out.println("Name is "+rs.getString(2));
			 System.out.println("Salary is "+rs.getInt(3));
		 }
		 con.close();
	}
//  ******************STORED PROCEDURE WITH IN PARAMETER*****************************	
	public static void sp2() throws Exception{
		String url="jdbc:mysql://localhost:3306/jdbcdemo";
		 String username="abstract-programmer";
		 String password="example-password";
		 int id=3;
		 Connection con=DriverManager.getConnection(url,username,password);
//     prepareCall gives the callable statement object so we are creating the callable statement object		 
		 CallableStatement cst=  con.prepareCall("{call GetEmpById(?)}");
//     we can set value in callable statement directly dont need to use prepared statement		 
		 cst.setInt(1, id);
		 ResultSet rs=cst.executeQuery();
		 
		 while(rs.next()) {
			 System.out.println("Id is "+rs.getInt(1));
			 System.out.println("Name is "+rs.getString(2));
			 System.out.println("Salary is "+rs.getInt(3));
		 }
		 con.close();
	}
//  ******************STORED PROCEDURE WITH OUT PARAMETER*****************************	
	public static void sp3() throws Exception{
		String url="jdbc:mysql://localhost:3306/jdbcdemo";
		 String username="abstract-programmer";
		 String password="example-password";
		 int id=3;
		 Connection con=DriverManager.getConnection(url,username,password);
//     prepareCall gives the callable statement object so we are creating the callable statement object		 
		 CallableStatement cst=  con.prepareCall("{call GetEmpNameById(?,?)}");
//     we can set value in callable statement directly dont need to use prepared statement		 
		 cst.setInt(1, id);
//		 we cannot use this cst.setString(2,string) because we are going to get the output and then print 
		 cst.registerOutParameter(2,Types.VARCHAR);
//    we cannot use executeQuery because OUT parameter value is updated inside the procedure
		 cst.executeUpdate();
//     to access the value we are using getString()		 
		 System.out.println(cst.getString(2));
		 
		 con.close();
	}
//	******************COMMIT VS AUTO COMMIT****************************
//  NOTE:when giving autocommit to false we must give commit later in the program
	public static void commitdemo() throws Exception{
		String url="jdbc:mysql://localhost:3306/jdbcdemo";
		 String username="abstract-programmer";
		 String password="example-password";
		 String query1="update employee set salary=550000 where emp_id=1";
		 String query2="update employee set salary=550000 where emp_id=2";
		 
		 Connection con=DriverManager.getConnection(url,username,password);
//   this setAutoCommit to false prevents the database being updated by undesired actions		 
		 con.setAutoCommit(false);
		 Statement st=con.createStatement();
		 int rows1=st.executeUpdate(query1);
		 System.out.println("Rows affected"+rows1);
		 int rows2=st.executeUpdate(query2);
		 System.out.println("Rows affected"+rows2);
//    by this condition we allow it to commit changes to the database on our desire		
		 if(rows1>0 && rows2>0) {
			 con.commit();
			 }
		 con.close();
		 }
	
//   ****************************** BATCH PROCESSING *******************
//  NOTE:Batch Processing is done to execute all queries in a single batch  by adding all queries in one batch
	public static void batchdemo() throws Exception{
		String url="jdbc:mysql://localhost:3306/jdbcdemo";
		 String username="abstract-programmer";
		 String password="example-password";
		 
		 String query1="update employee set salary=4000 where emp_id=1";
		 String query2="update employee set salary=4000 where emp_id=2";
		 String query3="update employee set salary=4000 where emp_id=3";
		 String query4="updat employee set salary=4000 where emp_id=4";
		 
		 Connection con=DriverManager.getConnection(url,username,password);
		 con.setAutoCommit(false);
		 Statement st=con.createStatement();
		 st.addBatch(query1);
		 st.addBatch(query2);
		 st.addBatch(query3);
		 st.addBatch(query4);
//    executebatch() will produce output as integer array so we are stroing it in integer array	 
		 int[] res=st.executeBatch();
		 
		 for(int i:res) {
			 if(i>0)
			 System.out.println("Rows Affected:"+i);
//    ****************ROLL BACK************************			 
			 else con.rollback();
		 }
		 con.commit();
		 con.close();
		 }
}
