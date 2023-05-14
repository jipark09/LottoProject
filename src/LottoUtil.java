import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class LottoUtil {
    //이미지 이름과 가로 세로 넣어주면 알아서 레이블 만들어주는 메서
    public static JLabel setImageSize(String imgName, int width, int height) {
        ImageIcon icon = new ImageIcon(imgName);
        Image img = icon.getImage();
        Image changeImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon changeIcon = new ImageIcon(changeImg);
        return new JLabel(changeIcon);
    }

    // 번호선택(자동,수동) 후 이미지 바꾸기 메서드
    public static ArrayList<JLabel> imgChange(ArrayList<Integer> arr) {
        ArrayList<JLabel> imgLabel = new ArrayList<JLabel>();
        ArrayList<String> fileName = new ArrayList<String>();
        for(int i=0; i<arr.size(); i++) {
            String file = String.valueOf(arr.get(i));
            fileName.add(file + ".png");
        }
        for (int i=0; i<fileName.size(); i++) {
            imgLabel.add(setImageSize(fileName.get(i), 55, 50));
        }
        return imgLabel;
    }

    //결과창 비교해서  이미지바꾸기 메서드
    public static ArrayList<JLabel> imgChangeWhite(ArrayList<Integer> myNums, ArrayList<Integer> lottoNums) {
        ArrayList<JLabel> imgLabel = new ArrayList<JLabel>();
        ArrayList<String> fileName = new ArrayList<String>();
        for(int i=0; i<myNums.size(); i++) {
            String file = String.valueOf(myNums.get(i));
            if(!(lottoNums.contains(myNums.get(i)))) {
                file += "white";
            }
            fileName.add(file + ".png");
        }
        for (int i=0; i<fileName.size(); i++) {
            imgLabel.add(setImageSize(fileName.get(i), 55, 50));
        }
        return imgLabel;
    }
}