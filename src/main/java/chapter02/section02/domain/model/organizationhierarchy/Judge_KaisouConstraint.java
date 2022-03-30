package chapter02.section02.domain.model.organizationhierarchy;

import chapter02.section02.domain.model.organizationhierarchy.value_object.*;

//『階層制約』クラス(の、つもり。。。)
class Judge_KaisouConstraint {

    private final KaisouMei oyaKaisouMei;
    private final KaisouMei kaisouMei;

    //コンストラクタ
    Judge_KaisouConstraint( KaisouMei oyaKaisouMei , KaisouMei kaisouMei ) {
        this.oyaKaisouMei   = oyaKaisouMei;
        this.kaisouMei      = kaisouMei;
    }

    Boolean isKoKaisou(){

        //内容チェック
        if(this.isBadContents()) { return false; }

        //二重登録チェック
        if(this.isDouble())      { return false; }

        return true;
    }
    Boolean isBadKoKaisou(){
        return ! this.isKoKaisou();
    }

    private Boolean isContents(){
        //内容チェック
        return new Check_KaisouContents(oyaKaisouMei ,kaisouMei)
                .isContents();
    }
    private Boolean isBadContents(){ return ! this.isContents(); }

    private Boolean isDouble(){
        //二重登録チェック
        return new Check_DoubleEntryKaisouMei(oyaKaisouMei ,kaisouMei)
                .isDouble();
    }
}
