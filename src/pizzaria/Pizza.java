package pizzaria;


public class Pizza extends Produto {
	private static final long serialVersionUID = 1L;
	private String ingredientes;
	
	public String getIngredientes() {
		return ingredientes;
	}
	public String setIngredientes(String ingredientes) {
		return this.ingredientes = ingredientes;
	}
	
	public Pizza(int uniqueId, String tipo, String nome, int valor, int desconto, String ingredientes) {
		super(uniqueId, tipo, nome, valor, desconto);
		
		setIngredientes(ingredientes);
	}
}