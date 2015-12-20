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


    Button bmas, bmenos, bpor, bentre, b0, b1, b2, b3, b4, b5, b6, b7, b8, b9, bpunto, bc, bac, bporcentaje, braiz, bmasomenos, bigual, bms, bmr, bmmenos, bmmas;
    int estado = 0, CICLONUMERICO = 0, CICLOOPERADOR = 1, NumDecsSig = 10;
    Double numhis = 0d, numdisp = 0d, nummem=0d;
    Boolean swdot = false, swig = false;
    TextView longDisplay, shortDisplay;
    String shortDisplayString = "0", longDisplayString = "", lastoperator, DEVELOP = "Desarrollo";
    View.OnClickListener handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        longDisplay = (TextView) findViewById(R.id.longdisplay);
        shortDisplay = (TextView) findViewById(R.id.shortdisplay);

        setShortDisplay(shortDisplayString);
        setLongDisplay(longDisplayString);

        bmas = (Button) findViewById(R.id.bmas);
        bmas.setTag("+");
        bmenos = (Button) findViewById(R.id.bmenos);
        bmenos.setTag("-");
        bpor = (Button) findViewById(R.id.bpor);
        bpor.setTag("x");
        bentre = (Button) findViewById(R.id.bentre);
        bentre.setTag("/");
        b0 = (Button) findViewById(R.id.b0);
        b0.setTag(0);
        b1 = (Button) findViewById(R.id.b1);
        b1.setTag(1);
        b2 = (Button) findViewById(R.id.b2);
        b2.setTag(2);
        b3 = (Button) findViewById(R.id.b3);
        b3.setTag(3);
        b4 = (Button) findViewById(R.id.b4);
        b4.setTag(4);
        b5 = (Button) findViewById(R.id.b5);
        b5.setTag(5);
        b6 = (Button) findViewById(R.id.b6);
        b6.setTag(6);
        b7 = (Button) findViewById(R.id.b7);
        b7.setTag(7);
        b8 = (Button) findViewById(R.id.b8);
        b8.setTag(8);
        b9 = (Button) findViewById(R.id.b9);
        b9.setTag(9);
        bpunto = (Button) findViewById(R.id.bpunto);
        bpunto.setTag(".");
        bc = (Button) findViewById(R.id.bc);
        bc.setTag("c");
        bac = (Button) findViewById(R.id.bac);
        bac.setTag("ac");
        bporcentaje = (Button) findViewById(R.id.bporcentaje);
        bporcentaje.setTag("%");
        braiz = (Button) findViewById(R.id.braiz);
        braiz.setTag("\u221A");
        bmasomenos = (Button) findViewById(R.id.bmasomenos);
        bmasomenos.setTag("+/-");
        bigual = (Button) findViewById(R.id.bigual);
        bigual.setTag("=");
        bms = (Button) findViewById(R.id.bms);
        bms.setTag("ms");
        bmr = (Button) findViewById(R.id.bmr);
        bmr.setTag("mr");
        bmmas = (Button) findViewById(R.id.bmmas);
        bmmas.setTag("mmas");
        bmmenos = (Button) findViewById(R.id.bmmenos);
        bmmenos.setTag("mmenos");


        handler = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Si se teclea un número
                if (isNumeric(view)) {
                    estado = 0;
                    if (view.getTag().toString() == ".") {
                        if (!swdot) {
                            shortDisplayString = shortDisplayString.concat(String.valueOf(view.getTag()));
                            swdot = true;
                        }
                    } else {
                        //Si el display numerico es igual a 0
                        if (shortDisplayString == "0" || swig) {
                            //Se Remplaza por el numero
                            shortDisplayString = view.getTag().toString();
                            swdot = false;
                        }
                        //Si el display numerico no es igual a 0
                        else {
                            //Se le agrega el otro número
                            shortDisplayString = shortDisplayString.concat(String.valueOf(view.getTag()));
                        }
                    }
                    //Se actualiza el display numérico
                    setShortDisplay(shortDisplayString);
                    if (swig) {
                        numhis = 0d;
                        longDisplayString = "";
                        setLongDisplay(longDisplayString);
                        swig = false;
                    }
                }
                if (estado == 1 && isOperator(view)) {
                    lastoperator = view.getTag().toString();
                    longDisplayString = longDisplayString.substring(0, longDisplayString.length() - 1) + view.getTag().toString();
                    setLongDisplay(longDisplayString);
                    if (view.getTag() == "=") {
                        estado = 0;
                        shortDisplayString = getOnResultFormat(numhis);
                        setShortDisplay(shortDisplayString);
                        longDisplayString = "";
                        lastoperator = null;
                        swig = true;
                    }
                } else {
                    //Si se teclea un operador
                    if (estado == 0 && isOperator(view)) {

                        swdot = false;
                        numdisp = Double.parseDouble(shortDisplayString);
                        Log.d(DEVELOP, shortDisplayString.substring(shortDisplayString.length() - 1));
                        //Si el display numerico termina en "."
                        if (shortDisplayString.substring(shortDisplayString.length() - 1).equals(".")) {
                            //ELimina el punto y pone en la carga del display de historial el display numerico y el operador
                            longDisplayString = longDisplayString.concat(shortDisplayString.substring(0, shortDisplayString.length() - 1) + view.getTag().toString());
                            //carga el numero desde el display
                            numdisp = Double.parseDouble(shortDisplayString.substring(0, shortDisplayString.length() - 1));

                        } else {
                            //Pone en la carga del display de historial el display numerico y el operador
                            longDisplayString = longDisplayString.concat(shortDisplayString + view.getTag().toString());
                            //carga el numero desde el display
                            numdisp = Double.parseDouble(shortDisplayString);
                        }
                        //Actualiza el display
                        setLongDisplay(longDisplayString);

                        //si hubo un operador anterior es decir no es el primer operador que se presiona
                        if (lastoperator != null) {
                            //numero del historial operalo con el numero del shortdisplay
                            numhis = doOperation(numhis, numdisp, lastoperator);
                            ResultadosEnFormato result = new ResultadosEnFormato(numhis, NumDecsSig);
                            //muestra el resultado en el short diaplay
                            setShortDisplay(result.getNumeroToDisplay());
                            swdot = result.isDot();
                            //El valor del short display se reasigna a 0
                            shortDisplayString = "0";
                        } else {
                            swig = false;
                            //se carga el valor del shortdisplay para una futura operacion
                            numhis = Double.parseDouble(shortDisplayString);
                            //se asigna el shortdisplay string a 0
                            shortDisplayString = "0";
                            //se muestra el short display en 0
                            setShortDisplay(shortDisplayString);
                            if (view.getTag() == "=") {
                                ResultadosEnFormato result = new ResultadosEnFormato(numhis, NumDecsSig);
                                //muestra el resultado en el short diaplay
                                setShortDisplay(result.getNumeroToDisplay());
                                swdot = result.isDot();
                            }
                        }
                        lastoperator = view.getTag().toString();
                        estado = 1;
                        if (lastoperator == "=") {
                            estado = 0;
                            shortDisplayString = getOnResultFormat(numhis);
                            longDisplayString = "";
                            lastoperator = null;
                            swig = true;
                        }

                    }
                }

                if (view.getTag() == "ac") {
                    numhis = 0d;
                    lastoperator = null;
                    estado = 0;
                    shortDisplayString = "0";
                    longDisplayString = "";
                    swdot = false;
                    swig = false;
                    setShortDisplay(shortDisplayString);
                    setLongDisplay(longDisplayString);
                }

                if (view.getTag() == "c" && estado == 0) {
                    shortDisplayString = "0";
                    swdot = false;
                    setShortDisplay(shortDisplayString);
                    if (swig == true) {
                        setLongDisplay(longDisplayString);
                    }
                }
                Log.d(DEVELOP, shortDisplayString);
                if (view.getTag() == "+/-" && estado == 0 && !shortDisplayString.equals("0")) {
                    if (shortDisplayString.substring(0, 1).equals("-")) {
                        Log.d(DEVELOP, shortDisplayString.substring(1, shortDisplayString.length()));
                        shortDisplayString = shortDisplayString.substring(1, shortDisplayString.length());

                    } else {
                        Log.d(DEVELOP, shortDisplayString);
                        shortDisplayString = "-" + shortDisplayString;
                    }
                    setShortDisplay(shortDisplayString);

                }

                if (isSpecialOperator(view) && estado == 0 && !shortDisplayString.equals("0")) {

                    swdot = false;
                    numdisp = Double.parseDouble(shortDisplayString);
                    Log.d(DEVELOP, shortDisplayString.substring(shortDisplayString.length() - 1));
                    //Si el display numerico termina en "."
                    if (shortDisplayString.substring(shortDisplayString.length() - 1).equals(".")) {
                        //ELimina el punto y pone en la carga del display de historial el display numerico y el operador
                        longDisplayString = longDisplayString.concat(shortDisplayString.substring(0, shortDisplayString.length() - 1) + view.getTag().toString());
                        //carga el numero desde el display
                        numdisp = Double.parseDouble(shortDisplayString.substring(0, shortDisplayString.length() - 1));

                    } else {
                        //Pone en la carga del display de historial el display numerico y el operador
                        longDisplayString = longDisplayString.concat(shortDisplayString + view.getTag().toString());
                        //carga el numero desde el display
                        numdisp = Double.parseDouble(shortDisplayString);
                    }
                    //Actualiza el display
                    setLongDisplay(longDisplayString);

                    //si hubo un operador anterior es decir no es el primer operador que se presiona

                    //numero del historial operalo con el numero del shortdisplay
                    if (lastoperator == null) {
                        lastoperator = "x";
                        numhis = 1d;
                    }
                    if (view.getTag() == "%") {
                        numhis = doOperation(numhis, numdisp / 100, lastoperator);
                    }
                    if (view.getTag() == "\u221A") {
                        numhis = doOperation(numhis, Math.sqrt(numdisp), lastoperator);
                    }
                    ResultadosEnFormato result = new ResultadosEnFormato(numhis, NumDecsSig);
                    //muestra el resultado en el short diaplay
                    setShortDisplay(result.getNumeroToDisplay());
                    swdot = result.isDot();
                    //El valor del short display se reasigna a 0
                    shortDisplayString = "0";
                    estado = 1;
                    estado = 0;
                    shortDisplayString = getOnResultFormat(numhis);
                    longDisplayString = "";
                    lastoperator = null;
                    swig = true;
                }

                if(isMemoryOperator(view)){
                    switch (view.getTag().toString())
                    {
                        case "ms":
                            nummem=Double.parseDouble(getShortDisplay());
                            break;
                        case "mr":
                            shortDisplayString=getOnResultFormat(nummem);
                            setShortDisplay(shortDisplayString);
                            break;
                        case "mmas":
                            nummem=nummem+Double.parseDouble(getShortDisplay());
                            break;
                        case "mmenos":
                            nummem=nummem-Double.parseDouble(getShortDisplay());
                            break;
                    }
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
        bigual.setOnClickListener(handler);
        bc.setOnClickListener(handler);
        bac.setOnClickListener(handler);
        bmasomenos.setOnClickListener(handler);
        bporcentaje.setOnClickListener(handler);
        braiz.setOnClickListener(handler);
        bms.setOnClickListener(handler);
        bmr.setOnClickListener(handler);
        bmmas.setOnClickListener(handler);
        bmmenos.setOnClickListener(handler);
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

    private Boolean isNumeric(View view) {
        int id = view.getId();
        if (view.getId() == b0.getId()
                || id == b1.getId()
                || id == b2.getId()
                || id == b3.getId()
                || id == b4.getId()
                || id == b5.getId()
                || id == b6.getId()
                || id == b7.getId()
                || id == b8.getId()
                || id == b9.getId()
                || id == bpunto.getId()) {
            return true;
        } else {
            return false;
        }
    }

    private Boolean isOperator(View view) {
        int id = view.getId();
        if (id == bmas.getId()
                || id == bmenos.getId()
                || id == bpor.getId()
                || id == bentre.getId()
                || id == bigual.getId()) {
            return true;
        } else {
            return false;
        }
    }

    private Boolean isSpecialOperator(View view) {
        int id = view.getId();
        if (id == bporcentaje.getId()
                || id == braiz.getId()) {
            return true;
        } else {
            return false;
        }
    }

    private Boolean isMemoryOperator(View view){
        int id= view.getId();
        if(id== bms.getId()
                || id==bmr.getId()
                || id== bmmas.getId()
                || id==bmmenos.getId()){
            return true;
        }else{
            return false;
        }
    }

    private void setShortDisplay(String string) {
        shortDisplay.setText(string);
    }

    private String getShortDisplay() {
        return shortDisplay.getText().toString();
    }

    private void setLongDisplay(String string) {
        longDisplay.setText(string);
    }

    private Double doOperation(Double d1, Double d2, String lastoperator) {
        Double result = 0d;
        switch (lastoperator) {
            case "+":
                result = d1 + d2;
                break;
            case "-":
                result = d1 - d2;
                break;
            case "x":
                result = d1 * d2;
                break;
            case "/":
                result = d1 / d2;
                break;
        }
        return result;
    }

    private String getOnResultFormat(Double d) {

        ResultadosEnFormato result = new ResultadosEnFormato(d, NumDecsSig);
        return result.getNumeroToDisplay();
    }
}
