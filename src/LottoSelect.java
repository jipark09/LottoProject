import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class LottoSelect extends JDialog {

    private LottoMain owner;
    private MyNums myNums;

    private JLabel lblLogo;
    private JLabel lblexplain;
    private JToggleButton[] tbtnNums;
    private JButton	btnSave;
    private JButton btnCancel;

    //생성자
    public LottoSelect(LottoMain owner) {
        super(owner,"번호선택",true);
        this.owner = owner;
        init();
        setDisplay();
        addListeners();
        showFrame();
    }

    //getter,setter
    public MyNums getMyNums() {
        return myNums;
    }

    private void init() {
        myNums = new MyNums();

        //로고(사이즈,오른쪽정렬)
        Toolkit kit = Toolkit.getDefaultToolkit();
        Image logoImg = kit.getImage("lotto_logo.png");
        Image newLogoImg = logoImg.getScaledInstance(150,100, Image.SCALE_SMOOTH);
        ImageIcon logoIcon = new ImageIcon(newLogoImg);
        lblLogo = new JLabel(logoIcon, JLabel.CENTER);

        //번호판 토글버튼배열(1~45), 버튼 색, 여백
        tbtnNums = new JToggleButton[45];
        for(int i = 0; i < 45; i++) {
            JToggleButton tbtn = new JToggleButton(String.valueOf(i + 1));
            tbtn.setBackground(Color.WHITE);
            tbtn.setMargin(new Insets(2,2,2,2));
            tbtnNums[i] = tbtn;
        }

        // 설명글
        lblexplain = new JLabel("6개의 숫자를 선택해주세요", JLabel.CENTER);
        lblexplain.setFont(new Font("맑은고딕", Font.BOLD, 12));


        //저장,취소버튼
        btnSave = getButton("저장");
        btnCancel = getButton("취소");
    }

    //setDisplay
    private void setDisplay() {
        JPanel pnlNorth = new JPanel(new BorderLayout());
        pnlNorth.add(lblLogo, BorderLayout.NORTH);
        pnlNorth.add(lblexplain, BorderLayout.CENTER);
        pnlNorth.setBackground(Color.WHITE);

        JPanel pnlCenter = new JPanel(new GridLayout(9,5));
        pnlCenter.setBorder(new EmptyBorder(10,30,0,30));
        pnlCenter.setBackground(Color.WHITE);
        for(int i = 0; i< tbtnNums.length; i++) {
            pnlCenter.add(tbtnNums[i]);
        }

        JPanel pnlSouth = new JPanel();
        pnlSouth.setBorder(new EmptyBorder(5,30,10,30));
        pnlSouth.setBackground(Color.WHITE);
        pnlSouth.add(btnSave);
        pnlSouth.add(btnCancel);

        add(pnlNorth,BorderLayout.NORTH);
        add(pnlCenter,BorderLayout.CENTER);
        add(pnlSouth,BorderLayout.SOUTH);

    }

    //addListeners
    private void addListeners() {

        ActionListener aListener = new ActionListener () {
            @Override
            public void actionPerformed(ActionEvent e) {

                int select = 0;
                for(int i = 0; i < tbtnNums.length; i++) {
                    if(tbtnNums[i].isSelected()) {
                        int lottoNum = i + 1;
                        select++;
                        myNums.addMyNum(lottoNum);
                    }
                }

                //경우의 수 : 6자리 숫자일 때, 6자리 숫자지만 no라고 했을 때, 6자리 숫자가 아닐 때
                boolean flag = true;
                if(select == 6) {
                    int answer = JOptionPane.showConfirmDialog(null,
                            "선택하신 번호가 ("+ myNums.getHandNums() +")가 맞습니까?",
                            "확인",
                            JOptionPane.YES_NO_OPTION
                    );
                    if(answer == JOptionPane.YES_OPTION) {
                        dispose();
                        flag = false;
                    }
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "로또번호 6개를 선택하십시오",
                            "확인",
                            JOptionPane.WARNING_MESSAGE
                    );
                }
                if(flag) {
                    for(int i : myNums.getHandNums()) {
                        tbtnNums[i-1].setSelected(false);
                    }
                    myNums.deleteMyNum();
                }
            }
        };
        btnSave.addActionListener(aListener);

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();

            }
        });
    };

    //showFrame
    private void showFrame() {
        setTitle("번호선택");
        setSize(300,500);
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }


    //버튼 만드는 메소드
    public JButton getButton(String btnName) {
        JButton btn = new JButton(btnName);
        btn.setBackground(new Color(0x7DB249));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("맑은 고딕",Font.PLAIN, 12));
        btn.setForeground(Color.WHITE);
        btn.setMargin(new Insets(2,5,2,5));
        btn.setPreferredSize(new Dimension(45,20));
        return btn;
    }
}