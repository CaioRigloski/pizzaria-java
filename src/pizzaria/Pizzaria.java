package pizzaria;

import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import com.jgoodies.forms.factories.DefaultComponentFactory;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import java.awt.Insets;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;



public class Pizzaria extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JTable table;
	
	// Formato para valores
	setFormatterFactory setFormat = new setFormatterFactory();
	
	/**
	 * Gera um id único com base no maior valor da coluna id da tabela de produtos.
	 * @return o id único.
	 */
	public final int gerarUniqueId() {
		int id = 0;
		ArrayList<Produto> produtos = verificarArquivoProdutos();
		
		for(Produto produto: produtos) {
			if(produto.getId() > id) {
				id = produto.getId();
			}
		}
		
	    return id + 1;
	}
	
	/**
	 * Adiciona o produto dinamicamente à tabela.
	 * @param produto o objeto da classe Produto.
	 */
	public void adicionarLinhaProduto(Produto produto) {
		String id = String.valueOf(produto.getId());
		String tipo = produto.getTipo();
		String nome = produto.getNome();
		String valor = String.valueOf(produto.getValor());
		String desconto = String.valueOf(produto.getDesconto());
		
		try {
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			model.addRow(new Object[] {id, tipo, nome, valor, desconto});			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * Remove o produto da tabela.
	 * @param row o índice da linha.
	 */
	public void removerLinhaProduto(int row) {
		try {
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			model.removeRow(row);			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * Salva o ArrayList de produtos no arquivo .txt.
	 * @param produtos o ArrayList de produtos.
	 */
	public void salvarListaProdutos(ArrayList<Produto> produtos) {
		try {
			ObjectOutputStream file = new ObjectOutputStream(new FileOutputStream("c:\\\\temp\\\\produtos.txt"));
			file.writeObject(produtos);
			file.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * Adiciona o produto no ArrayList retirado do arquivo produtos e salva esse array novamente.
	 * @param produto o Produto.
	 */
	public void salvarProduto(Produto produto) {
		ArrayList<Produto> list = verificarArquivoProdutos();
		try {
			list.add(produto);
			salvarListaProdutos(list);
			adicionarLinhaProduto(produto);
			
			System.out.println("Produto salvo com sucesso!");
		} catch (Exception e) {
			System.out.println(e);
		}
	};
	
	public void editarProduto(String columnName, int id, String newValue) {
		ArrayList<Produto> list = verificarArquivoProdutos();
		try {
			Iterator<Produto> i = list.iterator();
			while(i.hasNext()) {
				Produto produto = (Produto) i.next();
				if(produto.getId() == id) {
					switch(columnName) {
						case "Tipo": {
							produto.setTipo(newValue);
							break;
						}
						case "Nome": {
							produto.setNome(newValue);
							break;
						}
						case "Valor": {
							produto.setValor(Integer.valueOf(newValue));
							break;
						}
						case "Desconto": {
							produto.setDesconto(Integer.valueOf(newValue));
							break;
						}
					}
				}
			}
		
		salvarListaProdutos(list);
		System.out.println("Produto editado com sucesso!");
		} catch (Exception e){
			System.out.println(e);
		}
	}
	
	/**
	 * Remove o produto da lista retirada do arquivo de produtos com base no ID.
	 * @param row o índice da linha.
	 * @param id o id do produto.
	 */
	public void excluirProduto(int row, int id) {
		ArrayList<Produto> list = verificarArquivoProdutos();
		try {
			if(list.size() > 0) {
				Iterator<Produto> i = list.iterator();
				
				while(i.hasNext()) {
					Produto produto = (Produto) i.next();
					if(produto.getId() == id) {
						i.remove();
					}
				}
				
				removerLinhaProduto(row);
				salvarListaProdutos(list);
				System.out.println("Produto excluído com sucesso!");
			}
		} catch (Exception e){
			System.out.println(e);
		}
	}
	
	/**
	 * Verifica se o arquivo existe.
	 * @return a lista de Produto.
	 */
	public ArrayList<Produto> verificarArquivoProdutos() {
		ArrayList<Produto> list = new ArrayList<Produto>();
		try {
			File file = new File("c:\\\\temp\\\\produtos.txt");
			if(file.exists()) {
				ObjectInputStream fileObject = new ObjectInputStream(new FileInputStream("c:\\\\temp\\\\produtos.txt"));
				ArrayList<Produto> fileList = ((ArrayList<Produto>) fileObject.readObject());
				list.addAll(fileList);
				
				fileObject.close();
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return list;
	}
	
	/**
	 * Lista os produtos e os valores de atributos e itera um String Array 2D.
	 * @return o String Array 2D.
	 */
	public String[][] listarProdutos() {
		String[][] list = new String[0][6];
		
		try {
			File file = new File("c:\\\\temp\\\\produtos.txt");
			if(file.exists()) {
				ObjectInputStream fileObject = new ObjectInputStream(new FileInputStream("c:\\\\temp\\\\produtos.txt"));
				ArrayList<Produto> fileList = ((ArrayList<Produto>) fileObject.readObject());
				
				list = new String[fileList.size()][6];
				
				int produtoIndex = 0;
				for(Produto produto: fileList) {
					ArrayList<String> array = produto.getArray();
					int stringIndex = 0;
				
					for(String string: array) {
						list[produtoIndex][stringIndex] = string;	
						stringIndex++;
					}
					produtoIndex++;
				}
				fileObject.close();
			} else {
				return list;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return list;
	}
	
	/**
	 * Cria o Panel.
	 */
	public Pizzaria() {
		setSize(1280, 720);
		setResizable(true);
		
		this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                int i=JOptionPane.showConfirmDialog(null, "Deseja realmente sair?");
                if(i==0) {
                	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                	System.exit(0); // Encerra a aplicação.
                } else {
                	setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
            }
        });
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{421, 100, 489, 0};
		gridBagLayout.rowHeights = new int[]{681, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
				
		
		String[][] data = listarProdutos();
		String[] columnNames = { "id", "Tipo", "Nome", "Valor", "Desconto" };
		
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		model.setColumnIdentifiers(columnNames);
		
		CustomTableCellRenderer customTable = new CustomTableCellRenderer();
		
		JPanel principal = new JPanel();
		GridBagConstraints gbc_principal = new GridBagConstraints();
		gbc_principal.fill = GridBagConstraints.BOTH;
		gbc_principal.insets = new Insets(0, 0, 0, 5);
		gbc_principal.gridx = 0;
		gbc_principal.gridy = 0;
		getContentPane().add(principal, gbc_principal);
		GridBagLayout gbl_principal = new GridBagLayout();
		gbl_principal.columnWidths = new int[]{204, 69, 60, 184, 0, 0, 0, 0, 0, 0};
		gbl_principal.rowHeights = new int[]{250, 20, 22, 20, 20, 35, 23, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_principal.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_principal.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		principal.setLayout(gbl_principal);
		
		JLabel tipoLabel = DefaultComponentFactory.getInstance().createLabel("Tipo");
		GridBagConstraints gbc_tipoLabel = new GridBagConstraints();
		gbc_tipoLabel.insets = new Insets(0, 0, 5, 5);
		gbc_tipoLabel.gridx = 1;
		gbc_tipoLabel.gridy = 1;
		principal.add(tipoLabel, gbc_tipoLabel);
		
		JComboBox<String> tipo = new JComboBox<String>(new String[]{"Pizza", "Sanduíche"});
		GridBagConstraints gbc_tipo = new GridBagConstraints();
		gbc_tipo.fill = GridBagConstraints.HORIZONTAL;
		gbc_tipo.insets = new Insets(0, 0, 5, 5);
		gbc_tipo.gridx = 3;
		gbc_tipo.gridy = 1;
		principal.add(tipo, gbc_tipo);
		
		
		JLabel nomeLabel = DefaultComponentFactory.getInstance().createLabel("Nome");
		GridBagConstraints gbc_nomeLabel = new GridBagConstraints();
		gbc_nomeLabel.insets = new Insets(0, 0, 5, 5);
		gbc_nomeLabel.gridx = 1;
		gbc_nomeLabel.gridy = 2;
		principal.add(nomeLabel, gbc_nomeLabel);
		
		TextField nome = new TextField();
		GridBagConstraints gbc_nome = new GridBagConstraints();
		gbc_nome.fill = GridBagConstraints.HORIZONTAL;
		gbc_nome.insets = new Insets(0, 0, 5, 5);
		gbc_nome.gridx = 3;
		gbc_nome.gridy = 2;
		principal.add(nome, gbc_nome);
		
				
		JLabel valorLabel = DefaultComponentFactory.getInstance().createLabel("Valor (R$)");
		GridBagConstraints gbc_valorLabel = new GridBagConstraints();
		gbc_valorLabel.insets = new Insets(0, 0, 5, 5);
		gbc_valorLabel.gridx = 1;
		gbc_valorLabel.gridy = 3;
		principal.add(valorLabel, gbc_valorLabel);
		
		JFormattedTextField valor = new JFormattedTextField(Double.valueOf(0));
		GridBagConstraints gbc_valor = new GridBagConstraints();
		gbc_valor.fill = GridBagConstraints.HORIZONTAL;
		gbc_valor.insets = new Insets(0, 0, 5, 5);
		gbc_valor.gridx = 3;
		gbc_valor.gridy = 3;
		principal.add(valor, gbc_valor);
		
		valor.setFormatterFactory(setFormat);
		
				
		JLabel descontoLabel = DefaultComponentFactory.getInstance().createLabel("Desconto (R$)");
		GridBagConstraints gbc_descontoLabel = new GridBagConstraints();
		gbc_descontoLabel.insets = new Insets(0, 0, 5, 5);
		gbc_descontoLabel.gridx = 1;
		gbc_descontoLabel.gridy = 4;
		principal.add(descontoLabel, gbc_descontoLabel);
		
		JFormattedTextField desconto = new JFormattedTextField(Double.valueOf(0));
		GridBagConstraints gbc_desconto = new GridBagConstraints();
		gbc_desconto.fill = GridBagConstraints.HORIZONTAL;
		gbc_desconto.insets = new Insets(0, 0, 5, 5);
		gbc_desconto.gridx = 3;
		gbc_desconto.gridy = 4;
		principal.add(desconto, gbc_desconto);
		
		desconto.setFormatterFactory(setFormat);
		
				
		JButton botaoSalvar = new JButton("Salvar");
		GridBagConstraints gbc_botaoSalvar = new GridBagConstraints();
		gbc_botaoSalvar.insets = new Insets(0, 0, 5, 5);
		gbc_botaoSalvar.gridx = 3;
		gbc_botaoSalvar.gridy = 6;
		principal.add(botaoSalvar, gbc_botaoSalvar);
		
		botaoSalvar.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
				String regex = "[.,]";
		    	String valorString = valor.getText().split(",")[0].replaceAll(regex, "");
		    	String descontoString = desconto.getText().split(",")[0].replaceAll(regex, "");
		    	
		    	int id = gerarUniqueId();
		    	String tipoProduto = tipo.getSelectedItem().toString();
		    	String nomeProduto = nome.getText();
		    	int valorProduto = (int) Double.parseDouble(valorString);
		    	int descontoProduto = (int) Double.parseDouble(descontoString);

		    	switch(tipoProduto) {
		    		case "Pizza":
		    			Produto produto = new Pizza(id, tipoProduto, nomeProduto, valorProduto, descontoProduto, null);
		    			salvarProduto(produto);
		    			break;
		    	}
		    }
		});
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.insets = new Insets(0, 0, 0, 5);
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 0;
		getContentPane().add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{89, 0};
		gbl_panel.rowHeights = new int[]{23, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JButton botaoSalvarEdicoes = new JButton("Editar");
		botaoSalvarEdicoes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GridBagConstraints gbc_botaoSalvarEdicoes = new GridBagConstraints();
		gbc_botaoSalvarEdicoes.insets = new Insets(0, 0, 5, 0);
		gbc_botaoSalvarEdicoes.anchor = GridBagConstraints.NORTH;
		gbc_botaoSalvarEdicoes.gridx = 0;
		gbc_botaoSalvarEdicoes.gridy = 1;
		panel.add(botaoSalvarEdicoes, gbc_botaoSalvarEdicoes);
		
		JButton botaoExcluir = new JButton("Excluir");
		
		botaoExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int column = 0;
				int row = table.getSelectedRow();
				int id = Integer.valueOf(table.getModel().getValueAt(row, column).toString());
				
				excluirProduto(row, id);
			}
		});
		
		GridBagConstraints gbc_botaoExcluir = new GridBagConstraints();
		gbc_botaoExcluir.gridx = 0;
		gbc_botaoExcluir.gridy = 2;
		panel.add(botaoExcluir, gbc_botaoExcluir);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 2;
		gbc_scrollPane.gridy = 0;
		getContentPane().add(scrollPane, gbc_scrollPane);
		table = new JTable();
		table.setModel(model);
		table.getColumnModel().getColumn(3).setCellRenderer(customTable);
		table.getColumnModel().getColumn(4).setCellRenderer(customTable);
		
		table.setFillsViewportHeight(true);
		scrollPane.setViewportView(table);
		
		table.getModel().addTableModelListener(new TableModelListener(){
			@Override
			public void tableChanged(TableModelEvent tableModelEvent) {
				int rowCount = table.getModel().getRowCount();
				int firtRowIndex = tableModelEvent.getFirstRow();
				
				if(rowCount > 0 && rowCount < firtRowIndex) {
					int row = tableModelEvent.getFirstRow();
					int column = tableModelEvent.getColumn();
					int id =  Integer.valueOf(table.getModel().getValueAt(row, 0).toString());
					
					if(table.isEditing()) {
						String value = table.getValueAt(row, column).toString();
						editarProduto(table.getColumnName(column), id, value);
					}					
				}
			}
		});

	}
	
	public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
         
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Pizzaria().setVisible(true);
            }
        });
    }
}
