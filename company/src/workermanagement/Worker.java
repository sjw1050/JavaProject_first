package workermanagement;

import java.io.Serializable;

public class Worker implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name; // 직원 이름
	private String workerRank; // 직원의 직급
	private String workerId; // 직원의 사원번호
	private int basicSalary; // 직원의 기본급
	private int bonus; // 직원 수당
	private double taxRate; // 직원의 세액 공제율
	private int monthSalary; // 해당 직원의 총 급여

	public Worker(String name, String workerRank, String workerId, int basicSalary, int bonus) {
		super();
		this.name = name;
		this.workerRank = workerRank;
		this.workerId = workerId;
		this.basicSalary = basicSalary;
		this.bonus = bonus;
	}

	public int getMonthSalary() {
        monthSalary = (int)((basicSalary + bonus)*(1 - taxRate)); //월급 구하는 공식
        return monthSalary;
    }
    
    public double getTaxRate() { // 기본급에 따른 세율 
        if (basicSalary <= 2000000) {
            taxRate = 0.08;
            return taxRate;
        } else if (basicSalary > 2000000 && basicSalary <= 4000000) {
            taxRate = 0.12;
            return taxRate;
        } else {
            taxRate = 0.33;
            return taxRate;
        }
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWorkerRank() {
		return workerRank;
	}

	public void setWorkerRank(String workerRank) {
		this.workerRank = workerRank;
	}

	public String getworkerId() {
		return workerId;
	}

	public void setworkerId(String workerId) {
		this.workerId = workerId;
	}

	public int getBasicSalary() {
		return basicSalary;
	}

	public void setBasicSalary(int basicSalary) {
		this.basicSalary = basicSalary;
	}

	public int getBonus() {
		return bonus;
	}

	public void setBonus(int bonus) {
		this.bonus = bonus;
	}

	@Override
	public String toString() {
		return "[직원이름=" + name + "|직원 직급=" + workerRank + "|직원 사번=" + workerId + "|기본급="
				+ basicSalary + "|보너스수당=" + bonus + "|세율=" + taxRate + "|월급=" + monthSalary + "]";
	}

}
