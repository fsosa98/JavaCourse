package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;

import javax.swing.JFrame;

/**
 * Class FormLocalizationProvider is form localization provider
 * 
 * @author Filip
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {

	/**
	 * Constructor with given parameters.
	 * 
	 * @param parent
	 * @param frame
	 */
	public FormLocalizationProvider(ILocalizationProvider parent, JFrame frame) {
		super(parent);

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				connect();
			}

			@Override
			public void windowClosed(WindowEvent e) {
				disconnect();
			}
		});

	}
}
