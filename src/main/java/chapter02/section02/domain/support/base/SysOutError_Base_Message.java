package chapter02.section02.domain.support.base;

import chapter02.section02.domain.support.value_object.SysOutErrorMessage;

//『エラーメッセージ出力基底部』クラス
public class SysOutError_Base_Message {

    //コンストラクタ
    public SysOutError_Base_Message(final String sysOutErrorMeiMessage )
    {
        //値オブジェクトにてメッセージ内容のチェック
        final SysOutErrorMessage this_SysOutErrorMessage
                = new SysOutErrorMessage( sysOutErrorMeiMessage );

        final String e_Message = this_SysOutErrorMessage.SysOutErrorMessage();
        throw new RuntimeException( e_Message ) ;
    }
}
