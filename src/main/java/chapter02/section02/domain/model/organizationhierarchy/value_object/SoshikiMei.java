package chapter02.section02.domain.model.organizationhierarchy.value_object;

import chapter02.section02.domain.support.base.Judge_Base_RangeConstraint;

//『組織名』値オブジェクト
public record SoshikiMei( String soshikiMei ) {
    private static final int MAX_Charactor = 30;
    private static final int MIN_Charactor = 1;

    //コンストラクタ
    public SoshikiMei( String soshikiMei ){
        this.soshikiMei = soshikiMei;

        //文字数の範囲チェック
        Judge_Base_RangeConstraint this_soshikiMei
                = new Judge_Base_RangeConstraint( MAX_Charactor ,MIN_Charactor ,soshikiMei.length() );
        if(this_soshikiMei.isError())
        { throw new RuntimeException("設定した組織名は範囲内の文字数ではございません。:[" + soshikiMei + "]");}
    }
}