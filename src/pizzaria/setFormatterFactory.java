package pizzaria;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.JFormattedTextField;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JFormattedTextField.AbstractFormatterFactory;
import javax.swing.text.InternationalFormatter;

public class setFormatterFactory extends AbstractFormatterFactory {
	NumberFormat format = DecimalFormat.getInstance();
	
	@Override
    public AbstractFormatter getFormatter(JFormattedTextField tf) {
		format.setMinimumFractionDigits(2);
		format.setMaximumFractionDigits(2);
		format.setRoundingMode(RoundingMode.HALF_UP);
		
        InternationalFormatter formatter = new InternationalFormatter(format);
        formatter.setAllowsInvalid(false);
        return formatter;
    }
}
