package section02;

import java.util.*;

//※素早く作るために、
//とりま１ファイルに『一筆書き』で記述。
//その目的のために、
//下記空のクラスファイルを作成。
class Analysis_Patterns_Chp02_Sec02_Sample {}

//【名前】
//  組織クラス。
//【設計概要】
//  ①コンポジット・パターンの『コンポジット』のみで再帰的に構成。
//  ②『サブタイプ』は廃止し、『制約クラス』としてコンポジット自体からは外だし。
//  ③視認性向上のためにモデル記載外の簡易な描画クラスを追加。
//【主な目的】
//  登録済みの組織データから組織表を作成する。
//  また、登録済みの組織データの階層が、
//  正しいものであるのかをチェックする。
class Soshiki {
    private final List<Soshiki> list;
    private final KaisouLevel   kaisouLevel;
    private final KaisouMei     kaisouMei;
    private final SoshikiMei    soshikiMei;

    //コンストラクタ
    public Soshiki( int kaisouLevel ,String kaisouMei ,String soshikiMei ) {
        this.kaisouLevel  = new KaisouLevel( kaisouLevel );
        this.kaisouMei    = new KaisouMei( kaisouMei );
        this.soshikiMei   = new SoshikiMei( soshikiMei );
        list = new ArrayList<>();

        //制約チェック(外だし)
        Judge_SoshikiMeiConstraint this_Soshiki
                = new Judge_SoshikiMeiConstraint(this.kaisouMei ,this.soshikiMei);
        if(this_Soshiki.isBadSoshikiMei())
            { throw new RuntimeException("組織名の追加の際にエラーが発生致しました。"); }
    }

    public void add( Soshiki Soshiki ){
        KaisouMei oya = this.kaisouMei;
        KaisouMei ko  = new KaisouMei( Soshiki.kaisouMei_toString() );

        //制約チェック(外だし)
        Judge_KaisouConstraint this_Kaisou
                = new Judge_KaisouConstraint(oya ,ko);
        if(this_Kaisou.isBadKoKaisou())
            { throw new RuntimeException("組織階層の追加の際にエラーが発生致しました。"); }

        list.add( Soshiki );
    }

    public String kaisouMei_toString(){
        return this.kaisouMei.kaisouMei();
    }

    public void soshikiHyo(){
        //組織一覧を描画
        String sout
                = new DrawThree( this.kaisouLevel.kaisouLevel()
                                ,this.kaisouMei.kaisouMei()
                                ,this.soshikiMei.soshikiMei()
                                ).Draw();
        System.out.println(sout);

        for (Soshiki listElement : list) {
            listElement.soshikiHyo();
        }
    }
}

//『階層レベル』値オブジェクト
//以降、『レコード』クラスは
//すべて『値オブジェクト』(の、つもり。。。)
record KaisouLevel( Integer kaisouLevel ) {
    private static final int MAX_Charactor = 20;
    private static final int MIN_Charactor = 1;

    //コンストラクタ
    public KaisouLevel( Integer kaisouLevel ){
        this.kaisouLevel = kaisouLevel;

        //レベル数値の範囲チェック
        Judge_RangeConstraint this_LevelValue
                = new Judge_RangeConstraint( MAX_Charactor ,MIN_Charactor ,kaisouLevel );
        if(this_LevelValue.isError())
            { throw new RuntimeException("設定した階層レベルは範囲内の数値ではございません。:[" + kaisouLevel + "]");}
    }
}

//『階層名』値オブジェクト
record KaisouMei(String kaisouMei ) {
    private static final int MAX_Charactor = 50;
    private static final int MIN_Charactor = 1;

    //コンストラクタ
    public KaisouMei( String kaisouMei ){
        this.kaisouMei = kaisouMei;

        //文字数の範囲チェック
        Judge_RangeConstraint this_KaisouMei
                = new Judge_RangeConstraint( MAX_Charactor ,MIN_Charactor ,kaisouMei.length() );
        if(this_KaisouMei.isError())
            { throw new RuntimeException("設定した階層名は範囲内の文字数ではございません。:[" + kaisouMei + "]");}
    }
}

//『組織名』値オブジェクト
record SoshikiMei( String soshikiMei ) {
    private static final int MAX_Charactor = 30;
    private static final int MIN_Charactor = 1;

    //コンストラクタ
    public SoshikiMei( String soshikiMei ){
        this.soshikiMei = soshikiMei;

        //文字数の範囲チェック
        Judge_RangeConstraint this_soshikiMei
                = new Judge_RangeConstraint( MAX_Charactor ,MIN_Charactor ,soshikiMei.length() );
        if(this_soshikiMei.isError())
            { throw new RuntimeException("設定した組織名は範囲内の文字数ではございません。:[" + soshikiMei + "]");}
    }
}

//『範囲制約』クラス(の、つもり。。。)
class Judge_RangeConstraint {
    private final Integer _max;
    private final Integer _minimum;
    private final Integer _value;

    //コンストラクタ
    public Judge_RangeConstraint( Integer _max ,Integer _minimum ,Integer _value ){
        this._max = _max;
        this._minimum = _minimum;
        this._value = _value;
    }

    public Boolean isCollect(){

        if( this._value >= _max)    { return false; }
        if( this._value < _minimum) { return false; }
        else { return true; }
    }

    public Boolean isError(){ return ! this.isCollect(); }
}

//『階層制約』クラス(の、つもり。。。)
class Judge_KaisouConstraint {

    private final KaisouMei oyaKaisouMei;
    private final KaisouMei kaisouMei;

    //コンストラクタ
    Judge_KaisouConstraint( KaisouMei oyaKaisouMei ,KaisouMei kaisouMei ) {
        this.oyaKaisouMei   = oyaKaisouMei;
        this.kaisouMei      = kaisouMei;
    }

    public Boolean isKoKaisou(){

        //内容チェック
        if(this.isBadContents()) { return false; }

        //二重登録チェック
        if(this.isDouble())      { return false; }

        return true;
    }
    public Boolean isBadKoKaisou(){
        return ! this.isKoKaisou();
    }

    private Boolean isContents(){
        //内容チェック
        return new Check_KaisouContents(this.oyaKaisouMei ,this.kaisouMei)
                .isContents();
    }
    private Boolean isBadContents(){ return ! this.isContents(); }

    private Boolean isDouble(){
        //二重登録チェック
        return new Check_DoubleEntryKaisouMei(this.oyaKaisouMei ,this.kaisouMei)
                .isDouble();
    }
}

//『階層制約専用エラーメッセージ出力』クラス
class SysOutErrorMessage_Judge_KaisouConstraint {

    //コンストラクタ
    SysOutErrorMessage_Judge_KaisouConstraint( String kaisouMeiMessage ) {
        throw new RuntimeException( kaisouMeiMessage ) ;
    }
}

//『階層内容チェック』クラス
class Check_KaisouContents{

    private final KaisouMei oyaKaisouMei;
    private final KaisouMei kaisouMei;

    //コンストラクタ
    Check_KaisouContents( KaisouMei oyaKaisouMei ,KaisouMei kaisouMei ){
        this.oyaKaisouMei = oyaKaisouMei;
        this.kaisouMei    =    kaisouMei;
    }

    protected Boolean isContents(){

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

//『二重登録チェック基底部』クラス
class Check_Base_DoubleEntry {

    private final String checkKey;
    private static final List<String> entryList
            = new ArrayList<>();

    //コンストラクタ
    Check_Base_DoubleEntry( String checkKey ){
        this.checkKey = checkKey;
    }

    protected Boolean isDouble(){

        //キー登録
        keySet(checkKey);

        //重複チェック(※けっきょく楽なんでStreamで逃げた)
        if(entryList
                .stream()
                .filter(allList -> allList.equals(checkKey))
                .count() == 2
        )
        {
            new SysOutErrorMessage_Judge_KaisouConstraint(
                    "二重登録違反:" + checkKey
            );
            return true;
        }
        else
        { return false; }
    }
    private void keySet( String newKey ){
        entryList.add( newKey );
    }
}

//『階層名二重登録チェック』クラス
class Check_DoubleEntryKaisouMei{

    private final KaisouMei oyaKaisouMei;
    private final KaisouMei kaisouMei;

    //コンストラクタ
    Check_DoubleEntryKaisouMei( KaisouMei oyaKaisouMei ,KaisouMei kaisouMei ){
        this.oyaKaisouMei = oyaKaisouMei;
        this.kaisouMei    =    kaisouMei;
    }

    protected Boolean isDouble(){
        String checkKey = "親：" + oyaKaisouMei.kaisouMei()
                       + "／子：" + kaisouMei.kaisouMei();
        //重複チェック
        return new Check_Base_DoubleEntry( checkKey ).isDouble();
    }
}

//『組織名制約』クラス(の、つもり。。。)
class Judge_SoshikiMeiConstraint {
    private final KaisouMei kaisouMei;
    private final SoshikiMei soshikiMei;

    //コンストラクタ
    public Judge_SoshikiMeiConstraint( KaisouMei kaisouMei ,SoshikiMei soshikiMei ){
        this.kaisouMei = kaisouMei;
        this.soshikiMei = soshikiMei;
    }

    public Boolean isSoshikiMei() {

        //内容チェック
        if(this.isBadContents()) { return false; }

        //二重登録チェック
        if(this.isDouble())      { return false; }

        //最終結果
        return true;
    }

    public Boolean isBadSoshikiMei() {
        return !this.isSoshikiMei();
    }

    private Boolean isContents() {

        //内容チェック
        return new Check_SoshikiMeiContents(this.kaisouMei, this.soshikiMei)
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

//『組織名内容チェック』クラス
class Check_SoshikiMeiContents {

    private final KaisouMei kaisouMei;
    private final SoshikiMei soshikiMei;

    //コンストラクタ
    public Check_SoshikiMeiContents( KaisouMei kaisouMei ,SoshikiMei soshikiMei ){
        this.kaisouMei = kaisouMei;
        this.soshikiMei = soshikiMei;
    }

    protected Boolean isContents() {

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

//『組織名二重登録チェック』クラス
class Check_DoubleEntrySoshikiMei {

    private final KaisouMei kaisouMei;
    private final SoshikiMei soshikiMei;

    //コンストラクタ
    public Check_DoubleEntrySoshikiMei( KaisouMei kaisouMei ,SoshikiMei soshikiMei ){
        this.kaisouMei = kaisouMei;
        this.soshikiMei = soshikiMei;
    }

    protected Boolean isDouble() {
        String checkKey = kaisouMei.kaisouMei()
                 + "／" + soshikiMei.soshikiMei();
        //重複チェック
        return new Check_Base_DoubleEntry( checkKey ).isDouble();
    }
}

//『組織名制約専用エラーメッセージ出力』クラス
class SysOutErrorMessage_Judge_SoshikiMeiConstraint {

    //コンストラクタ
    SysOutErrorMessage_Judge_SoshikiMeiConstraint( String SoshikiMeiMeiMessage ) {
        throw new RuntimeException( SoshikiMeiMeiMessage ) ;
    }
}

//『地域名』値オブジェクト
record TiikiMei( String tiikiMei ) {
    private static final int MAX_Charactor = 30;
    private static final int MIN_Charactor = 1;

    //コンストラクタ
    public TiikiMei( String tiikiMei ){
        this.tiikiMei = tiikiMei;

        //文字数範囲チェック
        Judge_RangeConstraint this_tiikiMei
                = new Judge_RangeConstraint( MAX_Charactor ,MIN_Charactor ,tiikiMei.length() );
        if(this_tiikiMei.isError())
            { throw new RuntimeException("設定した地域名は範囲内の文字数ではございません。:[" + tiikiMei + "]");}

        //地域名の内容チェック
        Judge_TiikiMeiConstraint this_tiikiMei_naiyo
                = new Judge_TiikiMeiConstraint( tiikiMei );
        if(this_tiikiMei_naiyo.isError())
            { throw new RuntimeException("設定した地域名に該当する地域はございません。:[" + tiikiMei + "]");}
    }
}

//『地域名制約』クラス(の、つもり。。。)
class Judge_TiikiMeiConstraint {
    String tiikiMei;

    //コンストラクタ
    Judge_TiikiMeiConstraint( String tiikiMei ){ this.tiikiMei = tiikiMei; }

    public boolean isCollect(){

        return switch (tiikiMei) {
            case "北海道" -> true;
            case "東北" -> true;
            case "北陸" -> true;
            case "北関東" -> true;
            case "南関東" -> true;
            default -> false;
        };
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

    //コンストラクタ
    DrawThree( Integer level ,String KaisouMei ,String soshikiMei ){
        this.level = level;
        this.KaisouMei = KaisouMei;
        this.soshikiMei = soshikiMei;
    }

    public String Draw(){
        String Branch = null;
        String leaf = KaisouMei + ":" + soshikiMei;
        String root = soshikiMei;

        switch (level){
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
        JigyouBu.add(Tiiki);
        Tiiki.add(Bumon);
        Bumon.add(EigyouSho);

        //組織表描画
        JigyouBu.soshikiHyo();

        //新たに『サービスセンター』を追加
        Tiiki.add(ServiceCenter);
        ServiceCenter.add(EigyouSho);
//        ServiceCenter.add(EigyouSho); //『＜階層名+組織名＞の親子の組み合わせ』二重違反データ
//        EigyouSho.add(Bumon); //『親子階層』制約違反エラー

        //組織表描画(追加)
        ServiceCenter.soshikiHyo();
    }
}