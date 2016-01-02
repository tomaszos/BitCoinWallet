package rest.model;

import java.util.Date;

public class Wallet {

	String code;
	int type_coin_id;
	String user_login;
	Date date;
	Double saldo;

	public Wallet() {
		// Must have no-argument constructor
	}

	public Wallet(String code, int typeCoinId, String userLogin, Date date){
		this.code = code;
		this.type_coin_id = typeCoinId;
		this.user_login = userLogin;
		this.date = date;
	}

	public Wallet(String code, int typeCoinId, String userLogin, Double saldo, Date date){
		this.code = code;
		this.type_coin_id = typeCoinId;
		this.user_login = userLogin;
		this.date = date;
		this.saldo = saldo;
	}

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getType_coin_id() {
		return type_coin_id;
	}
	public void setType_coin_id(int type_coin_id) {
		this.type_coin_id = type_coin_id;
	}
	public String getUserLogin() {
		return user_login;
	}
	public void setUserLogin(String userLogin) {
		this.user_login = userLogin;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

	public Double getSaldo() {
		return saldo;
	}
	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}
}