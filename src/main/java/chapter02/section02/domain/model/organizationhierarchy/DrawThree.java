package chapter02.section02.domain.model.organizationhierarchy;

//『樹形図描画』クラス
class DrawThree {

    private final int    level;
    private final String KaisouMei;
    private final String soshikiMei;

    //コンストラクタ
    DrawThree(
             final Integer level
            ,final String KaisouMei
            ,final String soshikiMei
    ){
        this.level = level;
        this.KaisouMei = KaisouMei;
        this.soshikiMei = soshikiMei;
    }

    String Draw(){
        final String leaf = KaisouMei + ":" + soshikiMei;
        final String root = soshikiMei;

        String Branch = null;
        switch (level){
            case 1 -> Branch = root;
            case 2 -> Branch = "\t" + leaf;
            case 3 -> Branch = "\t" + "\t" + leaf;
            case 4 -> Branch = "\t" + "\t" + "\t" + leaf;
        }
        return  Branch;
    }
}
