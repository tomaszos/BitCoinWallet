package rest.model;

public class TypeCoin {

	int type_coin_id;
	String name;

	TypeCoin(int type_coin_id, String name){
		this.type_coin_id = type_coin_id;
		this.name = name;
	}

	public int getType_coin_id() {
		return type_coin_id;
	}
	public void setType_coin_id(int type_coin_id) {
		this.type_coin_id = type_coin_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
