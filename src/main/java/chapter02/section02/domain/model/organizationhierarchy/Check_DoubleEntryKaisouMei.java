package chapter02.section02.domain.model.organizationhierarchy;

import chapter02.section02.domain.model.organizationhierarchy.value_object.*;
import chapter02.section02.domain.support.base.Check_Base_DoubleEntry;

//『階層名二重登録チェック』クラス
class Check_DoubleEntryKaisouMei{

    private final KaisouMei oyaKaisouMei;
    private final KaisouMei kaisouMei;

    //コンストラクタ
    Check_DoubleEntryKaisouMei(
              final KaisouMei oyaKaisouMei
            , final KaisouMei kaisouMei
    ){
        this.oyaKaisouMei = oyaKaisouMei;
        this.kaisouMei    =    kaisouMei;
    }

    Boolean isDouble(){
        final String checkKey = "親：" + oyaKaisouMei.kaisouMei()
                            + "／子：" + kaisouMei.kaisouMei();
        //※マトリョーシカ//重複チェック
        return new Check_Base_DoubleEntry( checkKey ).isDouble();
    }
}
