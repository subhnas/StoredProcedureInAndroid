package com.example.storedprocedureinandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.TextView;

import com.example.storedprocedureinandroid.R;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

public class MainActivity extends AppCompatActivity {

    AppCompatButton button;
    TextView textView;


    private static String ip = "65.1.99.186";
    private static String port = "1232";
    private static String Classes = "net.sourceforge.jtds.jdbc.Driver";
    private static String database = "GTECH_1203SLFNDH";
    private static String username = "DEV_SVKSR186";
    private static String password = "bT!23!mkQ2%jK!k8$dbQm";
    private static String url = "jdbc:jtds:sqlserver://" + ip + ":" + port + "/" + database;

    private Connection connection = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);
        textView = findViewById(R.id.textView);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            Class.forName(Classes);
            connection = DriverManager.getConnection(url, username, password);
            textView.setText("SUCCESS");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            textView.setText("ERROR");
        } catch (SQLException e) {
            e.printStackTrace();
            textView.setText("FAILURE");
        }

}


    public void sqlButton(View view){
        if (connection==null){
           textView.setText("SQL query failed !!");

                   }
       try {
           CallableStatement callableStatement= connection.prepareCall("{call A_Test_TBL_sp_fetch_data(?,?)}");

           callableStatement.setInt("@id",5);
           callableStatement.registerOutParameter(2, Types.INTEGER);
           callableStatement.execute();

           callableStatement.getMoreResults();
           int intOutput=callableStatement.getInt(2);

           textView.setText(Integer.toString(intOutput));
           callableStatement.close();


       } catch (SQLException throwables) {
           throwables.printStackTrace();
       }
    }


}