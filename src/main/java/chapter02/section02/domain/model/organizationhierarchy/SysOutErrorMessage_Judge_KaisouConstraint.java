package chapter02.section02.domain.model.organizationhierarchy;

import chapter02.section02.domain.support.base.SysOutError_Base_Message;

//『階層制約専用エラーメッセージ出力』クラス
class SysOutErrorMessage_Judge_KaisouConstraint {

    //コンストラクタ
    SysOutErrorMessage_Judge_KaisouConstraint( final String kaisouMeiMessage )
    {
        //※マトリョーシカ
        new SysOutError_Base_Message( kaisouMeiMessage );
    }
}
