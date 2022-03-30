package chapter02.section02.domain.model.organizationhierarchy;

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

    String Draw(){
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
