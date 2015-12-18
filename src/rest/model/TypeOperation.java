package rest.model;

public class TypeOperation {
	int type_operation_id;
	String name;

	TypeOperation(int typeOperation, String name){
		this.type_operation_id = typeOperation;
		this.name = name;
	}

	public int getType_operation_id() {
		return type_operation_id;
	}

	public void setType_operation_id(int type_operation_id) {
		this.type_operation_id = type_operation_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
