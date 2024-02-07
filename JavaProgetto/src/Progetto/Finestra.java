package Progetto;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swtchart.ISeries.SeriesType;
import org.eclipse.swtchart.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Composite;

public class Finestra {
	
	protected Shell shell;
	private Table table;
	Spinner spinner;
	ProgressBar progressBar;
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Finestra window = new Finestra();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
	
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	
	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(419, 428);
		shell.setText("SWT Application");
			
		table = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(157, 11, 225, 360);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn clmLanci = new TableColumn(table, SWT.NONE);
		clmLanci.setWidth(49);
		clmLanci.setText("Lanci");
		
		TableColumn clmTesta = new TableColumn(table, SWT.NONE);
		clmTesta.setWidth(49);
		clmTesta.setText("Testa");
		
		TableColumn clmCroce = new TableColumn(table, SWT.NONE);
		clmCroce.setWidth(46);
		clmCroce.setText("Croce");
		
		TableColumn clmDiffTesta = new TableColumn(table, SWT.NONE);
		clmDiffTesta.setWidth(60);
		clmDiffTesta.setText("Diff Testa");
		
		Button btnEsegui = new Button(shell, SWT.NONE);
		btnEsegui.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//esegui			
				try {
					FileWriter myWriter = new FileWriter("DatiGrafico.csv");
					myWriter.write("Lanci,Testa,Croce,DiffTesta \n");
				
							      
				int tentativi = Integer.parseInt(spinner.getText()); //inizializzazione
				int testa = 0;
				int croce = 0;
				int diffTesta = 0;
				int lanci = 0;
				double random = 0;
				progressBar.setMaximum(tentativi/1000);
				Random aspetta = new Random();
				System.out.println("Tentativi = "+tentativi);
				
				for(int j=0;j<tentativi/1000;j++) { //fatto ciclo esterno cisì da poter metter uno sleep da 100 ogni 1000 lanci
				try {
					Thread.sleep(aspetta.nextInt(100-10)+10); //sleep perche il random genera numeri molto vicini 
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				progressBar.setSelection(j+1);
				for(int i=0;i<=1000;i++) {		
				
					if(i%1000==0 && i!=0) { //immissione nella tabella
						lanci = lanci+1000;
						TableItem item = new TableItem(table,SWT.NONE);
						item.setText(0, ""+lanci);
						myWriter.write(""+lanci+",");
						item.setText(1, ""+testa);
						myWriter.write(""+testa+",");
						item.setText(2, ""+croce);	
						myWriter.write(""+croce+",");
						diffTesta = testa-(lanci/2);
						item.setText(3, ""+diffTesta);
						myWriter.write(""+diffTesta+","+"\n");
					}
					random = Math.round(Math.random()); //determina se 0 o 1
					if(random==0) {
						croce++;
					}else {
						testa++;
					}
					
					}
				}
				myWriter.close();
				System.out.println("croce="+croce);
				System.out.println("testa="+testa);
			}
			 catch (IOException e2) {
				e2.printStackTrace();
			}
			}
		}
		);
		btnEsegui.setBounds(10, 60, 75, 25);
		btnEsegui.setText("Esegui");
		
		Label lblLanci = new Label(shell, SWT.NONE);
		lblLanci.setBounds(10, 11, 143, 15);
		lblLanci.setText("Inserisci il numero di lanci");

		spinner = new Spinner(shell, SWT.BORDER | SWT.READ_ONLY);
		spinner.setMaximum(1000000);
		spinner.setMinimum(1000);
		spinner.setIncrement(1000);
		spinner.setBounds(9, 32, 101, 22);
		
		progressBar = new ProgressBar(shell, SWT.NONE);
		progressBar.setBounds(8, 91, 143, 17);
		
	}
}
