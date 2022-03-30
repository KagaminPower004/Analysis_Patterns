package chapter02.section02.domain.support;

import java.util.ArrayList;
import java.util.List;

//『二重登録チェック基底部』クラス
public class Check_Base_DoubleEntry {

    private final String checkKey;
    private static final List<String> entryList
            = new ArrayList<>();

    //コンストラクタ
    public Check_Base_DoubleEntry( String checkKey ){
        this.checkKey = checkKey;
    }

    public Boolean isDouble(){

        //キー登録
        keySet(checkKey);

        //重複チェック(※けっきょく楽なんでStreamで逃げた)
        if(entryList
                .stream()
                .filter(allList -> allList.equals(checkKey))
                .count() == 2
        )
        {
            new SysOutError_Base_Message(
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
