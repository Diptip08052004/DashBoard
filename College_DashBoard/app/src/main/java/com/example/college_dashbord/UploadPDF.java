package com.example.college_dashbord;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class UploadPDF extends AppCompatActivity {

    private CardView addpdf;
    private final int REQ=1;
    private Uri pdfdata;
    private EditText pdfTitle;
    private Button uplodpdfBtn;
    private DatabaseReference databaseReference;
    private StorageReference storagereferance;
    String downlodurl="";
    private ProgressDialog pd;
    private TextView pdftextview;
    private String pdfname,title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_pdf);

        pdftextview=findViewById(R.id.pdftextveiw);
        addpdf=findViewById(R.id.addpdf);
        pdfTitle=findViewById(R.id.pdfTitle);
        uplodpdfBtn=findViewById(R.id.uploadpdfBtn);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        storagereferance= FirebaseStorage.getInstance().getReference();
        pd=new ProgressDialog(this);

        uplodpdfBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title=pdfTitle.getText().toString();
                if (title.isEmpty()){
                    pdfTitle.setError("Empty");
                    pdfTitle.requestFocus();
                }else if (pdfdata==null){
                    Toast.makeText(UploadPDF.this, "please upload pdf", Toast.LENGTH_SHORT).show();
                }else {
                    uploadpdf();
                }
            }
        });

        addpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
    }

    private void uploadpdf() {
        pd.setTitle("please wait...");
        pd.setMessage("Uploading pdf");
        pd.show();
        StorageReference reference=storagereferance.child("pdf/"+pdfname+":"+System.currentTimeMillis()+".pdf");
        reference.putFile(pdfdata).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri>uriTask=taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri uri=uriTask.getResult();
                uploadData(String.valueOf(uri));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(UploadPDF.this, "Something went Wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadData(String valueOf) {
        String uniquekey=databaseReference.child("pdf").push().getKey();
        HashMap data=new HashMap();
        data.put("pdfTitle",title);
        data.put("pdfUrl",downlodurl);

        databaseReference.child("pdf").child(uniquekey).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                pd.dismiss();
                Toast.makeText(UploadPDF.this, "pdf uploaded successfully", Toast.LENGTH_SHORT).show();
                pdfTitle.setText("");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(UploadPDF.this, "Fail to upload pdf", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private  void openGallery(){
      Intent intent=new Intent();
      intent.setType("pdf/docs/ppt");////if this is not work then take star here for alphaits
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select pdf file"),REQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQ&&resultCode==RESULT_OK){
            pdfdata=data.getData();
            if (pdfdata.toString().startsWith("content://")){
                Cursor cursor=null;
                try {
                    cursor = UploadPDF.this.getContentResolver().query(pdfdata, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        pdfname = cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME));
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else if (pdfdata.toString().startsWith("file://")){
                pdfname=new File(pdfdata.toString()).getName();
            }
            pdftextview.setText(pdfname);
            Toast.makeText(this, ""+pdfdata, Toast.LENGTH_SHORT).show();
        }
    }
}