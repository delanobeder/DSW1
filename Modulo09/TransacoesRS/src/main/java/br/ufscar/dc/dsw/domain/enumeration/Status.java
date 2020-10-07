package br.ufscar.dc.dsw.domain.enumeration;

public enum Status {
	
	CONFIRMADA("CONFIRMADA"), CANCELADA("CANCELADA");
	
	private String value;
	
	private Status(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
	public static Status parse(String value) {
		if (value.equals(CONFIRMADA.getValue())) {
			return CONFIRMADA;
		} else {
			return CANCELADA;
		}
	}

}