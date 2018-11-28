package heroservice.com.qlsv;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btncreat, btninsertlop, btndellop, btnuplop, btnquerylop, btninstsv, btndelsv, btnupsv, btnqerysv;
    EditText edtmalop, edttenlop, edtsiso, edtmasv, edttensv, edtmalop1;
    SQLiteDatabase database1 = null;
    String tbllop = "tbllop";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btninsertlop = findViewById(R.id.btninstlop);
        btndellop = findViewById(R.id.btndellop);
        btnuplop = findViewById(R.id.btnuplop);
        btnquerylop = findViewById(R.id.btnqrylop);
        btninstsv = findViewById(R.id.btninstsv);
        btndelsv = findViewById(R.id.btndelsv);
        btnupsv = findViewById(R.id.btnupdatesb);
        btnqerysv = findViewById(R.id.btnqrysv);
        edtmalop = findViewById(R.id.edtmalop);
        edttenlop = findViewById(R.id.edttenlop);
        edtsiso = findViewById(R.id.edtsiso);
        edtmasv = findViewById(R.id.edtmasv);
        edttensv = findViewById(R.id.edttensv);
        edtmalop1 = findViewById(R.id.edtmalop1);
        database1 = openOrCreateDatabase("qlsinhvien.db", MODE_PRIVATE, null);
        try {
            docreattable();
        } catch (Exception e) {
            Log.e("ERROR", "TABLE IS EXISTS");
        }
        btninsertlop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doinsertrecordtolop();
            }
        });
        btndellop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delelerowlop();
            }
        });
        btnuplop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doupdaterowlop();
            }
        });
        btnquerylop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                querytablelop();
            }
        });
        btninstsv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doinsertrecordSV();
            }
        });
        btndelsv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleterowSV();
            }
        });
        btnupsv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doupdaterowSV();
            }
        });
        btnqerysv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                querytableSV();
            }
        });
    }

    private void docreattable() {
        String sql1 = "CREATE TABLE tbllop("+
                "malop TEXT primary key,"+
                "tenlop TEXT,"+"siso INTEGER)";
        database1.execSQL(sql1);
        String sql2 = "CREATE TABLE tblsinhvien("+
                "masv TEXT primary key,"+
                "tensv TEXT,"+"malop TEXT NOT NULL CONSTRAINT malop "+" REFERENCES tbllop(malop) ON DELETE CASCADE)";
        database1.execSQL(sql2);
    }
    private void doinsertrecordtolop() {
        String malop = edtmalop.getText().toString();
        String tenlop = edttenlop.getText().toString();
        String siso = edtsiso.getText().toString();
        ContentValues values = new ContentValues();
        values.put("malop", malop);
        values.put("tenlop", tenlop);
        values.put("siso", siso);
        String msg= "";
        if(database1.insert("tbllop", null, values)==-1) {
            msg="Failed to insert record";
        }
        else {
            msg="Insert record is successully";
        }
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
    private void delelerowlop() {
        String malop = edtmalop.getText().toString();
        int d = database1.delete("tbllop", "malop=?", new String[]{malop});
        String msg= "";
        if(d==0) {
            msg="Delete Row "+malop +"Fail";
        }
        else {
            msg="Delete Row "+malop +"Successfully";
        }
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
    private void doupdaterowlop() {
        String malop = edtmalop.getText().toString();
        String new_tenlop = edttenlop.getText().toString();
        String siso = edtsiso.getText().toString();
        ContentValues values = new ContentValues();
        values.put("tenlop", new_tenlop);
        values.put("siso", siso);
        String msg= "";
        int ret = database1.update("tbllop", values, "malop=?", new String[]{malop});
        if(ret==0) {
            msg="Failed to update";
        }
        else {
            msg="updating is successfully";
        }
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
    private void querytablelop() {
        Cursor c = database1.query("tbllop",null,null,null,null,null,null);
        c.moveToFirst();
        String data="";
        while (c.isAfterLast()==false) {
            data+=c.getString(0)+"-"+c.getString(1)+"-"+c.getString(2);
            data+="\n";
            c.moveToNext();
        }
        Toast.makeText(this, data, Toast.LENGTH_LONG).show();
        c.close();
    }
    private void doinsertrecordSV() {
        String masv = edtmasv.getText().toString();
        String tensv = edttensv.getText().toString();
        String malop = edtmalop1.getText().toString();
        ContentValues values = new ContentValues();
        values.put("masv", masv);
        values.put("tensv", tensv);
        values.put("malop", malop);
        String msg= "";
        if(database1.insert("tblsinhvien", null, values)==-1) {
            msg="Failed to insert record";
        }
        else {
            msg="Insert record is successully";
        }
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
    private void deleterowSV() {
        String masv = edtmasv.getText().toString();
        int d = database1.delete("tblsinhvien", "masv=?", new String[]{masv});
        String msg= "";
        if(d==0) {
            msg="Delete Row "+masv +"Fail";
        }
        else {
            msg="Delete Row "+masv +"Successfully";
        }
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
    private void doupdaterowSV() {
        String masv = edtmasv.getText().toString();
        String tensv = edttensv.getText().toString();
        String malop = edtmalop1.getText().toString();
        ContentValues values = new ContentValues();
        values.put("tensv", tensv);
        values.put("malop", malop);
        String msg= "";
        int ret = database1.update("tblsinhvien", values, "masv=?", new String[]{masv});
        if(ret==0) {
            msg="Failed to update";
        }
        else {
            msg="updating is successfully";
        }
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
    private void querytableSV() {
        Cursor c = database1.query("tblsinhvien",null,null,null,null,null,null);
        c.moveToFirst();
        String data="";
        while (c.isAfterLast()==false) {
            data+=c.getString(0)+"-"+c.getString(1)+"-"+c.getString(2);
            data+="\n";
            c.moveToNext();
        }
        Toast.makeText(this, data, Toast.LENGTH_LONG).show();
        c.close();
    }
}
