package com.example.cardealer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var ref: DatabaseReference//mengambil fitur/library dari database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ref = FirebaseDatabase.getInstance().getReference("Data_Mobil")//path adalah sama sperti tabel

        btnAdd.setOnClickListener{
            savedata()// fungsi save data
        }

        btnShow.setOnClickListener{
            val intent = Intent(this, ActivityList::class.java)// pindah ke activity list
            startActivity(intent)
        }
    }

    private fun savedata() { //fungsi create informasi mobil
        val merk = editMerk.text.toString()
        val tipe = editTipe.text.toString()
        val warna = editWarna.text.toString()
        val kapasitas = editKapasitasMesin.text.toString()
        val kondisi = editKondisi.text.toString()
        val harga = editHarga.text.toString()
        val MobilID = ref.push().key.toString()//push adalah kode auto generate

        val user = Users(MobilID, merk, tipe, warna, kapasitas, kondisi, harga) // harus dibuat class dulu utk buat field pada tabel nanti dan tipe datanya

        ref.child(MobilID).setValue(user).addOnCompleteListener{
            Toast.makeText(this, "Success Add Data", Toast.LENGTH_SHORT).show()//ini code utk di databasenya
            editMerk.setText("")
            editTipe.setText("")
            editWarna.setText("")
            editKapasitasMesin.setText("")
            editKondisi.setText("")
            editHarga.setText("")
        }
    }
}