package pizzaria;

import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.TableColumnModel;
import javax.swing.text.InternationalFormatter;

import com.jgoodies.forms.factories.DefaultComponentFactory;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import java.awt.Insets;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JFormattedTextField.AbstractFormatterFactory;


public class Pizzaria extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTable table;
	
	// Formato para valores
	setFormatterFactory setFormat = new setFormatterFactory();
	
	/**
	 * Salva o ArrayList de Produto em um arquivo txt.
	 * @param produtos  o ArrayList de Produto.
	 */
	public void salvarProdutos(ArrayList<Produto> produtos) {
		try {
			ObjectOutputStream file = new ObjectOutputStream(new FileOutputStream("c:\\\\temp\\\\produtos.txt"));
			file.writeObject(produtos);
			file.close();
			System.out.println("Produto salvo com sucesso!");
		} catch (Exception e) {
			System.out.println(e);
		}
	};
	
	/**
	 * Verifica se o arquivo existe e o itera com o novo produto.
	 * @param fileName o nome do arquivo a ser verificado.
	 * @param produto o Produto.
	 * @return a nova lista de Produto.
	 */
	public ArrayList<Produto> verificarArquivoProdutos(String fileName, Produto produto) {
		ArrayList<Produto> list = new ArrayList<Produto>();
		try {
			File file = new File("c:\\\\temp\\\\" + fileName);
			if(file.exists()) {
				ObjectInputStream fileObject = new ObjectInputStream(new FileInputStream("c:\\\\temp\\\\" + fileName));
				ArrayList<Produto> fileList = ((ArrayList<Produto>) fileObject.readObject());
				list.addAll(fileList);
				list.add(produto);
				
				fileObject.close();
			} else {
				list.add(produto);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		System.out.println(list);
		return list;
	}
	
	/**
	 * Lista os produtos e os valores de atributos e itera um String Array 2D.
	 * @return o String Array 2D.
	 */
	public String[][] listarProdutos() {
		String[][] list = new String[100][5];
		
		try {
			File file = new File("c:\\\\temp\\\\produtos.txt");
			if(file.exists()) {
				ObjectInputStream fileObject = new ObjectInputStream(new FileInputStream("c:\\\\temp\\\\produtos.txt"));
				ArrayList<Produto> fileList = ((ArrayList<Produto>) fileObject.readObject());
				
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
		getContentPane().setLayout(new GridLayout(0, 2, 0, 0));
		
		
		JPanel descontoLabel = new JPanel();
		getContentPane().add(descontoLabel);
		GridBagLayout gbl_descontoLabel = new GridBagLayout();
		gbl_descontoLabel.columnWidths = new int[]{204, 69, 60, 184, 0};
		gbl_descontoLabel.rowHeights = new int[]{250, 20, 22, 20, 20, 35, 23, 0};
		gbl_descontoLabel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_descontoLabel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		descontoLabel.setLayout(gbl_descontoLabel);
		
		JLabel tipoLabel = DefaultComponentFactory.getInstance().createLabel("Tipo");
		GridBagConstraints gbc_tipoLabel = new GridBagConstraints();
		gbc_tipoLabel.insets = new Insets(0, 0, 5, 5);
		gbc_tipoLabel.gridx = 1;
		gbc_tipoLabel.gridy = 1;
		descontoLabel.add(tipoLabel, gbc_tipoLabel);
		
		JComboBox<String> tipo = new JComboBox<String>(new String[]{"Pizza", "Sanduíche"});
		GridBagConstraints gbc_tipo = new GridBagConstraints();
		gbc_tipo.fill = GridBagConstraints.HORIZONTAL;
		gbc_tipo.insets = new Insets(0, 0, 5, 0);
		gbc_tipo.gridx = 3;
		gbc_tipo.gridy = 1;
		descontoLabel.add(tipo, gbc_tipo);
		
		
		JLabel nomeLabel = DefaultComponentFactory.getInstance().createLabel("Nome");
		GridBagConstraints gbc_nomeLabel = new GridBagConstraints();
		gbc_nomeLabel.insets = new Insets(0, 0, 5, 5);
		gbc_nomeLabel.gridx = 1;
		gbc_nomeLabel.gridy = 2;
		descontoLabel.add(nomeLabel, gbc_nomeLabel);
				
		TextField nome = new TextField();
		GridBagConstraints gbc_nome = new GridBagConstraints();
		gbc_nome.fill = GridBagConstraints.HORIZONTAL;
		gbc_nome.insets = new Insets(0, 0, 5, 0);
		gbc_nome.gridx = 3;
		gbc_nome.gridy = 2;
		descontoLabel.add(nome, gbc_nome);

		
		JLabel valorLabel = DefaultComponentFactory.getInstance().createLabel("Valor (R$)");
		GridBagConstraints gbc_valorLabel = new GridBagConstraints();
		gbc_valorLabel.insets = new Insets(0, 0, 5, 5);
		gbc_valorLabel.gridx = 1;
		gbc_valorLabel.gridy = 3;
		descontoLabel.add(valorLabel, gbc_valorLabel);
		
		JFormattedTextField valor = new JFormattedTextField(Double.valueOf(0));
		GridBagConstraints gbc_valor = new GridBagConstraints();
		gbc_valor.fill = GridBagConstraints.HORIZONTAL;
		gbc_valor.insets = new Insets(0, 0, 5, 0);
		gbc_valor.gridx = 3;
		gbc_valor.gridy = 3;
		descontoLabel.add(valor, gbc_valor);
		
		valor.setFormatterFactory(setFormat);
		
				
		JLabel lblNewJgoodiesLabel_3 = DefaultComponentFactory.getInstance().createLabel("Desconto (R$)");
		GridBagConstraints gbc_lblNewJgoodiesLabel_3 = new GridBagConstraints();
		gbc_lblNewJgoodiesLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewJgoodiesLabel_3.gridx = 1;
		gbc_lblNewJgoodiesLabel_3.gridy = 4;
		descontoLabel.add(lblNewJgoodiesLabel_3, gbc_lblNewJgoodiesLabel_3);
		
		JFormattedTextField desconto = new JFormattedTextField(Double.valueOf(0));
		GridBagConstraints gbc_desconto = new GridBagConstraints();
		gbc_desconto.fill = GridBagConstraints.HORIZONTAL;
		gbc_desconto.insets = new Insets(0, 0, 5, 0);
		gbc_desconto.gridx = 3;
		gbc_desconto.gridy = 4;
		descontoLabel.add(desconto, gbc_desconto);
		
		desconto.setFormatterFactory(setFormat);
		
				
		JButton botaoSalvar = new JButton("Salvar");
		GridBagConstraints gbc_botaoSalvar = new GridBagConstraints();
		gbc_botaoSalvar.gridx = 3;
		gbc_botaoSalvar.gridy = 6;
		descontoLabel.add(botaoSalvar, gbc_botaoSalvar);
		
		botaoSalvar.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
				String regex = "[.,]";
		    	String valorString = valor.getText().split(",")[0].replaceAll(regex, "");
		    	String descontoString = desconto.getText().split(",")[0].replaceAll(regex, "");
		    	
		    	String tipoProduto = tipo.getSelectedItem().toString();
		    	String nomeProduto = nome.getText();
		    	int valorProduto = (int) Double.parseDouble(valorString);
		    	int descontoProduto = (int) Double.parseDouble(descontoString);
		    	
		    	switch(tipoProduto) {
		    		case "Pizza":
		    			Produto produto = new Pizza(tipoProduto, nomeProduto, valorProduto, descontoProduto, null);
		    			salvarProdutos(verificarArquivoProdutos("produtos.txt", produto));
		    			break;
		    	}
		    }
		});
		
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane);
		
		String[][] data = listarProdutos();
		String[] columnNames = { "Tipo", "Nome", "Valor", "Desconto" };
		table = new JTable(data, columnNames);
		CustomTableCellRenderer customTable = new CustomTableCellRenderer();
		table.getColumnModel().getColumn(2).setCellRenderer(customTable);
		table.getColumnModel().getColumn(3).setCellRenderer(customTable);
		table.setFillsViewportHeight(true);
		scrollPane.setViewportView(table);

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
