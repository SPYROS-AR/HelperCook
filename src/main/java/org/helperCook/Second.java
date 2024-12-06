package org.helperCook;

public class Second implements UnitConverter
{
    @Override
    public double toBase(double value) {return value;}

    @Override
    public double fromBase(double value) {return value;}

    @Override
    public String format(double value) {return value + " second" + (value > 1 ? "s" : "");}

    @Override
    public UnitType getUnitType() {return UnitType.TIME;}
}