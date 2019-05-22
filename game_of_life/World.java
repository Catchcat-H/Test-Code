package game_of_life;

import java.awt.Button;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;



public class World extends JFrame {
	JTextField[][] nowlife;
    int life[][]=new int[40][55];
    Rules rules = new Rules(life, 40, 55);
    boolean gameBegin,gameStop = false;
    TextField textField;
    int i=0;
    JPanel contentPane;


	public static void main(String[] args) {
		new World();
	}
	
	public void showWorld() {
    int[][] matrix = rules.getNowlife();
        for (int x = 0; x < 40; x++) {
            for (int y = 0; y < 55; y++) {
                if (matrix[x][y] == 1) {
                	nowlife[x][y].setBackground(Color.BLACK);
                	i++;
                } else {
                	nowlife[x][y].setBackground(Color.WHITE);
                }
            }
        }
    }
	
	public World() {
		setSize(1250, 900);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setLayout(new GridLayout(40, 55));
		setContentPane(contentPane);
		nowlife = new JTextField[40][55];
		for (int x = 0; x < 40; x++) {
            for (int y = 0; y < 55; y++) {
            	JTextField text = new JTextField();
            	text.setEnabled(false);
            	nowlife[x][y] = text;
            	contentPane.add(text);
            }
        }
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		
		
		Button randomcell = new Button("随机分布细胞");
		randomcell.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				rules.random();
				showWorld();
				gameBegin=true;
			}
		});
		randomcell.setForeground(Color.RED);
		menuBar.add(randomcell);
		
		
		textField = new TextField();
		textField.setBackground(Color.WHITE);
		textField.setForeground(Color.BLUE);
		textField.setEditable(false);;
		menuBar.add(textField);
		
		
		Button start = new Button("开始演化");
		start.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!gameBegin) //未选择
					JOptionPane.showMessageDialog(null, "未选择细胞", "错误警告", JOptionPane.ERROR_MESSAGE);
				else {
					new Thread(new Game()).start();
				    start.setEnabled(false);
				    randomcell.setEnabled(false);
				    }
			}
		});
		start.setForeground(Color.RED);
		menuBar.add(start);
		
		
		Button clean = new Button("暂停演化");
		clean.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!gameStop) {
					gameBegin=false;
					gameStop=true;
					clean.setLabel("细胞清零");
			}
				else if (gameStop){
					gameBegin=false;
					rules.cleanNowlife();
					showWorld();
					start.setEnabled(true);
					randomcell.setEnabled(true);
					textField.setText("");
					gameStop=false;
					clean.setLabel("暂停演化");
					}
					
				}
		});
		clean.setForeground(Color.RED);
		menuBar.add(clean);
		
		
		setVisible(true);
	}
	
	 public class Game implements Runnable {
          public void run() {
        	  int cnum = 0;
        	  try {
				Thread.sleep(640);				
			} catch (InterruptedException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
              while (gameBegin) {
                  rules.celllife();
                  showWorld();
                  cnum++;
                  textField.setText("细胞迭代:" + cnum + "次 " + "剩余细胞数:" + i);
                  i=0;
                  try {
					Thread.sleep(800);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}           
              }
      
          }
      }
	 
}