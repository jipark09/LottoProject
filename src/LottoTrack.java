import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class LottoTrack extends JDialog {
    private LottoMain owner;
    private LottoNums lottoNums;
    private JLabel lblTrack; // 추적하기 레이블
    private JLabel lblHome; // 홈 아이콘

    // 몇 번째 당첨될까요오?
    private JLabel lblGoal;


    private ArrayList<Integer> chooseNums;
    private ArrayList<Integer> lotto;
    private String rank = "";

    public LottoTrack(LottoMain owner) {
        super(owner, "추적하기", false);
        this.owner = owner;
        init();
        setDisplay();
        addListener();
        showFrame();
    }

    private void init() {

        /// 추적결과 레이블
        lblTrack = new JLabel("추적 결과", JLabel.CENTER);
        lblTrack.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        lblTrack.setForeground(Color.WHITE);

        // 홈 화면 설정
        lblHome = LottoUtil.setImageSize("Home_icon_w.png", 50, 50);
        lblHome.setBorder(new EmptyBorder(new Insets(0,10,0,0)));
        lblHome.setToolTipText("처음으로 돌아가기");

        // 몇번째 당첨
        lblGoal = new JLabel("몇 번째 당첨될까요?",JLabel.CENTER);
        lblGoal.setPreferredSize(new Dimension(300,40));
        lblGoal.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        lblGoal.setForeground(Color.BLACK);
        lblGoal.setBorder(new LineBorder(Color.BLACK, 1));

    }

    private void setDisplay() {

        //pnlNorth
        JPanel pnlNorth = new JPanel(new BorderLayout());
        pnlNorth.setBackground(new Color(0x7DB249));
        pnlNorth.setBorder(BorderFactory.createLineBorder(new Color(0x7DB249),3));
        pnlNorth.add(lblHome,BorderLayout.WEST);
        pnlNorth.add(lblTrack,BorderLayout.CENTER);

        //pnlWhen
        ArrayList<JPanel> pnlWhen = new ArrayList<JPanel>();

        for(int i = 1; i < owner.getMap().size() + 1; i++) {
            JPanel pnl = new JPanel();
            String howMany = String.valueOf(whenGoal(i));
            //인덱스
            pnl.add(new JLabel(String.valueOf(i)));
            //자동수동
            pnl.add(owner.getMap().get(i).getComponent(1));
            //번호가져오기
            ArrayList<JLabel> arr = LottoUtil.imgChangeWhite(chooseNums, lotto);
            for(int j = 0; j < arr.size(); j++) {
                pnl.add(arr.get(j));
            }
            //횟수
            pnl.add(new JLabel(howMany + "회"));
            //등수
            pnl.add(new JLabel(rank));

            pnlWhen.add(pnl);
        }
        //pnlCNorth
        JPanel pnlCNorth = new JPanel();
        pnlCNorth.setBackground(Color.WHITE);
        pnlCNorth.add(lblGoal);
        pnlNorth.add(LottoUtil.setImageSize("nullGreen.png", 60, 50), BorderLayout.EAST);
        pnlCNorth.setBorder(new EmptyBorder(10,0,10,0));

        //pnlCCenter
        JPanel pnlCCenter = new JPanel(new GridLayout(0, 1));
        for(JPanel p : pnlWhen) {
            pnlCCenter.add(p);
            p.setBackground(Color.WHITE);
        }
        pnlCCenter.setBackground(Color.WHITE);
        pnlCCenter.setBorder(new EmptyBorder(20, 20, 20, 20));

        //pnlCenter
        JPanel pnlCenter = new JPanel(new BorderLayout());
        pnlCenter.setBackground(Color.WHITE);
        pnlCenter.add(pnlCNorth, BorderLayout.NORTH);
        pnlCenter.add(pnlCCenter, BorderLayout.CENTER);

        pnlCenter.setBorder(BorderFactory.createLineBorder(new Color(0x7DB249),3));


        // add
        add(pnlNorth, BorderLayout.NORTH);
        add(pnlCenter, BorderLayout.CENTER);

    }
    public void addListener() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int choice = JOptionPane.showConfirmDialog(
                        LottoTrack.this,
                        "로또 프로그램을 종료하시겠습니까?",
                        "종료",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );
                if(choice == JOptionPane.OK_OPTION) {
                    System.exit(0);
                }
            }
        });
        MouseListener mouseListener = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) { // 사용자가 홈버튼 누르면 로또메인으로 감
                if(e.getSource() == lblHome) {
                    owner.initActive(owner);
                    dispose();
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);

                if(e.getSource() == lblHome) {
                    lblHome.setCursor(handCursor);
                }
            }
        };
        lblHome.addMouseListener(mouseListener);
    }
    private void showFrame() {
        pack();
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setVisible(true);

    }

    // 라인번호를 넣으면 1등이나 2등이 나올때 까지 반복한횟수를 반환함.
    public int whenGoal(int lineIdx) {

        int count = 0;
        boolean isGoal = false;

        while(isGoal == false) {
            lottoNums = new LottoNums(); // 로또 번호 계속 돌림
            lotto = lottoNums.getAutoNums(); // 로또 번호 배열
            count++;

            chooseNums = owner.getMapLine(lineIdx).nums(); // Mynums
            rank = lottoNums.showResult(chooseNums, lotto); // MyNums와 lotto번호 비교 후 등수 반환
            if(rank.equals("1등") || rank.equals("2등")) {
                isGoal = true;
            }
        }
        return count;
    }
} 