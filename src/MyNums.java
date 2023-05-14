import java.util.*;

public class MyNums {

    private ArrayList<Integer> autoNums; // 자동숫자 배열
    private ArrayList<Integer> handNums; // 수동숫자 배열

    //생성자
    public MyNums(int num) {
        autoNums = autoSelect(num); // 숫자만 파라미터로 넣으면 자동으로 뽑아서 배열에 넣는 생성
    }
    public MyNums() {
        handNums = new ArrayList<Integer>(); // 수동배열 초기화 하는 생성자
    }

    //getter/setter
    public ArrayList<Integer> getHandNums() {
        return handNums;
    }

    public ArrayList<Integer> getAutoNums() {
        return autoNums;
    }
    // LottoNums생성할때 사용함
    public void setAutoNums(ArrayList<Integer> autoNums) {
        this.autoNums = autoNums;
    }

    @Override
    public String toString() {
        return autoNums.toString();
    }


    //LottoSelect에서 수동숫자배열에 추가 삭제를 편하게 하려
    public void addMyNum(int num) {
        handNums.add(num);
    }
    public void deleteMyNum() {
        handNums.clear();
    }


    // 내 자동 번호
    public ArrayList<Integer> autoSelect(int count) {
        Random r = new Random();
        HashSet<Integer> set = new HashSet<Integer>();

        while(set.size() < count) {
            set.add(r.nextInt(45) + 1);
        }
        // 정렬해야 되기 때문에 일반 배열 -> ArrayList로 바꿈
        Integer[] nums = set.toArray(new Integer[0]);
        Arrays.sort(nums, 0, 6);
        autoNums = new ArrayList<>();
        for(Integer num : nums) {
            autoNums.add(num);
        }
        return autoNums;
    }
}


