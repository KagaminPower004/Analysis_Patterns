package chapter02.section02.domain.model.area.value_object;

//『地域名制約』クラス(の、つもり。。。)
class Judge_TiikiMeiConstraint {
    String tiikiMei;

    //コンストラクタ
    Judge_TiikiMeiConstraint( final String tiikiMei ){ this.tiikiMei = tiikiMei; }

    boolean isCollect(){

        return switch (tiikiMei) {
            case "北海道" -> true;
            case "東北" -> true;
            case "北陸" -> true;
            case "北関東" -> true;
            case "南関東" -> true;
            default -> false;
        };
    }

    Boolean isError(){
        return ! this.isCollect();
    }
}
