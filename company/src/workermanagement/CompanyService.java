package workermanagement;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class CompanyService implements Company {

	List<Worker> list = new ArrayList<>();
	List<Admin> adminlist = new ArrayList<>();
	String path = "worker.dat";
	String adminpath = "admin.dat";
	boolean isLogin; // 기본값이 false
	String workerId;

	private static CompanyService instance;

	// 생성자
	private CompanyService() {
	}

	public static CompanyService getInstance() {
		if (instance == null) {
			instance = new CompanyService();
		}
		return instance;
	}

	@Override
	// 직원 추가
	public int addWorker(Worker cb) {
		int result = 0;
		Worker work = searchWorker(cb.getworkerId());
		if (cb.getName().trim().equals("") | cb.getworkerId().trim().equals("")
				| cb.getWorkerRank().trim().equals("")) {
			result = 0;
		} else {
			if (list.size() < 1) {
				cb.getTaxRate();
				cb.getMonthSalary();
				list.add(cb);
				save(path, cb);
				result = 1;
			} else if (work == null) {
				cb.getTaxRate();
				cb.getMonthSalary();
				list.add(cb);
				save(path, cb);
				result = 1;
			} else {
				if (cb.getworkerId().trim().equals(work.getworkerId())) {
					result = 2;
				}
			}
		}
		return result;
	}

	@Override
	// 직원 삭제
	public int removeWorker(Worker w) {
		int result = 0;
		Worker worker = searchWorker(w.getworkerId());
		for (int i = 0; i < list.size(); i++) {
			if (w.getworkerId().trim().equals(worker.getworkerId())) {
				list.remove(w);
				result = 1;
				break;
			}
		}
		return result;
	}

	// 아웃풋스트림으로 직원 리스트 파일로 저장
	public void save(String path, Worker cb) {
		path = "worker.dat";
		try {
			File file = new File(path);
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			for (Worker c : list) {
				oos.writeObject(c);
				oos.flush();
			}

			oos.close();

		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

	}

	public void adminSave(String adminpath, Admin ad) {
		adminpath = "admin.dat";
		try {
			File file = new File(adminpath);
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			for (Admin a : adminlist) {
				oos.writeObject(a);
				oos.flush();
			}

			oos.close();

		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public void adminLoad() {
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(adminpath));
			while (true) {
				try {
					Admin a = (Admin) ois.readObject();
					adminlist.add(a);
				} catch (EOFException e) {
					System.out.println("관리자 정보 로딩완료");
					System.out.println();
					break;
				}
			}
		} catch (IOException e) {
			System.out.println("////////////////////////////////////////");
			System.out.println("※파일이 없는 상태로 관리자 회원가입부터 시작해주세요※");
			System.out.println("///////////////////////////////////////");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// 인풋스트림으로 파일에 있는 직원 리스트 불러오기
	public void load() {
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(path));
			while (true) {
				try {
					Worker w = (Worker) ois.readObject();
					list.add(w);
				} catch (EOFException e) {
					System.out.println("직원정보 로딩완료");
					break;
				}
			}
		} catch (IOException e) {
			System.out.println("================================================");
			System.out.println("※현재 직원정보가 없습니다 로그인 후  직원 추가부터 시작해주세요※");
			System.out.println("================================================");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
//		return list;
	}

	public List<Worker> loadWorker() {
//		for (Worker c : list) {
//			System.out.println(c);
//		}
		return list;
	}

	public boolean check(String id, String pw) {
		for (Admin a : adminlist) {
			if (a.getId().trim().equals(id)) {
				if (a.getPw().trim().equals(pw)) {
					isLogin = true;
				}
			} else {
				isLogin = false;
			}
		}
		return isLogin;
	}

	@Override
	public Worker searchWorker(String workerId) {
		Worker worker = null;
		for (Worker w : list) {
			if (workerId.trim().equals(w.getworkerId())) {
				worker = w;
				break;
			}
		}
		return worker;
	}

	public Admin searchAdmin(String id, String pw) {
		Admin admin = null;
		for (Admin a : adminlist) {
			if (a.getId().trim().equals(id)) {
				if (a.getPw().equals(pw)) {
					admin = a;
				}
			}
		}
		return admin;

	}

	public int addAdmin(Admin ad) {
		int chadmin = 0;
		boolean chkad = check(ad.getId(), ad.getPw());
		if (chkad) {
			chadmin = 0;
		} else {
			if(ad.getId().trim().equals("") || ad.getPw().trim().equals("")) {
				chadmin = 2;
			}else{
			chadmin = 1;
			adminlist.add(ad);
			adminSave(adminpath, ad);
			}
		}

		return chadmin;
	}

	public int modiAdminId(Admin adchk, String id) {
		int modiadmin = 0;
		for (int i = 0; i < adminlist.size(); i++) {
			if(adminlist.get(i).getId().trim().equals(id)){
				modiadmin = 0;
			}else {
				if(id.trim().equals("")) {
					modiadmin = 2;
				}else {
				modiadmin = 1;
				adchk.setId(id);
				adminSave(adminpath, adchk);
				break;
				}
			}
		}
//		for(@SuppressWarnings("unused") Admin a : adminlist) {
//			if(adchk.getId().equals(id)){
//				modiadmin = 1;
//				
//			}else{
//				modiadmin = 0;
//				adchk.setId(id);
//				adminSave(adminpath, adchk);				
//			}
//		}
		return modiadmin;		
	}

	public int modiAdminPw(Admin adchk, String pw) {
		if(pw.trim().equals("")) {
			return 0;
		}else {
		adchk.setPw(pw);
		adminSave(adminpath, adchk);
		return 1;
		}
	}

	public int modiWorkerName(Worker w, String name) {
		if(name.trim().equals("")) {
			return 0;
		}else {
	      w.setName(name);
	      save(path, w);
	      return 1;
		}
	   }

	   public int modiWorkerRank(Worker w, String workerRank) {
		   if(workerRank.trim().equals("")) {
			   return 0;
		   }else {
	      w.setWorkerRank(workerRank);
	      save(path, w);
	      return 1;
		   }
	}

	public void modiBasicSalary(Worker w, int basicSalary) {
	   w.setBasicSalary(basicSalary);
	   save(path, w);
	}

	public void modiBonus(Worker w, int bonus) {
	   w.setBonus(bonus);
	   save(path, w);
	}

}
