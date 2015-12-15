package com.ingdanielpadilla.myeasycalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    Button bmas, bmenos, bpor, bentre, b0, b1, b2, b3, b4, b5, b6, b7, b8, b9, bpunto, bc, bac, bporcentaje, braiz, bmasomenos, bigual;
    int estado=0,CICLONUMERICO=0,CICLOOPERADOR=1;
    Double numhis=0d,numdisp=0d;
    Boolean swdot=false;
    TextView longDisplay,shortDisplay;
    String shortDisplayString="0",longDisplayString="",lastoperator,DEVELOP="Desarrollo";
    View.OnClickListener handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        longDisplay= (TextView) findViewById(R.id.longdisplay);
        shortDisplay=(TextView) findViewById(R.id.shortdisplay);

        setShortDisplay(shortDisplayString);
        setLongDisplay(longDisplayString);

        bmas= (Button) findViewById(R.id.bmas);
        bmas.setTag("+");
        bmenos= (Button) findViewById(R.id.bmenos);
        bmenos.setTag("-");
        bpor= (Button) findViewById(R.id.bpor);
        bpor.setTag("x");
        bentre= (Button) findViewById(R.id.bentre);
        bentre.setTag("/");
        b0= (Button) findViewById(R.id.b0);
        b0.setTag(0);
        b1= (Button) findViewById(R.id.b1);
        b1.setTag(1);
        b2= (Button) findViewById(R.id.b2);
        b2.setTag(2);
        b3= (Button) findViewById(R.id.b3);
        b3.setTag(3);
        b4= (Button) findViewById(R.id.b4);
        b4.setTag(4);
        b5= (Button) findViewById(R.id.b5);
        b5.setTag(5);
        b6= (Button) findViewById(R.id.b6);
        b6.setTag(6);
        b7= (Button) findViewById(R.id.b7);
        b7.setTag(7);
        b8= (Button) findViewById(R.id.b8);
        b8.setTag(8);
        b9= (Button) findViewById(R.id.b9);
        b9.setTag(9);
        bpunto= (Button) findViewById(R.id.bpunto);
        bpunto.setTag(".");
        bc= (Button) findViewById(R.id.bc);
        bac= (Button) findViewById(R.id.bac);
        bporcentaje= (Button) findViewById(R.id.bporcentaje);
        braiz= (Button) findViewById(R.id.braiz);
        bmasomenos= (Button) findViewById(R.id.bmasomenos);
        bigual= (Button) findViewById(R.id.bigual);


        Double alfa=3.1416;
        Double beta=3.1d;
        Log.d(DEVELOP,alfa.toString());
        Log.d(DEVELOP,beta.toString());

        handler = new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                    //Si se teclea un número
                    if(isNumeric(view)){
                        estado=0;
                        if(view.getTag().toString()=="."){
                            if(!swdot){
                                shortDisplayString = shortDisplayString.concat(String.valueOf(view.getTag()));
                                swdot=true;
                            }
                        }
                        else {
                            //Si el display numerico es igual a 0
                            if (shortDisplayString == "0") {
                                //Se Remplaza por el numero
                                shortDisplayString = view.getTag().toString();
                            }
                            //Si el display numerico no es igual a 0
                            else {
                                //Se le agrega el otro número
                                shortDisplayString = shortDisplayString.concat(String.valueOf(view.getTag()));
                            }
                        }
                        //Se actualiza el display numérico
                        setShortDisplay(shortDisplayString);

                    }
                //Si lo último es un operador
                if(estado==0&&isOperator(view)){
                    //Si se teclea un operador

                        numdisp=Double.parseDouble(shortDisplayString);
                        Log.d(DEVELOP,shortDisplayString.substring(shortDisplayString.length() - 1));
                        //Si el display numerico termina en "."
                        if(shortDisplayString.substring(shortDisplayString.length() - 1).equals(".")){
                            //ELimina el punto y pone en la carga del display de historial el display numerico y el operador
                            longDisplayString=longDisplayString.concat(shortDisplayString.substring(0,shortDisplayString.length() - 1)+view.getTag().toString());
                            //carga el numero desde el display
                            numdisp=Double.parseDouble(shortDisplayString.substring(0,shortDisplayString.length() - 1));
                            //Actualiza el display
                            setLongDisplay(longDisplayString);
                        }else{
                            //Pone en la carga del display de historial el display numerico y el operador
                            longDisplayString=longDisplayString.concat(shortDisplayString+view.getTag().toString());
                            //carga el numero desde el display
                            numdisp=Double.parseDouble(shortDisplayString);
                            setLongDisplay(longDisplayString);
                        }
                        if(lastoperator!=null){
                            numhis=doOperation(numhis,numdisp,lastoperator);
                            setShortDisplay(numhis.toString());
                            shortDisplayString="0";
                        }else{numhis=Double.parseDouble(shortDisplayString);}
                        lastoperator=view.getTag().toString();
                    estado=1;

                }
                    if(estado==1&&isOperator(view)){
                        lastoperator=view.getTag().toString();
                        longDisplayString=longDisplayString.substring(0,longDisplayString.length() - 1)+view.getTag().toString();
                        setLongDisplay(longDisplayString);
                    }

            }
        };

        b0.setOnClickListener(handler);
        b1.setOnClickListener(handler);
        b2.setOnClickListener(handler);
        b3.setOnClickListener(handler);
        b4.setOnClickListener(handler);
        b5.setOnClickListener(handler);
        b6.setOnClickListener(handler);
        b7.setOnClickListener(handler);
        b8.setOnClickListener(handler);
        b9.setOnClickListener(handler);
        bpunto.setOnClickListener(handler);
        bmas.setOnClickListener(handler);
        bmenos.setOnClickListener(handler);
        bpor.setOnClickListener(handler);
        bentre.setOnClickListener(handler);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private Boolean isNumeric(View view){
        int id=view.getId();
        if(view.getId()==b0.getId()
                ||id==b1.getId()
                ||id==b2.getId()
                ||id==b3.getId()
                ||id==b4.getId()
                ||id==b5.getId()
                ||id==b6.getId()
                ||id==b7.getId()
                ||id==b8.getId()
                ||id==b9.getId()
                ||id==bpunto.getId()){return true;}else{return false;}
    }
    private Boolean isOperator(View view){
        int id=view.getId();
        if(id==bmas.getId()
                ||id==bmenos.getId()
                ||id==bpor.getId()
                ||id==bentre.getId()){return true;}else{return false;}
    }
    private void setShortDisplay(String string){
        shortDisplay.setText(string);
    }
    private void setLongDisplay(String string){
        longDisplay.setText(string);
    }
    private Double doOperation(Double d1,Double d2,String lastoperator){
        Double result=0d;
        switch (lastoperator){
            case "+":
                result=d1+d2;
                break;
            case "-":
                result=d1-d2;
                break;
            case "*":
                result=d1*d2;
                break;
            case "/":
                result=d1/d2;
                break;
        }
        return result;
    }
}
