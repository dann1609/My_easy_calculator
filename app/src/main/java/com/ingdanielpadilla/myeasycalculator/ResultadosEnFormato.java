package com.ingdanielpadilla.myeasycalculator;

import android.util.Log;

/**
 * Created by Lucia on 17/12/2015.
 */
public class ResultadosEnFormato {
    private Double numero;
    private int NumDecsSig;
    private Boolean sw=true;
    private Double diferencial,d5;
    private int solve=0,dsig;


    public ResultadosEnFormato(Double d,int i){
        this.numero=d;
        this.NumDecsSig=i;
        Log.d("Desarrollo-ddo", Double.toString(d));

        while(sw && solve<NumDecsSig){
            double ddo=d;
            long dlong=(long) ddo;
            diferencial=d-dlong;
            d5=(diferencial * 100000);
            dsig=d5.intValue();
            Log.d("Desarrollo-ddo", Double.toString(ddo));
            Log.d("Desarrollo-dlong", Long.toString(dlong));
            Log.d("Desarrollo-diferencial", Double.toString(diferencial));
            Log.d("Desarrollo-d5", Double.toString(d5));
            Log.d("Desarrollo-dsig", Integer.toString(dsig));
            Log.d("Desarrollo-solve", Integer.toString(solve));

            if(dsig==0||dsig==99999){
                sw=false;
            }
            else{
                d=diferencial*10;
                solve++;
            }
            //Log.d("Desarrollo solve",Integer.toString(solve));
        }
        if(dsig==99999) {
            //this.numero = this.numero + 1/(10*(solve+1));
        }
        Log.d("Desarrollo-result", Double.toString(this.numero));
    }
    public int getSolve(){return this.solve;}
    public Double getNumero(){return this.numero;}
    public String getNumeroToDisplay(){
        double ddo=this.numero;
        long dlong=(long) ddo;
        diferencial=ddo-dlong;
        String numToDisp;
        Log.d("Desarrollo-numero", Double.toString(this.numero));
        /**
        numToDisp=Long.toString(dlong);
        if (solve==0){
        }else{
            if(solve<10){
                numToDisp= numToDisp+Double.toString(diferencial).substring(1,2+solve);
            }else{
                numToDisp=Double.toString(ddo);
            }
        }
        */
        switch (solve){
            case 0:
                numToDisp= String.format("%.0f", this.numero);
                break;
            case 1:
                numToDisp= String.format("%.1f", this.numero);
                break;
            case 2:
                numToDisp= String.format("%.2f", this.numero);
                break;
            case 3:
                numToDisp= String.format("%.3f", this.numero);
                break;
            case 4:
                numToDisp= String.format("%.4f", this.numero);
                break;
            case 5:
                numToDisp= String.format("%.5f", this.numero);
                break;
            case 6:
                numToDisp= String.format("%.6f", this.numero);
                break;
            case 7:
                numToDisp= String.format("%.7f", this.numero);
                break;
            case 8:
                numToDisp= String.format("%.8f", this.numero);
                break;
            case 9:
                numToDisp= String.format("%.9f", this.numero);
                break;
            default:
                numToDisp= Double.toString(this.numero);
                break;
        }
        return numToDisp;
    }
    public Boolean isDot(){
        if(solve>0){return true;}else {return false;}
    }
}
