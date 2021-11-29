package Controller;

import javax.swing.JProgressBar;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class MainFrame extends JFrame implements ActionListener, Runnable {

    private boolean pause = false;
    private boolean resume = false;
    private int row = 8;
    private int col = 8;
    private int width = 1000;
    private int height = 800;
    private ButtonEvent graphicsPanel;
    private JPanel mainPanel;
    private int MAX_TIME = 300;
    public int time = MAX_TIME;
    public JLabel lbScore;
    private JProgressBar progressTime;
    private JButton btnNewGame;

    public MainFrame() {
        add(mainPanel = createMainPanel()); // add content to Jframe
        setTitle("Pokemon Game");// tao ten cho Jrame
        setResizable(true);// cho phep phong to thu nho frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        /*
               DO_NOTHING_ON_CLOSE– Không làm thêm bất cứ điều gì khi JFrame bị đóng
HIDE_ON_CLOSE – JFrame sẽ bị ẩn đi khi người dùng đóng nó lại. Chương trình vẫn sẽ hoạt động bình thường trong khi JFrame này bị ẩn. Đây là một hành động mặc định của JFrame.
DISPOSE_ON_CLOSE – Sau khi bị đóng lại, nó đồng thời sẽ bị dọn dẹp rác, các tài nguyên được JFrame này sử dụng sẽ bị thu hồi nhường chỗ cho những nơi khác sử dụng.
EXIT_ON_CLOSE – Sau khi JFrame bị đóng, chương trình cũng sẽ tắt the
         */
        setSize(width, height);// quy dinh kich thuoc cho frame
        setLocationRelativeTo(null);// thiet lap cua so xuat hien giua man hinh desktop
        setVisible(true);// an hien Jframe

    }

    public boolean isResume() {
        return resume;
    }

    public void setResume(boolean resume) {
        this.resume = resume;
    }

    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    private JPanel createMainPanel() {

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(createGraphicsPanel(), BorderLayout.CENTER);
        panel.add(createControlPanel(), BorderLayout.EAST);
        return panel;
    }

    private JPanel createGraphicsPanel() {
//        MainFrame frame = new MainFrame();
        graphicsPanel = new ButtonEvent(this, row, col);
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.gray);
        panel.add(graphicsPanel);
        return panel;
    }

    private JButton createButton(String buttonName) {
        JButton btn = new JButton(buttonName);
        btn.addActionListener(this);
        return btn;
    }

    private JPanel createControlPanel() {
        //tạo JLabel lblScore với giá trị ban đầu là 0
        lbScore = new JLabel("0");
        progressTime = new JProgressBar(0, 100);
        progressTime.setValue(100);

        //tạo Panel chứa Score và Time
        JPanel panelLeft = new JPanel(new GridLayout(2, 1, 5, 5));
        panelLeft.add(new JLabel("Score:"));
        panelLeft.add(new JLabel("Time:"));

        JPanel panelCenter = new JPanel(new GridLayout(2, 1, 5, 5));
        panelCenter.add(lbScore);
        panelCenter.add(progressTime);

        JPanel panelScoreAndTime = new JPanel(new BorderLayout(5, 0));
        panelScoreAndTime.add(panelLeft, BorderLayout.WEST);
        panelScoreAndTime.add(panelCenter, BorderLayout.CENTER);

        // tạo Panel mới chứa panelScoreAndTime và nút New Game
        JPanel panelControl = new JPanel(new BorderLayout(10, 10));
        panelControl.setBorder(new EmptyBorder(10, 3, 5, 3));
        panelControl.add(panelScoreAndTime, BorderLayout.CENTER);
        panelControl.add(btnNewGame = createButton("New Game"),
                BorderLayout.PAGE_END);

        // Set BorderLayout để panelControl xuất hiện ở đầu trang
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new TitledBorder("Good luck"));
        panel.add(panelControl, BorderLayout.PAGE_START);

        return panel;
    }

    public void newGame() {
        time = MAX_TIME;
        graphicsPanel.removeAll();
        mainPanel.add(createGraphicsPanel(), BorderLayout.CENTER);
        mainPanel.validate();
        mainPanel.setVisible(true);
        lbScore.setText("0");
    }

    public void showDialogNewGame(String message, String title, int t) {
        pause = true;
        resume = false;
        int select = JOptionPane.showOptionDialog(null, message, title,
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                null, null);
        if (select == 0) {
            pause = false;
            newGame();
        } else {
            if (t == 1) {
                System.exit(0);
            } else {
                resume = true;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnNewGame) {
            showDialogNewGame("Your game hasn't done. Do you want to create a new game?", "Warning", 0);
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            progressTime.setValue((int) ((double) time / MAX_TIME * 100));
        }
    }
}
