package com.ncu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.ncu.entity.AddCourse;
import com.ncu.entity.Course;
import com.ncu.entity.FeedBack;
import com.ncu.entity.Sign;
import com.ncu.entity.SignList;
import com.ncu.entity.Student;
import com.ncu.util.DbConnect;


public class StudentDAO implements IStudentDAO {
	protected static final String  FIELDS_INSERT ="openID,studentName,studentNum,joinTime";
	protected static String INSERT_SQL="insert into student ("+FIELDS_INSERT+")"+"values (?,?,?,?)";
	protected static String FINDCHECK_SQL="select * from student where openID=?";

	@Override
	public int create(Student stu) throws Exception { //ѧ��ע��
		  Connection con=null;
	      PreparedStatement prepStmt=null;
	      ResultSet rs=null;
	      int addNum=0;
	      SimpleDateFormat format=new  SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	      String nowtime=format.format(stu.getJoinTime());
	      try{
	    	  con=DbConnect.getDBconnection();
	    	  prepStmt =con.prepareStatement(INSERT_SQL);	    	  
	    	  prepStmt.setString(1,stu.getOpenID());
	    	  prepStmt.setString(2,stu.getStudentName());
	    	  prepStmt.setString(3,stu.getStudentNum());
	    	  prepStmt.setString(4, nowtime);
	          addNum=prepStmt.executeUpdate();
	          System.out.println(addNum);
	      } catch(Exception e){
	    	  System.out.println("ѧ��ע���׳��쳣");
	       e.printStackTrace();
	      } finally{
	    	  System.out.println("�ر�ע��ѧ������");
	    	  DbConnect.closeDB(con, prepStmt, rs);
	      }
		return addNum;
	}

	@Override
	public boolean findCheck(String userid) throws Exception {//����ж�
		Connection con=null;
	      PreparedStatement prepStmt=null;
	      ResultSet rs=null;
	      boolean res=true;
	      try{
	    	  con=DbConnect.getDBconnection();
	    	  prepStmt =con.prepareStatement(FINDCHECK_SQL);	    	  
	    	  prepStmt.setString(1,userid);
	          rs=prepStmt.executeQuery();
	          if(!rs.next()) {
	        	  res=false;
	          }
	      } catch(Exception e){
	       e.printStackTrace();
	      } finally{
	    	  System.out.println("�ر�����1");
	    	  DbConnect.closeDB(con, prepStmt, rs);
	      }
		return res;
	}

	@Override
	public List<Course> getAllCourse() throws Exception {//���пγ�
		String sql="select courseID,openID,courseName,ofClass,buildTime from course order by buildTime desc";
		Connection con=null;
		PreparedStatement prepStmt=null;
		ResultSet rs=null;
		List<Course> all=new ArrayList<>();
		try {
			con=DbConnect.getDBconnection();
			prepStmt=con.prepareStatement(sql);
			rs=prepStmt.executeQuery();
			while(rs.next())
			{
				Course course=new Course();
				course.setCourseID(rs.getInt(1));
				course.setOpenID(rs.getString(2));
				course.setCourseName(rs.getString(3));
				course.setOfClass(rs.getString(4));
				course.setBuildTime(rs.getTimestamp(5));
				all.add(course);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {			
			DbConnect.closeDB(con, prepStmt, rs);
			System.out.println("�ر��˲�ѯ���пγ�����");
		}		
		return all;
	}

	@Override
	public List<Course> getmyCourse(String userid) throws Exception {//ѧ���Ŀγ�
		String sql="select a.courseID,b.openID,courseName,ofClass,addTime from course a,addcourse b where a.courseID=b.courseID and b.openID=?";
		Connection con=null;
		PreparedStatement prepStmt=null;
		ResultSet rs=null;
		List<Course> all=new ArrayList<>();
		try {
			con=DbConnect.getDBconnection();
			prepStmt=con.prepareStatement(sql);
			prepStmt.setString(1, userid);
			rs=prepStmt.executeQuery();
			while(rs.next())
			{
				Course course=new Course();
				course.setCourseID(rs.getInt(1));
				course.setOpenID(rs.getString(2));
				course.setCourseName(rs.getString(3));
				course.setOfClass(rs.getString(4));
				course.setBuildTime(rs.getTimestamp(5));
				all.add(course);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {			
			DbConnect.closeDB(con, prepStmt, rs);
			System.out.println("�ر���ѧ����ѯ�ҵĿγ�����");
		}		
		return all;
	}

	@Override
	public int addCourse(AddCourse course) throws Exception {//ѧ����ӿγ�
		String sql="insert into addcourse(openID,courseID,addTime) values (?,?,?)";
		Connection con=null;
	      PreparedStatement prepStmt=null;
	      ResultSet rs=null;
	      int addNum=0;
	      SimpleDateFormat format=new  SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	      String nowtime=format.format(course.getAddTime());
	      try{
	    	  con=DbConnect.getDBconnection();
	    	  prepStmt =con.prepareStatement(sql);	    	  
	    	  prepStmt.setString(1,course.getOpenID());
	    	  prepStmt.setInt(2, course.getCourseID());
	    	  prepStmt.setString(3,nowtime);
	          addNum=prepStmt.executeUpdate();
	          System.out.println(addNum);
	      } catch(Exception e){
	    	  System.out.println("�׳��쳣3");
	       e.printStackTrace();
	      } finally{
	    	  System.out.println("�ر�ѧ����ӿγ�����");
	    	  DbConnect.closeDB(con, prepStmt, rs);
	      }
		return addNum;
	}

	@Override
	public boolean addCourseCheck(AddCourse course) throws Exception {//ѧ����ӿγ�ʱ���ж��Ƿ��Ѿ���ӹ���
		String sql="select * from addcourse where openID=? and courseID=?";
		Connection con=null;
	      PreparedStatement prepStmt=null;
	      ResultSet rs=null;
	      boolean res=false;
	      try{
	    	  con=DbConnect.getDBconnection();
	    	  prepStmt =con.prepareStatement(sql);	    	  
	    	  prepStmt.setString(1, course.getOpenID());
	    	  prepStmt.setInt(2, course.getCourseID());
	          rs=prepStmt.executeQuery();
	          if(rs.next()) {
	        	  res=true;
	          }
	      } catch(Exception e){
	       e.printStackTrace();
	      } finally{
	    	  System.out.println("�رղ�ѯ�Ƿ�γ��Ѿ����");
	    	  DbConnect.closeDB(con, prepStmt, rs);
	      }
		return res;
	}
    
	@Override
	public int saveSign(Sign sign) throws Exception { //�洢ѧ����ǩ��
		String sql="insert into sign (checkID,courseID,openID,signTime) values (?,?,?,?)";
		  Connection con=null;
	      PreparedStatement prepStmt=null;
	      ResultSet rs=null;
	      int addNum=0;
	      SimpleDateFormat format=new  SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	      String nowtime=format.format(sign.getSignTime());
	      try{
	    	  con=DbConnect.getDBconnection();
	    	  prepStmt =con.prepareStatement(sql);	    	  
	    	  prepStmt.setInt(1,sign.getCheckID());
	    	  prepStmt.setInt(2, sign.getCourseID());
	    	  prepStmt.setString(3, sign.getOpenID());
	    	  prepStmt.setString(4, nowtime);
	          addNum=prepStmt.executeUpdate();
	      } catch(Exception e){
	       e.printStackTrace();
	      } finally{
	    	  System.out.println("�رմ洢ѧ��ǩ������");
	    	  DbConnect.closeDB(con, prepStmt, rs);
	      }
		return addNum;
	}

	@Override
	public String checkSign(Sign sign,String key,double wei,double jing) throws Exception {
		String sql="select checkID,courseID,checkKey,state,checkTime,cLatitude,cLongitude from mycheck where courseID=? order by checkTime desc limit 1";
		Connection con=null;
	      PreparedStatement prepStmt=null;
	      ResultSet rs=null;
	      String reString="no";
	      try{
	    	  con=DbConnect.getDBconnection();
	    	  prepStmt =con.prepareStatement(sql);	    	  
	    	  prepStmt.setInt(1,sign.getCourseID());
	          rs=prepStmt.executeQuery();
	          if(rs.next()) {
	        	 if(rs.getBoolean(4)) {
	        		 if(key.equals(rs.getString(3))) {
	        			 sign.setCheckID(rs.getInt(1));
	        			 if(  ifSign(sign.getCheckID(), sign.getOpenID()) ){
	        				 reString="done";
	        			 }else {
	        				 System.out.println("���ݿ⾭γ��:"+Double.parseDouble(rs.getString(7))+"    "+Double.parseDouble(rs.getString(6)));
	        				 if ( ifNear(jing, wei,Double.parseDouble(rs.getString(7)),Double.parseDouble(rs.getString(6))) ) {
	        					 saveSign(sign);
			        			 reString="sus";
							}
	        				 else {
								reString="dis";
							}
	        				 
	        			 }
	        		 }
	        		 else {
						reString="false";
					}
	        	 }
	        	 else {
	        		 reString="over";
	        	 }
	          }
	      } catch(Exception e){
	    	  System.out.println("���ݿ��׳��쳣");
	       e.printStackTrace();
	      } finally{
	    	  System.out.println("�رռ��ѧ��ǩ������");
	    	  DbConnect.closeDB(con, prepStmt, rs);
	      }
		return reString;
	}

	@Override
	public List<SignList> showList(int courseid) throws Exception {
		String sql="select checkID,courseID,checkKey,state,checkTime from mycheck where courseID=? order by checkID desc limit 1";
		String sql2="select studentName,studentNum,signTime from sign a,student b where a.openID=b.openID and a.checkID=?";
		Connection con=null;
	      PreparedStatement prepStmt=null;
	      ResultSet rs=null;
	      List<SignList> array=new ArrayList<SignList>();
	      int keyid=0;
	      SimpleDateFormat format=new  SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	      try{
	    	  con=DbConnect.getDBconnection();
	    	  prepStmt =con.prepareStatement(sql);	    	  
	    	  prepStmt.setInt(1,courseid);
	          rs=prepStmt.executeQuery();
	          while(rs.next()) {
	        	  keyid=rs.getInt(1);
	          }
	      } catch(Exception e){
	       e.printStackTrace();
	      } finally{
	    	  System.out.println("�رտ���ҳ���ȡѧ�����ӣ���");
	    	  DbConnect.closeDB(con, prepStmt, rs);
	      }
	      try{
	    	  con=DbConnect.getDBconnection();
	    	  prepStmt =con.prepareStatement(sql2);	    	  
	    	  prepStmt.setInt(1,keyid);
	          rs=prepStmt.executeQuery();
	          while(rs.next()) {
	        	 SignList list=new SignList();
	        	 list.setStudentName(rs.getString(1));
	        	 list.setStudentNum(rs.getString(2));
	        	 String nowtime=format.format(rs.getTimestamp(3));
	        	 list.setSignTime(nowtime);
	        	 array.add(list);
	          }
	      } catch(Exception e){
	       e.printStackTrace();
	      } finally{
	    	  System.out.println("�رտ���ҳ���ȡѧ������2");
	    	  DbConnect.closeDB(con, prepStmt, rs);
	      }
		return array;
	}

	@Override
	public boolean ifSign(int checkid, String userid) throws Exception {
		String sql="select * from sign where checkID=? and openID=?";
		Connection con=null;
	      PreparedStatement prepStmt=null;
	      ResultSet rs=null;
	      boolean res=false;
	      try{
	    	  con=DbConnect.getDBconnection();
	    	  prepStmt =con.prepareStatement(sql);	    	  
	    	  prepStmt.setInt(1, checkid);
	    	  prepStmt.setString(2, userid);
	          rs=prepStmt.executeQuery();
	          if(rs.next()) {
	        	  res=true;
	          }
	      } catch(Exception e){
	       e.printStackTrace();
	      } finally{
	    	  System.out.println("�رղ�ѯ�Ƿ��Ѿ�ǩ��");
	    	  DbConnect.closeDB(con, prepStmt, rs);
	      }
		return res;
	}
	
	/**
	 * �����������������(��γ��)����      *       
	 ** @param long1	** ��һ�㾭��     
	 ** @param lat1 	** ��һ��γ��      
	 ** @param long2    ** �ڶ��㾭��      
	 ** @param lat2     ** �ڶ���γ��     
	 ** @return ���ؾ��� ��λ����
	 */
	@Override
	public boolean ifNear(double long1, double lat1, double long2, double lat2) throws Exception {
		double a, b, R;
	    boolean dis=true;
	    R = 6378137; // ����뾶         
	    lat1 = lat1 * Math.PI / 180.0;
	    lat2 = lat2 * Math.PI / 180.0;
	    a = lat1 - lat2;
	    b = (long1 - long2) * Math.PI / 180.0;
	    double sa2, sb2;
	    sa2 = Math.sin(a / 2.0);
	    sb2 = Math.sin(b / 2.0);
	    double d = 2 * R * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1) * Math.cos(lat2) * sb2 * sb2));
	    long res=Math.round(d);
	    if(res>160) {
	    	dis=false;
	    }
	    return dis;
	}

	@Override
	public int suggestBack(FeedBack feedBack) throws Exception {
		String sql="insert into feedback (openID,suggestCon,backTime) values (?,?,?)";
		 Connection con=null;
	      PreparedStatement prepStmt=null;
	      ResultSet rs=null;
	      int addNum=0;
	      SimpleDateFormat format=new  SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	      String nowtime=format.format(feedBack.getBackTime());
	      try{
	    	  con=DbConnect.getDBconnection();
	    	  prepStmt =con.prepareStatement(sql);	    	  
	    	  prepStmt.setString(1,feedBack.getOpenID());
	    	  prepStmt.setString(2,feedBack.getSuggestCon());
	    	  prepStmt.setString(3,nowtime);
	          addNum=prepStmt.executeUpdate();
	      } catch(Exception e){
	    	  System.out.println("�׳��쳣");
	       e.printStackTrace();
	      } finally{
	    	  System.out.println("�ر�����2");
	    	  DbConnect.closeDB(con, prepStmt, rs);
	      }
		return addNum;
	}

	@Override
	public List<String> myInfo(String userid) throws Exception {
		String sql="select studentName,studentNum from student where openID=?";
		Connection con=null;
	      PreparedStatement prepStmt=null;
	      ResultSet rs=null;
	      List<String> res=new ArrayList<>();
	      try{
	    	  con=DbConnect.getDBconnection();
	    	  prepStmt =con.prepareStatement(sql);	    	  
	    	  prepStmt.setString(1, userid);
	          rs=prepStmt.executeQuery();
	          while (rs.next()) {
				res.add(rs.getString(1));
				res.add(rs.getString(2));
			}
	      } catch(Exception e){
	       e.printStackTrace();
	      } finally{
	    	  System.out.println("�رղ�ѯ�ҵ���Ϣ");
	    	  DbConnect.closeDB(con, prepStmt, rs);
	      }
		return res;
	}

}
