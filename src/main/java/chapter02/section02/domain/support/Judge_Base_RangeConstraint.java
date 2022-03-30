package chapter02.section02.domain.support;

//『範囲制約』クラス(の、つもり。。。)
public class Judge_Base_RangeConstraint {
    private final Integer _max;
    private final Integer _minimum;
    private final Integer _value;

    //コンストラクタ
    public Judge_Base_RangeConstraint(Integer _max , Integer _minimum , Integer _value ){
        this._max = _max;
        this._minimum = _minimum;
        this._value = _value;
    }

    public Boolean isCollect(){

        if( this._value >= _max)    { return false; }
        if( this._value < _minimum) { return false; }
        else { return true; }
    }

    public Boolean isError(){ return ! this.isCollect(); }
}