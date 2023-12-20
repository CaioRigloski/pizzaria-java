package pizzaria;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Produto implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String tipo;
	private String nome;
	private int valor;
	private int desconto;

	
	/**
	 * Insere os atributos do Produto em um ArrayList.
	 * @return o String ArrayList.
	 */
	public ArrayList<String> getArray() {
		ArrayList<String> array = new ArrayList<String>();
		String val = String.valueOf(valor);
		String desc = String.valueOf(desconto);
		
		array.add(this.tipo);
		array.add(this.nome);
		array.add(val);
		array.add(desc);
		
		return array;
	}
	
	public Produto(String tipo, String nome, int valor, int desconto) {
		this.tipo = tipo;
		this.nome = nome;
		this.valor = valor;
		this.desconto = desconto;
	}
}