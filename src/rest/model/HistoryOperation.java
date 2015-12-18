package rest.model;

import java.util.Date;

public class HistoryOperation {

	String userLogin;
	String walletCode;
	String name;
	double value;
	Date date;

	public HistoryOperation(String userLogin, String walletCode, String name, double value, Date date) {
		this.userLogin = userLogin;
		this.walletCode = walletCode;
		this.name = name;
		this.value = value;
		this.date = date;
	}

	public String getTypeOperationName() {
		return name;
	}

	public void setTypeOperationName(String name) {
		this.name = name;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	public String getWalletCode() {
		return walletCode;
	}

	public void setWalletCode(String walletCode) {
		this.walletCode = walletCode;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
