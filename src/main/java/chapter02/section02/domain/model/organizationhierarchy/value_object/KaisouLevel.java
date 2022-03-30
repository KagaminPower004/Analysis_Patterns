package chapter02.section02.domain.model.organizationhierarchy.value_object;

import chapter02.section02.domain.support.Judge_Base_RangeConstraint;

//『階層レベル』値オブジェクト
public record KaisouLevel( Integer kaisouLevel ) {
    private static final int MAX_Charactor = 20;
    private static final int MIN_Charactor = 1;

    //コンストラクタ
    public KaisouLevel( Integer kaisouLevel ){
        this.kaisouLevel = kaisouLevel;

        //レベル数値の範囲チェック
        Judge_Base_RangeConstraint this_LevelValue
                = new Judge_Base_RangeConstraint( MAX_Charactor ,MIN_Charactor ,kaisouLevel );
        if(this_LevelValue.isError())
        { throw new RuntimeException("設定した階層レベルは範囲内の数値ではございません。:[" + kaisouLevel + "]");}
    }
}
