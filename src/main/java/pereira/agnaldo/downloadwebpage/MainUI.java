package pereira.agnaldo.downloadwebpage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.SwingWorker.StateValue;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileSystemView;

public class MainUI {

	// http://d7m.com.br/manager/player/16

	private File downloadDir;
	private DonwloadTask donwloadTask;

	private JFrame frmDownloadDePginas;
	private JTextField httpTextField;
	private JTextArea resultTextArea;
	private JLabel lblDownloadDir;
	private JButton btnDownload;
	private JButton btnChange;
	private JCheckBox chkDeleteDir;
	private JCheckBox chkOpenBrowser;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainUI window = new MainUI();
					window.frmDownloadDePginas.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainUI() {
		try {
			initialize();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws IOException
	 */
	private void initialize() throws IOException {

		downloadDir = new File(FileSystemView.getFileSystemView().getDefaultDirectory().getPath(), "DownloadedWebPage");

		frmDownloadDePginas = new JFrame();
		frmDownloadDePginas.setMinimumSize(new Dimension(600, 300));
		frmDownloadDePginas.setTitle("Download de páginas WEB");
		frmDownloadDePginas.setBounds(100, 100, 832, 385);
		frmDownloadDePginas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		frmDownloadDePginas.setContentPane(panel);
		panel.setLayout(new BorderLayout(2, 2));

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
		panel.add(panel_2, BorderLayout.NORTH);
		panel_2.setLayout(new BorderLayout(2, 2));

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
		panel_2.add(panel_1, BorderLayout.NORTH);

		JLabel lblDiertrioDeDownload = new JLabel("Diertório de download:");

		lblDownloadDir = new JLabel(downloadDir.getAbsolutePath());

		btnChange = new JButton("Trocar");
		btnChange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectDirectory();
			}
		});
		panel_1.setLayout(new BorderLayout(2, 2));
		panel_1.add(lblDiertrioDeDownload, BorderLayout.WEST);
		panel_1.add(lblDownloadDir, BorderLayout.CENTER);
		panel_1.add(btnChange, BorderLayout.EAST);

		JPanel panel_6 = new JPanel();
		panel_6.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
		panel_1.add(panel_6, BorderLayout.SOUTH);
		panel_6.setLayout(new BorderLayout(0, 0));

		chkDeleteDir = new JCheckBox("Apagar diretório antes do dowload");
		chkDeleteDir.setHorizontalAlignment(SwingConstants.LEFT);
		chkDeleteDir.setHorizontalTextPosition(SwingConstants.LEFT);
		panel_6.add(chkDeleteDir, BorderLayout.EAST);

		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
		panel_2.add(panel_3, BorderLayout.SOUTH);
		panel_3.setLayout(new BorderLayout(2, 2));

		JLabel lblLinkHttp = new JLabel("Link HTTP");
		panel_3.add(lblLinkHttp, BorderLayout.WEST);

		httpTextField = new JTextField();
		httpTextField.setToolTipText("http://...");
		panel_3.add(httpTextField, BorderLayout.CENTER);
		httpTextField.setColumns(10);

		btnDownload = new JButton("Download");
		btnDownload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				downloadWebPage();
			}
		});
		panel_3.add(btnDownload, BorderLayout.EAST);

		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
		panel_3.add(panel_5, BorderLayout.SOUTH);
		panel_5.setLayout(new BorderLayout(0, 0));

		chkOpenBrowser = new JCheckBox("Abrir navegador ao terminar  download");
		chkOpenBrowser.setHorizontalTextPosition(SwingConstants.LEFT);
		chkOpenBrowser.setHorizontalAlignment(SwingConstants.LEFT);
		panel_5.add(chkOpenBrowser, BorderLayout.EAST);

		final Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
		if (desktop == null || !desktop.isSupported(Desktop.Action.BROWSE)) {
			chkOpenBrowser.setVisible(false);
		}

		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
		panel.add(panel_4, BorderLayout.CENTER);
		panel_4.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		panel_4.add(scrollPane, BorderLayout.CENTER);

		resultTextArea = new JTextArea();
		scrollPane.setViewportView(resultTextArea);
		scrollPane.setAutoscrolls(true);
	}

	public void selectDirectory() {
		final JFileChooser jFileChooser = new JFileChooser();
		jFileChooser.setCurrentDirectory(downloadDir);
		jFileChooser.setDialogTitle("Escolha um diretório de download");
		jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		jFileChooser.setAcceptAllFileFilterUsed(false);

		if (jFileChooser.showOpenDialog(frmDownloadDePginas) == JFileChooser.APPROVE_OPTION) {
			downloadDir = new File(jFileChooser.getSelectedFile().getAbsolutePath(), "DownloadedWebPage");
			lblDownloadDir.setText(downloadDir.getAbsolutePath());
		}
	}

	public void downloadWebPage() {
		if (donwloadTask == null || donwloadTask.getState() != StateValue.STARTED) {
			donwloadTask = new DonwloadTask();
			donwloadTask.execute();
		}
	}

	public class DonwloadTask extends SwingWorker<Boolean, String> {

		public DonwloadTask() {
			super();
			btnChange.setEnabled(false);
			btnDownload.setEnabled(false);
			resultTextArea.setEditable(false);
			httpTextField.setEditable(false);
		}

		@Override
		protected Boolean doInBackground() throws Exception {
			FileUtils.copyURLToFile(httpTextField.getText().trim(), downloadDir, this, chkDeleteDir.isSelected());
			return false;
		}

		@Override
		protected void process(List<String> statusList) {
			super.process(statusList);
			if (statusList != null && !statusList.isEmpty()) {
				for (final String status : statusList) {
					resultTextArea.append("\r\n" + status);
				}
			}
		}

		@Override
		protected void done() {
			super.done();
			btnChange.setEnabled(true);
			btnDownload.setEnabled(true);
			resultTextArea.setEditable(true);
			httpTextField.setEditable(true);

			if (chkOpenBrowser.isSelected()) {
				try {
					final File htmlFile = new File(downloadDir.getAbsolutePath(), "index.html");
					final Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
					if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
						desktop.browse(htmlFile.toURI());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}
}
