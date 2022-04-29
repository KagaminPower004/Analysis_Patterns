package chapter02.section02.domain.model.area.value_object;

import chapter02.section02.domain.support.base.Judge_Base_RangeConstraint;

//『地域名』値オブジェクト
public record TiikiMei(String tiikiMei ) {
    private static final int MAX_Charactor = 30;
    private static final int MIN_Charactor = 1;

    //コンストラクタ
    public TiikiMei(final String tiikiMei ){
        this.tiikiMei = tiikiMei;

        //文字数範囲チェック
        final Judge_Base_RangeConstraint this_tiikiMei
                = new Judge_Base_RangeConstraint( MAX_Charactor ,MIN_Charactor ,tiikiMei.length() );
        if(this_tiikiMei.isError())
        { throw new RuntimeException("設定した地域名は範囲内の文字数ではございません。:[" + tiikiMei + "]");}

        //地域名の内容チェック
        final Judge_TiikiMeiConstraint this_tiikiMei_naiyo
                = new Judge_TiikiMeiConstraint( tiikiMei );
        if(this_tiikiMei_naiyo.isError())
        { throw new RuntimeException("設定した地域名に該当する地域はございません。:[" + tiikiMei + "]");}
    }
}
