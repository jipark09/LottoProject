import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collection;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class LottoMain extends JFrame {

    private JPanel pnlCCenter;
    private JPanel pnlCSouth;

    private JLabel lblImgHelp;
    private JLabel lblImgLogo;
    private JLabel lblInit;
    private JLabel lblCheck;
    private JLabel lblAdd;
    private JButton btnResult;
    private int count = 1; // 1번라인은 무조건 처음에 있음

    private HashMap<Integer, CreateLine> map;

    // 생성자
    public LottoMain() {
        init();
        setDisplay();
        addListeners();
        showFrame();
    }

    // getter/setter
    public int getCount() { // CreateLine 에서 버튼지울 때 추가레이블을 넣으려면 카운팅이 필요
        return count;
    }

    public HashMap<Integer, CreateLine> getMap() {
        return map;
    }

    /*
     *우리가 원하는 번호(자동,수동)을 가진 크리에이트 객체를 다른 클래스에서 사용하고싶은데
     *클래스 마다 createLine 변수를 만들게되면 초기화과정을 거치면서 새로운 객체가 되어버림..
     *크리에이트 객체를 한번만 만든 다음, 그 주소값을 맵에 저장해서 맵을 통해
     *우리가 원하는 번호(자동,수동)를 가진 크리에이트 객체에 접근하려고 했음
     */

    // 맵을 통해 객체에 접근하기 위한 메소드
    public CreateLine getMapLine(int idx) {
        return map.get(idx);
    }


    // init
    public void init() {
        map = new HashMap<>();
        // btnResult 설정
        btnResult = new JButton("결과 확인");
        btnResult.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        btnResult.setForeground(Color.WHITE);
        btnResult.setBackground(new Color(0x7DB249));
        btnResult.setBorder(new LineBorder(Color.BLACK, 1));

        // lblCheck 설정
        lblCheck = new JLabel("선택번호 확인");
        lblCheck.setFont(new Font("맑은 고딕", Font.BOLD, 15));

        // lblAdd 설정
        lblAdd = new JLabel("+ 추가 ", JLabel.CENTER);
        lblAdd.setFont(new Font("맑은 고딕", Font.BOLD, 20));

        // lblInit 설정 => label로 변경!
        lblInit = LottoUtil.setImageSize("reset.png", 60, 30);
        lblInit.setBorder(new EmptyBorder(new Insets(0, 10, 0, 0)));

        // 도움말 아이콘 설정
        lblImgHelp = LottoUtil.setImageSize("help_icon.png", 50, 50);
        lblImgHelp.setBorder(new EmptyBorder(new Insets(0, 10, 0, 0)));
        lblImgHelp.setToolTipText("게임 도움말");

        // 로또 로고 아이콘 설정
        lblImgLogo = LottoUtil.setImageSize("lotto_logo.png", 150, 100);

    }

    // setDisplay
    public void setDisplay() {
        // 패널여백
        EmptyBorder size = new EmptyBorder(new Insets(10, 10, 10, 10));

        // pnlNorth
        JPanel pnlNorth = new JPanel(new BorderLayout());
        pnlNorth.setBorder(new LineBorder(Color.BLACK, 1));
        pnlNorth.setBackground(Color.WHITE);
        pnlNorth.add(lblImgHelp, BorderLayout.WEST);
        pnlNorth.add(lblImgLogo, BorderLayout.CENTER);
        // 로고 오른쪽에 여백주는 이미지
        pnlNorth.add(LottoUtil.setImageSize("null.png", 60, 50), BorderLayout.EAST);

        // pnlCNorth
        JPanel pnlCNorth = new JPanel(new BorderLayout());
        pnlCNorth.setBorder(size);
        pnlCNorth.setBackground(Color.WHITE);
        pnlCNorth.add(lblCheck, BorderLayout.WEST);
        pnlCNorth.add(lblInit, BorderLayout.EAST);

        // pnlCCenter
        pnlCCenter = new JPanel(new GridLayout(0, 1));
        // 처음에는 무조건 1번라인이 있어야함(테이블1번에 만들고 가져와서 추가)
        map.put(1, new CreateLine(count, this));
        pnlCCenter.add(map.get(1));

        pnlCCenter.setBackground(Color.WHITE);
        pnlCCenter.setBorder(new EmptyBorder(new Insets(0, 10, 0, 10)));

        // pnlCSouth
        pnlCSouth = new JPanel();
        pnlCSouth.add(lblAdd);
        pnlCSouth.setBackground(Color.WHITE);
        pnlCSouth.setBorder(size);

        // pnlCenter
        JPanel pnlCenter = new JPanel(new BorderLayout());
        pnlCenter.setBackground(Color.WHITE);
        pnlCenter.add(pnlCNorth, BorderLayout.NORTH);
        pnlCenter.add(pnlCCenter, BorderLayout.CENTER);
        pnlCenter.add(pnlCSouth, BorderLayout.SOUTH);
        pnlCenter.setBorder(new LineBorder(Color.BLACK, 1));

        // add
        add(pnlNorth, BorderLayout.NORTH);
        add(pnlCenter, BorderLayout.CENTER);
        add(btnResult, BorderLayout.SOUTH);
    }

    // addListeners
    public void addListeners() {

        ActionListener aListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JButton btn = (JButton) ae.getSource();

                if (btn == btnResult) {
                    //선택되지 않은 라인이 있는지 찾아보기.
                    Collection<CreateLine> values = map.values();
                    int selectedLine = 0;
                    for(CreateLine value : values) {
                        if(value.nums() != null) {
                            selectedLine++;
                        }
                    }
                    //선택된 라인이 테이블 사이즈랑 같으면 결과확인, 아니면 못
                    if(selectedLine == map.size()) {
                        int choice = JOptionPane.showConfirmDialog(LottoMain.this,
                                "결과를 확인하시겠습니까?",
                                "결과확인",
                                JOptionPane.OK_CANCEL_OPTION,
                                JOptionPane.INFORMATION_MESSAGE);
                        if (choice == JOptionPane.OK_OPTION) {
                            new LottoResult(LottoMain.this);
                        }
                    } else {
                        JOptionPane.showConfirmDialog(LottoMain.this,
                                "번호를 선택해주십시오",
                                "선택",
                                JOptionPane.PLAIN_MESSAGE,
                                JOptionPane.INFORMATION_MESSAGE);
                    }

                }
            }
        };
        btnResult.addActionListener(aListener);


        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int choice = JOptionPane.showConfirmDialog(
                        LottoMain.this,
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
            public void mousePressed(MouseEvent e) {

                if(e.getSource() == lblImgHelp) {
                    new LottoHelp(LottoMain.this);
                }
                //추가레이블 선택할 때
                else if(e.getSource() == lblAdd) {
                    //해쉬테이블에 추가,꺼내서 패널에 붙이기
                    count++;
                    map.put(count, new CreateLine(count, LottoMain.this));
                    pnlCCenter.add(map.get(count));
                    //System.out.println(table.get(count).getLineIdx() + " 추가해서 " + count);
                    pack();

                    // 5장만 살 수 있음!
                    if (count == 5) {
                        pnlCSouth.remove(lblAdd); // 추가 라벨 사라짐
                        pnlCSouth.revalidate();
                    }
                }
                if(e.getSource() == lblInit) {
                    initActive(LottoMain.this);
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
                Object src = e.getSource();

                if(src == lblAdd) {
                    lblAdd.setCursor(handCursor);
                }
                if(src == btnResult) {
                    btnResult.setCursor(handCursor);
                }
                if(src == lblImgHelp) {
                    lblImgHelp.setCursor(handCursor);
                }
            }
        };
        lblImgHelp.addMouseListener(mouseListener);
        btnResult.addMouseListener(mouseListener);
        lblAdd.addMouseListener(mouseListener);
        lblInit.addMouseListener(mouseListener);

    }
    public void initActive(LottoMain lottoMain) {
        count = 1;
        pnlCCenter.removeAll();
        map.put(1, new CreateLine(1, LottoMain.this));
        pnlCCenter.add(map.get(1));
        pnlCSouth.add(lblAdd);
        pnlCSouth.revalidate();
        pack();
    }

    // showFrame
    public void showFrame() {
        setTitle("LottoMain");
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setVisible(true);
    }

    // 한 줄 패널 삭제 메서드(패널 타겟)
    public void pnlCCenterRemove(CreateLine createLine) {
        int deletedIdx = createLine.getLineIdx(); // 지운라인의 번호받아옴
        pnlCCenter.remove(createLine); // 지움

        // 지운거 다음라인들의 인덱스 번호 바꿔서 테이블에 지운라인번호로 다시 넣음
        for (int i = deletedIdx; i < map.size(); i++) {
            CreateLine replaceLine = map.get(i + 1); // 지운거 다음 거 들고옴
            replaceLine.setIndex(i); // 만든 메소드
            map.put(i, replaceLine);
        }
        // 개수는 꼭!!!여기서 줄여야함!!
        count--;
        map.remove(map.size());// 제일 마지막 테이블 없앰
    }

    // +추가 레이블넣는 메소드
    public void addLblAdd() {
        pnlCSouth.add(lblAdd);
    }

    public static void main(String[] args) {
        new LottoMain();
    }
}