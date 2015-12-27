package rest.model;

public class TypeCoin {

	String name;
	double exchangeDolar;

	public TypeCoin() {
		// Must have no-argument constructor
	}

	public TypeCoin(String name, double exchangeDolar){
		this.name = name;
		this.exchangeDolar = exchangeDolar;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public double getExchangeDolar() {
		return exchangeDolar;
	}

	public void setExchangeDolar(double exchangeDolar) {
		this.exchangeDolar = exchangeDolar;
	}
}
