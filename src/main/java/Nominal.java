public enum Nominal {
    FIFTY(50),
    ONE_HUNDRED(100),
    TWO_HUNDRED(200),
    FIVE_HUNDRED(500),
    ONE_THOUSAND(1000),
    TWO_THOUSAND(2000),
    FIVE_THOUSAND(5000);

    private Integer nominal;
    Nominal(int i) {
        this.nominal=i;
    }

    public Integer getNominal() {
        return nominal;
    }
    public static  Nominal getNominalFromInt(Integer value)
    {
        if (value==null) return  null;
        for(Nominal nominal:values()){
            if (nominal.nominal.equals(value))
                return nominal;
        }
        return  null;
    }

    @Override
    public String toString() {
        return this.nominal.toString();
    }
}
