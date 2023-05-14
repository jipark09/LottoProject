import java.util.ArrayList;
import java.util.Collections;

public class LottoNums extends MyNums {
    //보너스배열은 배열에서 6번째니깐
    public static final int BONUS = 6;
    //몇등 or 낙첨  결과
    private String result;

    public LottoNums() {
        setAutoNums(autoSelect(7));
    }

    // 보너스 번호 뽑을 수 있는 메서드
    public Integer getBonusNum() {
        return getAutoNums().get(BONUS);
    }

    //파라미터의 두 번호배열을 비교하고 교집합의 개수로 당첨을 확인함.
    public String showResult(ArrayList<Integer> myNums, ArrayList<Integer> lottoNums) {

        ArrayList<Integer> myList = new ArrayList<>(myNums); // 교집합하고 다 지울려고 복사

        // 로또 6개 뽑기 (보너스 빼고)
        ArrayList<Integer> lottoList = new ArrayList<>(lottoNums.subList(0, 6));

        myList.retainAll(lottoList); // lottoList와S 교집합

        int count = myList.size();
        int rank = 0;

        switch(count) {
            case 6:
                rank = 1;
                break;

            case 5:
                // 5개 맞을경우 보너스 번호 비교
                int bonusIdx = Collections.binarySearch(
                        myNums, getBonusNum()
                );
                if (bonusIdx >= 0) {
                    rank = 2;
                } else {
                    rank = 3;
                }
                break;

            case 4:
                rank = 4;
                break;

            case 3:
                rank = 5;
                break;
        }
        // 번호배열 뒤에 텍스트 넣으려고!

        if(rank == 0) {
            result = "낙첨";
        } else {
            result = rank + "등";
        }
        return result;
    }
}