import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;


public class LottoHelp extends JDialog {
    private JLabel lblBuy;
    private LottoMain owner;
    private JTextArea howToBuy;

    public LottoHelp(LottoMain owner) {
        super(owner,"LottoHelp", true);
        this.owner = owner;
        init();
        setDisplay();
        showFrame();
    }

    public void init() {
        lblBuy = new JLabel("♡로또를 즐기는 방법♡", JLabel.CENTER);
        howToBuy = new JTextArea("\n   1. 구매수량은 추가버튼을 눌러서 원하는 만큼 구입 해 주세요 (구매횟수 최대 10회) \n \n"
                + "   2. 메인화면에서 직접선택 또는 자동선택 \n"
                + "\t -수동: 1~45까지 숫자 중 사용자가 원하는 숫자 6개를 직접 선택 \n"
                + "\t -자동: 1~45까지 숫자 중 임의의 숫자 6개가 선택됨 \n"
                + "   *반자동 수동: 자동 선택한 상태에서, 사용자가 변경하고 싶은 숫자들을 직접 변경가능 \n"
                + "   직접선택한 상태에서, 사용자가 변경하고 싶은 숫자들을 자동으로 선택가능 \n \n"
                + "   3. 선택번호 추가 기능 \n"
                + "\t -초기화: 모든 번호를 삭제 \n"
                + "\t -수정: 선택된 6개의 숫자 중 전체 또는 일부 변경가능 \n"
                + "\t -삭제: 선택한 번호 6개를 삭제 \n \n"
                + "   4. 1등: 6개 번호 모두 일치 / 2등: 5개 번호+보너스번호 일치 \n "
                + "   3등: 5개 번호 일치 / 4등: 4개 번호 일치 / 5등: 3개 번호 일치 \n \n"
                + "   5. 2등 보너스 번호 설명 \n"
                + "   뽑기 순서대로 선정된 6개의 번호 중 5개의 번호가 일치하고, \n "
                + "   7번째로 추첨된 보너스 번호가 일치하면 2등에 당첨됩니다!! \n"
                ,16,30
        );
        howToBuy.setEditable(false);
    }

    public void setDisplay() {
        JPanel JNorth = new JPanel();
        JNorth.setBackground(new Color(0x7DB249));
        lblBuy.setPreferredSize(new Dimension(500,50));
        lblBuy.setFont(new Font("맑은 고딕", Font.BOLD, 30));
        lblBuy.setForeground(Color.WHITE);
        lblBuy.setBackground(new Color(0x7DB249));
        howToBuy.setBorder(BorderFactory.createLineBorder(new Color(0x7DB249),3));
        JNorth.add(lblBuy);

        add(JNorth, BorderLayout.NORTH);
        add(howToBuy,BorderLayout.SOUTH);

    }

    public void showFrame() {
        setTitle("Help");
        pack();
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}