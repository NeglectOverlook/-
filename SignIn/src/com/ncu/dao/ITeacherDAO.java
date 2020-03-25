package com.ncu.dao;

import java.util.List;

import com.ncu.entity.CheckKey;
import com.ncu.entity.Course;
import com.ncu.entity.History;
import com.ncu.entity.SignList;
import com.ncu.entity.Teacher;

public interface ITeacherDAO {
	public abstract int create(Teacher tea) throws Exception;
	public abstract boolean findCheck(String userid) throws Exception;
	public abstract int AddCourse(Course course) throws Exception;
	public abstract List<Course> getmyCourse(String userid) throws Exception;
	public abstract int judgeSign(int id) throws Exception;//�ú������ڵ���γ̽��п���ʱ���жϸÿγ��Ƿ��ϴο��ڽ��������������ʼ�µĿ��ڣ����û�н��������ϴο��ڡ�
	public abstract int saveSign(CheckKey check) throws Exception;//������ʦǩ����ֻ����ʦ�����޸�״̬
	public abstract int stopSign(int courseid) throws Exception;
	public abstract int DeleteCourse(int courseid) throws Exception;
	public abstract List<History> HistoryRd(int courseid) throws Exception;
	public abstract List<SignList> showList(int checkid) throws Exception;//չʾǩ���б�

}
