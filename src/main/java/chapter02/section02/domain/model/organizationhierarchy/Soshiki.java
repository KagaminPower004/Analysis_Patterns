package chapter02.section02.domain.model.organizationhierarchy;

import chapter02.section02.domain.model.organizationhierarchy.value_object.*;

import java.util.ArrayList;
import java.util.List;

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
public class Soshiki {
    private final List<Soshiki> list;
    private final KaisouLevel kaisouLevel;
    private final KaisouMei kaisouMei;
    private final SoshikiMei soshikiMei;

    //コンストラクタ
    public Soshiki(
             final int kaisouLevel
            ,final String kaisouMei
            ,final String soshikiMei
    ){
        this.kaisouLevel  = new KaisouLevel( kaisouLevel );
        this.kaisouMei    = new KaisouMei( kaisouMei );
        this.soshikiMei   = new SoshikiMei( soshikiMei );
        list = new ArrayList<>();

        //制約チェック(外だし)
        Judge_SoshikiMeiConstraint this_Soshiki
                = new Judge_SoshikiMeiConstraint( this.kaisouMei ,this.soshikiMei );
        if(this_Soshiki.isBadSoshikiMei())
        { throw new RuntimeException("組織名の追加の際にエラーが発生致しました。"); }
    }

    public void linking(Soshiki Soshiki ){
        final KaisouMei oya = this.kaisouMei;
        final KaisouMei ko  = new KaisouMei( Soshiki.kaisouMei_toString() );

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
        final String sout
                = new DrawThree( this.kaisouLevel.kaisouLevel()
                ,this.kaisouMei.kaisouMei()
                ,this.soshikiMei.soshikiMei()
        ).Draw();
        System.out.println(sout);

        for (final Soshiki listElement : list) {
            listElement.soshikiHyo();
        }
    }
}
