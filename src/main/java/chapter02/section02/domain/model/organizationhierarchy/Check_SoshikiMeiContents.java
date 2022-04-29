package chapter02.section02.domain.model.organizationhierarchy;

import chapter02.section02.domain.model.organizationhierarchy.value_object.*;
import chapter02.section02.domain.model.area.value_object.TiikiMei;

//『組織名内容チェック』クラス
class Check_SoshikiMeiContents {

    private final KaisouMei kaisouMei;
    private final SoshikiMei soshikiMei;

    //コンストラクタ
    Check_SoshikiMeiContents(
              final KaisouMei kaisouMei
            , final SoshikiMei soshikiMei
    ){
        this.kaisouMei = kaisouMei;
        this.soshikiMei = soshikiMei;
    }

    Boolean isContents() {

        switch (kaisouMei.kaisouMei()) {

            case "地域" -> {

                //『地域名』オブジェクトに例外処理を丸投げ
                try {
                    new TiikiMei( soshikiMei.soshikiMei() );
                }
                catch (Exception e) {
                    new SysOutErrorMessage_Judge_SoshikiMeiConstraint( e.getMessage() );
                    return false;
                }

                return true;
            }
            default -> {
                return true;
            }
        }
    }
}
