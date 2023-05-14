import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class CreateLine extends JPanel {
    public static final int SELECT_NUMBER = 6; // 로또 6자리

    private LottoMain lottoMain; // 이 패널을 만든 LottoMain 객체
    private MyNums myNums;
    private LottoSelect lottoSelect;
    private int lineIdx;
    private ArrayList<JLabel> labels;
    private JLabel lblAutoSelect; // 자동, 수동 뜨는 Label
    private JLabel label;
    private JButton btnSelect;
    private JButton btnAuto;
    private JButton btnRemove;
    private boolean result = true;

    public CreateLine(int idx, LottoMain lottoMain) {
        this.lottoMain = lottoMain;
        lineIdx = idx;
        init();
        makeLine(); //  파라미터 안 지움 (불필요)
        addListeners();
    }

    public int getLineIdx() {
        return lineIdx;
    }

    private void init() {
        labels = new ArrayList<JLabel>();
        btnSelect = makeButton("번호선택");
        btnAuto = makeButton("자동선택");
        btnRemove = makeButton("삭제");
        lblAutoSelect = new JLabel();
        lblAutoSelect.setFont(new Font("맑은 고딕", Font.BOLD, 10));
        lblAutoSelect.setVisible(false);
    }
    public JButton makeButton(String str) {
        JButton btn = new JButton(str);
        btn.setFont(new Font("맑은고딕", Font.PLAIN, 12));
        btn.setMargin(new Insets(0,0,0,0));
        btn.setBackground(Color.WHITE);
        return btn;
    }
    // makeLine
    public void makeLine() {
        if(result == true) {
            add(new JLabel(String.valueOf(lineIdx)));
        }
        for(int i = 0; i < SELECT_NUMBER; i++) {
            label = LottoUtil.setImageSize("Gom.png", 55, 40);
            labels.add(label);
            add(labels.get(i));
        }
        add(lblAutoSelect);
        add(btnSelect);
        add(btnAuto);
        add(btnRemove);
        this.setBackground(Color.WHITE);
    };

    //받은 레이블배열 + 버튼
    public void pnlChange(ArrayList<JLabel> jLabels) {

        this.removeAll();
        labels.clear();

        add(new JLabel(String.valueOf(lineIdx)));
        add(lblAutoSelect);

        for (int i = 0; i < SELECT_NUMBER; i++) {
            labels.add(jLabels.get(i));
            add(labels.get(i));
        }
        add(btnSelect);
        add(btnAuto);
        add(btnRemove);
        lottoMain.pack();
    }
    //인덱스 레이블만 바꾸는 메소드
    public void setIndex(int i) {
        lineIdx = i; // 지운번호
        remove(0);
        add(new JLabel(String.valueOf(lineIdx)), 0);
    }

    public ArrayList<Integer> nums() {
        if (lblAutoSelect.getText().equals("자동")) {
            return myNums.getAutoNums();
        } else if (lblAutoSelect.getText().equals("수동")) {
            return lottoSelect.getMyNums().getHandNums();
        }
        return null;
    }

    //addLinsteners
    public void addListeners() {
        ActionListener aListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JButton btn = (JButton)ae.getSource();

                if(btn == btnSelect) {
                    lblAutoSelect.setText(String.format("수동"));
                    lottoSelect = new LottoSelect(lottoMain);
                    try {
                        pnlChange(LottoUtil.imgChange(nums()));
                        lblAutoSelect.setVisible(true);
                        lottoMain.pack();
                    } catch (IndexOutOfBoundsException ie) {
                        result = false;
                        makeLine();
                    }
                }
                // 자동선택하면 자동 뜸
                if(btn == btnAuto) {
                    lblAutoSelect.setText(String.format("자동"));
                    lblAutoSelect.setVisible(true);
                    myNums = new MyNums(6);
                    pnlChange(LottoUtil.imgChange(nums()));
                    lottoMain.pack();

                    System.out.println(myNums); // 확인차 찍음
                }
                if(btn == btnRemove) {
                    //카운트가 5였을때 삭제버튼누르면 추가레이블이 생기고
                    //1이 아닐때는 삭제가 되지만, 카운트가 1일때는 삭제불가
                    if(lottoMain.getCount() != 1) {
                        if(lottoMain.getCount() == 5) {
                            lottoMain.addLblAdd();
                        }
                        lottoMain.pnlCCenterRemove(CreateLine.this);
                        lottoMain.pack();
                    } else {
                        JOptionPane.showConfirmDialog(
                                CreateLine.this,
                                "하나 이상 구입해야 합니다.",
                                "삭제불가",
                                JOptionPane.PLAIN_MESSAGE,
                                JOptionPane.WARNING_MESSAGE
                        );
                    }
                }
            }
        };
        btnSelect.addActionListener(aListener);
        btnAuto.addActionListener(aListener);
        btnRemove.addActionListener(aListener);
    }
}