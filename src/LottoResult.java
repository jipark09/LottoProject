import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class LottoResult extends JDialog {

    private LottoMain owner;
    private LottoNums lottoNums;
    private JLabel lblHome;
    private JLabel lblResult;
    private JLabel lblTrack;
    private JLabel lblPlus; // 보너스번호 앞에 +

    //로또당첨번호 배열
    private ArrayList<Integer> lotto;

    public LottoResult(LottoMain owner) {
        super(owner, "결과보기", true);
        this.owner = owner;
        init();
        setDisplay();
        addListener();
        showFrame();
    }

    private void init() {

        lottoNums = new LottoNums();

        lblResult = new JLabel("당첨 결과를 알려드립니다", JLabel.CENTER);
        lblResult.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        lblResult.setForeground(Color.WHITE);

        // 홈 화면 설정
        lblHome = LottoUtil.setImageSize("home_icon_w.png", 50, 50);
        lblHome.setBorder(new EmptyBorder(new Insets(0,10,0,0)));
        lblHome.setToolTipText("처음으로 돌아가기");

        // 추적하기 아이콘 설정
        lblTrack = LottoUtil.setImageSize("search_icon.png", 50, 50);
        lblTrack.setBorder(new EmptyBorder(new Insets(0,10,0,0)));
        lblTrack.setToolTipText("추적하기");

        // 로또당첨번호 7개뽑기
        lotto = lottoNums.getAutoNums();

        lblPlus = new JLabel(" + ");
        lblPlus.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        //resultNums.add(lottoNums.BONUS, lblPlus);
        System.out.println("결과: "+ lotto);
    }

    private void setDisplay() {
        EmptyBorder size = new EmptyBorder(new Insets(10,10,10,10));

        //pnlNorth
        JPanel pnlNorth = new JPanel(new BorderLayout());
        pnlNorth.setBackground(new Color(0x7DB249));
        pnlNorth.setBorder(size);
        pnlNorth.add(lblResult,BorderLayout.CENTER);
        pnlNorth.add(lblHome,BorderLayout.WEST);
        pnlNorth.add(lblTrack,BorderLayout.EAST);


        // pnlCNorth
        JPanel pnlCNorth = new JPanel();

        ArrayList<JLabel> imgLotto = LottoUtil.imgChange(lotto);
        imgLotto.add(LottoNums.BONUS, lblPlus);
        for(JLabel label : imgLotto) {
            label.setBackground(Color.lightGray);
            pnlCNorth.add(label);
        }
        pnlCNorth.setBorder(size);

        pnlCNorth.setBorder(new EmptyBorder(10,10,10,10));


        // pnlResult
        // pnlCCenter
        JPanel pnlCCenter = new JPanel(new GridLayout(0,1));

        for(int i = 1; i < owner.getMap().size() + 1; i++) {
            JPanel pnlResult = new JPanel();
            pnlResult.setBackground(Color.WHITE);
            // CreateLine 객체숫자배열받아옴
            ArrayList<Integer> numsOfLine = owner.getMapLine(i).nums();
            // 로또번호랑 입력한 번호 비교해서 이미지배열로 만듦
            ArrayList<JLabel> list = LottoUtil.imgChangeWhite(numsOfLine,lotto);

            //인덱스 만들고 패널에 붙
            JLabel index = new JLabel(String.valueOf(i));
            index.setBackground(Color.GRAY);
            index.setBorder(size);
            pnlResult.add(index);

            //라인의 숫자배열과 로또당첨번호랑 비교해서 나온 결과(String)을 레이블로 만들고 패널에 추가
            pnlResult.add(new JLabel(lottoNums.showResult(numsOfLine, lotto)));

            //이미지배열 하나씩 꺼내서 이미지 패널에 붙임
            for (JLabel jLabel : list) {
                pnlResult.add(jLabel);
            }
            //라인하나 완성했고 패널에 붙임!
            pnlCCenter.add(pnlResult);
            pnlCCenter.setBorder(new LineBorder(Color.BLACK,1));
        }
        pnlCCenter.setBorder(new EmptyBorder(20,20,20,20));
        pnlCCenter.setBackground(Color.WHITE);


        // pnlCenter
        JPanel pnlCenter = new JPanel(new BorderLayout());
        pnlCenter.add(pnlCNorth, BorderLayout.NORTH);
        pnlCenter.add(pnlCCenter, BorderLayout.CENTER);
        pnlCenter.setBackground(Color.WHITE);
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
                        LottoResult.this,
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
                    owner.setVisible(true);
                    dispose();
                }
                // 사용자가 Track버튼 누르면 추적하기 창으로 이동
                if(e.getSource() == lblTrack) {
                    setVisible(false);
                    new LottoTrack(owner);
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);

                if(e.getSource() == lblHome) {
                    lblHome.setCursor(handCursor);
                }
                if(e.getSource() == lblTrack) {
                    lblTrack.setCursor(handCursor);
                }
            }
        };
        lblHome.addMouseListener(mouseListener);
        lblTrack.addMouseListener(mouseListener);
    }


    private void showFrame() {
        setTitle("로또 당첨 결과");
        pack();
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setVisible(true);
    }


}