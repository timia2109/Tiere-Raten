package com.timia2109.schulprojekt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ZeigeBaumActivity extends AppCompatActivity {
    StringBuilder baumText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zeige_baum);

        Intent i = getIntent();
        BinBaum baum = (BinBaum) i.getSerializableExtra("baum");
        baumText = new StringBuilder();
        baumText.append(baum.getContent());
        print("", baum.getLeft());
        print("", baum.getRight());

        LinearLayout layout = (LinearLayout) findViewById(R.id.layoutZwei);

        Button teilen = new Button(this);
        teilen.setText("Teilen");
        teilen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent teilIntent = new Intent(Intent.ACTION_SEND);
                teilIntent.putExtra(Intent.EXTRA_TEXT, "Mein BinärBaum ist:\n"+baumText.toString());
                teilIntent.setType("text/plain");
                startActivity(Intent.createChooser(teilIntent, "Teilen mit: "));
            }
        });
        teilen.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        layout.addView(teilen);

        TextView text = new TextView(this);
        text.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        text.setText( baumText.toString() );
        layout.addView(text);
    }

    private void print(String prefix, BinBaum b) {
        if (b==null)
            return;
        baumText.append("\n")
            .append(prefix)
            .append("|--- ")
            .append(b.getContent());
        print(prefix + "|   ", b.getLeft() );
        print(prefix + "|   ", b.getRight() );
    }
}
