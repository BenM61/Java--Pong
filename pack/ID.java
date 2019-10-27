package pack;

import java.io.IOException;

public enum ID {
	P1("P1"),
	P2("P2"),
	ERROR(""); //should be used for verifying that every ID is P1 or P2
	
	private String name;
	ID(String name){
		this.name = name;
	}
	public String getName() {
		return name;
	}
}
