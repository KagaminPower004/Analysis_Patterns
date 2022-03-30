package chapter02.section02.domain.support;

//『エラーメッセージ出力基底部』クラス
public class SysOutError_Base_Message {

    //コンストラクタ
    public SysOutError_Base_Message(String kaisouMeiMessage ) {
        throw new RuntimeException( kaisouMeiMessage ) ;
    }
}
