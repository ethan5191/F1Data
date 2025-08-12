package packets.enums;

//0 is the F1 driver lineup for the game year.
//1 is the F2 driver lineup for the game year.
//2 is the F2 previous year driver lineup for the game year.
//for F1 24 (0 is the F1 lineup, 1 is the F2 '24 lineup, 2 is the F2 '23 lineup).
public enum FormulaTypeEnum {

    F1(0),
    F2(1),
    F2_PREV(2);

    private final int index;

    FormulaTypeEnum(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public static boolean isF1(FormulaTypeEnum formulaTypeEnum) {
        return FormulaTypeEnum.F1.equals(formulaTypeEnum);
    }
}
