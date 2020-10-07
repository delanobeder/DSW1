package br.ufscar.dc.dsw.domain.enumeration;

public enum Categoria {
	
	COMPRA("COMPRA"), PAGAMENTO("PAGAMENTO");
	
	private String value;
	
	private Categoria(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
	public static Categoria parse(String value) {
		if (value.equals(COMPRA.getValue())) {
			return COMPRA;
		} else {
			return PAGAMENTO;
		}
	}

}