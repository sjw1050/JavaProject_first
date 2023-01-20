package workermanagement;

import java.util.List;

public interface Company {
	// 직원 추가하는 추상 메소드
	int addWorker(Worker cb);
	// 직원 검색하는 추상 메소드
	Worker searchWorker(String workerId);
	// 직원 삭제하는 추상 메소드
	int removeWorker(Worker w);
	// 직원 전체 리스트 확인
	List<Worker> loadWorker();

}
