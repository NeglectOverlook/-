package com.ncu.dao;

import java.util.List;

import com.ncu.entity.AddCourse;
import com.ncu.entity.Course;
import com.ncu.entity.FeedBack;
import com.ncu.entity.Sign;
import com.ncu.entity.SignList;
import com.ncu.entity.Student;

public interface IStudentDAO {
	public abstract int create(Student stu) throws Exception;  //ע���û�
	public abstract boolean findCheck(String userid) throws Exception; //�ж��Ƿ��Ѿ�ע��
	public abstract List<Course> getAllCourse() throws Exception; //ȫ���γ�
	public abstract List<Course> getmyCourse(String userid) throws Exception;  //�ҵĿγ�
	public abstract int addCourse(AddCourse course) throws Exception;  //��ӿγ����ҵĿγ�
	public abstract boolean addCourseCheck(AddCourse course) throws Exception;  //���γ��Ƿ��Ѿ����
	public abstract String checkSign(Sign sign,String key,double wei,double jing) throws Exception;  //���ѧ����ǩ��
	public abstract int saveSign(Sign sign) throws Exception;  //����ѧ����ǩ��
	public abstract List<SignList> showList(int courseid) throws Exception;//չʾǩ���б�
	public abstract boolean ifSign(int checkid,String userid) throws Exception;//����ѧ���Ƿ��Ѿ�ǩ������
	public abstract boolean ifNear(double long1, double lat1, double long2, double lat2) throws Exception;//�ж��Ƿ��ڽ���
	public abstract int suggestBack(FeedBack feedBack) throws Exception;//�洢�������
	public abstract List<String> myInfo(String userid)throws Exception;//�����ҵ���Ϣ
}
