package chapter02.section02.domain.model.organizationhierarchy.value_object;

import chapter02.section02.domain.support.base.Judge_Base_RangeConstraint;

//『階層名』値オブジェクト
public record KaisouMei(String kaisouMei ) {
    private static final int MAX_Charactor = 50;
    private static final int MIN_Charactor = 1;

    //コンストラクタ
    public KaisouMei( String kaisouMei ){
        this.kaisouMei = kaisouMei;

        //文字数の範囲チェック
        Judge_Base_RangeConstraint this_KaisouMei
                = new Judge_Base_RangeConstraint( MAX_Charactor ,MIN_Charactor ,kaisouMei.length() );
        if(this_KaisouMei.isError())
        { throw new RuntimeException("設定した階層名は範囲内の文字数ではございません。:[" + kaisouMei + "]");}
    }
}
