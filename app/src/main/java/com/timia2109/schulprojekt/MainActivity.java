package com.timia2109.schulprojekt;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * GERMAN ONLY!
 * Ein Beispielprogramm für Android Apps
 * Als Projekt für die Schule um Android Entwicklung vorzustellen
 * Dieses Programm versucht Tiere zu erraten. 
 *
 * @author Tim Ittermann (timia2109)
 * @version 1.0
 */

public class MainActivity extends AppCompatActivity {
    BinBaum hBinBaum, baum;
    Button ja, nein, einfuegenBtn, zeigeBaum;
    TextView ausgabe, ausgabeFrage;
    EditText eingabeName, eingabeFrage;
    CheckBox trifftZu;
    int frage;
    boolean kannEinfuegen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            InputStream file = new FileInputStream(new File(Environment.getExternalStorageDirectory(),"binbaum.ser"));
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);
            hBinBaum = (BinBaum) input.readObject();
            file.close();
        }
        catch(Exception ex){
            Toast.makeText(this, "Kann nicht laden!", Toast.LENGTH_SHORT).show();
            hBinBaum = new BinBaum("Hat es Beine?");
            hBinBaum.setLeft(new BinBaum("Pferd"));
            hBinBaum.setRight(new BinBaum("Fisch"));
        }

        ja = (Button) findViewById(R.id.jaBtn);
        nein = (Button) findViewById(R.id.neinBtn);
        einfuegenBtn = (Button) findViewById(R.id.einfuegenBtn);
        ausgabe = (TextView) findViewById(R.id.ausgabe);
        ausgabeFrage = (TextView) findViewById(R.id.ausgabeFrage);
        eingabeName = (EditText) findViewById(R.id.eingabeName);
        eingabeFrage = (EditText) findViewById(R.id.eingabeFrage);
        trifftZu = (CheckBox) findViewById(R.id.trifftZu);
        zeigeBaum = (Button) findViewById(R.id.zeigeBaum);

        einfuegenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                einfuegen();
            }
        });

        zeigeBaum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ZeigeBaumActivity.class);
                i.putExtra("baum", hBinBaum);
                startActivity(i);
            }
        });

        neuStarten();
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            OutputStream file = new FileOutputStream(new File(Environment.getExternalStorageDirectory(),"binbaum.ser"));
            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutput output = new ObjectOutputStream(buffer);
            output.writeObject(hBinBaum);
            output.close();
            Toast.makeText(this, "Gespeichert!", Toast.LENGTH_SHORT).show();
        }
        catch(Exception ex){
            Toast.makeText(this, "Kann nicht speichern!", Toast.LENGTH_SHORT).show();
        }
    }

    private void naechsteFrage() {
        if (baum.isLeaf())
            antwort();
        else {
            frage++;
            ausgabe.setText("Frage " + frage);
            ausgabeFrage.setText((String) baum.getContent());
        }
    }

    private void antwort() {
        ausgabe.setText("Antwort");
        ausgabeFrage.setText((String)baum.getContent());
        ja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                neuStarten();
            }
        });
        nein.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ausgabeFrage.setText("Bitte neues Tier einfügen");
                kannEinfuegen = true;
            }
        });
    }

    private void einfuegen() {
        if (kannEinfuegen) {
            String alt = (String) baum.getContent();
            baum.setContent( eingabeFrage.getText().toString() );
            if (trifftZu.isChecked()) {
                baum.setLeft(new BinBaum( eingabeName.getText().toString() ));
                baum.setRight(new BinBaum( alt ));
            }
            else {
                baum.setLeft(new BinBaum( alt ));
                baum.setRight(new BinBaum( eingabeName.getText().toString() ));
            }
            neuStarten();
        }
        else
            Toast.makeText(this, "Du darfst hier nicht einfügen!", Toast.LENGTH_SHORT).show();
    }

    private void neuStarten() {
        baum = hBinBaum;
        frage = 0;
        naechsteFrage();
        kannEinfuegen = false;
        ja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baum = baum.getLeft();
                naechsteFrage();
            }
        });
        nein.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baum = baum.getRight();
                naechsteFrage();
            }
        });
    }
}
