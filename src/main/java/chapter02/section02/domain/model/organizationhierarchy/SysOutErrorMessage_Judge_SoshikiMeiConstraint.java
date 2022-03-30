package chapter02.section02.domain.model.organizationhierarchy;

import chapter02.section02.domain.support.SysOutError_Base_Message;

//『組織名制約専用エラーメッセージ出力』クラス
class SysOutErrorMessage_Judge_SoshikiMeiConstraint {

    //コンストラクタ
    SysOutErrorMessage_Judge_SoshikiMeiConstraint( String SoshikiMeiMeiMessage ) {
        //※マトリョーシカ
        new SysOutError_Base_Message( SoshikiMeiMeiMessage );
    }
}
