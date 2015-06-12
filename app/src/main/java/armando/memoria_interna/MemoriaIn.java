package armando.memoria_interna;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class MemoriaIn extends ActionBarActivity implements View.OnClickListener{
    private EditText edtexto;
    private Button bguardar, babrir;
    private static final int READ_BLOCK_SIZE=100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memoria_in);
        edtexto = (EditText) findViewById(R.id.archivo);
        bguardar = (Button)findViewById(R.id.guardar);
        babrir = (Button)findViewById(R.id.abrir);
        babrir.setOnClickListener(this);
        bguardar.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_memoria_in, menu);
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

    @Override
    public void onClick(View v) {
            if (v.equals(bguardar)){
                String strtext = edtexto.getText().toString();
                //FileOutputStream permite grabar texto en un archivo
                FileOutputStream outstre=null;
                try {
                    //Metodo que escribe y abre un archivo con un nombre especificado
                    outstre = openFileOutput("archivodetexto.txt", MODE_WORLD_READABLE);
                    //Convierte un stream de caracteres en un stream de bytes
                    OutputStreamWriter writer = new OutputStreamWriter(outstre);
                    writer.write(strtext);
                    //Escribe en el buffer la cadena de texto
                    writer.flush(); //Envia el texto que hay en el buffer al archivo
                    writer.close(); //Cierra el archivo de texto
                }
                catch (IOException e) {
                    // TODO Auto-generated catch block e.printStackTrace();
                }
                Toast.makeText(getBaseContext(), R.string.guardararchivo, Toast.LENGTH_SHORT).show();
                edtexto.setText(""); }
            if (v.equals(babrir)) {
                try {
                    //Se lee el archivo de texto
                    FileInputStream filestre = openFileInput("archivodetexto.txt");
                    InputStreamReader reader = new InputStreamReader(filestre);
                    char[] Buffer = new char[READ_BLOCK_SIZE];
                    String texto = "";
                    //Se lee el archivo de texto mientras no se llegue al final de él
                    int lector;
                    while ((lector = reader.read(Buffer)) > 0) {
                        //Se lee por bloques de 100 caracteres
                        // Y se va copiando a una cadena de texto
                        String strlector = String.copyValueOf(Buffer, 0, lector);
                        texto += strlector;
                        Buffer = new char[READ_BLOCK_SIZE];
                    }
                    //Se muestra el texto leido en la caje de texto
                    edtexto.setText(texto);
                    reader.close();
                    Toast.makeText(getBaseContext(), R.string.cargararchivo, Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    // TODO: handle exception e.printStackTrace(); }
                }
            }
        }

}
