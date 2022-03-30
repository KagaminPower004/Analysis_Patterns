package chapter02.section02.domain.model.organizationhierarchy;

import chapter02.section02.domain.model.organizationhierarchy.value_object.*;

//『組織名制約』クラス(の、つもり。。。)
class Judge_SoshikiMeiConstraint {
    private final KaisouMei kaisouMei;
    private final SoshikiMei soshikiMei;

    //コンストラクタ
    Judge_SoshikiMeiConstraint( KaisouMei kaisouMei , SoshikiMei soshikiMei ){
        this.kaisouMei = kaisouMei;
        this.soshikiMei = soshikiMei;
    }

    Boolean isSoshikiMei() {

        //内容チェック
        if(this.isBadContents()) { return false; }

        //二重登録チェック
        if(this.isDouble())      { return false; }

        //最終結果
        return true;
    }

    Boolean isBadSoshikiMei() {
        return !this.isSoshikiMei();
    }

    private Boolean isContents() {

        //内容チェック
        return new Check_SoshikiMeiContents(kaisouMei , soshikiMei)
                .isContents();
    }

    private Boolean isBadContents() {
        return !this.isContents();
    }

    private Boolean isDouble() {
        //二重登録チェック
        return new Check_DoubleEntrySoshikiMei(kaisouMei, soshikiMei)
                .isDouble();
    }
}
