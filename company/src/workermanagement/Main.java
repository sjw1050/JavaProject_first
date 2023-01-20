package workermanagement;

import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		// 싱글톤 생성 
		CompanyService cs = CompanyService.getInstance();
		Scanner sc = new Scanner(System.in);		
		boolean flag = true;
		boolean flag2 = true;
		boolean admin = true;
		Worker w = null;
		Admin ad = null;
		String path = "worker.dat";
		String adminpath = "admin.dat";
		String id = null;
		String pw = null;
		cs.adminLoad();
		cs.load();
		List<Worker> list = null;
		
		first: 
			while(admin) {
			try {			
			System.out.println("|================================================|");
			System.out.println("|================관리자 로그인 모드입니다 ==============|");
			System.out.println("|================================================|");
			System.out.println("|1.관리자가입|2.관리자 정보수정|3.관리자로그인|4.프로그램종료 |");	
			System.out.println("|================================================|");
			int adminsel = Integer.parseInt(sc.nextLine().trim());
			switch (adminsel) {
			case 1:
				System.out.println("관리자 id를 입력하세요");
				id = sc.nextLine().trim();
				System.out.println("관리자 pw를 입력하세요");
				pw = sc.nextLine().trim();
				ad = new Admin(id, pw);
				int adresult = cs.addAdmin(ad);
				if(adresult == 1) {
					System.out.println("가입이 완료됐습니다 로그인해주세요~");
				}else if(adresult == 2){
					System.out.println("id또는 pw에는 공백이 들어갈 수 없습니다.");
				}else {
					System.out.println("중복된 id가 있어 가입이 불가합니다 다른 id로 가입하세요");
				}
				break;
			case 2:
				System.out.println("관리자 id를 입력하세요");
				id = sc.nextLine().trim();
				System.out.println("관리자 pw를 입력하세요");
				pw = sc.nextLine().trim();
				Admin adchk = cs.searchAdmin(id, pw);
				if(adchk == null) {
					System.out.println("관리자 정보를 확인해주세요");
				}else {
					System.out.println("어떤 정보를 수정하시겠습니까? ※id,pw만 가능합니다");
					String modiadmin = sc.nextLine().trim();
					if(modiadmin.equals("id")) {
						System.out.println("변경할 id를 입력해주세요");
						id = sc.nextLine().trim(); 
						int modiad = cs.modiAdminId(adchk, id);
						System.out.println(modiad);
						if(modiad== 1) {
							System.out.println("id: " + adchk.getId() + "변경완료되었습니다.");
						}else if(modiad == 2){
							System.out.println("공백은 들어갈 수 없습니다.");
						}else {
							System.out.println("이미 있는 ID입니다 다른 ID를 입력하세요");
							continue;
						}
					}else if(modiadmin.equals("pw")) {
						System.out.println("변경할 pw를 입력해주세요");
						pw = sc.nextLine().trim();
						int modipw = cs.modiAdminPw(adchk, pw);
						if(modipw == 0) {
							System.out.println("pw를 공백으로만 사용할 수는 없습니다.");
						}else {
						System.out.println("pw: " + adchk.getPw() + "변경완료되었습니다.");
						}
					}else {
						System.out.println("id또는 pw가 아닐 시에는 입력이 불가합니다.");
					}
				}
				break;
			case 3:
				System.out.println("관리자 id를 입력하세요 >>");
		         id = sc.nextLine().trim();
		         System.out.println("관리자 pw를 입력하세요 >>");
		         pw = sc.nextLine().trim();
		         boolean adminresult = cs.check(id,pw);
		         if(adminresult) {
			            System.out.println("로그인 성공");			            
			            admin = false;				            
			         }else {
			              System.out.println("관리자 로그인 실패 id,pw를 정확히 확인하세요.");
			              continue;
			           }		         		
		 		while(flag) {
		 			System.out.println("============================================================================");
		 			System.out.println("1.직원 추가 | 2.직원 삭제 | 3.직원수정 | 4.직원 검색 | 5.직원 전체 조회 | 6.끝내기 | 7.로그아웃  ");
		 			System.out.println("============================================================================");
		 			try {
		 			System.out.println("위 메뉴중 실행할 번호 입력하세요 >>");
		 			int sel = Integer.parseInt(sc.nextLine().trim());
		 			if(sel <= 0 || sel > 8 ) {
		 				System.out.println("제대로된 번호 입력하세요");
		 			}
		 			switch (sel) {
		 			case 1:
		 				System.out.println("직원의 성함을 입력하세요");
		 				String name = sc.nextLine().trim();
		 				System.out.println("직원의 직급을 입력하세요");
		 				String workerRank = sc.nextLine().trim();
		 				System.out.println("직원의 사원번호 입력하세요");
		 				String noos = sc.nextLine().trim();
		 				System.out.println("직원의 급여를 입력하세요");
		 				int basicSalary = Integer.parseInt(sc.nextLine().trim());
		 				System.out.println("직원의 수당을 입력하세요");
		 				int bonus = Integer.parseInt(sc.nextLine().trim());
		 				w = new Worker(name, workerRank, noos, basicSalary, bonus);
		 				int result = cs.addWorker(w);
		 				if(result == 0) {
		 					System.out.println("직원추가 실패 관리자 문의 바랍니다.");
		 				}else if(result ==2) {
		 				System.out.println(w.getworkerId() + "는 이미 있는 사번입니다.");
		 				}else {
		 					System.out.println(w.getName() + "직원추가 성공");
		 				}
		 				break;
		 			case 2:
		 				System.out.println("삭제할 직원의 사번을 입력하세요");
		 				String workerId = sc.nextLine().trim();
		 				w = cs.searchWorker(workerId);
		 				if(w == null) {
		 					System.out.println("삭제실패 사번을 다시 확인해주세요");
		 				}
		 				result = cs.removeWorker(w);
		 				if(result == 1) {
		 				System.out.println("삭제완료");
		 				cs.save(path, w);
		 				}else {
		 					System.out.println("삭제에 실패했습니다 관리자에게 문의하세요");
		 				}
		 				break;
		 			case 3:
		 				System.out.println("수정할 직원의 사번을 입력하세요");
		 				workerId = sc.nextLine().trim();
		 				w = cs.searchWorker(workerId);
		 				if(w ==null) {
		 					System.out.println("사번을 다시 확인해주세요");
		 				}else {
		 				while(flag2) {				
		 				System.out.println("=================================================================");
		 				System.out.println("1.직원 이름 | 2.직원직급  | 3.기본급 | 4.수당 | 5.이전으로 돌아가기 ");
		 				System.out.println("=================================================================");
		 				int sel2 = Integer.parseInt(sc.nextLine().trim());
		 				switch (sel2) {
		 				case 1:
		 					System.out.println("변경할 이름을 입력하세요");
		 					name = sc.nextLine().trim();
		 					int modiname = cs.modiWorkerName(w,name);
		 					if(modiname == 0) {
		 						System.out.println("공백은 사용할 수 없습니다.");
		 					}else {
		 					System.out.println("이름:" + w.getName()+ "으로 변경 완료되었습니다.");
		 					}
		 					break;
		 				case 2:
		 					System.out.println("변경할 직급을 입력하세요");
		 					workerRank = sc.nextLine().trim();
		 					int modirank = cs.modiWorkerRank(w, workerRank);
		 					if(modirank == 0) {
		 						System.out.println("공백은 사용할 수 없습니다.");
		 					}else {
		 					System.out.println("직급:" + w.getWorkerRank()+ "으로 변경 완료되었습니다.");
		 					}
		 					break;
		 				case 3:
		 					System.out.println("변경할 급여를 입력하세요");
		 					basicSalary = Integer.parseInt(sc.nextLine().trim());
		 					cs.modiBasicSalary(w, basicSalary);
		 					System.out.println("기본급:" + w.getBasicSalary()+ "으로 변경 완료되었습니다.");
		 					break;
		 				case 4: 
		 					System.out.println("변경할 수당을 입력하세요");
		 					bonus = Integer.parseInt(sc.nextLine().trim());
		 					cs.modiBonus(w, bonus);
		 					System.out.println("추가수당:" + w.getBonus()+ "으로 변경 완료되었습니다.");
		 					break;
		 				case 5:					
		 					w.getTaxRate();
		 					w.getMonthSalary();
		 					flag2 = false;
		 					break;					
		 				}				
		 				}
		 				flag2 = true;
		 				}
		 				break;
		 			case 4:
		 				System.out.println("검색할 직원의 사번을 입력해주세요");
		 				workerId = sc.nextLine().trim();
		 				w = cs.searchWorker(workerId);
		 				if(w ==null) {
		 					System.out.println("해당하는 사번의 직원이 없어요~!");
		 				}else {
		 				System.out.println(w);
		 				}
		 				break;
		 			case 5:
		 				list = cs.loadWorker();
		 				for(Worker work : list) {
		 					System.out.println(work);
		 				}
		 				break;
		 			case 6:
		 				cs.save(path, w);
		 				flag = false;
		 				break;
		 			case 7:
		 				admin = true;
		 				cs.save(path, w);
		 				continue first;
		 			default:
		 				break;
		 			}
		 			}catch (NumberFormatException e) {
		 				System.out.println("※올바른 값을 입력하시오※ 처음으로 돌아갑니다.");
		 			}catch (NullPointerException e) {
		 				System.out.println("※올바른 값을 입력하시오※ 처음으로 돌아갑니다.");
		 			}
		 		}
		         break;
			case 4:
				cs.adminSave(adminpath, ad);
				admin = false;
				break;
			}	         
			}catch (NumberFormatException e) {
				System.out.println("※올바른 값을 입력하시오※");
			}			
	      }
		sc.close();
		System.out.println("시스템을 종료합니다~");
		
	}

}
