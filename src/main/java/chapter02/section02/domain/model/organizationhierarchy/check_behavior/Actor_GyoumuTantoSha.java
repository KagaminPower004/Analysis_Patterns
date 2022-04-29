package chapter02.section02.domain.model.organizationhierarchy.check_behavior;

import chapter02.section02.domain.model.organizationhierarchy.Soshiki;

//『アクター：業務担当者』クラス
class Actor_GyoumuTantoSha {
    public static void main( String[] args ){

        //組織データ登録
        Soshiki JigyouBu      = new Soshiki(1,"事業部","IoT事業部");
        Soshiki Tiiki         = new Soshiki(2,"地域","北陸");
        Soshiki Bumon         = new Soshiki(3,"部門","第一営業部");
        Soshiki ServiceCenter = new Soshiki(3,"サービスセンター","北陸サービスセンター");
        Soshiki EigyouSho     = new Soshiki(4,"営業所","北陸第一営業所");
//        Soshiki EigyouSho2     = new Soshiki(4,"営業所","北陸第一営業所"); //『階層名 + 組織名』の二重登録違反データ
//        Soshiki EigyouSho3     = new Soshiki(4,"営業所",""); //範囲外データ(少)
//        Soshiki EigyouSho4     = new Soshiki(4,"営業所","北陸第一営業所ショアアアアアアアアアアアアアアアアアア！！！"); //範囲外データ(多)
//        Soshiki EigyouSho4     = new Soshiki(999,"宿営所","北陸『これからの星』宿営所"); //範囲外データ(多)

        //階層紐づけ作業
        JigyouBu.linking(Tiiki);
        Tiiki.linking(Bumon);
        Bumon.linking(EigyouSho);

        //組織表描画
        JigyouBu.soshikiHyo();

        //新たに『サービスセンター』を追加
        Tiiki.linking(ServiceCenter);
        ServiceCenter.linking(EigyouSho);
//        ServiceCenter.linking(EigyouSho); //『＜階層名+組織名＞の親子の組み合わせ』二重違反データ
//        EigyouSho.linking(Bumon); //『親子階層』制約違反エラー

        //組織表描画(追加)
        ServiceCenter.soshikiHyo();

        System.out.println("---------------------------------------------------------");
        //組織表描画(まとめてすべて)
        JigyouBu.soshikiHyo();
    }
}
