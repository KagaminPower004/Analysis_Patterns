package chapter02.section02.domain.support.value_object;

import chapter02.section02.domain.support.Judge_Base_RangeConstraint;

//『階層レベル』値オブジェクト
public record SysOutErrorMessage( String SysOutErrorMessage) {
    private static final int MAX_Charactor = 10000;
    private static final int MIN_Charactor = 1;

    //コンストラクタ
    public SysOutErrorMessage( String SysOutErrorMessage){
        this.SysOutErrorMessage = SysOutErrorMessage;

        //レベル数値の範囲チェック
        Judge_Base_RangeConstraint this_SysOutErrorMessage
                = new Judge_Base_RangeConstraint( MAX_Charactor ,MIN_Charactor , SysOutErrorMessage.length() );
        if(this_SysOutErrorMessage.isError())
        { throw new RuntimeException("設定したエラーメッセージは範囲内の文字数ではございません。:[" + SysOutErrorMessage + "]");}
    }
}
