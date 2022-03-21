package chapter02.section02;

import javax.management.ConstructorParameters;
import java.util.*;

//※素早く作りために、とりま１ファイルに『一筆書き』で記述。
//その目的のために下記空のクラスファイルを作成。
class Analysis_Patterns_Chp02_Sec02_Sample {}

//組織クラス。コンポジット・パターンの『コンポジット』のみで再帰的に構成。
//【主な目的】
//登録済みの組織データから組織表を作成する。
//また、登録済みの組織データの階層が、正しいものであるのかをチェックする。
class Soshiki {
    private final List<Soshiki> list;
    private final KaisouLevel   kaisouLevel;
    private final KaisouMei     kaisouMei;
    private final SoshikiMei    soshikiMei;

    @ConstructorParameters({"kaisouLevel", "kaisouMei", "soshikiMei"})
    public Soshiki(
             int  kaisouLevel
            ,String kaisouMei
            ,String soshikiMei)
    {
        this.kaisouLevel  = new KaisouLevel(kaisouLevel);
        this.kaisouMei    = new KaisouMei(kaisouMei);
        this.soshikiMei   = new SoshikiMei(soshikiMei);
        list = new ArrayList<>();

        //制約チェック(外だし)
        Judge_SoshikiMeiConstraint this_Soshiki
                = new Judge_SoshikiMeiConstraint(this.kaisouMei,this.soshikiMei);
        if(this_Soshiki.isBadSoshikiMei()){ throw new RuntimeException("組織名が正しい値ではございません。"); }
    }

    public void add(Soshiki Soshiki){
        KaisouMei oya = this.kaisouMei;
        KaisouMei ko  = new KaisouMei(Soshiki.kaisouMei_toString());

        //制約チェック(外だし)
        Judge_KaisouConstraint this_Kaisou = new Judge_KaisouConstraint( oya ,ko);
        if(this_Kaisou.isBadKoKaisou()){ throw new RuntimeException("正しい子階層の値ではございません。"); }
        list.add(Soshiki);
    }

    public String kaisouMei_toString(){ return this.kaisouMei.kaisouMei();}

    public void soshikiHyo(){
        //組織一覧を描画
        String sout = new DrawThree(this.kaisouLevel.kaisouLevel()
                                    ,this.kaisouMei.kaisouMei()
                                    ,this.soshikiMei.soshikiMei())
                            .Draw();
        System.out.println(sout);

        for (chapter02.section02.Soshiki Soshiki : list) {
            Soshiki.soshikiHyo();
        }
    }
}

//『階層レベル』値オブジェクト
//以降、『レコード』クラスはすべて『値オブジェクト』(の、つもり。。。)
record KaisouLevel( Integer kaisouLevel ) {
    private static final int MAX_Charactor = 20;
    private static final int MIN_Charactor = 1;

    public KaisouLevel( Integer kaisouLevel ){
        this.kaisouLevel = kaisouLevel;

        //レベル数値の範囲チェック
        Judge_RangeConstraint this_LevelValue
                = new Judge_RangeConstraint(MAX_Charactor, MIN_Charactor, kaisouLevel);
        if(this_LevelValue.isError()){ throw new RuntimeException("設定した階層レベルは範囲内の数値ではございません。");}
    }
}

//『階層名』値オブジェクト
record KaisouMei(String kaisouMei ) {
    private static final int MAX_Charactor = 50;
    private static final int MIN_Charactor = 1;

    public KaisouMei( String kaisouMei ){
        this.kaisouMei = kaisouMei;

        //文字数の範囲チェック
        Judge_RangeConstraint this_KaisouMei
                = new Judge_RangeConstraint(MAX_Charactor, MIN_Charactor, kaisouMei.length());
        if(this_KaisouMei.isError()){ throw new RuntimeException("設定した階層名は範囲内の文字数ではございません。");}
    }
}

//『組織名』値オブジェクト
record SoshikiMei( String soshikiMei ) {
    private static final int MAX_Charactor = 30;
    private static final int MIN_Charactor = 1;

    public SoshikiMei( String soshikiMei ){
        this.soshikiMei = soshikiMei;

        //文字数の範囲チェック
        Judge_RangeConstraint this_soshikiMei
                = new Judge_RangeConstraint(MAX_Charactor, MIN_Charactor, soshikiMei.length());
        if(this_soshikiMei.isError()){ throw new RuntimeException("設定した組織名は範囲内の文字数ではございません。");}
    }
}

//『範囲制約』クラス(の、つもり。。。)
class Judge_RangeConstraint {
    private final Integer _max;
    private final Integer _minimum;
    private final Integer _value;

    public Judge_RangeConstraint(Integer _max, Integer _minimum, Integer _value){
        this._max = _max;
        this._minimum = _minimum;
        this._value = _value;
    }

    public Boolean isCollect(){

        if( this._value >= _max)    { return false; }
        if( this._value < _minimum) { return false; }
        else { return true; }
    }

    public Boolean isError(){
        return ! this.isCollect();
    }
}

//『階層制約』クラス(の、つもり。。。)
class Judge_KaisouConstraint {

    private final KaisouMei oyaKaisouMei;
    private final KaisouMei kaisouMei;

    Judge_KaisouConstraint(KaisouMei oyaKaisouMei , KaisouMei kaisouMei) {
        this.oyaKaisouMei   = oyaKaisouMei;
        this.kaisouMei      = kaisouMei;
    }

    public Boolean isKoKaisou(){
        boolean result = false;

        switch (kaisouMei.kaisouMei()) {
            case "事業部" -> {
                if (oyaKaisouMei.kaisouMei() == "") {
                    result = true;
                }
            }
            case "地域" -> {
                if (oyaKaisouMei.kaisouMei() == "事業部") {
                    result = true;
                }
            }
            case "部門", "サービスセンター" -> {
                if (oyaKaisouMei.kaisouMei() == "地域") {
                    result = true;
                }
            }
            case "営業所" -> {
                if (oyaKaisouMei.kaisouMei() == "部門") {
                    result = true;
                }
                if (oyaKaisouMei.kaisouMei() == "サービスセンター") {
                    result = true;
                }
            }
        }
        return result;
    }

    public Boolean isBadKoKaisou(){
        return ! this.isKoKaisou();
    }

}

//『組織名称制約』クラス(の、つもり。。。)
class Judge_SoshikiMeiConstraint {
    private final KaisouMei  kaisouMei;
    private final SoshikiMei soshikiMei;
    private static Map<String,String> soshikiMeiList = new HashMap<>();

    public Judge_SoshikiMeiConstraint(KaisouMei kaisouMei, SoshikiMei soshikiMei){
        this.kaisouMei  = kaisouMei;
        this.soshikiMei = soshikiMei;
    }

    public Boolean isSoshikiMei(){
        boolean result;

        switch (kaisouMei.kaisouMei()) {
            case "地域" -> {
                new TiikiMei(soshikiMei.soshikiMei());
                result = true;
            }
            default -> result = true;
        }

        //二重登録チェック
        if(this.isDouble()){ result = false; }

        return result;
    }
    private Boolean isDouble(){

        //重複チェック
        String checkKey = kaisouMei.kaisouMei() + soshikiMei.soshikiMei();
        if(soshikiMeiList.get(checkKey) == null){ keySet(checkKey); return false;}
        else { return true; }
    }
    private void keySet(String newKey){
        soshikiMeiList.put(newKey,newKey);
    }

    public Boolean isBadSoshikiMei(){
        return ! this.isSoshikiMei();
    }
}

//『地域名』値オブジェクト
record TiikiMei(String tiikiMei) {
    private static final int MAX_Charactor = 30;
    private static final int MIN_Charactor = 1;

    public TiikiMei(String tiikiMei){
        this.tiikiMei = tiikiMei;

        //文字数範囲チェック
        Judge_RangeConstraint this_tiikiMei
                = new Judge_RangeConstraint(MAX_Charactor, MIN_Charactor, tiikiMei.length());
        if(this_tiikiMei.isError()) { throw new RuntimeException("設定した地域名は範囲内の文字数ではございません。");}

        //地域名の内容チェック
        Judge_TiikiMeiConstraint this_tiikiMei_naiyo
                = new Judge_TiikiMeiConstraint(tiikiMei);
        if(this_tiikiMei_naiyo.isError()){ throw new RuntimeException("設定した地域名は正しい名称ではありません。");}
    }
}

//『地域名制約』クラス(の、つもり。。。)
class Judge_TiikiMeiConstraint {
    String tiikiMei;

    Judge_TiikiMeiConstraint(String tiikiMei){ this.tiikiMei = tiikiMei; }

    public boolean isCollect(){
        boolean result = switch (tiikiMei) {
            case "北海道" -> true;
            case "東北" -> true;
            case "北陸" -> true;
            case "北関東" -> true;
            case "南関東" -> true;
            default -> false;
        };

        return result;
    }

    public Boolean isError(){
        return ! this.isCollect();
    }
}

//『樹形図描画』クラス
class DrawThree {

    private final int    level;
    private final String KaisouMei;
    private final String soshikiMei;

    DrawThree(Integer level, String KaisouMei, String soshikiMei){
        this.level = level;
        this.KaisouMei = KaisouMei;
        this.soshikiMei = soshikiMei;
    }

    public String Draw(){
        String Branch = null;
        String leaf = KaisouMei + ":" + soshikiMei;
        String root = soshikiMei;

        switch (level) {
            case 1 -> Branch = root;
            case 2 -> Branch = "\t" + leaf;
            case 3 -> Branch = "\t" + "\t" + leaf;
            case 4 -> Branch = "\t" + "\t" + "\t" + leaf;
        }
        return  Branch;
    }
}

//『アクター：業務担当者』クラス
class Actor_GyoumuTantoSha {
    public static void main(String[] args){

        //組織データ登録
        Soshiki JigyouBu      = new Soshiki(1,"事業部","IoT事業部");
        Soshiki Tiiki         = new Soshiki(2,"地域","北陸");
        Soshiki Bumon         = new Soshiki(3,"部門","第一営業部");
        Soshiki ServiceCenter = new Soshiki(3,"サービスセンター","北陸サービスセンター");
        Soshiki EigyouSho     = new Soshiki(4,"営業所","北陸第一営業所");

        //階層紐づけ作業
        JigyouBu.add(Tiiki);
        Tiiki.add(Bumon);
        Bumon.add(EigyouSho);

        //組織表描画
        JigyouBu.soshikiHyo();

        //新たに『サービスセンター』を追加
        Tiiki.add(ServiceCenter);
        ServiceCenter.add(EigyouSho);

        //組織表描画(追加)
        ServiceCenter.soshikiHyo();
    }
}