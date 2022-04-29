package chapter02.section02.domain.model.organizationhierarchy;

import chapter02.section02.domain.model.organizationhierarchy.value_object.*;

//『階層内容チェック』クラス
class Check_KaisouContents{

    private final KaisouMei oyaKaisouMei;
    private final KaisouMei kaisouMei;

    //コンストラクタ
    Check_KaisouContents(
              final KaisouMei oyaKaisouMei
            , final KaisouMei kaisouMei
    ){
        this.oyaKaisouMei = oyaKaisouMei;
        this.kaisouMei    =    kaisouMei;
    }

    Boolean isContents(){

        //内容チェック
        switch (kaisouMei.kaisouMei()) {
            case "事業部" -> {
                if (oyaKaisouMei.kaisouMei().equals("") ) {
                    return true;
                }
            }
            case "地域" -> {
                if (oyaKaisouMei.kaisouMei().equals("事業部") ) {
                    return true;
                }
            }
            case "部門", "サービスセンター" -> {
                if (oyaKaisouMei.kaisouMei().equals("地域") ) {
                    return true;
                }
            }
            case "営業所" -> {
                if (oyaKaisouMei.kaisouMei().equals("部門") ) {
                    return true;
                }
                if (oyaKaisouMei.kaisouMei().equals("サービスセンター") ) {
                    return true;
                }
            }
        }

        new SysOutErrorMessage_Judge_KaisouConstraint(
                "正しい子階層の値ではございません。:"
                        + "親:" + oyaKaisouMei.kaisouMei()
                        + "／"
                        + "子:" + kaisouMei.kaisouMei()
        );

        return false;
    }
}
