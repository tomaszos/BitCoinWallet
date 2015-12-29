package rest.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement(name = "response")
@XmlSeeAlso({User.class, Wallet.class, HistoryOperation.class, TypeCoin.class})
public class Response {

	int status;
	String message;
	Object object;
	List<Wallet> wallets = null;
	List<HistoryOperation> historyOperations = null;


	// Must have no-argument constructor
	public Response() {

	}

	public Response(int status, String message){
		this.status = status;
		this.message = message;
	}

	public Response(int status, String message, Object object){
		this.status = status;
		this.message = message;
		this.object = object;
	}
	public Response(int status, String message, List<Wallet> wallets){
		this.status = status;
		this.message = message;
		this.wallets = wallets;
	}

	public Response(int status, List<HistoryOperation> historyOperations){
		this.status = status;
		this.historyOperations = historyOperations;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	@XmlElementWrapper(name="wallets")
	@XmlElement(name="wallet")
	public List<Wallet> getWallets() {
		return wallets;
	}

	public void setWallets(List<Wallet> wallets) {
		this.wallets = wallets;
	}

	@XmlElementWrapper(name="historyOperations")
	@XmlElement(name="operation")
	public List<HistoryOperation> getHistoryOperations() {
		return historyOperations;
	}

	public void setHistoryOperations(List<HistoryOperation> historyOperations) {
		this.historyOperations = historyOperations;
	}
}
