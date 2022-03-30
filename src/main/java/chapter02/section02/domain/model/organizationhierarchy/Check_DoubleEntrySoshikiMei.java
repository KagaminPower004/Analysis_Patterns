package chapter02.section02.domain.model.organizationhierarchy;

import chapter02.section02.domain.model.organizationhierarchy.value_object.*;
import chapter02.section02.domain.support.Check_Base_DoubleEntry;

//『組織名二重登録チェック』クラス
class Check_DoubleEntrySoshikiMei {

    private final KaisouMei kaisouMei;
    private final SoshikiMei soshikiMei;

    //コンストラクタ
    Check_DoubleEntrySoshikiMei(KaisouMei kaisouMei , SoshikiMei soshikiMei ){
        this.kaisouMei = kaisouMei;
        this.soshikiMei = soshikiMei;
    }

    Boolean isDouble() {
        String checkKey = kaisouMei.kaisouMei()
                + "／" + soshikiMei.soshikiMei();
        //※マトリョーシカ//重複チェック
        return new Check_Base_DoubleEntry( checkKey ).isDouble();
    }
}
